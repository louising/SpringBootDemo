package com.zero.springboot.demo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import com.zero.demo.util.SimpleDataSource;

public class JdbcDemo {
    static DataSource dataSource;
    
    static {
        String driverClass = "org.h2.Driver";
        String url = "jdbc:h2:tcp://localhost/~/H2DB-SpringRestDemo";
        String user = "sa";
        String pwd = "sa";
    
        dataSource = new SimpleDataSource(driverClass, url, user, pwd);         
    }
    
    public static void main(String[] args) throws Exception {
        addData();        
    }

    protected static void addData() throws SQLException {
        Connection con = dataSource.getConnection();
        con.setAutoCommit(false);

        //1) clear users
        Statement stmt = con.createStatement();
        stmt.execute("delete t_user");
        con.commit();
        
        //2) Add users
        PreparedStatement pstmt = con.prepareStatement("insert into t_user(user_name,login_name, create_time) values (?, ?, CURRENT_TIMESTAMP)");
        for (int i = 1; i <= 10; i++) {
            pstmt.setString(1, "Alice" + i);
            pstmt.setString(2, "alice" + i);
            pstmt.addBatch();
        }
        pstmt.executeBatch();
        con.commit();
        
        /*
        PreparedStatement pstmt = con.prepareStatement("insert into t_user(user_name,login_name, create_time) values (?, ?, CURRENT_TIMESTAMP)");
        for (int i = 1; i <= 10; i++) {
            pstmt.setString(1, "AAA" + i);
            pstmt.setString(2, "aaa" + i);
            pstmt.execute();
        }
        con.commit();
        */
        
        /*
        String sql = "insert into t_user(user_name,login_name, create_time) values ('%s', '%s', CURRENT_TIMESTAMP)";
        Statement stmt = con.createStatement();
        for (int i = 1; i <= 10; i++) {
            stmt.addBatch(String.format(sql, "CCC" + i, "ccc" + i));
        }
        stmt.executeBatch();
        stmt.close();
        con.commit();
        */
        
        con.setAutoCommit(true);        
        con.close();
    }

}
