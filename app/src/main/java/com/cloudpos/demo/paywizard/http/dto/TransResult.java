package com.cloudpos.demo.paywizard.http.dto;

public class TransResult {
    private String code;
    private String msg;
    private String timestamp;
    private String data;
    private String checkoutId;
    private String transIndexCode;
    private String paywizardId;
    private String mid;
    private String amount;
    private String state;
    private String currencyCode;
    private String transType;
    private String tranTime;
    private String cardNo;
    private String cardToken;
    private String transResult;
    private String transId;
    private String invoiceNum;
    private String traceNum;
    private String rrn;
    private String respCode;
    private String respDesc;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getCheckoutId() {
        return checkoutId;
    }

    public void setCheckoutId(String checkoutId) {
        this.checkoutId = checkoutId;
    }

    public String getTransIndexCode() {
        return transIndexCode;
    }

    public void setTransIndexCode(String transIndexCode) {
        this.transIndexCode = transIndexCode;
    }

    public String getPaywizardId() {
        return paywizardId;
    }

    public void setPaywizardId(String paywizardId) {
        this.paywizardId = paywizardId;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType;
    }

    public String getTranTime() {
        return tranTime;
    }

    public void setTranTime(String tranTime) {
        this.tranTime = tranTime;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getCardToken() {
        return cardToken;
    }

    public void setCardToken(String cardToken) {
        this.cardToken = cardToken;
    }

    public String getTransResult() {
        return transResult;
    }

    public void setTransResult(String transResult) {
        this.transResult = transResult;
    }

    public String getTransId() {
        return transId;
    }

    public void setTransId(String transId) {
        this.transId = transId;
    }

    public String getInvoiceNum() {
        return invoiceNum;
    }

    public void setInvoiceNum(String invoiceNum) {
        this.invoiceNum = invoiceNum;
    }

    public String getTraceNum() {
        return traceNum;
    }

    public void setTraceNum(String traceNum) {
        this.traceNum = traceNum;
    }

    public String getRrn() {
        return rrn;
    }

    public void setRrn(String rrn) {
        this.rrn = rrn;
    }

    public String getRespCode() {
        return respCode;
    }

    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }

    public String getRespDesc() {
        return respDesc;
    }

    public void setRespDesc(String respDesc) {
        this.respDesc = respDesc;
    }

    @Override
    public String toString() {
        return "TransResult{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", data='" + data + '\'' +
                ", checkoutId='" + checkoutId + '\'' +
                ", transIndexCode='" + transIndexCode + '\'' +
                ", paywizardId='" + paywizardId + '\'' +
                ", mid='" + mid + '\'' +
                ", amount='" + amount + '\'' +
                ", state='" + state + '\'' +
                ", currencyCode='" + currencyCode + '\'' +
                ", transType='" + transType + '\'' +
                ", tranTime='" + tranTime + '\'' +
                ", cardNo='" + cardNo + '\'' +
                ", cardToken='" + cardToken + '\'' +
                ", transResult='" + transResult + '\'' +
                ", transId='" + transId + '\'' +
                ", invoiceNum='" + invoiceNum + '\'' +
                ", traceNum='" + traceNum + '\'' +
                ", rrn='" + rrn + '\'' +
                ", respCode='" + respCode + '\'' +
                ", respDesc='" + respDesc + '\'' +
                '}';
    }
}
