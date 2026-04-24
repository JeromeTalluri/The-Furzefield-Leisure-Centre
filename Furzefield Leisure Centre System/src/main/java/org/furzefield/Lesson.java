package org.furzefield;

import java.util.ArrayList;
import java.util.List;

public class Lesson {

    int id;
    int week;   // 🔥 IMPORTANT

    String type;
    String day;
    String time;
    double price;

    int capacity = 4;
    List<Booking> bookings = new ArrayList<>();

    public Lesson(int id, int week, String type, String day, String time, double price) {
        this.id = id;
        this.week = week;
        this.type = type;
        this.day = day;
        this.time = time;
        this.price = price;
    }

    public boolean isFull() {
        return bookings.size() >= capacity;
    }
}
