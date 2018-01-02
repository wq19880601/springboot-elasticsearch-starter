package com.dianwoba.middleware.springbootelasticsearchstarter.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QBalanceLog is a Querydsl query type for BalanceLog
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QBalanceLog extends EntityPathBase<BalanceLog> {

    private static final long serialVersionUID = 720736281L;

    public static final QBalanceLog balanceLog = new QBalanceLog("balanceLog");

    public final StringPath account = createString("account");

    public final StringPath bankCard = createString("bankCard");


    public final NumberPath<Byte> bankCardType = createNumber("bankCardType", Byte.class);

    public final NumberPath<Byte> bankName = createNumber("bankName", Byte.class);

    public final StringPath batchNo = createString("batchNo");

    public final NumberPath<Long> batchRecordId = createNumber("batchRecordId", Long.class);

    public final NumberPath<Byte> blocked = createNumber("blocked", Byte.class);

    public final NumberPath<Integer> cityId = createNumber("cityId", Integer.class);

    public final NumberPath<java.math.BigDecimal> cost = createNumber("cost", java.math.BigDecimal.class);

    public final NumberPath<Byte> currentServiceType = createNumber("currentServiceType", Byte.class);

    public final NumberPath<java.math.BigDecimal> factorage = createNumber("factorage", java.math.BigDecimal.class);

    public final DateTimePath<java.util.Date> finishTm = createDateTime("finishTm", java.util.Date.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.util.Date> insTm = createDateTime("insTm", java.util.Date.class);

    public final StringPath name = createString("name");

    public final NumberPath<Byte> paid = createNumber("paid", Byte.class);

    public final NumberPath<Byte> payType = createNumber("payType", Byte.class);

    public final StringPath reason = createString("reason");

    public final NumberPath<Integer> riderId = createNumber("riderId", Integer.class);

    public final NumberPath<Byte> riskChecked = createNumber("riskChecked", Byte.class);

    public final NumberPath<Integer> shardx = createNumber("shardx", Integer.class);

    public final StringPath tradeNo = createString("tradeNo");

    public final NumberPath<Byte> tradeWay = createNumber("tradeWay", Byte.class);

    public final NumberPath<Byte> type = createNumber("type", Byte.class);

    public final DateTimePath<java.util.Date> verifyTm = createDateTime("verifyTm", java.util.Date.class);

    public final DateTimePath<java.util.Date> withdrawTm = createDateTime("withdrawTm", java.util.Date.class);

    public QBalanceLog(String variable) {
        super(BalanceLog.class, forVariable(variable));
    }

    public QBalanceLog(Path<? extends BalanceLog> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBalanceLog(PathMetadata metadata) {
        super(BalanceLog.class, metadata);
    }

}

