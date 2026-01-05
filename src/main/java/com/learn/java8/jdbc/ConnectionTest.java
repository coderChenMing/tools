package com.learn.java8.jdbc;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionTest {
    @Test
    public void testConnection() throws SQLException {
        // 获取Driver实现类对象
        Driver driver = new com.mysql.jdbc.Driver();
        String url = "jdbc:mysql://localhost:3306/test?useSSL=false&serverTimezone=UTC&useUnicode=true&characterEncoding=utf8";
        Properties info = new Properties();
        info.put("user", "root");
        info.put("password", "123456");
        Connection connect = driver.connect(url, info);
        System.out.println( connect.toString());
    }
    @Test
    public void testConnection2() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        // 获取Driver实现类对象
        Class<?> driverClass = Class.forName("com.mysql.jdbc.Driver");
        Driver driver = (Driver) driverClass.newInstance();
        String url = "jdbc:mysql://localhost:3306/test?useSSL=false&serverTimezone=UTC&useUnicode=true&characterEncoding=utf8";
        Properties info = new Properties();
        info.put("user", "root");
        info.put("password", "123456");
        Connection connect = driver.connect(url, info);
        System.out.println( connect.toString());
    }
    @Test
    public void testConnection3() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        // 使用DriverManager替换Driver
        Class<?> driverClass = Class.forName("com.mysql.jdbc.Driver");
        Driver driver = (Driver) driverClass.newInstance();
        DriverManager.registerDriver(driver);
        String url = "jdbc:mysql://localhost:3306/test?useSSL=false&serverTimezone=UTC&useUnicode=true&characterEncoding=utf8";
        Properties info = new Properties();
        info.put("user", "root");
        info.put("password", "123456");
        Connection connect = DriverManager.getConnection(url, info);
        System.out.println( connect.toString());
    }
    @Test
    public void testConnection4() throws SQLException, ClassNotFoundException {
        // 提供三个连接的基本信息
        String url = "jdbc:mysql://localhost:3306/test?useSSL=false&useUnicode=true&characterEncoding=utf8&autoReconnect=true";
        String username = "root";
        String password = "123456";
        // 1.加载驱动程序,静态代码块执行,完成驱动的注册
        Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection(url, username, password);
        System.out.println( connection.toString());
    }
    @Test
    public void testConnection5() throws SQLException {
        // 提供三个连接的基本信息
        String url = "jdbc:mysql://localhost:3306/test?useSSL=false&useUnicode=true&characterEncoding=utf8&autoReconnect=true";
        String username = "root";
        String password = "123456";
        // spi机制
        // 1.加载驱动程序,静态代码块执行,完成驱动的注册
        Connection connection = DriverManager.getConnection(url, username, password);
        System.out.println( connection.toString());
    }
    @Test
    public void testConnection6() throws SQLException, IOException, ClassNotFoundException {
        // 读取配置文件的方式获取连接信息
        InputStream inputStream = ConnectionTest.class.getClassLoader().getResourceAsStream("jdbc.properties");
        Properties properties = new Properties();
        properties.load(inputStream);
        String url = properties.getProperty("jdbc.url");
        String username = properties.getProperty("jdbc.username");
        String password = properties.getProperty("jdbc.password");
        String driverClass = properties.getProperty("jdbc.driverClass");
        // 1.加载驱动程序,静态代码块执行,完成驱动的注册
        Class.forName(driverClass);
        Connection connection = DriverManager.getConnection(url, username, password);
        System.out.println( connection.toString());

    }
}
