package com.sarathi.library_management;

import java.sql.SQLException;
import java.util.Scanner;

public class LibraryService {

    private final static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws SQLException {
        if (!DbHandler.createConnection()) return;

        System.out.println("Hello!!! Welcome to Sarathi Library");
        User user = doSignUpOrLogin();
        while (user == null)    user = doSignUpOrLogin();
        System.out.println("\nHurray!!!! You have logged in successfully");




    }

    public static User doSignUpOrLogin() {
        System.out.print("\nSign Up(1) / Login (2) : ");
        int option = scanner.nextInt();
        scanner.nextLine();

        if (option == 1) Validation.signUp();

        System.out.print("\nEnter your email : ");
        String email = scanner.nextLine();
        System.out.print("Enter your password : ");
        String password = scanner.nextLine();
        System.out.print("Are you an library Staff (yes / no) : ");
        boolean isManagement = scanner.nextLine().equalsIgnoreCase("yes");
        if (!Validation.login(email, password, isManagement))
            return null;
        if (isManagement)
            return new Staff(email);
        return new Member(email);
    }

}
