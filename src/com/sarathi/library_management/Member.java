package com.sarathi.library_management;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Member extends User implements Privileges{

    private final String email;
    private PreparedStatement preparedStatement;
    private static final int MAXIMUM_BOOKS = 3;
    private final Scanner scanner = new Scanner(System.in);

    public Member(String email) {
        this.email = email;
    }

    @Override
    public void showPrivileges() {
        System.out.println("\n1. View Available Books");
        System.out.println("2. Search Books");
        System.out.println("3. Borrow Books");
        System.out.println("4. Return Books");
        System.out.println("5. Pay Fine");
        System.out.println("6. Exit\n");
    }

    @Override
    public void showAllBooks() {
        String fetchQuery = "SELECT book_id, title, author, genre " +
                            "FROM books "  +
                            "WHERE available_copies > 0;";
        try {
            preparedStatement = connection.prepareStatement(fetchQuery);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                System.out.printf("\nBook id = %d, title = %s, author = %s, genre = %s", resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4));
            }
            System.out.println();
        }
        catch (SQLException e) {
            System.out.println("Please try after a while unable to show all the books");
        }
    }

    @Override
    public void searchByGenre() {
        String genre = selectGenre();
        String fetchQuery = "SELECT * FROM books " +
                "WHERE available_copies > 0 AND genre = ?";
        try {
            preparedStatement = connection.prepareStatement(fetchQuery);
            preparedStatement.setString(1, genre);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                System.out.printf("\nBook id = %d, Title = %s, Author = %s", resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3));
            }
            System.out.println();
        } catch (SQLException e) {
            System.out.println("Sorry unable to retrive the books");
            System.out.println("Please try after a while....");
        }    }

    public int showBorrowedBooksCount() {
        String query = "SELECT COUNT(email) " +
                "FROM books_tracker " +
                "WHERE email = ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, this.email);
            ResultSet borrowedCount = preparedStatement.executeQuery();
            borrowedCount.next();
            return borrowedCount.getInt(1);
        }
        catch (SQLException e) {
            return 0;
        }
    }

    public void borrowBooks() {

        if (showBorrowedBooksCount() == MAXIMUM_BOOKS) {
            System.out.println("You have taken maximum number of books, Return the books to take other books......");
            return;
        }
        System.out.print("\nEnter the name of the Book you want to borrow : ");
        String title = scanner.nextLine();
        String checkQuery = "SELECT book_id, available_copies " +
                "FROM books " +
                "WHERE title LIKE '%" + title + "%';";
        try {
            preparedStatement = connection.prepareStatement(checkQuery);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.next())
                System.out.println("Please enter the valid Book Title");

            else if (resultSet.getInt(2) == 0)
                System.out.println("The book is not available currently...");

            else {
                int book_id = resultSet.getInt(1);
                String DateQuery = "SELECT CURRENT_DATE, CURRENT_DATE + INTERVAL 14 DAY;";
                preparedStatement = connection.prepareStatement(DateQuery);
                resultSet = preparedStatement.executeQuery();
                resultSet.next();
                Date borrowDate = resultSet.getDate(1);
                Date dueDate = resultSet.getDate(2);
                String insertQuery = "INSERT INTO books_tracker VALUES (?, ?, ?, ?);";
                preparedStatement = connection.prepareStatement(insertQuery);
                preparedStatement.setString(1, this.email);
                preparedStatement.setInt(2, book_id);
                preparedStatement.setDate(3, borrowDate);
                preparedStatement.setDate(4, dueDate);

                if (preparedStatement.executeUpdate() == 1) {
                    System.out.println("Now you can take " + title + " book to home");
                    Fine.showFineDetails();
                    System.out.println("You must return the book within " + dueDate);
                    String reduceCopyQuery = "UPDATE books set available_copies = available_copies - 1 WHERE book_id = ?";
                    preparedStatement = connection.prepareStatement(reduceCopyQuery);
                    preparedStatement.setInt(1, book_id);
                    preparedStatement.executeUpdate();
                }
                else {
                    System.out.println("Please try after a while........");
                }

            }
        }
        catch (SQLException e) {
            System.out.println("Please borrow the book after a while......");
        }
        System.out.print("Do you want to take another book ?(yes / no) : ");
        if (scanner.nextLine().equalsIgnoreCase("yes"))
            borrowBooks();
    }

    public void returnBooks() {
        System.out.print("Enter the book id you wanna return : ");
        int bookId = scanner.nextInt();
        scanner.nextLine();
        Fine fine = new Fine(this.email);
        fine.calculateFine(bookId);
        String removeQuery = "DELETE FROM books_tracker WHERE email = ? AND book_id = ?;";
        try {
            preparedStatement = connection.prepareStatement(removeQuery);
            preparedStatement.setString(1, email);
            preparedStatement.setInt(2, bookId);
            if (preparedStatement.executeUpdate() == 1) {
                System.out.println("You have returned the book successfully....");
                String bookCopyIncrease = "UPDATE books " +
                        "SET available_copies = available_copies + 1 " +
                        "WHERE book_id = ?;";
                preparedStatement = connection.prepareStatement(bookCopyIncrease);
                preparedStatement.setInt(1, bookId);
                preparedStatement.executeUpdate();
                fine.payFine();
            }
            else {
                System.out.println("Please enter the correct book_id");
                returnBooks();
            }
        }
        catch (SQLException e) {
            System.out.println("Sorry!!!! little problem in our server....");
            System.out.println("Please return the book after a while....");
        }
    }

    public void payFine() {
        new Fine(this.email).payFine();
    }

}
