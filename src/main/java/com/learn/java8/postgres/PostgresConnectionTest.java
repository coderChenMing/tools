package com.learn.java8.postgres;

import java.sql.*;
import java.util.Properties;

public class PostgresConnectionTest {
    public static void main(String[] args) {
        test1();
        test2();
    }

    public static void test1() {
        //String url = "jdbc:postgresql://localhost:5432/sakila";
        String url = "jdbc:postgresql://localhost:5432/";
        Properties props = new Properties();
        props.setProperty("user", "postgres");
        props.setProperty("password", "123456");
        //props.setProperty("database", "sakila");

        //props.setProperty("ssl", "true");
        try {
           /* String url = "jdbc:postgresql://localhost/sakila?user=postgres&password=123456&ssl=true";
            Connection conn = DriverManager.getConnection(url);*/
            Connection conn = DriverManager.getConnection(url, props);
            Statement st = conn.createStatement();
            //ResultSet rs = st.executeQuery("SELECT datname FROM pg_database");
            ResultSet rs = st.executeQuery("SELECT table_name,column_name,data_type,column_default FROM information_schema.columns WHERE table_name = 'actor'");
            while (rs.next()) {
                System.out.print("Column 1 returned ");
                System.out.print(rs.getString(1)+"\t");
                System.out.print(rs.getString(2)+"\t");
                System.out.print(rs.getString(3)+"\t");
                System.out.print(rs.getString(4)+"\t");
                System.out.println();
            }
            rs.close();
            st.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void test2() {
        String jdbcURL = "jdbc:postgresql://localhost:5432/sakila";
        String username = "postgres";
        String password = "123456";
        String tableName = "actor";

        try {
            Connection connection = DriverManager.getConnection(jdbcURL, username, password);
            System.out.println("Connected to the PostgreSQL database!");

            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet resultSet = metaData.getColumns(null, null, tableName, null);

            while (resultSet.next()) {
                String columnName = resultSet.getString("COLUMN_NAME");
                String dataType = resultSet.getString("DATA_TYPE");
                String columnSize = resultSet.getString("COLUMN_SIZE");
                String isNullable = resultSet.getString("IS_NULLABLE");

                System.out.println("Column Name: " + columnName);
                System.out.println("Data Type: " + dataType);
                System.out.println("Column Size: " + columnSize);
                System.out.println("Is Nullable: " + isNullable);
                System.out.println("-----------------------------------");
            }

            resultSet.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println("Failed to connect to the PostgreSQL database!");
            e.printStackTrace();
        }
    }
}
