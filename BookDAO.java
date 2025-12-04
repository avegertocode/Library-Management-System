package project;
import java.sql.*;
import java.util.Scanner;

public class BookDAO {

    Scanner sc = new Scanner(System.in);

    // 1. Add Book
    public void addBook() {
        try (Connection con = DBConnection.getConnection()) {
            System.out.print("Enter Book Title: ");
            String title = sc.nextLine();

            System.out.print("Enter Author: ");
            String author = sc.nextLine();

            System.out.print("Enter Quantity: ");
            int qty = sc.nextInt();
            sc.nextLine();

            String sql = "INSERT INTO books (title, author, quantity) VALUES (?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, title);
            ps.setString(2, author);
            ps.setInt(3, qty);

            ps.executeUpdate();
            System.out.println("Book added successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 2. View All Books
    public void viewBooks() {
        try (Connection con = DBConnection.getConnection()) {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM books");

            System.out.println("\n--- Book List ---");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") +
                                   ", Title: " + rs.getString("title") +
                                   ", Author: " + rs.getString("author") +
                                   ", Qty: " + rs.getInt("quantity"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 3. Search Book by Name
    public void searchBook() {
        try (Connection con = DBConnection.getConnection()) {
            System.out.print("Enter title to search: ");
            String title = sc.nextLine();

            String sql = "SELECT * FROM books WHERE title LIKE ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, "%" + title + "%");

            ResultSet rs = ps.executeQuery();

            System.out.println("\n--- Search Results ---");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") +
                                   ", Title: " + rs.getString("title") +
                                   ", Author: " + rs.getString("author") +
                                   ", Qty: " + rs.getInt("quantity"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
