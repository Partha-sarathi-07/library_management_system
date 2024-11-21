package com.sarathi.library_management;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Validation extends DbHandler {

    private static PreparedStatement preparedStatement;

    public static boolean login(String email, String password, boolean isStaff) {
        String tableName = isStaff ? "staffs" : "members";
        String query = "SELECT COUNT(email) FROM " + tableName + " WHERE email = ? AND password = ?";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, String.valueOf(password.hashCode()));

            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            if (resultSet.getInt(1) == 1)
                return true;
            throw new SQLException();

        }
        catch (SQLException e) {
            System.out.println("\nUsername or Password is incorrect");
            return false;
        }
    }

    public static void signUp() {
        Scanner scanner = new Scanner(System.in);
        String contact;
        String email;
        String name;
        String password;
        System.out.print("\nEnter your email : ");
        email = scanner.nextLine();
        System.out.print("Enter your name : ");
        name = scanner.nextLine();
        contact = scanner.nextLine();
        while (contact.length() != 10) {
            System.out.print("Enter your phone number : ");
            contact = scanner.nextLine();
        }

        System.out.print("Enter your password : ");
        password = scanner.nextLine();

        String query = "INSERT INTO members VALUES (?, ?, ?, ?);";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, contact);
            preparedStatement.setString(4, String.valueOf(password.hashCode()));
            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            System.out.println("Unable to sign in.. Please try after a while");
        }

        System.out.println("\nCongratulation!!!");
        System.out.println("You have become an member!!!!");
        System.out.println("Login to continue");
    }
}
