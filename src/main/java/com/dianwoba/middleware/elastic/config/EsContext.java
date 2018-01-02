package com.dianwoba.middleware.elastic.config;


import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import org.elasticsearch.client.Client;

public class EsContext {

  private Client client;

  private EsConfig esConfig;

  public EsContext() {
  }

  public EsContext(Client client, EsConfig esConfig) {
    this.client = client;
    this.esConfig = esConfig;
  }

  public Client getClient() {
    return client;
  }

  public void setClient(Client client) {
    this.client = client;
  }

  public EsConfig getEsConfig() {
    return esConfig;
  }

  public void setEsConfig(EsConfig esConfig) {
    this.esConfig = esConfig;
  }


  public void validate() {
    Preconditions.checkNotNull(client, "client is null");
    Preconditions.checkNotNull(esConfig, "es config is null");

    Preconditions.checkState(!Strings.isNullOrEmpty(esConfig.getType()), "type can not be null");
  }
}
