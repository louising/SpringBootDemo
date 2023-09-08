package com.zero.core;

/**
 * Exception that throw when a error occurs in service layer
 * 
 * @author Louisling
 * @version 2018-07-25
 */
public class ServiceException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private String errCode;
    private Object[] args;

    public ServiceException() {
    }

    public ServiceException(String errCode, Object... args) {
        super();
        this.errCode = errCode;
        this.args = args;
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

}
