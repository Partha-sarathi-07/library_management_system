package com.sarathi.library_management;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Fine extends DbHandler{
    private static final Scanner scanner = new Scanner(System.in);
    private final String email;

    public Fine(String email) {
        this.email = email;
    }

    public static void showFineDetails() {
        System.out.println("If you don't return the books within the due date then every day One Rupee will be fined");
    }

    public int calculateFine(int bookId) {
        String dateDiffQuery = "SELECT DATEDIFF(current_date, due_date) " +
                "FROM books_tracker " +
                "WHERE book_id = ? AND email = ?;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(dateDiffQuery);
            preparedStatement.setInt(1, bookId);
            preparedStatement.setString(2, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next() && resultSet.getInt(1) > 0) {
                int fine =  resultSet.getInt(1);
                updateFineInDb(email, fine);
                return fine;
            }
        }
        catch (SQLException e) {
            System.out.println("Sorry unable to see the fine.....");
        }
        return 0;
    }

    public void updateFineInDb(String email, int fine) {
        int existingFine = fineAlreadyPresentInDb();
        if (existingFine > 0)
            updateExistingFine(fine);
        else
            createNewFineRecord(fine);
    }

    public int fineAlreadyPresentInDb() {
        String checkQuery = "SELECT fine FROM fine_tracker WHERE email = ?;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(checkQuery);
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
                return resultSet.getInt(1);
        }
        catch (SQLException e) {
            System.out.println("Unable to check whether the fine is present or not...");
        }
        return 0;
    }

    public void updateExistingFine(int fine) {
        String updateQuery = "UPDATE fine_tracker SET fine = fine + ? WHERE email = ?;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
            preparedStatement.setInt(1, fine);
            preparedStatement.setString(2, email);
            int row = preparedStatement.executeUpdate();
            if (row == 1)
                System.out.println("Fine has been Updated");
        }
        catch (SQLException e) {
            System.out.println("Unable to update fine in already existing fine....");
        }
    }

    public void createNewFineRecord(int fine) {
        String insertQuery = "INSERT INTO fine_tracker VALUES (?, ?);";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setString(1, email);
            preparedStatement.setInt(2, fine);
            int row = preparedStatement.executeUpdate();
            if (row == 1)
                System.out.println("New fine Record has been created..");
        }
        catch (SQLException e) {
            System.out.println("Sorry unable to create new Fine Record");
        }

    }

    public void payFine() {
        int fine = 0;
        String fetchQuery = "SELECT fine FROM fine_tracker WHERE email = ?;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(fetchQuery);
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
                fine = resultSet.getInt(1);
        }
        catch (SQLException e) {
            System.out.println("Unable to fetch fine.. kindly try after a while...");
            return;
        }
        if (fine == 0) {
            System.out.println("You have no fine to pay");
            return;
        }
        System.out.print("Are you willing to pay your fine amount " + fine + " (yes / no) : ");
        if (scanner.nextLine().equalsIgnoreCase("yes")) {
            System.out.print("\nEnter the amount : ");
            int currentPaying = scanner.nextInt();
            scanner.nextLine();
            while (currentPaying > fine) {
                System.out.println("Your fine amount is " + fine);
                System.out.print("\nEnter the amount less than or equal to your fine : ");
                currentPaying = scanner.nextInt();
            }
            fine -= currentPaying;
            if (fine == 0) {
                deleteFineAfterPayment();
            }
            else {
                updateFineAfterPayment(fine);
            }

        }
        else {
            System.out.println("Try to pay your fine as soon as possible bro..");
        }
    }

    public void deleteFineAfterPayment() {
        String deleteRecord = "DELETE FROM fine_tracker WHERE email = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(deleteRecord);
            preparedStatement.setString(1, email);
            int row = preparedStatement.executeUpdate();
            if (row == 1)
                System.out.println("Congratulations!!! You have paid all your Fine");
        }
        catch (SQLException e) {
            System.out.println("Sorry!! Please Pay your fine after a while dude...");
        }
    }

    public void updateFineAfterPayment(int fine) {
        String updateQuery = "UPDATE fine_tracker SET fine = ? WHERE email = ?;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
            preparedStatement.setInt(1, fine);
            preparedStatement.setString(2, email);
            int row = preparedStatement.executeUpdate();
            if (row == 1) {
                System.out.println("Balance Fine = " + fine);
                System.out.println("Please try to pay as soon as possible....");
            }
        }
        catch(SQLException e) {
            System.out.println("Sorry!! please pay your fine after a while....");
        }

    }
}
