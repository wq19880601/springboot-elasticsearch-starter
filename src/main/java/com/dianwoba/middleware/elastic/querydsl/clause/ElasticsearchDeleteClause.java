package com.dianwoba.middleware.elastic.querydsl.clause;

import com.dianwoba.middleware.elastic.config.EsConfig;
import com.dianwoba.middleware.elastic.config.EsContext;
import com.dianwoba.middleware.elastic.annotations.Id;
import com.dianwoba.middleware.elastic.visitor.ElasticsearchSerializer;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.querydsl.core.DefaultQueryMetadata;
import com.querydsl.core.QueryMetadata;
import com.querydsl.core.dml.DeleteClause;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Ops;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.PredicateOperation;
import org.apache.lucene.queryparser.flexible.core.util.StringUtils;
import org.elasticsearch.action.bulk.byscroll.BulkByScrollResponse;
import org.elasticsearch.action.delete.DeleteRequestBuilder;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.reindex.DeleteByQueryAction;
import org.elasticsearch.index.reindex.DeleteByQueryRequestBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ElasticsearchDeleteClause extends
    AbstractElasticsearchClause<ElasticsearchDeleteClause> implements
    DeleteClause<ElasticsearchDeleteClause> {

  private static Logger logger = LoggerFactory.getLogger(ElasticsearchDeleteClause.class);

  private QueryMetadata metadata = new DefaultQueryMetadata();

  private EsContext esContext;


  private Class<?> clazz;

  public ElasticsearchDeleteClause(Client client, EsConfig esConfig, Class<?> clazz) {
    this.clazz = clazz;

    esContext = new EsContext();
    esContext.setClient(client);
    esContext.setEsConfig(esConfig);

    esContext.validate();
  }

  @Override
  public ElasticsearchDeleteClause where(Predicate... predicates) {
    for (Predicate predicate : predicates) {
      metadata.addWhere(predicate);
    }
    return this;
  }

  public ElasticsearchDeleteClause where(Predicate p) {
    metadata.addWhere(p);
    return this;
  }

  @Override
  public void clear() {
  }

  @Override
  public long execute() {
    Predicate where = metadata.getWhere();

    Client client = esContext.getClient();

    EsConfig esConfig = esContext.getEsConfig();

    PredicateOperation wpo = (PredicateOperation) where;
    if (wpo.getOperator() != Ops.EQ) {
      QueryBuilder queryBuilder = getQueryBuilder(where);
      deleteByQuery(queryBuilder, client, esConfig);
    } else {
      Expression<?> keyArg = wpo.getArg(0);
      Path keyPath = (Path) keyArg;
      String keyPathDesc = keyPath.getMetadata().getElement().toString();

      String primaryId = getFieldKey(clazz, Id.class);

      if (keyArg instanceof Path<?> && !Strings.isNullOrEmpty(primaryId) && keyPathDesc
          .equals(primaryId)) {
        Expression<?> valueExp = wpo.getArg(1);
        String value = StringUtils.toString(valueExp);
        Preconditions.checkArgument(!Strings.isNullOrEmpty(value), "value is null");

        DeleteRequestBuilder deleteRequestBuilder = client.prepareDelete();
        deleteRequestBuilder.setIndex(esConfig.getIndex());
        deleteRequestBuilder.setType(esConfig.getType());
        deleteRequestBuilder.setId(value);
        esConfig.getRouting().ifPresent(x -> {
          deleteRequestBuilder.setRouting(x);
        });

        try {
          DeleteResponse deleteResponse = deleteRequestBuilder.get();
          logger.info("delete by id success, msg={}", deleteResponse.toString());
        } catch (Exception e) {
          logger.error("delete occur error", e);
        }
      } else {
        QueryBuilder queryBuilder = getQueryBuilder(where);
        deleteByQuery(queryBuilder, client, esConfig);
      }
    }

    return 0;
  }

  private void deleteByQuery(QueryBuilder queryBuilder, Client client, EsConfig esConfig) {
    DeleteByQueryRequestBuilder deleteByQueryRequestBuilder = DeleteByQueryAction.INSTANCE
        .newRequestBuilder(client);
    deleteByQueryRequestBuilder.setMaxRetries(3);
    deleteByQueryRequestBuilder.setRetryBackoffInitialTime(TimeValue.timeValueMillis(30));
    BulkByScrollResponse bulkByScrollResponse = deleteByQueryRequestBuilder.filter(queryBuilder)
        .source(esConfig.getIndex()).get();

    long totalDeleted = bulkByScrollResponse.getDeleted();
    logger.info("delete success, total={}", totalDeleted);
  }

  private QueryBuilder getQueryBuilder(Predicate where) {
    ElasticsearchSerializer elasticsearchSerializer = createElasticsearchSerializer();
    QueryBuilder queryBuilder = (QueryBuilder) elasticsearchSerializer.handle(where);

    return queryBuilder;
  }
}
