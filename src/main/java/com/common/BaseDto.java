/**
 * 文件名：CmfClmBaseDto.java
 * 版权：Copyright 2017-2022 CMCC All Rights Reserved.
 * 描述：报账单基本类DTO
 */
package com.common;


import java.io.Serializable;

/**
 * 报账单基本类DTO
 * 所有报账单对象DTO必须继承此DTO
 */
public class BaseDto implements Serializable {

    private static final long serialVersionUID = 2898864566065272885L;

    /**
     * 资金计划校验标志
     * Y - 已校验过
     * N - 未校验过
     */
    private String fundPlanFlag;

    /**
     * 预算检查业务活动标志
     * Y - 已检查过
     * N - 未检查过
     */
    private String budgetCheckFlag;

    /**
     * 创建部分核销的提示flag
     * Y - 已经提示
     * N - 未提示
     */
    private String writeOffFlag;

    /**
     * 只列账不支付提示标志
     */
    private String remPaymentLineAmountFlag;

    /**
     * 提示标志
     */
    private String notifyFlag;

    /**
     * 提示标志 - 派生计提冲销用
     */
    private String deriveNotifyFlag;

    /**
     * 提示标志 - 收款账号与对应合同上的收款账号不一致 receiptAccount
     */
    private String receiptAccountNotifyFlag;
    /**
     *  是否手工填写付款方式标志
     */
    private String manualPayMethodFlag;


    private String checkFlag;

    public String getCheckFlag() {
        return checkFlag;
    }

    public void setCheckFlag(String checkFlag) {
        this.checkFlag = checkFlag;
    }

    /**
     * 临时审批意见
     */
    private String tempApproveMsg;

    public String getTempApproveMsg() {
        return tempApproveMsg;
    }

    public void setTempApproveMsg(String tempApproveMsg) {
        this.tempApproveMsg = tempApproveMsg;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getFundPlanFlag() {
        return fundPlanFlag;
    }

    public void setFundPlanFlag(String fundPlanFlag) {
        this.fundPlanFlag = fundPlanFlag;
    }

    public String getBudgetCheckFlag() {
        return budgetCheckFlag;
    }

    public void setBudgetCheckFlag(String budgetCheckFlag) {
        this.budgetCheckFlag = budgetCheckFlag;
    }

    public String getWriteOffFlag() {
        return writeOffFlag;
    }

    public void setWriteOffFlag(String writeOffFlag) {
        this.writeOffFlag = writeOffFlag;
    }

    public String getRemPaymentLineAmountFlag() {
        return remPaymentLineAmountFlag;
    }

    public void setRemPaymentLineAmountFlag(String remPaymentLineAmountFlag) {
        this.remPaymentLineAmountFlag = remPaymentLineAmountFlag;
    }

    public String getNotifyFlag() {
        return notifyFlag;
    }

    public void setNotifyFlag(String notifyFlag) {
        this.notifyFlag = notifyFlag;
    }

    public String getDeriveNotifyFlag() {
        return deriveNotifyFlag;
    }

    public void setDeriveNotifyFlag(String deriveNotifyFlag) {
        this.deriveNotifyFlag = deriveNotifyFlag;
    }

    public String getReceiptAccountNotifyFlag() {
        return receiptAccountNotifyFlag;
    }

    public void setReceiptAccountNotifyFlag(String receiptAccountNotifyFlag) {
        this.receiptAccountNotifyFlag = receiptAccountNotifyFlag;
    }

    public String getManualPayMethodFlag() {
        return manualPayMethodFlag;
    }

    public void setManualPayMethodFlag(String manualPayMethodFlag) {
        this.manualPayMethodFlag = manualPayMethodFlag;
    }
}
