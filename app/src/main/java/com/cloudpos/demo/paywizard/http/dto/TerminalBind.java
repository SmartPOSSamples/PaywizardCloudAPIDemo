package com.cloudpos.demo.paywizard.http.dto;

import androidx.annotation.NonNull;

public class TerminalBind {
    private String terminalId;
    private String terminalSn;
    private String bindPosId;

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }

    public String getTerminalSn() {
        return terminalSn;
    }

    public void setTerminalSn(String terminalSn) {
        this.terminalSn = terminalSn;
    }

    public String getBindPosId() {
        return bindPosId;
    }

    public void setBindPosId(String bindPosId) {
        this.bindPosId = bindPosId;
    }

    @Override
    public String toString() {
        return "TerminalBind{" +
                "terminalId='" + terminalId + '\'' +
                ", terminalSn='" + terminalSn + '\'' +
                ", bindPosId='" + bindPosId + '\'' +
                '}';
    }
}
