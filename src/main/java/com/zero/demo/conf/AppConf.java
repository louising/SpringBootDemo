package com.zero.demo.conf;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Demo for ConfigurationProperties
 * ignoreUnknownFields = false: check the field in application.yml has a field in this class
 * 
 * @author Louisling
 * @version 2018-07-01
 */
@Component
@ConfigurationProperties(prefix = "appConf", ignoreUnknownFields = true)
public class AppConf {
    private String configPath;
    private Long version;
    private String logPath;

    public String getLogPath() {
        return logPath;
    }

    public void setLogPath(String logPath) {
        this.logPath = logPath;
    }

    public String getConfigPath() {
        return configPath;
    }

    public void setConfigPath(String configPath) {
        this.configPath = configPath;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return configPath + ", " + version;
    }
}
