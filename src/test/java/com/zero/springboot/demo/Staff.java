package com.zero.springboot.demo;

public class Staff {
    static int USERID = getUserId();
    
    static int getUserId() {
        //return 19;
        throw new RuntimeException("Unknown Error");
    }
    
    public Staff(int userId) {
        USERID = userId;
    }
}
