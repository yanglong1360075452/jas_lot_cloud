package com.wizinno.jas.unity;

/**
 * Created by Administrator on 2017/1/19.
 */

import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class C3P0Util {
    static ComboPooledDataSource cpds = null;

    static {
// 这里有个优点，写好配置文件，想换数据库，简单
// cpds = new ComboPooledDataSource("oracle");//这是oracle数据库
        cpds = new ComboPooledDataSource("mysql");// 这是mysql数据库
    }

    /**
     * 获得数据源
     *
     *
     */
    public static DataSource getDataSource() {
        return cpds;
    }
    /**
     * 获得数据库连接
     *
     * @return Connection
     */
    public static Connection getConnection() {
        try {
            return cpds.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 数据库关闭操作
     *
     * @param conn
     * @param
     * @param pst
     * @param rs
     */
    public static void close(Connection conn, PreparedStatement pst, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (pst != null) {
            try {
                pst.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
//    测试类
//    public static void main(String[] args) {
//        Connection conn = getConnection();
//        System.out.println(conn.getClass().getName());
//        close(conn, null, null);
//    }
}