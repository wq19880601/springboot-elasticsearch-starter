package com.dianwoba.middleware.elastic.config;

import java.util.Optional;

public class EsConfig {

  private String index;

  private String type;

  private Optional<String> routing = Optional.empty();

  public EsConfig() {
  }

  public EsConfig(String index, String type, Optional<String> routing) {
    this.index = index;
    this.type = type;
    this.routing = routing;
  }

  public EsConfig index(String index) {
    this.index = index;
   return this;
  }

  public EsConfig type(String type) {
    this.type = type;

    return this;
  }

  public  EsConfig rouing(String routing) {
    this.routing = Optional.ofNullable(routing);
    return this;
  }

  public String getIndex() {
    return index;
  }

  public void setIndex(String index) {
    this.index = index;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public Optional<String> getRouting() {
    return routing;
  }

  public void setRouting(Optional<String> routing) {
    this.routing = routing;
  }
}

