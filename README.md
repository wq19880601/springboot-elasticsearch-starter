# springboot elasticsearch starter

> 鉴于elasticsearch的api使用太过复杂繁琐，使用开源的querydsl做了下包装，使用起来更简单


主要针对elsticserach 版本5.3.0

## jar dependency

```


<dependency>
  <groupId>com.dianwoba.middleware</groupId>
  <artifactId>springboot-elasticsearch-starter</artifactId>
  <version>0.0.1-SNAPSHOT</version>
</dependency>

```

## usage

### 生成操作辅助类

* 需要生成相关辅助类，在过滤数据的时候特别有用，需要类上边加上相关注解才能生成数据

```
@QueryEntity
@QuerySupertype
public class BalanceLog implements Serializable {
......
}
```
```
   GenericExporter exporter = new GenericExporter();
    exporter.setEntityAnnotation(QueryEntity.class);
    exporter.setSupertypeAnnotation(QuerySupertype.class);
    exporter.setSkipAnnotation(Transient.class);
    // 指定文件生成的位置
    exporter.setTargetFolder(new File("/Users/walis/work/dwb/rider-trade-witness-service/springboot-elasticsearch-starter/src/test/java"));
    exporter.export(BalanceLog.class.getPackage());
```

* 需要指定id字段

需要有个字段作为es的唯一id才能使用，标注方式@Id


```
  @QueryEntity
@QuerySupertype
public class BalanceLog implements Serializable {

    @Id
    private Long id;

```


### 常用操作


* query

```
public static final QBalanceLog balance_log = QBalanceLog.balanceLog;
```


// 查询所有数据

```
elasticSearchFactory
        .query("balance_log-201711", "balance_log", BalanceLog.class).where(balance_log.riderId.eq(1000)).fetch();
```

// 查询一条数据

```
elasticSearchFactory
        .query("balance_log-201711", "balance_log", BalanceLog.class).where(balance_log.riderId.eq(1000)).fetchFirst();
```

// in 查询

```
query()
        .where(balance_log.riderId.eq(92941).and(balance_log.type.in(36,37,50)))
```

// 不等于

```
query().where(balance_log.riderId.eq(92941).and(balance_log.type.ne((byte)36)))
        .fetch
```

// is null, not null

```
query()
        .where(balance_log.riderId.eq(92941).and(balance_log.finishTm.isNull())).fetch()
```

```
query()
        .where(balance_log.riderId.eq(92941).and(balance_log.finishTm.isNotNull())).fetch()
```


// 组合查询

```
query().where(balance_log.riderId.eq(92941)
        .and(balance_log.type.eq((byte) 36).or(balance_log.name.startsWith("购买意外险")))).fetch()
```

// 总数

```
query().fetchCount()
```

* update

// 更新部分字段

```
elasticSearchQueryFactory.update("balance_log-201706","balance_log", "162", BalanceLog.class).set(b.name,"sb,haha").set(b.cost,new BigDecimal("20")).where(
        balance_log.id.eq(9506L))
        .execute()
```

// 更新对象

```
    BalanceLog balanceLog = new BalanceLog();
    balanceLog.setName("21311");
    long execute = elasticSearchQueryFactory.update("balance_log-201706","balance_log", "162", BalanceLog.class).populate(balanceLog).where(
        balance_log.id.eq(12313L))
        .execute();
```

* 删除

// 删除数据

```
    DateTime start = DateTime.parse("2017-12-29 14:52:58", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss"));
    DateTime end = DateTime.parse("2017-12-29 14:52:58", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss"));

    QBalanceLog  b = QBalanceLog.balanceLog;
    long execute = elasticSearchQueryFactory
        .delete("balance_log-201711", "balance_log", "1", BalanceLog.class).where(
            b.insTm.goe(start.toDate()).and(b.insTm.loe(end.toDate())));
```

* 插入

// 支持批量插入，单个插入，populate是list的话是批量插入，是单个对象的话是单条插入
```
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
```

