package com.sarathi.library_management;

public class Member implements Privileges{

    @Override
    public void showPrivileges() {
        System.out.println("1. View Available Books");
        System.out.println("2. Borrow Books");
        System.out.println("3. Return Books");
    }
}
