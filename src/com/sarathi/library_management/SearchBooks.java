package com.sarathi.library_management;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class SearchBooks extends DbHandler {
    private final Scanner scanner = new Scanner(System.in);
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    public void showAllBooks() {
        String fetchQuery = "SELECT * FROM books";
        try {
            preparedStatement = connection.prepareStatement(fetchQuery);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                System.out.println("\nBook id = " + resultSet.getInt(1) + ", title = " + resultSet.getString(2) + ", author = " + resultSet.getString(3) + ", genre = " + resultSet.getString(4) + ", available copies = " + resultSet.getInt(5));
            }
        }
        catch (SQLException e) {
            System.out.println("Unable to show the books");
            System.out.println("Kindly check after a while");
        }
    }

    public void showByGenre() {
        System.out.print("\nEnter the books genre : ");
        String genre = scanner.nextLine();
        String fetchQuery = "SELECT * FROM books WHERE genre LIKE '%" + genre + "%'";
        try {
            preparedStatement = connection.prepareStatement(fetchQuery);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                System.out.println("Book id = " + resultSet.getInt(1) + ", title = " + resultSet.getString(2) + ", author = " + resultSet.getString(3) + ", available copies = " + resultSet.getInt(5));
            }
        } catch (SQLException e) {
            System.out.println("Sorry unable to retrive the books");
            System.out.println("Please try after a while....");
        }
    }

    public void showByTitle() {
        System.out.print("\nEnter the book title : ");
        String title = scanner.nextLine();
        String fetchQuery = "SELECT * FROM books WHERE title LIKE '%" + title + "%'";
        try {
            preparedStatement = connection.prepareStatement(fetchQuery);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            System.out.println("Book id = " + resultSet.getInt(1) + ", title = " + resultSet.getString(2) + ", author = " + resultSet.getString(3) + ", genre = " + resultSet.getString(4) + ", available copies = " + resultSet.getInt(5));
        } catch (SQLException e) {
            System.out.println("The searched book is not available");
        }
    }
    public ResultSet showByTitle(String title) {

        String fetchQuery = "SELECT * FROM books WHERE title LIKE '%" + title + "%'";
        try {
            preparedStatement = connection.prepareStatement(fetchQuery);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            System.out.println("Book id = " + resultSet.getInt(1) + ", title = " + resultSet.getString(2) + ", author = " + resultSet.getString(3) + ", genre = " + resultSet.getString(4) + ", available copies = " + resultSet.getInt(5));
        } catch (SQLException e) {
            System.out.println("The searched book is not available");
        }
        return resultSet;
    }

    public void showByAuthor() {
        System.out.print("\nEnter the book author : ");
        String author = scanner.nextLine();
        String fetchQuery = "SELECT * FROM books WHERE author LIKE '%" + author + "%';";

        try {
            preparedStatement = connection.prepareStatement(fetchQuery);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                System.out.println("\nBook id = " + resultSet.getInt(1) + ", title = " + resultSet.getString(2) + ", author = " + resultSet.getString(3) + ", genre = " + resultSet.getString(4) + ", available copies = " + resultSet.getInt(5));
            }
        }
        catch (SQLException e) {
            System.out.println("There is a little problem in out side");
            System.out.println("So please kindly search after a while");
        }
    }

}
