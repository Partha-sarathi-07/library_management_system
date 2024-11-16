package com.sarathi.library_management;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class Staff extends DbHandler implements Privileges {
    private Scanner scanner = new Scanner(System.in);
    private PreparedStatement preparedStatement;

    @Override
    public void showPrivileges() {
        System.out.println("1. Add Books");
        System.out.println("2. Remove Books");
        System.out.println("3. Show All Books");
        System.out.println("4. Show Currently Available Books");
        System.out.println("5. Show Book Copies Available");
        System.out.println("6. Show Non Available Books");
        System.out.println("7. Show Books Not Returned Members");
    }

    public void addBooks() throws SQLException {

        while (true) {

            System.out.print("\nEnter the title of the book : ");
            String title = scanner.nextLine();
            System.out.print("Enter the author of the book : ");
            String author = scanner.nextLine();
            System.out.print("Enter the book genre : ");
            String genre = scanner.nextLine();
            System.out.print("Enter the number of book : ");
            int count = scanner.nextInt();
            scanner.nextLine();

            String query = "INSERT INTO books (title, author, genre, available_copies) VALUES (?, ?, ?, ?);";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, author);
            preparedStatement.setString(3, genre);
            preparedStatement.setInt(4, count);

            preparedStatement.executeUpdate();
            System.out.println("Book has been added Successfully");

            System.out.print("Do you wish to add more books (yes / no) : ");
            if (scanner.nextLine().toLowerCase().equals("no"))
                break;
        }
    }

    public void showBookDetail() throws SQLException{
        System.out.print("Enter the book title : ");
        String bookname = scanner.nextLine();
        String query = "SELECT * FROM books WHERE title like = '%" + bookname + "%'";
        preparedStatement = connection.prepareStatement(query);
//        preparedStatement.setString(1, bookname);
        ResultSet resultSet = preparedStatement.executeQuery();

        resultSet.next();
        if (resultSet != null) {
            System.out.println("Book id = " + resultSet.getInt(1));
            System.out.println("title = " + resultSet.getString(2));
            System.out.println("author = " + resultSet.getString(3));
            System.out.println("genre = " + resultSet.getString(4));
            System.out.println("Available copies = " + resultSet.getInt(5));
        }
        System.out.println("Do You want to know about any other book (yes / no) : ");
        if (scanner.nextLine().toLowerCase() == "yes")
            showBookDetail();
    }

    public void removeBooks() {

        while (true) {

        }
    }
}
