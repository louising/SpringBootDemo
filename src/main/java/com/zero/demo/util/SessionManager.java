package com.zero.demo.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * SessionManager
 * 
 * @author Louisling
 * @since 2018-07-01
 */
public class SessionManager {
    static Log log = LogFactory.getLog(SessionManager.class);
    static String SESSION_USER_INFO_KEY = "SESSION_USER_INFO_KEY";

    public static Object getUser() {
        Object userBean = HttpUtil.getRequest().getSession().getAttribute(SESSION_USER_INFO_KEY);
        return userBean;
    }

    public static String getLoginName() {
        Object userVO = getUser();
        return userVO.toString();
    }

    public static void setUser(Object userInfo) {
        HttpUtil.getRequest().getSession().setAttribute(SESSION_USER_INFO_KEY, userInfo);
    }
}