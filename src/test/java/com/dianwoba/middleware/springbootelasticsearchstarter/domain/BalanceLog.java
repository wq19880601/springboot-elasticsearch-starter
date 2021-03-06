package com.dianwoba.middleware.springbootelasticsearchstarter.domain;

import com.dianwoba.middleware.elastic.annotations.Id;
import com.dianwoba.middleware.elastic.annotations.Routing;
import com.querydsl.core.annotations.QueryEntity;
import com.querydsl.core.annotations.QuerySupertype;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.jdo.annotations.PrimaryKey;

@QueryEntity
@QuerySupertype
public class BalanceLog implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column balance_log.id
     *
     * @mbg.generated Sat Apr 15 16:34:36 CST 2017
     */
    @Id
    private Long id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column balance_log.city_id
     *
     * @mbg.generated Sat Apr 15 16:34:36 CST 2017
     */
    private Integer cityId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column balance_log.rider_id
     *
     * @mbg.generated Sat Apr 15 16:34:36 CST 2017
     */
    @Routing
    private Integer riderId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column balance_log.type
     *
     * @mbg.generated Sat Apr 15 16:34:36 CST 2017
     */
    private Byte type;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column balance_log.blocked
     *
     * @mbg.generated Sat Apr 15 16:34:36 CST 2017
     */
    private Byte blocked;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column balance_log.account
     *
     * @mbg.generated Sat Apr 15 16:34:36 CST 2017
     */
    private String account;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column balance_log.paid
     *
     * @mbg.generated Sat Apr 15 16:34:36 CST 2017
     */
    private Byte paid;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column balance_log.name
     *
     * @mbg.generated Sat Apr 15 16:34:36 CST 2017
     */
    private String name;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column balance_log.cost
     *
     * @mbg.generated Sat Apr 15 16:34:36 CST 2017
     */
    private BigDecimal cost;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column balance_log.factorage
     *
     * @mbg.generated Sat Apr 15 16:34:36 CST 2017
     */
    private BigDecimal factorage;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column balance_log.ins_tm
     *
     * @mbg.generated Sat Apr 15 16:34:36 CST 2017
     */
    private Date insTm;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column balance_log.verify_tm
     *
     * @mbg.generated Sat Apr 15 16:34:36 CST 2017
     */
    private Date verifyTm;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column balance_log.finish_tm
     *
     * @mbg.generated Sat Apr 15 16:34:36 CST 2017
     */
    private Date finishTm;
    /**
     * withdraw time
     */
    private Date withdrawTm;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column balance_log.reason
     *
     * @mbg.generated Sat Apr 15 16:34:36 CST 2017
     */
    private String reason;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column balance_log.trade_no
     *
     * @mbg.generated Sat Apr 15 16:34:36 CST 2017
     */
    private String tradeNo;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column balance_log.trade_way
     *
     * @mbg.generated Sat Apr 15 16:34:36 CST 2017
     */
    private Byte tradeWay;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column balance_log.risk_checked
     *
     * @mbg.generated Sat Apr 15 16:34:36 CST 2017
     */
    private Byte riskChecked;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column balance_log.current_service_type
     *
     * @mbg.generated Sat Apr 15 16:34:36 CST 2017
     */
    private Byte currentServiceType;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column balance_log.bank_name
     *
     * @mbg.generated Sat Apr 15 16:34:36 CST 2017
     */
    private Byte bankName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column balance_log.bank_card
     *
     * @mbg.generated Sat Apr 15 16:34:36 CST 2017
     */
    private String bankCard;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column balance_log.bank_card_type
     *
     * @mbg.generated Sat Apr 15 16:34:36 CST 2017
     */
    private Byte bankCardType;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column balance_log.pay_type
     *
     * @mbg.generated Sat Apr 15 16:34:36 CST 2017
     */
    private Byte payType;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column balance_log.batch_record_id
     *
     * @mbg.generated Sat Apr 15 16:34:36 CST 2017
     */
    private Long batchRecordId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column balance_log.batch_no
     *
     * @mbg.generated Sat Apr 15 16:34:36 CST 2017
     */
    private String batchNo;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column balance_log.shardx
     *
     * @mbg.generated Sat Apr 15 16:34:36 CST 2017
     */
    private Integer shardx;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table balance_log
     *
     * @mbg.generated Sat Apr 15 16:34:36 CST 2017
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column balance_log.id
     *
     * @return the value of balance_log.id
     *
     * @mbg.generated Sat Apr 15 16:34:36 CST 2017
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column balance_log.id
     *
     * @param id the value for balance_log.id
     *
     * @mbg.generated Sat Apr 15 16:34:36 CST 2017
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column balance_log.city_id
     *
     * @return the value of balance_log.city_id
     *
     * @mbg.generated Sat Apr 15 16:34:36 CST 2017
     */
    public Integer getCityId() {
        return cityId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column balance_log.city_id
     *
     * @param cityId the value for balance_log.city_id
     *
     * @mbg.generated Sat Apr 15 16:34:36 CST 2017
     */
    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column balance_log.rider_id
     *
     * @return the value of balance_log.rider_id
     *
     * @mbg.generated Sat Apr 15 16:34:36 CST 2017
     */
    public Integer getRiderId() {
        return riderId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column balance_log.rider_id
     *
     * @param riderId the value for balance_log.rider_id
     *
     * @mbg.generated Sat Apr 15 16:34:36 CST 2017
     */
    public void setRiderId(Integer riderId) {
        this.riderId = riderId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column balance_log.type
     *
     * @return the value of balance_log.type
     *
     * @mbg.generated Sat Apr 15 16:34:36 CST 2017
     */
    public Byte getType() {
        return type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column balance_log.type
     *
     * @param type the value for balance_log.type
     *
     * @mbg.generated Sat Apr 15 16:34:36 CST 2017
     */
    public void setType(Byte type) {
        this.type = type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column balance_log.blocked
     *
     * @return the value of balance_log.blocked
     *
     * @mbg.generated Sat Apr 15 16:34:36 CST 2017
     */
    public Byte getBlocked() {
        return blocked;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column balance_log.blocked
     *
     * @param blocked the value for balance_log.blocked
     *
     * @mbg.generated Sat Apr 15 16:34:36 CST 2017
     */
    public void setBlocked(Byte blocked) {
        this.blocked = blocked;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column balance_log.account
     *
     * @return the value of balance_log.account
     *
     * @mbg.generated Sat Apr 15 16:34:36 CST 2017
     */
    public String getAccount() {
        return account;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column balance_log.account
     *
     * @param account the value for balance_log.account
     *
     * @mbg.generated Sat Apr 15 16:34:36 CST 2017
     */
    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column balance_log.paid
     *
     * @return the value of balance_log.paid
     *
     * @mbg.generated Sat Apr 15 16:34:36 CST 2017
     */
    public Byte getPaid() {
        return paid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column balance_log.paid
     *
     * @param paid the value for balance_log.paid
     *
     * @mbg.generated Sat Apr 15 16:34:36 CST 2017
     */
    public void setPaid(Byte paid) {
        this.paid = paid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column balance_log.name
     *
     * @return the value of balance_log.name
     *
     * @mbg.generated Sat Apr 15 16:34:36 CST 2017
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column balance_log.name
     *
     * @param name the value for balance_log.name
     *
     * @mbg.generated Sat Apr 15 16:34:36 CST 2017
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column balance_log.cost
     *
     * @return the value of balance_log.cost
     *
     * @mbg.generated Sat Apr 15 16:34:36 CST 2017
     */
    public BigDecimal getCost() {
        return cost;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column balance_log.cost
     *
     * @param cost the value for balance_log.cost
     *
     * @mbg.generated Sat Apr 15 16:34:36 CST 2017
     */
    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column balance_log.factorage
     *
     * @return the value of balance_log.factorage
     *
     * @mbg.generated Sat Apr 15 16:34:36 CST 2017
     */
    public BigDecimal getFactorage() {
        return factorage;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column balance_log.factorage
     *
     * @param factorage the value for balance_log.factorage
     *
     * @mbg.generated Sat Apr 15 16:34:36 CST 2017
     */
    public void setFactorage(BigDecimal factorage) {
        this.factorage = factorage;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column balance_log.ins_tm
     *
     * @return the value of balance_log.ins_tm
     *
     * @mbg.generated Sat Apr 15 16:34:36 CST 2017
     */
    public Date getInsTm() {
        return insTm;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column balance_log.ins_tm
     *
     * @param insTm the value for balance_log.ins_tm
     *
     * @mbg.generated Sat Apr 15 16:34:36 CST 2017
     */
    public void setInsTm(Date insTm) {
        this.insTm = insTm;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column balance_log.verify_tm
     *
     * @return the value of balance_log.verify_tm
     *
     * @mbg.generated Sat Apr 15 16:34:36 CST 2017
     */
    public Date getVerifyTm() {
        return verifyTm;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column balance_log.verify_tm
     *
     * @param verifyTm the value for balance_log.verify_tm
     *
     * @mbg.generated Sat Apr 15 16:34:36 CST 2017
     */
    public void setVerifyTm(Date verifyTm) {
        this.verifyTm = verifyTm;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column balance_log.finish_tm
     *
     * @return the value of balance_log.finish_tm
     *
     * @mbg.generated Sat Apr 15 16:34:36 CST 2017
     */
    public Date getFinishTm() {
        return finishTm;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column balance_log.finish_tm
     *
     * @param finishTm the value for balance_log.finish_tm
     *
     * @mbg.generated Sat Apr 15 16:34:36 CST 2017
     */
    public void setFinishTm(Date finishTm) {
        this.finishTm = finishTm;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column balance_log.reason
     *
     * @return the value of balance_log.reason
     *
     * @mbg.generated Sat Apr 15 16:34:36 CST 2017
     */
    public String getReason() {
        return reason;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column balance_log.reason
     *
     * @param reason the value for balance_log.reason
     *
     * @mbg.generated Sat Apr 15 16:34:36 CST 2017
     */
    public void setReason(String reason) {
        this.reason = reason == null ? null : reason.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column balance_log.trade_no
     *
     * @return the value of balance_log.trade_no
     *
     * @mbg.generated Sat Apr 15 16:34:36 CST 2017
     */
    public String getTradeNo() {
        return tradeNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column balance_log.trade_no
     *
     * @param tradeNo the value for balance_log.trade_no
     *
     * @mbg.generated Sat Apr 15 16:34:36 CST 2017
     */
    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo == null ? null : tradeNo.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column balance_log.trade_way
     *
     * @return the value of balance_log.trade_way
     *
     * @mbg.generated Sat Apr 15 16:34:36 CST 2017
     */
    public Byte getTradeWay() {
        return tradeWay;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column balance_log.trade_way
     *
     * @param tradeWay the value for balance_log.trade_way
     *
     * @mbg.generated Sat Apr 15 16:34:36 CST 2017
     */
    public void setTradeWay(Byte tradeWay) {
        this.tradeWay = tradeWay;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column balance_log.risk_checked
     *
     * @return the value of balance_log.risk_checked
     *
     * @mbg.generated Sat Apr 15 16:34:36 CST 2017
     */
    public Byte getRiskChecked() {
        return riskChecked;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column balance_log.risk_checked
     *
     * @param riskChecked the value for balance_log.risk_checked
     *
     * @mbg.generated Sat Apr 15 16:34:36 CST 2017
     */
    public void setRiskChecked(Byte riskChecked) {
        this.riskChecked = riskChecked;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column balance_log.current_service_type
     *
     * @return the value of balance_log.current_service_type
     *
     * @mbg.generated Sat Apr 15 16:34:36 CST 2017
     */
    public Byte getCurrentServiceType() {
        return currentServiceType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column balance_log.current_service_type
     *
     * @param currentServiceType the value for balance_log.current_service_type
     *
     * @mbg.generated Sat Apr 15 16:34:36 CST 2017
     */
    public void setCurrentServiceType(Byte currentServiceType) {
        this.currentServiceType = currentServiceType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column balance_log.bank_name
     *
     * @return the value of balance_log.bank_name
     *
     * @mbg.generated Sat Apr 15 16:34:36 CST 2017
     */
    public Byte getBankName() {
        return bankName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column balance_log.bank_name
     *
     * @param bankName the value for balance_log.bank_name
     *
     * @mbg.generated Sat Apr 15 16:34:36 CST 2017
     */
    public void setBankName(Byte bankName) {
        this.bankName = bankName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column balance_log.bank_card
     *
     * @return the value of balance_log.bank_card
     *
     * @mbg.generated Sat Apr 15 16:34:36 CST 2017
     */
    public String getBankCard() {
        return bankCard;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column balance_log.bank_card
     *
     * @param bankCard the value for balance_log.bank_card
     *
     * @mbg.generated Sat Apr 15 16:34:36 CST 2017
     */
    public void setBankCard(String bankCard) {
        this.bankCard = bankCard == null ? null : bankCard.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column balance_log.bank_card_type
     *
     * @return the value of balance_log.bank_card_type
     *
     * @mbg.generated Sat Apr 15 16:34:36 CST 2017
     */
    public Byte getBankCardType() {
        return bankCardType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column balance_log.bank_card_type
     *
     * @param bankCardType the value for balance_log.bank_card_type
     *
     * @mbg.generated Sat Apr 15 16:34:36 CST 2017
     */
    public void setBankCardType(Byte bankCardType) {
        this.bankCardType = bankCardType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column balance_log.pay_type
     *
     * @return the value of balance_log.pay_type
     *
     * @mbg.generated Sat Apr 15 16:34:36 CST 2017
     */
    public Byte getPayType() {
        return payType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column balance_log.pay_type
     *
     * @param payType the value for balance_log.pay_type
     *
     * @mbg.generated Sat Apr 15 16:34:36 CST 2017
     */
    public void setPayType(Byte payType) {
        this.payType = payType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column balance_log.batch_record_id
     *
     * @return the value of balance_log.batch_record_id
     *
     * @mbg.generated Sat Apr 15 16:34:36 CST 2017
     */
    public Long getBatchRecordId() {
        return batchRecordId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column balance_log.batch_record_id
     *
     * @param batchRecordId the value for balance_log.batch_record_id
     *
     * @mbg.generated Sat Apr 15 16:34:36 CST 2017
     */
    public void setBatchRecordId(Long batchRecordId) {
        this.batchRecordId = batchRecordId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column balance_log.batch_no
     *
     * @return the value of balance_log.batch_no
     *
     * @mbg.generated Sat Apr 15 16:34:36 CST 2017
     */
    public String getBatchNo() {
        return batchNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column balance_log.batch_no
     *
     * @param batchNo the value for balance_log.batch_no
     *
     * @mbg.generated Sat Apr 15 16:34:36 CST 2017
     */
    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo == null ? null : batchNo.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column balance_log.shardx
     *
     * @return the value of balance_log.shardx
     *
     * @mbg.generated Sat Apr 15 16:34:36 CST 2017
     */
    public Integer getShardx() {
        return shardx;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column balance_log.shardx
     *
     * @param shardx the value for balance_log.shardx
     *
     * @mbg.generated Sat Apr 15 16:34:36 CST 2017
     */
    public void setShardx(Integer shardx) {
        this.shardx = shardx;
    }

    public Date getWithdrawTm() {
        return withdrawTm;
    }

    public void setWithdrawTm(Date withdrawTm) {
        this.withdrawTm = withdrawTm;
    }
}