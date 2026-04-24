package org.furzefield;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class SetupDatabase {

     void createTables() {

        createUserTable();
        createLessons();
        createSampleBookings();

    }

    void createUserTable() {

        String sql = """
            CREATE TABLE IF NOT EXISTS members (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name TEXT NOT NULL,
                email TEXT UNIQUE NOT NULL,
                password TEXT NOT NULL,
                type TEXT NOT NULL
            );
        """;

        try {
            Connection conn = Database.connect();
            if(conn != null) {
                Statement stmt = conn.createStatement();
                stmt.execute(sql);
            }

        } catch (Exception e) {
            System.out.println("Exception : "+e);
        }
    }

    public static void createLessons() {
        String[] types = {"Yoga", "Zumba", "Box Fit", "Aquacise", "Body Blitz"};
        String[] days = {"Saturday", "Sunday"};
        String[] times = {"Morning", "Afternoon", "Evening"};

        int id = 1;
        int typeIndex = 0;

        for (int week = 1; week <= 8; week++) {
            for (String day : days) {
                for (String time : times) {

                    String type = types[typeIndex % types.length];
                    double price = getPrice(type);

                    Database.lessons.add(
                            new Lesson(id++, week, type, day, time, price)
                    );

                    typeIndex++;
                }
            }
        }
    }

    public static void createSampleBookings() {

         createSampleUsers();

        String[] users = {
                "john@gmail.com",
                "james@gmail.com",
                "sarah@gmail.com",
                "connor@gmail.com",
                "arthur@gmail.com"
        };

        int bookingId = 1;

        for (int i = 0; i < Database.lessons.size(); i++) {

            Lesson lesson = Database.lessons.get(i);

            int count = 2 + (i % 3);

            for (int j = 0; j < count && j < users.length; j++) {

                Booking b = new Booking(bookingId++, users[j], lesson);

                // make some attended
                if (j % 2 == 0) {
                    b.attendLesson(4, "Good session");
                }

                Database.bookings.add(b);
                lesson.bookings.add(b);
            }
        }

        Database.bookingCounter = bookingId;
    }

    public static void createSampleUsers() {

        String sql = "INSERT OR IGNORE INTO members(name, email, password, type) VALUES(?,?,?,?)";

        String[][] users = {
                {"John", "john@gmail.com", "pass123", "user"},
                {"James", "james@gmail.com", "pass123", "user"},
                {"Sarah", "sarah@gmail.com", "pass123", "user"},
                {"Connor", "connor@gmail.com", "pass123", "user"},
                {"Arthur", "arthur@gmail.com", "pass123", "user"}
        };

        try {
            Connection conn = Database.connect();

            if (conn != null) {
                PreparedStatement ps = conn.prepareStatement(sql);

                for (String[] user : users) {
                    try {
                        ps.setString(1, user[0]);
                        ps.setString(2, user[1]);
                        ps.setString(3, user[2]);
                        ps.setString(4, user[3]);
                        ps.executeUpdate();
                    } catch (Exception e) {
                        // ignore duplicates (email already exists)
                    }
                }

                System.out.println("Sample users added.");

            } else {
                System.out.println("DB connection failed.");
            }

        } catch (Exception e) {
            System.out.println("Error adding sample users: " + e);
        }
    }

    public static double getPrice(String type) {
        switch (type) {
            case "Yoga": return 10;
            case "Zumba": return 12;
            case "Box Fit": return 15;
            case "Aquacise": return 8;
            case "Body Blitz": return 14;
            default: return 10;
        }
    }
}
