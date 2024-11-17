package com.sarathi.library_management;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Staff extends SearchBooks implements Privileges {
    private final Scanner scanner = new Scanner(System.in);
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


    public void addBooks() {

        System.out.print("Do you want to add a new book \"(yes)\" or increase the copies of available book \"(no)\"  or \"(exit)\" to exit : ");
        String choice = scanner.nextLine().toLowerCase();

        if (choice.equals("exit"))
            return;

        else if (choice.equals("yes")) {

            System.out.print("\nEnter the title of the book : ");
            String title = scanner.nextLine();
            System.out.print("Enter the author of the book : ");
            String author = scanner.nextLine();
            System.out.print("Enter the book genre : ");
            String genre = scanner.nextLine();
            System.out.print("Enter the number of book : ");
            int count = scanner.nextInt();
            scanner.nextLine();

            String addQuery = "INSERT INTO books (title, author, genre, available_copies) VALUES (?, ?, ?, ?)";
            try {
                preparedStatement = connection.prepareStatement(addQuery);
                preparedStatement.setString(1, title);
                preparedStatement.setString(2, author);
                preparedStatement.setString(3, genre);
                preparedStatement.setInt(4, count);
                preparedStatement.executeUpdate();
                System.out.println("The book has been added successfull hurray!!!!");
            } catch (SQLException e) {
                System.out.println("Please try adding the books after a while");
            }

        }
        else if (choice.equals("no")) {
            System.out.print("\nEnter the book title : ");
            String title = scanner.nextLine();
            System.out.print("Enter the new copies : ");
            int copies = scanner.nextInt();
            scanner.nextLine();
            String fetchQuery =  "SELECT book_id, available_copies FROM books WHERE title LIKE '%" + title + "%';";
            try {
                preparedStatement = connection.prepareStatement(fetchQuery);
                ResultSet resultSet = preparedStatement.executeQuery();
                resultSet.next();
                int book_id = resultSet.getInt(1);
                copies += resultSet.getInt(2);
                String updateQuery = "UPDATE books SET available_copies = ? WHERE book_id = ?;";
                preparedStatement = connection.prepareStatement(updateQuery);
                preparedStatement.setInt(1, copies);
                preparedStatement.setInt(2, book_id);
                preparedStatement.executeUpdate();
                System.out.println("Number of copies has been increased successfully......");
            }
            catch (SQLException e) {
                System.out.println("Please enter Valid book name");
            }

        }
        else {
            System.out.println("Please enter an valid option...");
        }

        System.out.print("\nDo you wish to add more books (yes / no) : ");
        if (scanner.nextLine().equalsIgnoreCase("yes"))
            addBooks();
    }


    public void removeBooks() {
        System.out.print("Enter the title of the book : ");
        String title = scanner.nextLine();

        try {
            int available_copies = showByTitle(title).getInt(5);
            System.out.print("Enter the number of copies of book you want to remove : ");
            int remove_copies = scanner.nextInt();
            scanner.nextLine();
            if (remove_copies > available_copies)
                throw new SQLException();
            else if (remove_copies == available_copies) {
                String query = "DELETE FROM books WHERE title LIKE '%" + title + "%';";
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.executeUpdate();
                System.out.println("The book has been removed successfully...!!!!");
            }
            else {
                String removeQuery = "UPDATE books SET available_copies = ? WHERE title LIKE '%" + title + "%';";
                preparedStatement = connection.prepareStatement(removeQuery);
                preparedStatement.setInt(1, available_copies - remove_copies);
                preparedStatement.executeUpdate();
                System.out.println("The number of copies has been reduced successfully.....");
            }
        } catch (SQLException e) {
            System.out.println("Enter the valid number of copies to remove....");
        }
    }
}
