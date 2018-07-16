package com.zero.core.domain;

import static com.zero.demo.constants.BaseConstants.*;

public class ResponseVO {
    private String statusCode = STATUS_OK;
    private String statusMsg;

    private Object data;

    public ResponseVO() {
    }

    public ResponseVO(String statusCode, String statusMsg) {
        super();
        this.statusCode = statusCode;
        this.statusMsg = statusMsg;
    }

    public ResponseVO(String statusCode, String statusMsg, Object data) {
        super();
        this.statusCode = statusCode;
        this.statusMsg = statusMsg;
        this.data = data;
    }

    public ResponseVO(Object data) {
        super();
        this.data = data;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getStatusMsg() {
        return statusMsg;
    }

    public void setStatusMsg(String errMsg) {
        this.statusMsg = errMsg;
    }

}
