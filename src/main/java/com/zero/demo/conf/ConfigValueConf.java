package com.zero.demo.conf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * ConfigValueConf
 * 
 * @author Louisling
 * @since 2018-07-01
 */
@Component
public class ConfigValueConf {
    @Value("${appconf.config-path}")
    private String configPath;

    public String getConfigPath() {
        return configPath;
    }

    public void setConfigPath(String configPath) {
        this.configPath = configPath;
    }
}
