package com.dianwoba.middleware.springbootelasticsearchstarter;

import com.dianwoba.middleware.springbootelasticsearchstarter.domain.BalanceLog;
import com.querydsl.codegen.GenericExporter;
import com.querydsl.core.annotations.QueryEntity;
import com.querydsl.core.annotations.QuerySupertype;
import java.beans.Transient;
import java.io.File;

public class CodeGen {

  public static  void main(String[] args){
    GenericExporter exporter = new GenericExporter();
    exporter.setEntityAnnotation(QueryEntity.class);
    exporter.setSupertypeAnnotation(QuerySupertype.class);
    exporter.setSkipAnnotation(Transient.class);
    exporter.setTargetFolder(new File("/Users/walis/work/dwb/rider-trade-witness-service/springboot-elasticsearch-starter/src/test/java"));
    exporter.export(BalanceLog.class.getPackage());
  }

}
