package com.zero.demo.util;
import static com.zero.demo.constants.I18NConstants.LANGUAGE_DEFAULT;
import static com.zero.demo.constants.I18NConstants.LANGUAGE_EN;
import static com.zero.demo.constants.I18NConstants.LANGUAGE_TOKEN;
import static com.zero.demo.constants.I18NConstants.LANGUAGE_ZH;
import static com.zero.demo.constants.I18NConstants.MESSAGE_BASE;
import static com.zero.demo.util.HttpUtil.getHeader;
import static com.zero.demo.util.HttpUtil.getParameter;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ResourceBundleMessageSource;


/**
* <pre>
* I18N Util class that provides methods to get I18N messages.
* Read language code from Cookie(cookie key = language) 
* e.g. Cookie("language") = "zh_CN", then load resource messages_zh_CN.properties.
* Usage: 
* ResourceUtil.getMessage("msg001");
* //i18n/messages.properties/ADDRESS=The address is province {0} city {1} Area {2}
* ResourceUtil.getMessage("ADDRESS", "Guangdong", "ShenZhen", "South Hill"); </pre>
* 
* @author louis
* @version 2017-7-5
*/
public class I18nUtil {
    static final Logger log = LoggerFactory.getLogger(I18nUtil.class);

    private static Map<Integer, String> msgCodeMap = new HashMap<>(); //Map<EnumType, EnumItem>
    private static final Map<String, Locale> localeMap = new HashMap<>();
    private static final ResourceBundleMessageSource bunndle = new ResourceBundleMessageSource();

    static {
        bunndle.setBasename(MESSAGE_BASE);

        localeMap.put(LANGUAGE_EN, Locale.US);
        localeMap.put(LANGUAGE_ZH, Locale.SIMPLIFIED_CHINESE);
    }

    public static String getMessage(String code, Object... args) {
        if (args == null)
            args = new Object[] {};

        String msg = bunndle.getMessage(code, args, code, getLocale());
        if (BaseUtil.isEmpty(msg)) {
            msg = code;
        }

        return msg;
    }

    private static Locale getLocale() {
        String language = getLanguageCode();

        Locale locale = localeMap.get(language);
        if (locale == null)
            locale = Locale.getDefault();

        return locale;
    }


    public static String getLanguageCode() {
        String language = getHeader(LANGUAGE_TOKEN);

        if (isBlank(language)) {
            language = getParameter(LANGUAGE_TOKEN);
            
            if (isBlank(language))
                language = LANGUAGE_DEFAULT;
        }
        return language;
    }
    
    private static boolean isBlank(String str) {
        return str == null || "".equals(str.trim()) || "null".equals(str.trim());
    }
    
    public static String getEnumMessage(Integer enumType, Integer code, Object... args) {
        if (enumType == null || code == null)
            return "";
        else
            return I18nUtil.getMessage(msgCodeMap.get(enumType + code), args);
    }    

}
