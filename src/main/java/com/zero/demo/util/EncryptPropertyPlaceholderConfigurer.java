package com.zero.demo.util;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

/** 
<bean name="propertyConfigurer" class="com.zero.cem.framework.util.EncryptPropertyPlaceholderConfigurer">
    <property name="locations">
        <list>
            <value>classpath:config.properties</value>
        </list>
    </property>
</bean>

 * @author Louis
 * @version 2018-5-30
 */
public class EncryptPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {

    private String[] encryptPropNames = { "jdbc.username", "jdbc.password", "redis.auth" };

    private boolean isEncryptProp(String propertyName) {
        for (String encryptName : encryptPropNames) {
            if (encryptName.equals(propertyName)) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected String convertProperty(String propertyName, String propertyValue) {
        if (isEncryptProp(propertyName)) {
            String decryptValue = SecurityUtil.encode(propertyValue);
            return decryptValue;
        } else {
            return propertyValue;
        }
    }
}