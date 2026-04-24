package org.furzefield;

import java.util.Scanner;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;

public class HomePage {

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        SetupDatabase setupdatabase = new SetupDatabase();

        setupdatabase.createTables();

        Home();
    }

    public static void Home() {
        int choice;


        System.out.println("\n===== Furzefield Leisure Centre System =====");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("0. Exit");
        System.out.print("Enter choice: ");

        choice = scanner.nextInt();
        scanner.nextLine();
        // clear buffer

        switch (choice) {

            case 1:
                login();
                break;

            case 2:
                register();
                break;

            case 0:
                System.out.println("Goodbye!");
                break;

            default:
                System.out.println("Invalid option.");
        }

    }

    private static void login() {

        
    }

    private static void register() {

        System.out.print("Enter name: ");
        String name = scanner.nextLine();

        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        try{
            updateInDatabase(name, email, password);
        }
        catch(Exception e){
            System.out.println("Error during registration: " + e.getMessage());
        }

        System.out.println("Registration successful!");
    }

    private static void updateInDatabase(String name, String email, String password) {
        String sql = "INSERT INTO members(name,email,password,type) VALUES(?,?,?,'user')";

        try {
            Connection conn = null;
            try {
                conn = Database.connect();
            } catch (Exception e) {
                System.out.println("Error while connecting to Database " +e);
            }

            if(conn != null) {
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, name);
                ps.setString(2, email);
                ps.setString(3, password);
                ps.executeUpdate();
                System.out.println("Registration successful.");
            }
            else
                System.out.println("Registration failed.");

        } catch (Exception e) {
            System.out.println("Email already exists.");
        }
    }
}