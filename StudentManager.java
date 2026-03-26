import java.sql.*;
import java.util.Scanner;

/**
 * Student Record Management System
 * Features: CRUD operations, JDBC integration, and SQL Injection prevention.
 */
public class StudentManager {

    // Database Configuration - Update these with your local MySQL details
    private static final String URL = "jdbc:mysql://localhost:3306/school_db";
    private static final String USER = "root"; 
    private static final String PASS = "password"; 

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS)) {
            System.out.println("--- Connected to Database ---");
            Scanner sc = new Scanner(System.in);
            
            while (true) {
                System.out.println("\n--- Student Management System ---");
                System.out.println("1. Add Student");
                System.out.println("2. View All Students");
                System.out.println("3. Update Student Course");
                System.out.println("4. Delete Student");
                System.out.println("5. Exit");
                System.out.print("Choose an option: ");
                
                int choice = sc.nextInt();
                sc.nextLine(); // Consume newline

                switch (choice) {
                    case 1 -> addStudent(conn, sc);
                    case 2 -> viewStudents(conn);
                    case 3 -> updateStudent(conn, sc);
                    case 4 -> deleteStudent(conn, sc);
                    case 5 -> {
                        System.out.println("Exiting System...");
                        return;
                    }
                    default -> System.out.println("Invalid choice. Try again.");
                }
            }
        } catch (SQLException e) {
            System.err.println("Database Error: " + e.getMessage());
        }
    }

    private static void addStudent(Connection conn, Scanner sc) throws SQLException {
        System.out.print("Enter Name: "); String name = sc.nextLine();
        System.out.print("Enter Email: "); String email = sc.nextLine();
        System.out.print("Enter Course: "); String course = sc.nextLine();

        String sql = "INSERT INTO students (name, email, course) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, email);
            pstmt.setString(3, course);
            pstmt.executeUpdate();
            System.out.println("✔ Student record added successfully!");
        }
    }

    private static void viewStudents(Connection conn) throws SQLException {
        String sql = "SELECT * FROM students";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println("\nID | Name | Email | Course");
            System.out.println("---------------------------");
            while (rs.next()) {
                System.out.printf("%d | %s | %s | %s%n",
                    rs.getInt("id"), rs.getString("name"), rs.getString("email"), rs.getString("course"));
            }
        }
    }

    private static void updateStudent(Connection conn, Scanner sc) throws SQLException {
        System.out.print("Enter Student ID to update: ");
        int id = sc.nextInt();
        sc.nextLine(); 
        System.out.print("Enter New Course Name: ");
        String newCourse = sc.nextLine();

        String sql = "UPDATE students SET course = ? WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newCourse);
            pstmt.setInt(2, id);
            int rows = pstmt.executeUpdate();
            if (rows > 0) System.out.println("✔ Record updated.");
            else System.out.println("❌ Student ID not found.");
        }
    }

    private static void deleteStudent(Connection conn, Scanner sc) throws SQLException {
        System.out.print("Enter Student ID to delete: ");
        int id = sc.nextInt();

        String sql = "DELETE FROM students WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int rows = pstmt.executeUpdate();
            if (rows > 0) System.out.println("✔ Record deleted.");
            else System.out.println("❌ Student ID not found.");
        }
    }
}
