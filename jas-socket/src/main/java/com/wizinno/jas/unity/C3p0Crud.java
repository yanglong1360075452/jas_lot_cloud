package com.wizinno.jas.unity;
import com.wizinno.jas.domain.Device;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.SQLException;

/**
 * User:
 * Date:
 * Time:
 */
public class C3p0Crud {
    /**
     * 在数据库中插入数据
     */
    public static void add(String sql,Object[] objects) {
        try {
            // 创建执行者
            QueryRunner runner = new QueryRunner(C3P0Util.getDataSource());
            // 准备sql语句
            String sqls = sql;
            // 准备参数
            Object[] params = objects;
            // 执行语句
            runner.update(sql, params);
            System.out.println("插入数据成功！");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * 查询数据
     */
    public static void query(String sql) {
        try {
            // 创建执行者
            QueryRunner runner = new QueryRunner(C3P0Util.getDataSource());
            // 准备sql语句
            String sqls = sql;
            // 执行语句
            Device device = runner.query(sql, new BeanHandler<Device>(Device.class));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * 更新数据
     */
    public static void update(String sql,Object[] objects) {
        try {
            // 创建执行者
            QueryRunner runner = new QueryRunner(C3P0Util.getDataSource());
            // 准备sql语句
            String sqls = sql;
            // 准备参数
            Object[] params = objects;
            // 执行语句
            runner.update(sql, params);
            System.out.println("更新数据成功！");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 删除数据
     */
    public static void delete(String sql) {
        try {
            // 创建执行者
            QueryRunner runner = new QueryRunner(C3P0Util.getDataSource());
            // 准备sql语句
            String sqls = "delete from user";
            // 执行语句
            runner.update(sql);
            System.out.println("删除数据成功");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
