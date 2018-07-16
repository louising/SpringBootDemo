package com.zero.springboot.demo;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import com.zero.core.jdbc.util.DataSourceFactory;

public class JdbcDemo {

    public static void main(String[] args) throws Exception {
        //addData();        
    }

    protected static void addData() throws SQLException {
        DataSource ds = DataSourceFactory.h2Datasource;
        Connection con = ds.getConnection();
        con.setAutoCommit(false);

        /*
        PreparedStatement pstmt = con.prepareStatement("insert into t_user(user_name,login_name, create_time) values (?, ?, CURRENT_TIMESTAMP)");
        for (int i = 1; i <= 10; i++) {
            pstmt.setString(1, "AAA" + i);
            pstmt.setString(2, "aaa" + i);
            pstmt.execute();
        }
        */
        String sql = "insert into t_user(user_name,login_name, create_time) values ('%s', '%s', CURRENT_TIMESTAMP)";
        Statement stmt = con.createStatement();
        for (int i = 1; i <= 10; i++) {
            stmt.addBatch(String.format(sql, "CCC" + i, "ccc" + i));
        }
        stmt.executeBatch();
        con.commit();
        
        con.setAutoCommit(true);        
        stmt.close();
        con.close();
    }

}
