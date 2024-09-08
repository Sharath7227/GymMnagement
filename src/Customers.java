import java.sql.*;
import java.util.Scanner;

public class Customers {
    private static final String URL = "jdbc:mysql://localhost:3306/Gym?useSSL=false";
    private static final String USER = "root";
    private static final String PASSWORD = "Chinnu@9";

    public static void main(String[] args) {
        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return;
        }

        Scanner sc = new Scanner(System.in);
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            // Establish the connection
            connection = DriverManager.getConnection(URL, USER, PASSWORD);

            // Fetch the latest User ID
            String fetchIdQuery = "SELECT Id FROM Customers ORDER BY Id DESC LIMIT 1";
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(fetchIdQuery);

            String lastId = "MAG000000000"; // Default if no user exists
            if (resultSet.next()) {
                lastId = resultSet.getString("Id");
            }

            // Increment the ID
            String numericPart = lastId.substring(3); // Extract numeric part
            int nextId = Integer.parseInt(numericPart) + 1;
            String newUserId = String.format("MAG%09d", nextId); // Format as MAG000000001

            // Prompt user for input
            System.out.println("Enter the user's name:");
            String name = sc.nextLine();

            System.out.println("Enter the user's email:");
            String email = sc.nextLine();

            System.out.println("Enter the user's password:");
            String password = sc.nextLine(); // Note: In practice, ensure this is hashed

            // SQL Insert query
            String sql = "INSERT INTO Customers (Id, Name, Email, Password) VALUES (?, ?, ?, ?)";

            // Prepare the statement
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, newUserId); // Use the new auto-incremented ID
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, password); // Note: In practice, hash the password before storing

            // Execute the statement
            int rowsAffected = preparedStatement.executeUpdate();

            // Check if the insertion was successful
            if (rowsAffected > 0) {
                System.out.println("User registered successfully with ID: " + newUserId);
            } else {
                System.out.println("Failed to register the user.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close resources
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
                sc.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
