package com.dianwoba.middleware.elastic.querydsl;

public interface ElasticsearchQueryAction {

  public  ElasticsearchQuery query(String index, String type,String routing, Class<?> entityClass);

}
