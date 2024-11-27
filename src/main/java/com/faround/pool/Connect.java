package com.faround.pool;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public  class Connect {
    public static String url = "jdbc:postgresql://localhost:5432/rssagg";
    public static String user = "postgres";
    public static String password = "supranova";
    public static int POOL_SIZE = 10;
    public static List<Connection> connectionPool = new ArrayList<>(POOL_SIZE);
    public static List<Connection> usedConnections = new ArrayList<>();



    public static List<Connection> createPool() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        for(int i = 0; i < POOL_SIZE; i++){
            Connection conn = DriverManager.getConnection(url,user,password);
            connectionPool.add(conn);
        }
        return connectionPool;
    }

    public static Connection getConnectionFromPool(){
        Connection conn = connectionPool.remove(connectionPool.size()-1);
        usedConnections.add(conn);
        return conn;
    }

    public static void releaseConnectionFromPool(){
        Connection conn = usedConnections.remove(usedConnections.size()-1);
        connectionPool.add(conn);
    }
    public static void connect() throws SQLException, ClassNotFoundException {
            Connection connection = DriverManager.getConnection(url, user, password);
            String query = "SELECT * FROM feed";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            resultSet.next();
            String name = resultSet.getString("id");
            System.out.println(name);
    }


}
