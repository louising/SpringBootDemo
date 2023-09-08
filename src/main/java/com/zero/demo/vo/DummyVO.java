package com.zero.demo.vo;

/**
 * DummyVO
 * 
 * @author Louisling
 * @since 2018-07-01
 */
public class DummyVO {
    private Integer userId;
    private String userName;
    private String loginName;

    public DummyVO() {
    }

    public DummyVO(Integer userId, String userName) {
        super();
        this.userId = userId;
        this.userName = userName;
    }

    public DummyVO(String userName, String loginName) {
        super();
        this.userName = userName;
        this.loginName = loginName;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "DummyVO(" + userId + "," + userName + "," + loginName + ")";
    }
}
