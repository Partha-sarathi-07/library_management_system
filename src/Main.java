import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        String url = "jdbc:mysql://localhost:3306/library_management";
        String username = "sarathi";
        String password = "root";
        String query = "UPDATE members SET password = ? WHERE user_id = ?";
        Connection connection = DriverManager.getConnection(url, username, password);
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        for (int i = 6; i <= 10; i++) {
            Scanner scanner = new Scanner(System.in);
            String passwor = scanner.nextLine();
            preparedStatement.setString(1, Integer.toString(passwor.hashCode()));
            preparedStatement.setInt(2, i);
            int row = preparedStatement.executeUpdate();
            System.out.println("Rows updated = " + row);
        }
    }
}