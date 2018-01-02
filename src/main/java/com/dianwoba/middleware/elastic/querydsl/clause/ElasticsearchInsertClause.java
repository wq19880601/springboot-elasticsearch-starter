package com.dianwoba.middleware.elastic.querydsl.clause;

import com.dianwoba.middleware.elastic.config.EsConfig;
import com.dianwoba.middleware.elastic.config.EsContext;
import com.dianwoba.middleware.elastic.annotations.Id;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.querydsl.core.dml.InsertClause;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.SubQueryExpression;
import java.beans.PropertyDescriptor;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nullable;
import org.elasticsearch.action.bulk.BackoffPolicy;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.bulk.BulkProcessor.Listener;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

public class ElasticsearchInsertClause extends
    AbstractElasticsearchClause<ElasticsearchInsertClause> implements
    InsertClause<ElasticsearchInsertClause> {

  private static Logger logger = LoggerFactory.getLogger(ElasticsearchInsertClause.class);


  private List<Object> objs = Lists.newLinkedList();


  private EsContext esContext;

  private Class<?> clazz;

  private boolean ignoreNull = false;

  public ElasticsearchInsertClause(Client client, EsConfig esConfig, Class<?> clazz) {
    this.clazz = clazz;

    esContext = new EsContext();
    esContext.setClient(client);
    esContext.setEsConfig(esConfig);

    esContext.validate();
  }

  public ElasticsearchInsertClause populate(Object bean) {
    return populate(bean, true);
  }

  @SuppressWarnings("unchecked")
  public ElasticsearchInsertClause populate(Object bean, boolean ignoreNull) {
    if (bean == null) {
      return this;
    }

    ignoreNull = true;

    if(bean instanceof  List){
      List<?> beans = (List) bean;
      objs.addAll(beans);
    }else {
      objs.add(bean);
    }

    return this;
  }

  @Override
  public ElasticsearchInsertClause columns(Path<?>... paths) {
    throw new UnsupportedOperationException("not support");
  }

  @Override
  public ElasticsearchInsertClause select(SubQueryExpression<?> subQueryExpression) {
    throw new UnsupportedOperationException("not support");
  }

  @Override
  public ElasticsearchInsertClause values(Object... objects) {
    throw new UnsupportedOperationException("not support");
  }

  @Override
  public <T> ElasticsearchInsertClause set(Path<T> path, @Nullable T value) {
    throw new UnsupportedOperationException("not support");
  }

  @Override
  public <T> ElasticsearchInsertClause set(Path<T> path, Expression<? extends T> expression) {
    throw new UnsupportedOperationException("not support");
  }

  @Override
  public <T> ElasticsearchInsertClause setNull(Path<T> path) {
    throw new UnsupportedOperationException("not support");
  }

  @Override
  public boolean isEmpty() {
    return false;
  }

  @Override
  public void clear() {

  }

  @Override
  public long execute() {
    Client client = esContext.getClient();
    EsConfig esConfig = esContext.getEsConfig();

    BulkProcessor build = BulkProcessor.builder(client, new Listener() {
      @Override
      public void beforeBulk(long executionId, BulkRequest request) {
        logger.info("batch start,req={}", request);
      }

      @Override
      public void afterBulk(long executionId, BulkRequest request, BulkResponse response) {
        String s = response.buildFailureMessage();
        if(response.hasFailures()){
          logger.error("bulk occur error,request={} ,error_msg={}", request,response.buildFailureMessage() );
        }else {
          logger.info("batch finish success, response={}", response);
        }
      }

      @Override
      public void afterBulk(long executionId, BulkRequest request, Throwable failure) {
        logger.error("batch occur error", failure);
      }
    }).setBackoffPolicy(BackoffPolicy.exponentialBackoff(TimeValue.timeValueMillis(10), 3))
        .setBulkActions(100).setConcurrentRequests(2).setFlushInterval(TimeValue.timeValueMillis(5)). build();

    final ObjectMapper mapper = new ObjectMapper().setPropertyNamingStrategy(
        PropertyNamingStrategy.SNAKE_CASE)
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    mapper.setDateFormat(sf);
    if(ignoreNull){
      mapper.setSerializationInclusion(Include.NON_NULL);
    }

    for (Object obj : objs) {
      Class<?> clazz = obj.getClass();

      String primaryKeyField = getFieldKey(clazz, Id.class);
      Preconditions
          .checkState(!Strings.isNullOrEmpty(primaryKeyField), "must have a primary column");

      PropertyDescriptor propertyDescriptor = BeanUtils
          .getPropertyDescriptor(clazz, primaryKeyField);

      try {
        Object primaryId = propertyDescriptor.getReadMethod().invoke(obj, null);
        Preconditions.checkNotNull(primaryId);

        IndexRequest indexRequest = new IndexRequest(esConfig.getIndex(), esConfig.getType(),
            String.valueOf(primaryId));
        String routing = getRouting(clazz, obj);
        Optional.ofNullable(routing).ifPresent(xx -> {
          indexRequest.routing(xx);
        });

        String doc = mapper.writeValueAsString(obj);
        indexRequest.source(doc, XContentType.JSON);

        UpdateRequest updateRequest = new UpdateRequest(esConfig.getIndex(), esConfig.getType(),
            String.valueOf(primaryId));
        updateRequest.doc(doc, XContentType.JSON);
        Optional.ofNullable(routing).ifPresent(xx -> {
          updateRequest.routing(xx).upsert(indexRequest);
        });

        build.add(updateRequest);
      } catch (Exception e) {
        logger.error("insert occur error", e);
        throw new RuntimeException(e);
      }

    }

    return 0;
  }


}
