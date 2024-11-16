package com.sarathi.library_management;

import java.sql.PreparedStatement;

public class Staff extends DbHandler implements Privileges {

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

//    public void getName() {
//        String query = "SELECT name FROM library_staffs WHERE email = "
//    }
}
