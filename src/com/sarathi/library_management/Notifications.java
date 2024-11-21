package com.sarathi.library_management;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Notifications extends DbHandler {

    private static String name;

    public static void displayWelcomeMsg(String email, boolean isStaff) {
        String tableName = isStaff ? "staffs" : "members";
        String query = "SELECT name FROM " + tableName + " WHERE email = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            name = resultSet.getString(1);
        }
        catch (SQLException e) {
            System.out.println("There is a error in our server kindly check after a while...");
        }
        System.out.println("\nHey!!! Welcome " + name);
        System.out.println("What do you want to do !!!!!!");
        System.out.println("<--------------------------------->\n");
    }

    public static void displayThankYouMsg() {
        System.out.println("\nThank you " + name + " for visiting our library...");
        System.out.println("See you soon!!!!!!!!");
    }
}
