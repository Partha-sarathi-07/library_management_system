package com.sarathi.library_management;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class User extends DbHandler {
    private final Scanner scanner = new Scanner(System.in);
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    public void showBookSearchOptions() {
        System.out.println("1. Search By Title");
        System.out.println("2. Search By Author");
        System.out.println("3. Search By Genre");
        System.out.println("4. Exit\n");
    }

    public void showAllBooks() {
        String fetchQuery = "SELECT * FROM books";
        try {
            preparedStatement = connection.prepareStatement(fetchQuery);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                System.out.printf("\nBook id = %d, Title = %s, Author = %s, Genre = %s, Available Copies = %d", resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getInt(5));
            }
            System.out.println();
        }
        catch (SQLException e) {
            System.out.println("Unable to show the books");
            System.out.println("Kindly check after a while");
        }
    }

    public void searchByGenre() {
        String genre = selectGenre();
        String fetchQuery = "SELECT * FROM books " +
                "WHERE genre LIKE '%" + genre + "%'";
        try {
            preparedStatement = connection.prepareStatement(fetchQuery);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                System.out.printf("\nBook id = %d, Title = %s, Author = %s, Available copies = %d", resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getInt(5));
            }
            System.out.println();
        } catch (SQLException e) {
            System.out.println("Sorry unable to retrive the books");
            System.out.println("Please try after a while....");
        }
    }

    public String selectGenre() {
        System.out.println();
        while (true) {
            System.out.println("Available Book Genre : ");
            System.out.println("----------------------");
            System.out.println("1. Fiction");
            System.out.println("2. Fantasy");
            System.out.println("3. Classic");
            System.out.println("4. Dystopian");
            System.out.println("5. Thriller");
            System.out.println("6. Romance");
            System.out.print("\nEnter the books genre (1 / 2 / 3 / 4 / 5 / 6) : ");
            String genre = switch (scanner.nextInt()) {
                case 1 -> "fiction";
                case 2 -> "fantasy";
                case 3 -> "classic";
                case 4 -> "dystopian";
                case 5 -> "thriller";
                case 6 -> "romance";
                default -> "none";
            };

            if (!genre.equalsIgnoreCase("none"))
                return genre;
        }

    }

    public void searchByTitle() {
        System.out.print("\nEnter the book title : ");
        String title = scanner.nextLine();
        String fetchQuery = "SELECT * FROM books " +
                "WHERE title LIKE '%" + title + "%'";
        try {
            preparedStatement = connection.prepareStatement(fetchQuery);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            System.out.printf("\nBook id = %d, title = %s, Author = %s, Genre = %s, Available copies = %d", resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getInt(5));
            System.out.println();
        } catch (SQLException e) {
            System.out.println("The searched book is not available");
        }
    }

    public ResultSet searchByTitle(String title) {

        String fetchQuery = "SELECT * FROM books " +
                "WHERE title LIKE '%" + title + "%'";
        try {
            preparedStatement = connection.prepareStatement(fetchQuery);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            System.out.printf("\nBook id = %d, Title = %s, Author = %s, Genre = %s, Available copies = %d", resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getInt(5));
            System.out.println();
        } catch (SQLException e) {
            System.out.println("The searched book is not available");
        }
        return resultSet;
    }

    public void searchByAuthor() {
        System.out.print("\nEnter the book author : ");
        String author = scanner.nextLine();
        String fetchQuery = "SELECT * FROM books " +
                "WHERE author LIKE '%" + author + "%';";

        try {
            preparedStatement = connection.prepareStatement(fetchQuery);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                System.out.printf("\nBook id = %d, Title = %s, Author = %s, Genre = %s, Available Copies = %d", resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getInt(5));
            }
            System.out.println();
        }
        catch (SQLException e) {
            System.out.println("There is a little problem in out side");
            System.out.println("So please kindly search after a while");
        }
    }


}
