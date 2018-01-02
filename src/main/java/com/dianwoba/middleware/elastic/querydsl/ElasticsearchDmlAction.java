package com.dianwoba.middleware.elastic.querydsl;

import com.dianwoba.middleware.elastic.querydsl.clause.ElasticsearchDeleteClause;
import com.dianwoba.middleware.elastic.querydsl.clause.ElasticsearchInsertClause;
import com.dianwoba.middleware.elastic.querydsl.clause.ElasticsearchUpdateClause;
import com.querydsl.core.types.EntityPath;
import java.util.Optional;

public interface ElasticsearchDmlAction<D extends ElasticsearchDeleteClause, I extends ElasticsearchInsertClause, U extends ElasticsearchUpdateClause>  {

  D delete(String index,String type,String routing, Class<?> clazz);

  I insert(String index,String type,String routing, Class<?> clazz);

  U update(String index,String type,String  routing, Class<?> clazz);

}