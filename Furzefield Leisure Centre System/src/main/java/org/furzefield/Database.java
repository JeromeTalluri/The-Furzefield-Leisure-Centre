package org.furzefield;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;

public class Database {

    private static final String URL = "jdbc:sqlite:flc.db";

    public static Connection connect() {

        try {
            return DriverManager.getConnection(URL);
        } catch (Exception e) {
            System.out.println("Exception :"+e);
        }

        return null;
    }

    // all lessons (48)
    public static List<Lesson> lessons = new ArrayList<>();

    // all bookings
    public static List<Booking> bookings = new ArrayList<>();

    // auto increment booking id
    public static int bookingCounter = 1;
}