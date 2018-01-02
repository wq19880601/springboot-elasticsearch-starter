package com.dianwoba.middleware.elastic;

import com.querydsl.core.support.SerializerBase;
import com.querydsl.core.types.SubQueryExpression;
import com.querydsl.core.types.Templates;

public class EsDmlSerializer  extends SerializerBase<EsDmlSerializer> {

  public EsDmlSerializer() {
    super(Templates.DEFAULT);
  }

  @Override
  public Void visit(SubQueryExpression<?> expr, Void context) {
    return null;
  }
}
