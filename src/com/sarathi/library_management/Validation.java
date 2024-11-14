package com.sarathi.library_management;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Validation extends DbConnection {

    private static PreparedStatement preparedStatement;

    public static boolean login(String email, String password, boolean isManagement) throws SQLException {
        String tableName = isManagement ? "library_staffs" : "members";
        String query = "SELECT COUNT(email) FROM " + tableName + " WHERE email = ? AND password = ?";

        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, email);
        preparedStatement.setString(2, String.valueOf(password.hashCode()));

        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return resultSet.getInt(1) == 1;
    }
}
