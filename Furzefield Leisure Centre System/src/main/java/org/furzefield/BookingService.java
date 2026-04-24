package org.furzefield;

public class BookingService {

    public static void viewTimetable() {

        for (Lesson l : Database.lessons) {
            System.out.println(
                    "ID: " + l.id +
                            " | Week: " + l.week +
                            " | " + l.type +
                            " | " + l.day +
                            " | " + l.time +
                            " | £" + l.price +
                            " | Booked: " + l.bookings.size()
            );
        }
    }

    public static void viewByDay(String day) {

        System.out.println("\n===== Timetable (" + day + ") =====");

        for (Lesson l : Database.lessons) {
            if (l.day.equalsIgnoreCase(day)) {

                System.out.println(
                        "ID: " + l.id +
                                " | Week: " + l.week +
                                " | " + l.type +
                                " | " + l.time +
                                " | £" + l.price +
                                " | Booked: " + l.bookings.size()
                );
            }
        }
    }

    public static void viewByType(String type) {

        System.out.println("\n===== Timetable (" + type + ") =====");

        for (Lesson l : Database.lessons) {
            if (l.type.equalsIgnoreCase(type)) {

                System.out.println(
                        "ID: " + l.id +
                                " | Week: " + l.week +
                                " | " + l.day +
                                " | " + l.time +
                                " | £" + l.price +
                                " | Booked: " + l.bookings.size()
                );
            }
        }
    }

    public static void bookLesson(String email, int lessonId) {

        Lesson selected = null;

        // find lesson
        for (Lesson l : Database.lessons) {
            if (l.id == lessonId) {
                selected = l;
                break;
            }
        }

        if (selected == null) {
            System.out.println("Lesson not found.");
            return;
        }

        // check capacity
        if (selected.isFull()) {
            System.out.println("Lesson is full.");
            return;
        }

        // check duplicate
        for (Booking b : Database.bookings) {
            if (b.memberEmail.equals(email) && b.lesson.id == lessonId) {
                System.out.println("You already booked this lesson.");
                return;
            }
        }

        // create booking
        Booking booking = new Booking(
                Database.bookingCounter++,
                email,
                selected
        );

        Database.bookings.add(booking);
        selected.bookings.add(booking);

        System.out.println("✅ Booking successful! ID: " + booking.id);
    }
}