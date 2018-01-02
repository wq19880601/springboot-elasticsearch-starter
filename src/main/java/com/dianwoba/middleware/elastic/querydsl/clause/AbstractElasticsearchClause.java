package com.dianwoba.middleware.elastic.querydsl.clause;

import com.dianwoba.middleware.elastic.EsDmlSerializer;
import com.dianwoba.middleware.elastic.annotations.Routing;
import com.dianwoba.middleware.elastic.visitor.ElasticsearchSerializer;
import com.google.common.base.Strings;
import com.querydsl.core.dml.DMLClause;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.beans.BeanUtils;

public abstract class AbstractElasticsearchClause<C extends AbstractElasticsearchClause<C>> implements DMLClause<C> {

  /**
   * Clear the internal state of the clause
   */
  public abstract void clear();



  protected EsDmlSerializer createCommonSerializer() {
    EsDmlSerializer serializer = new EsDmlSerializer();
    return serializer;
  }

  protected  ElasticsearchSerializer createElasticsearchSerializer(){
    ElasticsearchSerializer elasticsearchSerializer = new ElasticsearchSerializer();
    return elasticsearchSerializer;
  }



  protected String getFieldKey(Class<?> clazz, Class<? extends Annotation> annoClazz) {
    Field[] allFields = FieldUtils.getAllFields(clazz);
    for (Field allField : allFields) {
      if (allField.getAnnotation(annoClazz) != null) {
        return allField.getName();
      }
    }
    return null;
  }

  protected String getRouting(Class clazz,Object bean){
    try {
      String routingKey = getFieldKey(clazz, Routing.class);
      if (!Strings.isNullOrEmpty(routingKey)) {
        PropertyDescriptor routingPd = BeanUtils
            .getPropertyDescriptor(clazz, routingKey);
        Object routingId = routingPd.getReadMethod().invoke(bean, null);
        if (routingId != null) {
          return String.valueOf(routingId);
        }
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    return null;
  }

}
