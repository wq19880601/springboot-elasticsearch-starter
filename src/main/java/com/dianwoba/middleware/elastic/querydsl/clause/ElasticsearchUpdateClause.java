package com.dianwoba.middleware.elastic.querydsl.clause;

import com.dianwoba.middleware.elastic.config.EsConfig;
import com.dianwoba.middleware.elastic.config.EsContext;
import com.dianwoba.middleware.elastic.annotations.Id;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.google.common.base.CaseFormat;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.querydsl.core.DefaultQueryMetadata;
import com.querydsl.core.QueryMetadata;
import com.querydsl.core.dml.UpdateClause;
import com.querydsl.core.types.ConstantImpl;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.NullExpression;
import com.querydsl.core.types.Ops;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.PredicateOperation;
import com.querydsl.core.types.dsl.PathBuilder;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.annotation.Nullable;
import org.apache.lucene.queryparser.flexible.core.util.StringUtils;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.get.GetResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

public class ElasticsearchUpdateClause extends
    AbstractElasticsearchClause<ElasticsearchUpdateClause> implements
    UpdateClause<ElasticsearchUpdateClause> {

  private static Logger logger = LoggerFactory.getLogger(ElasticsearchUpdateClause.class);


  private Map<Path<?>, Expression<?>> updates = Maps.newLinkedHashMap();

  private QueryMetadata metadata = new DefaultQueryMetadata();

  private Optional<String> routing = Optional.empty();

  private Map<String, Optional<String>> primaryKeyMap = Maps.newHashMap();

  private EsContext esContext;

  private Class<?> clazz;


  public ElasticsearchUpdateClause(Client client, EsConfig esConfig, Class<?> clazz) {
    this.clazz = clazz;
    esContext = new EsContext();
    esContext.setClient(client);
    esContext.setEsConfig(esConfig);

    esContext.validate();
  }


  @Override
  public ElasticsearchUpdateClause set(List<? extends Path<?>> paths, List<?> values) {
    for (int i = 0; i < paths.size(); i++) {
      if (values.get(i) instanceof Expression) {
        updates.put(paths.get(i), (Expression<?>) values.get(i));
      } else if (values.get(i) != null) {
        updates.put(paths.get(i), ConstantImpl.create(values.get(i)));
      } else {
        updates.put(paths.get(i), NullExpression.DEFAULT);
      }
    }
    return this;
  }


  public ElasticsearchUpdateClause where(Predicate p) {
    metadata.addWhere(p);
    return this;
  }

  @Override
  public ElasticsearchUpdateClause where(Predicate... o) {
    throw new UnsupportedOperationException("mutli where condition, only have one");
  }

  @Override
  public <T> ElasticsearchUpdateClause set(Path<T> path, @Nullable T value) {
    if (value instanceof Expression<?>) {
      updates.put(path, (Expression<?>) value);
    } else if (value != null) {
      updates.put(path, ConstantImpl.create(value));
    } else {
      setNull(path);
    }
    return this;
  }

  @Override
  public <T> ElasticsearchUpdateClause set(Path<T> path, Expression<? extends T> expression) {
    if (expression != null) {
      updates.put(path, expression);
    } else {
      setNull(path);
    }
    return this;
  }

  @Override
  public <T> ElasticsearchUpdateClause setNull(Path<T> path) {
    updates.put(path, NullExpression.DEFAULT);
    return this;
  }

  @Override
  public boolean isEmpty() {
    return false;
  }


  /**
   * Populate the UPDATE clause with the properties of the given bean.
   * The properties need to match the fields of the clause's entity instance.
   * Primary key columns are skipped in the population.
   *
   * @param bean bean to use for population
   * @return the current object
   */
  @SuppressWarnings("unchecked")
  public ElasticsearchUpdateClause populate(Object bean) {
    if (bean == null) {
      return this;
    }

    Class<?> clazz = bean.getClass();
    PathBuilder pathBuilder = new PathBuilder(clazz, clazz.getSimpleName());

    getSetPrimaryInfo(bean, clazz);
    String routingValue = getRouting(clazz, bean);
    routing = Optional.ofNullable(routingValue);

    PropertyDescriptor[] propertyDescriptors = BeanUtils.getPropertyDescriptors(clazz);
    for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
      Class<?> declaringClass = propertyDescriptor.getPropertyType();
      Method method = propertyDescriptor.getReadMethod();
      String name = propertyDescriptor.getName();

      Path<?> path = null;
      if (declaringClass.isAssignableFrom(Date.class)) {
        path = pathBuilder.getDate(name, declaringClass);
      } else if (declaringClass.getSuperclass().isAssignableFrom(Number.class)) {
        path = pathBuilder.getNumber(name, declaringClass);
      } else if (declaringClass.isPrimitive() || declaringClass.isAssignableFrom(String.class)) {
        path = pathBuilder.getString(name);
      } else {
        throw new UnsupportedOperationException(
            "invalid type, not supported, type=" + declaringClass.getSimpleName());
      }
      try {
        Object value = method.invoke(bean, null);
        Preconditions.checkNotNull(path, "invalid path");
        set((Path) path, value);
      } catch (Exception e) {
        throw new RuntimeException("reflect occur error");
      }
    }
    return this;
  }

  private void getSetPrimaryInfo(Object bean, Class<?> clazz) {
    String fieldKey = getFieldKey(clazz, Id.class);
    if (!Strings.isNullOrEmpty(fieldKey)) {
      try {
        PropertyDescriptor propertyDescriptor = BeanUtils.getPropertyDescriptor(clazz, fieldKey);
        Method readMethod = propertyDescriptor.getReadMethod();
        Object idValue = readMethod.invoke(bean, null);
        if (idValue != null) {
          primaryKeyMap.put(fieldKey, Optional.of(String.valueOf(idValue)));
        } else {
          primaryKeyMap.put(fieldKey, Optional.empty());
        }
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }
  }


  @Override
  public void clear() {
    updates = Maps.newLinkedHashMap();
    metadata = new DefaultQueryMetadata();
  }

  @Override
  public long execute() {
    Client client = esContext.getClient();
    EsConfig esConfig = esContext.getEsConfig();

    UpdateRequest updateRequest = new UpdateRequest();
    if (!Strings.isNullOrEmpty(esConfig.getIndex())) {
      updateRequest.index(esConfig.getIndex());
    }

    // internal routing in update config
    routing.ifPresent(x -> {
      updateRequest.routing(x);
    });

    // global routing in class
    esConfig.getRouting().ifPresent(x -> {
      updateRequest.routing(x);
    });

    HashMap<Object, Object> maps = Maps.newHashMap();
    updates.entrySet().stream().filter(x -> {
      Path<?> key = x.getKey();
      return !key.getMetadata().getElement().equals("class");
    }).forEach(x -> {

      Object element = x.getKey().getMetadata().getElement();

      String column = element.toString();
      String underscoreColumn = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, column);

      maps.put(underscoreColumn, x.getValue().toString());
    });

    final ObjectMapper mapper = new ObjectMapper().setPropertyNamingStrategy(
        PropertyNamingStrategy.SNAKE_CASE)
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    mapper.setDateFormat(sf);

    try {
      String body = mapper.writeValueAsString(maps);

      if (primaryKeyMap == null || primaryKeyMap.isEmpty()) {
        Predicate where = metadata.getWhere();
        PredicateOperation wpo = (PredicateOperation) where;
        if (wpo.getOperator() != Ops.EQ) {
          throw new UnsupportedOperationException("must in eq");
        }

        Expression<?> keyArg = wpo.getArg(0);
        Path keyPath = (Path) keyArg;
        String keyPathDesc = keyPath.getMetadata().getElement().toString();

        String primaryId = getFieldKey(clazz, Id.class);

        if (keyArg instanceof Path<?> && !Strings.isNullOrEmpty(primaryId) && keyPathDesc
            .equals(primaryId)) {
          Expression<?> valueExp = wpo.getArg(1);
          String value = StringUtils.toString(valueExp);
          Preconditions.checkArgument(!Strings.isNullOrEmpty(value), "value is null");

          updateRequest.id(value);
        } else {
          throw new UnsupportedOperationException(
              "operator must equal to ==, others now not support");
        }
      } else {
        primaryKeyMap.entrySet().stream().findFirst().ifPresent(x -> {
          Optional<String> value = x.getValue();
          Preconditions.checkArgument(value.isPresent() && !Strings.isNullOrEmpty(value.get()), "id value is null or empty");
          updateRequest.id(value.get());
        });
      }

      updateRequest.type(esConfig.getType());
      updateRequest.doc(body, XContentType.JSON);

      UpdateResponse updateResponse = client.update(updateRequest).get();
      GetResult getResult = updateResponse.getGetResult();

      logger.info("update finish, id={}", updateRequest.id());

      return 0;
    } catch (Exception e) {
      throw new RuntimeException("update occur error, msg=" + e.getMessage());
    }
  }
}
