package project;
import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/librarydb";
    private static final String USER = "root";
    private static final String PASSWORD = "Omi@mysql2025";

    public static Connection getConnection() {
        Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (Exception e) {
            System.out.println("Database Error: " + e.getMessage());
        }
        return con;
    }
}
