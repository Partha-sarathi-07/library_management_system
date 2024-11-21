package com.sarathi.library_management;

import java.util.Scanner;

public class LibraryService {

    private final static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        if (!DbHandler.createConnection()) return;

        System.out.println("Hello!!! Welcome to Sarathi Library");
        Pair userDetail = doSignUpOrLogin();
        String email = userDetail.email;
        boolean isStaff = userDetail.isStaff;
        Notifications.displayWelcomeMsg(email, isStaff);
        if (isStaff) {
            Staff staff = new Staff();
            outerLoop : while (true) {
                staff.showPrivileges();
                System.out.print("Select your option (1 / 2 / 3 / 4 / 5 / 6 / 7 / 8) : ");
                int option = scanner.nextInt();
                scanner.nextLine();

                switch (option) {
                    case 1 -> staff.addBooks();
                    case 2 -> staff.removeBooks();
                    case 3 -> staff.showAllBooks();
                    case 4 -> searchBooks(staff);
                    case 5 -> staff.showNonAvailableBooks();
                    case 6 -> staff.showAllMembers();
                    case 7 -> staff.showMembersAndBorrowedBooks();
                    default ->  {
                        break outerLoop;
                    }
                }
            }
        }
        else {
            Member member = new Member(email);
            outerLoop : while (true) {
                member.showPrivileges();
                System.out.print("Select your option (1 / 2 / 3 / 4 / 5 / 6) : ");
                int option = scanner.nextInt();
                scanner.nextLine();

                switch (option) {
                    case 1 -> member.showAllBooks();
                    case 2 -> searchBooks(member);
                    case 3 -> member.borrowBooks();
                    case 4 -> member.returnBooks();
                    case 5 -> member.payFine();
                    default -> {
                        break outerLoop;
                    }
                }
            }
        }
        Notifications.displayThankYouMsg();
        DbHandler.closeConnection();

    }

    public static Pair doSignUpOrLogin() {

        String email;
        boolean isStaff;

        while (true) {
            System.out.print("\nSign Up(1) / Login (2) : ");
            int option = scanner.nextInt();
            scanner.nextLine();

            if (option == 1) Validation.signUp();

            System.out.print("\nEnter your email : ");
            email = scanner.nextLine();
            System.out.print("Enter your password : ");
            String password = scanner.nextLine();
            System.out.print("Are you an library Staff (yes / no) : ");
            isStaff = scanner.nextLine().equalsIgnoreCase("yes");
            if (Validation.login(email, password, isStaff))
                break;
        }

        return new Pair(email, isStaff);
    }

    public static void searchBooks(User user) {
        outerLoop: while (true) {
            System.out.println();
            user.showBookSearchOptions();
            System.out.print("Enter the option (1 / 2 / 3 / 4) : ");
            int searchOption = scanner.nextInt();
            scanner.nextLine();

            switch (searchOption) {
                case 1 -> user.searchByTitle();
                case 2 -> user.searchByAuthor();
                case 3 -> user.searchByGenre();
                default -> {
                    break outerLoop;
                }
            }
        }
    }

}
