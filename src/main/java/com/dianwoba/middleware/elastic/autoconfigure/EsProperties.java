package com.dianwoba.middleware.elastic.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "elasticsearch.config")
public class EsProperties {

  private String clusterName;

  private String clusterAddress;

  public String getClusterName() {
    return clusterName;
  }

  public void setClusterName(String clusterName) {
    this.clusterName = clusterName;
  }

  public String getClusterAddress() {
    return clusterAddress;
  }

  public void setClusterAddress(String clusterAddress) {
    this.clusterAddress = clusterAddress;
  }
}
