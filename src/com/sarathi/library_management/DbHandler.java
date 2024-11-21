package com.sarathi.library_management;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

 public class DbHandler {

    protected static Connection connection;

    public static boolean createConnection() {
        try {
            connection = DriverManager.getConnection(System.getenv("DB_URL"), System.getenv("DB_USERNAME"), System.getenv("DB_PASSWORD"));
        } catch (SQLException e) {
            System.out.println("Sorry for the inconvinience..");
            System.out.println("There is a little problem in our server");
            System.out.println("Thank you for your understanding");
            return false;
        }
        return true;
    }

    public static void closeConnection() {
        try {
            connection.close();
        }
        catch (SQLException e) {
            System.out.println("Create The connection first");
        }
    }
}
