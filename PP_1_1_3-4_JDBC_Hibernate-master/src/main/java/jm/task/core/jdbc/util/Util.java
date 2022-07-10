package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {

    // реализуйте настройку соеденения с БД
    private  static String URL = "jdbc:mysql://localhost:3306";
    private  static String USERNAME = "root";
    private  static String PASSWORD = "12345";
   private static Connection connection;
   static   {
        try {
            connection = DriverManager.getConnection(URL,USERNAME,PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("Не удалось установить соединение с базой данных");
        }
    }
   public static Connection getConnection() {
       return connection;
   }



}
