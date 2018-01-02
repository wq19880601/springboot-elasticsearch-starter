package com.dianwoba.middleware.elastic.autoconfigure;

import com.dianwoba.middleware.elastic.querydsl.ElasticSearchFactory;
import com.dianwoba.middleware.elastic.querydsl.jackson.JacksonElasticsearchQueries;
import com.dianwoba.middleware.elastic.util.EsAddressParser;
import com.google.common.collect.ImmutableMap;
import com.querydsl.core.SimpleQuery;
import java.net.InetAddress;
import java.util.List;
import java.util.stream.Collectors;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(EsProperties.class)
@ConditionalOnClass({TransportClient.class, SimpleQuery.class})
public class EsAutoConfiguration {

  @Bean
  public TransportClient createTransportClient(EsProperties esProperties) {
    ImmutableMap<String, Integer> clusterAddressMap = EsAddressParser
        .parseToHostAndPort(esProperties.getClusterAddress());
    Settings settings = Settings.builder().put("cluster.name", esProperties.getClusterName()).put("client.transport.sniff",true)
        .build();

    List<InetSocketTransportAddress> addresses = clusterAddressMap.entrySet().stream()
        .map(entry -> {
          try {
            return new InetSocketTransportAddress(InetAddress.getByName(entry.getKey()),
                entry.getValue());
          } catch (Exception e) {
            throw new RuntimeException("elastic addreess parse occur error");
          }
        }).collect(Collectors.toList());

    InetSocketTransportAddress[] inetSocketTransportAddresses = addresses
        .toArray(new InetSocketTransportAddress[addresses.size()]);
    return new PreBuiltTransportClient(settings)
        .addTransportAddresses(inetSocketTransportAddresses);
  }

  @Bean
  @ConditionalOnBean(Client.class)
  public ElasticSearchFactory createEsSeachFactory(Client client) {
    return new ElasticSearchFactory(client);
  }
}
