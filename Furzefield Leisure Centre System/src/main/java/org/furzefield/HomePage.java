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

        do {
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
        }while (choice != 0);
    }

    private static void login() {

        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        String sql = "SELECT * FROM members WHERE email = ? AND password = ?";

        try {
            Connection conn = Database.connect();

            if (conn != null) {
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, email);
                ps.setString(2, password);

                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    String name = rs.getString("name");
                    System.out.println("\nLogin successful! Welcome, " + name);
                    MainMenu.showMenu(email,name);
                } else {
                    System.out.println("Invalid email or password.");
                    Home();
                }

            } else {
                System.out.println("Database connection failed.");
            }

        } catch (Exception e) {
            System.out.println("Error during login: " + e.getMessage());
        }
    }

    private static void register() {

        int resp=0;

        System.out.print("Enter name: ");
        String name = scanner.nextLine();

        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        try{
            resp = updateInDatabase(name, email, password);
        }
        catch(Exception e){
            System.out.println("Error during registration: " + e.getMessage());
        }
        if (resp==0){
            MainMenu.showMenu(email,name);
        }
        else
            register();
    }

    private static int updateInDatabase(String name, String email, String password) {
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
                return 0;
            }
            else
                System.out.println("Registration failed.");
            return 1;

        } catch (Exception e) {
            System.out.println("Email already exists.");
            return 1;
        }
    }
}