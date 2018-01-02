package com.dianwoba.middleware.elastic.querydsl;

import com.dianwoba.middleware.elastic.config.EsConfig;
import com.dianwoba.middleware.elastic.querydsl.clause.ElasticsearchDeleteClause;
import com.dianwoba.middleware.elastic.querydsl.clause.ElasticsearchInsertClause;
import com.dianwoba.middleware.elastic.querydsl.clause.ElasticsearchUpdateClause;
import com.dianwoba.middleware.elastic.querydsl.jackson.JacksonElasticsearchQueries;
import java.util.Optional;
import org.elasticsearch.client.Client;

public class ElasticSearchFactory implements
    ElasticsearchDmlAction<ElasticsearchDeleteClause, ElasticsearchInsertClause, ElasticsearchUpdateClause>, ElasticsearchQueryAction {

  private Client client;

  public ElasticSearchFactory(Client client) {
    this.client = client;
  }

  public  ElasticsearchQuery query(String index, String type, Class<?> entityClass) {
   return  query(index, type, null, entityClass);
  }

  @Override
  public  ElasticsearchQuery query(String index, String type,String routing, Class<?> entityClass) {
    JacksonElasticsearchQueries jacksonElasticsearchQueries = new JacksonElasticsearchQueries(
        client);
    return jacksonElasticsearchQueries.query(index, type,  entityClass);
  }

  public ElasticsearchDeleteClause delete(String index,String type, Class<?> clazz) {
    return  delete(index, type, null, clazz);
  }

  @Override
  public ElasticsearchDeleteClause delete(String index,String type,String routing, Class<?> clazz) {
    EsConfig esConfig = new EsConfig(index,type,Optional.ofNullable(routing));
    return new ElasticsearchDeleteClause(client, esConfig, clazz);
  }

  public ElasticsearchInsertClause insert(String index,String type,Class<?> clazz) {
    return insert(index, type, null, clazz);
  }

  @Override
  public ElasticsearchInsertClause insert(String index,String type,String routing, Class<?> clazz) {
    EsConfig esConfig = new EsConfig(index,type,Optional.ofNullable(routing));
    return new ElasticsearchInsertClause(client, esConfig, clazz);
  }

  public ElasticsearchUpdateClause update(String index,String type,Class<?> clazz) {
    return update(index, type, null, clazz);
  }

  @Override
  public ElasticsearchUpdateClause update(String index,String type,String routing, Class<?> clazz) {
    EsConfig esConfig = new EsConfig(index,type,Optional.ofNullable(routing));
    return new ElasticsearchUpdateClause(this.client, esConfig, clazz);
  }


}
