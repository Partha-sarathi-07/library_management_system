import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        String url = "jdbc:mysql://localhost:3306/library_management";
        String username = "sarathi";
        String password = "root";
        String query = "UPDATE staffs SET password = ? WHERE email = ?";
        Connection connection = DriverManager.getConnection(url, username, password);
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        System.out.println("connection established");
        String query2 = "SELECT email, password FROM staffs;";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query2);
        Scanner scanner = new Scanner(System.in);

        while (resultSet.next()) {
            String email = resultSet.getString(1);
            String passwor = resultSet.getString(2);

            preparedStatement.setString(1, String.valueOf(passwor.hashCode()));
            preparedStatement.setString(2, email);

            int row = preparedStatement.executeUpdate();
            System.out.println("rows updated = " + row);
        }
    }
}