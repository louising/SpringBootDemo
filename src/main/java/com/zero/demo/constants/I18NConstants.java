package com.zero.demo.constants;

/**
 * I18NConstants
 * 
 * @author Louisling
 * @since 2018-07-01
 */
public interface I18NConstants {
    String LANGUAGE_EN = "en-US";
    String LANGUAGE_ZH = "zh-CN";
    String LANGUAGE_DEFAULT = LANGUAGE_ZH; //zh-CN, en-US
    
    String MESSAGE_BASE = "i18n.messages";
    String LANGUAGE_TOKEN = "languageToken"; //HTTP header or parameter from client side

    //Message
    String MSG_SUCCESS = "MSG_SUCCESS"; //General information

    //Error message
    String ERR_NO_TOKEN = "ERR_NO_TOKEN";
    String ERR_FAIL = "ERR_FAIL";
    String ERR_UPLOAD = "ERR_UPLOAD";
}
