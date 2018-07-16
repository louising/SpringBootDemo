package com.zero.demo.vo;

public class UserInfoBean {
    private String userId;

    public UserInfoBean(String uid) {
        super();
        this.userId = uid;
    }

    public String getUid() {
        return userId;
    }

    public void setUid(String uid) {
        this.userId = uid;
    }
}
