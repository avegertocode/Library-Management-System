package project;
import java.util.Scanner;

public class LibrarySystem {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        BookDAO bookDao = new BookDAO();
        IssueDAO issueDao = new IssueDAO();

        while (true) {
            System.out.println("\n======= LIBRARY MENU =======");
            System.out.println("1. Add Book");
            System.out.println("2. View Books");
            System.out.println("3. Search Book");
            System.out.println("4. Issue Book");
            System.out.println("5. Return Book");
            System.out.println("6. View Issued Books");
            System.out.println("7. Exit");
            System.out.print("Choose an option: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> bookDao.addBook();
                case 2 -> bookDao.viewBooks();
                case 3 -> bookDao.searchBook();
                case 4 -> issueDao.issueBook();
                case 5 -> issueDao.returnBook();
                case 6 -> issueDao.viewIssuedBooks();
                case 7 -> {
                    System.out.println("Exiting system...");
                    System.exit(0);
                }
                default -> System.out.println("Invalid choice! Try again.");
            }
        }
    }
}
