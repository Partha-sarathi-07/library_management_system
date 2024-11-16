package com.sarathi.library_management;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Notifications extends DbHandler {

    public static void displayWelcomeMsg(String email, boolean isStaff) throws SQLException {
        String tableName = isStaff ? "staffs" : "members";
        String name = "";
        String query = "SELECT name FROM " + tableName + " WHERE email = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, email);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();

        name = resultSet.getString(1);

        System.out.println("\nHey!!! Welcome " + name);
        System.out.println("What do you want to do !!!!!!");
        System.out.println("<--------------------------------->\n");
    }
}
