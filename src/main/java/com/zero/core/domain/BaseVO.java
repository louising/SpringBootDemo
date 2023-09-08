package com.zero.core.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * BaseVO
 * 
 * @author Louisling
 * @since 2018-07-01
 */
public class BaseVO {
    private String createBy;
    private String updateBy;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    private String languageCode; //e.g. en-US, zh-CN

    public BaseVO() {
    }

    public BaseVO(String languageCode) {
        super();
        this.languageCode = languageCode;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "BaseVO [createBy=" + createBy + ", createTime=" + createTime + ", updateBy=" + updateBy + ", updateTime=" + updateTime + "]";
    }

}
