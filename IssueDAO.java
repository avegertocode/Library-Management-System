package project;
import java.sql.*;
import java.util.Scanner;

public class IssueDAO {

    Scanner sc = new Scanner(System.in);

    // Issue Book
    public void issueBook() {
        try (Connection con = DBConnection.getConnection()) {
            System.out.print("Enter Book ID: ");
            int bookId = sc.nextInt();
            sc.nextLine();

            System.out.print("Enter Student Name: ");
            String student = sc.nextLine();

            // Check if available
            String check = "SELECT quantity FROM books WHERE id = ?";
            PreparedStatement ps1 = con.prepareStatement(check);
            ps1.setInt(1, bookId);
            ResultSet rs = ps1.executeQuery();

            if (rs.next() && rs.getInt("quantity") > 0) {

                // Issue book
                String sql = "INSERT INTO issued_books (book_id, student_name, issue_date) VALUES (?, ?, CURDATE())";
                PreparedStatement ps2 = con.prepareStatement(sql);
                ps2.setInt(1, bookId);
                ps2.setString(2, student);
                ps2.executeUpdate();

                // Reduce quantity
                String update = "UPDATE books SET quantity = quantity - 1 WHERE id = ?";
                PreparedStatement ps3 = con.prepareStatement(update);
                ps3.setInt(1, bookId);
                ps3.executeUpdate();

                System.out.println("Book issued successfully!");

            } else {
                System.out.println("Book not available!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // Show all issued books
    public void viewIssuedBooks() {
        try (Connection con = DBConnection.getConnection()) {

            String sql = """
                    SELECT i.issue_id, b.title, i.book_id, i.student_name, 
                           i.issue_date, i.return_date
                    FROM issued_books i
                    JOIN books b ON i.book_id = b.id
                    ORDER BY i.issue_id DESC
                    """;

            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            System.out.println("\n====== ISSUED BOOKS LIST ======");
            while (rs.next()) {
                System.out.println("Issue ID: " + rs.getInt("issue_id"));
                System.out.println("Book ID: " + rs.getInt("book_id"));
                System.out.println("Title: " + rs.getString("title"));
                System.out.println("Student: " + rs.getString("student_name"));
                System.out.println("Issued On: " + rs.getDate("issue_date"));
                System.out.println("Returned On: " + rs.getDate("return_date"));
                System.out.println("-----------------------------------");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // Return book
    public void returnBook() {
        try (Connection con = DBConnection.getConnection()) {
            System.out.print("Enter Issue ID: ");
            int issueId = sc.nextInt();

            // Get book_id first
            String fetch = "SELECT book_id FROM issued_books WHERE issue_id = ?";
            PreparedStatement ps1 = con.prepareStatement(fetch);
            ps1.setInt(1, issueId);
            ResultSet rs = ps1.executeQuery();

            if (rs.next()) {
                int bookId = rs.getInt("book_id");

                // Update return date
                String sql = "UPDATE issued_books SET return_date = CURDATE() WHERE issue_id = ?";
                PreparedStatement ps2 = con.prepareStatement(sql);
                ps2.setInt(1, issueId);
                ps2.executeUpdate();

                // Increase quantity
                String update = "UPDATE books SET quantity = quantity + 1 WHERE id = ?";
                PreparedStatement ps3 = con.prepareStatement(update);
                ps3.setInt(1, bookId);
                ps3.executeUpdate();

                System.out.println("Book returned successfully!");

            } else {
                System.out.println("Invalid Issue ID!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
