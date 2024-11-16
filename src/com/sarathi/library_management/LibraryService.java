package com.sarathi.library_management;

import java.sql.SQLException;
import java.util.Scanner;

public class LibraryService {

    public static void main(String[] args) throws SQLException {
        if (!DbHandler.createConnection()) return;

        var scanner = new Scanner(System.in);

        System.out.println("Welcome to Sarathi Library :");
        System.out.print("Signup (1)/Login (2) : ");

        if (scanner.nextInt() == 1) Validation.signUp();

        System.out.println("Enter your email : ");
        String email = scanner.nextLine();

        System.out.println("Enter your password : ");
        String password = scanner.nextLine();

        System.out.println("Are you an library staff (yes/no) : ");
        boolean isStaff = (scanner.nextLine().toLowerCase().equals("yes"));
        String name;

        if (Validation.login(email, password, isStaff)) {

            Notifications.displayWelcomeMsg(email, isStaff);

            if (isStaff) {
                Staff staff = new Staff();
                staff.showPrivileges();
            }
            else {
                Member member = new Member();
                member.showPrivileges();
            }
        }
        else {
            System.out.println("Username or password is incorrect");
        }
    }
}
