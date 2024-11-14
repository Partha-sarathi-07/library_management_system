package com.sarathi.library_management;

import java.sql.SQLException;
import java.util.Scanner;

public class LibraryService {

    public static void main(String[] args) throws SQLException {
        if (!DbConnection.createConnection()) return;

        var scanner = new Scanner(System.in);

        System.out.println("Enter your email : ");
        String email = scanner.nextLine();

        System.out.println("Enter your password : ");
        String password = scanner.nextLine();

        System.out.println("Are you an library staff (yes/no) : ");
        boolean isStaff = (scanner.nextLine().toLowerCase().equals("yes"));

        if (Validation.login(email, password, isStaff)) {
            if (isStaff)
                ShowPrivileges.showStaffPrivileges();
            else
                ShowPrivileges.showMemberPrivileges();

        }
    }
}
