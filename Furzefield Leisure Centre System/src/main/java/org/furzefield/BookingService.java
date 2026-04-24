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

        for (Booking b : Database.bookings) {
            if (b.memberEmail.equals(email) && b.lesson.id == lessonId) {
                System.out.println("You already booked this lesson.");
                return;
            }
        }

        Booking booking = new Booking(
                Database.bookingCounter++,
                email,
                selected
        );

        Database.bookings.add(booking);
        selected.bookings.add(booking);

        System.out.println("✅ Booking successful! ID: " + booking.id);
    }

    public static void changeBooking(String email, int bookingId, int newLessonId) {

        Booking selectedBooking = null;

        for (Booking b : Database.bookings) {
            if (b.id == bookingId && b.memberEmail.equals(email)) {
                selectedBooking = b;
                break;
            }
        }

        if (selectedBooking == null) {
            System.out.println("❌ Booking not found.");
            return;
        }

        if (selectedBooking.status.equals("cancelled") ||
                selectedBooking.status.equals("attended")) {
            System.out.println("❌ Cannot change this booking.");
            return;
        }

        Lesson newLesson = null;

        for (Lesson l : Database.lessons) {
            if (l.id == newLessonId) {
                newLesson = l;
                break;
            }
        }

        if (newLesson == null) {
            System.out.println("❌ New lesson not found.");
            return;
        }

        if (selectedBooking.lesson.id == newLessonId) {
            System.out.println("❌ Already booked in this lesson.");
            return;
        }

        if (newLesson.isFull()) {
            System.out.println("❌ New lesson is full.");
            return;
        }

        for (Booking b : Database.bookings) {
            if (b.memberEmail.equals(email) && b.lesson.id == newLessonId) {
                System.out.println("❌ You already booked this lesson.");
                return;
            }
        }

        selectedBooking.lesson.bookings.remove(selectedBooking);

        selectedBooking.lesson = newLesson;

        newLesson.bookings.add(selectedBooking);

        selectedBooking.status = "changed";

        System.out.println("✅ Booking changed successfully.");
    }


    public static void attendLesson(String email, int bookingId, int rating, String review) {

        Booking selectedBooking = null;

        for (Booking b : Database.bookings) {
            if (b.id == bookingId && b.memberEmail.equals(email)) {
                selectedBooking = b;
                break;
            }
        }

        if (selectedBooking == null) {
            System.out.println("❌ Booking not found.");
            return;
        }

        if (selectedBooking.status.equals("attended")) {
            System.out.println("❌ Already attended.");
            return;
        }

        if (selectedBooking.status.equals("cancelled")) {
            System.out.println("❌ Cannot attend cancelled booking.");
            return;
        }

        if (rating < 1 || rating > 5) {
            System.out.println("❌ Rating must be between 1 and 5.");
            return;
        }

        selectedBooking.status = "attended";
        selectedBooking.rating = rating;
        selectedBooking.review = review;

        System.out.println("✅ Lesson attended and review submitted.");
    }
}