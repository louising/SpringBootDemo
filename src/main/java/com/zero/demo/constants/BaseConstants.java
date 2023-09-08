package com.zero.demo.constants;

/**
 * BaseConstants
 * 
 * @author Louisling
 * @since 2018-07-01
 */
public interface BaseConstants {
    boolean LOGIN_ENABLE = false;
    boolean XSRF_ENABLE = false;

    String STATUS_OK        = "200"; //Success
    String STATUS_ERR       = "500"; //Server error
    String STATUS_NOT_LOGIN = "403"; //Not Login

    String HEADER_TOKEN     = "token";   //Defense XSRF
    String RESPONSE_TYPE    = "application/json;charset=UTF-8"; //Response MIME type
    String SESSION_USER_KEY = "SESSION_USER_KEY"; 
    
    String EXTENSION_ZIP         = ".zip";
    String EXTENSION_EXCEL_MACRO = ".xlsm";
    String EXTENSION_EXCEL       = ".xlsx";
    
    String DIR_CURR    = ".";
    String DIR_PARENET = "..";
}
