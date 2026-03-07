package dao;

import java.sql.*;

public class DatabaseUtil {
    public static Connection myGetConnection(){
        String url = "jdbc:postgresql://localhost:5432/assignment";
        String uname = "postgres";
        String password = "0009";

        try {
            return DriverManager.getConnection(url, uname, password); // automatically loads it...
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}
