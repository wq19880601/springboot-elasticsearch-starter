package com.dianwoba.middleware.springbootelasticsearchstarter;

import com.dianwoba.middleware.elastic.config.EsConfig;
import com.dianwoba.middleware.elastic.autoconfigure.EsAutoConfiguration;
import com.dianwoba.middleware.elastic.querydsl.ElasticSearchFactory;
import com.dianwoba.middleware.elastic.querydsl.ElasticsearchQuery;
import com.dianwoba.middleware.springbootelasticsearchstarter.domain.BalanceLog;
import com.dianwoba.middleware.springbootelasticsearchstarter.domain.QBalanceLog;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.get.GetResult;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.autoconfigure.context.PropertyPlaceholderAutoConfiguration;
import org.springframework.boot.test.util.EnvironmentTestUtils;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SpringbootElasticsearchStarterApplicationTests {


  public static final QBalanceLog balance_log = QBalanceLog.balanceLog;
  private final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();

  @Before
  public void init() {
    this.context.register(EsAutoConfiguration.class,
        PropertyPlaceholderAutoConfiguration.class);
    EnvironmentTestUtils.addEnvironment(this.context,
        "elasticsearch.config.clusterName:balance-es-trade",
        "elasticsearch.config.clusterAddress:192.168.11.53:9300,192.168.11.46:9300");

    this.context.refresh();
  }


  @Test
  public void queryAll() {
    List<BalanceLog> fetch = query().where(balance_log.riderId.eq(1000)).fetch();
    Assert.assertNotNull(fetch);
//
  }

  @Test
  public void fetchOne(){
    BalanceLog balanceLog = query().where(balance_log.riderId.eq(1000)).fetchFirst();
    Assert.assertNotNull(balanceLog);

    balanceLog = query().where(balance_log.id.eq(115096749L)).fetchOne();
    Assert.assertNotNull(balanceLog);
  }

  @Test
  public void test() {
    List<BalanceLog> fetch = query().orderBy(balance_log.insTm.desc()).limit(1).fetch();
    Assert.assertNotNull(fetch);
    Assert.assertEquals(fetch.size(),1);
  }

  @Test
  public void testIn(){
    List<BalanceLog> fetch = query()
        .where(balance_log.riderId.eq(92941).and(balance_log.type.in(36,37,50))).fetch();
    Assert.assertNotNull(fetch);
    Assert.assertEquals(fetch.size(),426);
  }

  @Test
  public void testOrEqualto() {
    List<BalanceLog> fetch = query().where(balance_log.riderId.eq(92941).and(balance_log.type.ne((byte)36)))
        .fetch();
    Assert.assertEquals(fetch.size(), 385);
  }

  @Test
  public  void testIsNull() {
    List<BalanceLog> fetch = query()
        .where(balance_log.riderId.eq(92941).and(balance_log.finishTm.isNull())).fetch();
    Assert.assertEquals(fetch.size(),426);


    fetch = query()
        .where(balance_log.riderId.eq(92941).and(balance_log.finishTm.isNotNull())).fetch();
    Assert.assertEquals(fetch.size(),0);
  }

  @Test
  public void testRange() {
    List<BalanceLog> balanceLogs = query().where(balance_log.riderId.eq(92941)
        .and(balance_log.type.eq((byte) 36).or(balance_log.name.startsWith("购买意外险")))).fetch();
    Assert.assertEquals(balanceLogs.size(),386);
  }


  @Test
  public void testCount(){
    long fetch = query().fetchCount();
    Assert.assertNotNull(fetch);
    Assert.assertEquals(fetch,500);
  }


  @Test
  public void testHaa() {
    ElasticSearchFactory elasticSearchQueryFactory = this.context.getBean(ElasticSearchFactory.class);

//    QBalanceLog  b = QBalanceLog.balanceLog;
//    BalanceLog balanceLog = new BalanceLog();
//    balanceLog.setName("21311");
//    long execute = elasticSearchQueryFactory.update("balance_log-201706","balance_log", "162", BalanceLog.class).populate(balanceLog).where(
//        balance_log.id.eq(12313L))
//        .execute();

//    Assert.assertTrue(execute > 0);


    QBalanceLog  b = QBalanceLog.balanceLog;
    BalanceLog balanceLog = new BalanceLog();
    balanceLog.setName("21311");
    long execute = elasticSearchQueryFactory.update("balance_log-201706","balance_log", "162", BalanceLog.class).set(b.name,"sb,haha").set(b.cost,new BigDecimal("20")).where(
        balance_log.id.eq(9506L))
        .execute();

    Assert.assertTrue(execute > 0);

  }

  @Test
  public  void testAa() throws ExecutionException, InterruptedException, JsonProcessingException {
    TransportClient transportClient = this.context.getBean(TransportClient.class);
    ElasticSearchFactory elasticSearchQueryFactory = this.context.getBean(ElasticSearchFactory.class);
    EsConfig esConfig = new EsConfig();
    esConfig.setIndex("balance_log-201706");
    esConfig.setType("balance_log");

    UpdateRequest updateRequest = new UpdateRequest(esConfig.getIndex(), esConfig.getType(),
        "9506");
    updateRequest.routing("162");


    final ObjectMapper mapper = new ObjectMapper().setPropertyNamingStrategy(
        PropertyNamingStrategy.SNAKE_CASE);
    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    mapper.setDateFormat(sf);

//    HashMap<String, Object> masp = Maps.newHashMap();
//    masp.put("reason", "haha");
//    masp.put("name", "haha");
//    masp.put("type", 60);

    BalanceLog balanceLog = new BalanceLog();
    balanceLog.setReason("gg");
    balanceLog.setName("gg");
    balanceLog.setType((byte)50);
    balanceLog.setId(9506L);
    String source = mapper.writeValueAsString(balanceLog);
    updateRequest.doc(source, XContentType.JSON);


    UpdateResponse updateResponse = transportClient.update(updateRequest).get();
    GetResult getResult = updateResponse.getGetResult();

    SearchRequestBuilder getRequestBuilder = transportClient
        .prepareSearch();
//    getRequestBuilder.setIndices(esConfig.getIndex());
//    getRequestBuilder.setTypes(esConfig.getType());
//    getRequestBuilder.setQuery(QueryBuilders.idsQuery().addIds("9506"));
//
//    SearchResponse getFields = getRequestBuilder.get();
//    SearchHits hits = getFields.getHits();

//    GetRequestBuilder getRequestBuilder = transportClient
//        .prepareGet(esConfig.getIndex(), esConfig.getType(), "9506");
//    getRequestBuilder.setRouting("162");
//    GetResponse getFields = getRequestBuilder.get();
//    String sourceAsString = getFields.getSourceAsString();
//    sourceAsString.toString();
  }


  @Test
  public void testDelete() {
    TransportClient transportClient = this.context.getBean(TransportClient.class);
    ElasticSearchFactory elasticSearchQueryFactory = this.context.getBean(ElasticSearchFactory.class);
    EsConfig esConfig = new EsConfig();
    esConfig.setIndex("balance_log-201711");
    esConfig.setType("balance_log");

    DateTime start = DateTime.parse("2017-12-29 14:52:58", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss"));
    DateTime end = DateTime.parse("2017-12-29 14:52:58", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss"));

    QBalanceLog  b = QBalanceLog.balanceLog;
//    long execute = elasticSearchQueryFactory
//        .delete("balance_log-201711", "balance_log", "1", BalanceLog.class).where(
//            b.insTm.goe(start.toDate()).and(b.insTm.loe(end.toDate())));
//    b.id.in(9222222222211L, 9222222222212L).execute();

//    Assert.assertTrue(execute > 0);

//    long execute1 = elasticSearchQueryFactory.delete("balance_log-201711","balance_log").where(b.type.eq((byte) 30))
//        .execute();
//    Assert.assertTrue(execute1 == 2);
  }



  @Test
  public void testInsert() throws InterruptedException {
    TransportClient transportClient = this.context.getBean(TransportClient.class);
    ElasticSearchFactory elasticSearchQueryFactory = this.context.getBean(ElasticSearchFactory.class);
    EsConfig esConfig = new EsConfig();
    esConfig.setIndex("balance_log-201711");
    esConfig.setType("balance_log");

    List<BalanceLog> balanceLogs = IntStream.range(11, 19).mapToObj(i -> {
      BalanceLog balanceLog = new BalanceLog();
      balanceLog.setName("1223112");
      balanceLog.setId(Long.valueOf(92222222222L + "" + i));
      balanceLog.setCityId(2);
      balanceLog.setRiderId(1);
      balanceLog.setTradeWay((byte) 2);
      balanceLog.setType((byte) 10);
      balanceLog.setTradeNo("201709252202130cc");
      balanceLog.setPaid((byte) 10);
      balanceLog.setInsTm(new Date());
      balanceLog.setName("withdraw syn coabrc test");
      balanceLog.setCost(new BigDecimal("-100"));
      balanceLog.setCurrentServiceType((byte) 0);
      balanceLog.setReason("hello, withdarw syn test");

      return balanceLog;
    }).collect(Collectors.toList());

    long execute = elasticSearchQueryFactory.insert("balance_log-201711", "balance_log", BalanceLog.class).populate(balanceLogs).execute();
    TimeUnit.HOURS.sleep(1);


  }
  private ElasticsearchQuery<BalanceLog> query() {
    ElasticSearchFactory elasticSearchFactory = this.context.getBean(ElasticSearchFactory.class);
    ElasticsearchQuery elasticsearchQuery = elasticSearchFactory
        .query("balance_log-201711", "balance_log", BalanceLog.class);

    return elasticsearchQuery;
  }


}
