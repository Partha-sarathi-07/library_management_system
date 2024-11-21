package com.sarathi.library_management;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Staff extends User implements Privileges {
    private final Scanner scanner = new Scanner(System.in);
    private PreparedStatement preparedStatement;


    @Override
    public void showPrivileges() {
        System.out.println("\n1. Add Books");
        System.out.println("2. Remove Books");
        System.out.println("3. Show All Books");
        System.out.println("4. Search Books");
        System.out.println("5. Show Non Available Books");
        System.out.println("6. Show All Members");
        System.out.println("7. Show Members and Books Taken By Them");
        System.out.println("8. Exit\n");
    }

    public void addBooks() {

        System.out.println();
        System.out.print("Do you want to add a new book \"(yes)\" or increase the copies of available book \"(no)\"  or \"(exit)\" to exit : ");
        String choice = scanner.nextLine().toLowerCase();

        switch (choice) {
            case "exit" -> {
                return;
            }
            case "yes" -> {

                System.out.print("\nEnter the title of the book : ");
                String title = scanner.nextLine();
                System.out.print("Enter the author of the book : ");
                String author = scanner.nextLine();
                System.out.print("Enter the book genre : ");
                String genre = selectGenre();
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
            case "no" -> {
                System.out.print("\nEnter the book title : ");
                String title = scanner.nextLine();
                System.out.print("Enter the new copies : ");
                int copies = scanner.nextInt();
                scanner.nextLine();
                String fetchQuery = "SELECT book_id, available_copies " +
                        "FROM books " +
                        "WHERE title LIKE '%" + title + "%';";
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
                } catch (SQLException e) {
                    System.out.println("Please enter Valid book name");
                }

            }
            default -> System.out.println("Please enter an valid option...");
        }

        System.out.print("\nDo you wish to add more books (yes / no) : ");
        if (scanner.nextLine().equalsIgnoreCase("yes"))
            addBooks();
    }

    public void removeBooks() {
        System.out.println();
        System.out.print("Enter the Correct title of the book : ");
        String title = scanner.nextLine();

        try {
            int available_copies = calculateAvailableCopies(title);
            System.out.print("\nEnter the number of copies of book you want to remove : ");
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
                String removeQuery = "UPDATE books SET available_copies = ? " +
                        "WHERE title LIKE '%" + title + "%';";
                preparedStatement = connection.prepareStatement(removeQuery);
                preparedStatement.setInt(1, available_copies - remove_copies);
                preparedStatement.executeUpdate();
                System.out.println("The number of copies has been reduced successfully.....");
            }
        } catch (SQLException e) {
            System.out.println("Enter the valid number of copies to remove....");
        }
        System.out.print("Do you want to remove another book ? (yes / no) : ");
        if (scanner.nextLine().equalsIgnoreCase("yes"))
            removeBooks();
    }

    private int calculateAvailableCopies(String title) {
        String fetchQuery = "SELECT available_copies FROM books WHERE title LIKE '%" + title + "%'";
        try {
            preparedStatement = connection.prepareStatement(fetchQuery);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
                return resultSet.getInt(1);
        }
        catch (SQLException e) {
            System.out.println("Unable to calculate availabe copies.. Please try after a while....");
        }
        return 0;
    }

    public void showAllMembers() {
        String fetchQuery = "SELECT * FROM members";
        try {
            preparedStatement = connection.prepareStatement(fetchQuery);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                System.out.printf("\nemail = %s, name = %s, phone_number = %s", resultSet.getString(1), resultSet.getString(2), resultSet.getString(3));
            }
            System.out.println();
        } catch (SQLException e) {
            System.out.println("There is a little problem in our side...");
            System.out.println("Please view the members after a while");
        }
    }

    public void showNonAvailableBooks() {
        String fetchQuery = "SELECT book_id, title, author, genre " +
                "FROM books " +
                "WHERE available_copies = 0;";
        try {
            preparedStatement = connection.prepareStatement(fetchQuery);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                System.out.printf("Book id = %d, Title = %s, Author = %s, Genre = %s", resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4));
            }
        }
        catch (SQLException e) {
            System.out.println("Unable to fetch the books..");
            System.out.println("Please try after a while");
        }
    }

    public void showMembersAndBorrowedBooks() {
        String fetchQuery = "SELECT members.email, members.name, books.book_id, books.title " +
                            "FROM members JOIN books_tracker " +
                            "ON members.email = books_tracker.email " +
                            "JOIN books " +
                            "ON books_tracker.book_id = books.book_id";
        try {
            preparedStatement = connection.prepareStatement(fetchQuery);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                System.out.printf("\nEmail = %s, Name = %s, Book Id = %d, Title = %s", resultSet.getString(1), resultSet.getString(2), resultSet.getInt(3), resultSet.getString(4));
            }
            System.out.println();
        }
        catch (Exception e) {
            System.out.println("Kindly check after a while.....");
            System.out.println("Thank you for your understanding!!!");
        }
    }
}
