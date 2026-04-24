package org.furzefield;

import java.util.Scanner;

public class MainMenu {

    public static void showMenu(String email,String name) {

        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n===== Main Menu =====");
            System.out.println("1. View Timetable");
            System.out.println("2. Book Lesson");
            System.out.println("3. Attend Lesson");
            System.out.println("4. View My Bookings");
            System.out.println("5. Change Booking");
            System.out.println("6. Cancel Booking");
            System.out.println("7. Generate Reports");
            System.out.println("0. Logout");

            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    viewTimetable();
                    break;
                case 2:
                    bookLesson(email);
                    break;
                case 3:
                    attendLesson(email);
                    break;
                case 4:
                    viewMyBookings(email);
                    break;
                case 5:
                    changeBooking(email);
                    break;
                case 6:
                    cancelBooking(email);
                    break;
                case 0:
                    System.out.println("Logging out...");
                    break;

                default:
                    System.out.println("Invalid option.");
            }

        } while (choice != 0);
    }

    public static void viewMyBookings(String email) {

        boolean found = false;

        System.out.println("\n===== Your Bookings =====");

        for (Booking b : Database.bookings) {

            if (b.memberEmail.equals(email)) {

                System.out.println(
                        "ID: " + b.id +
                                " | Week: " + b.lesson.week +
                                " | " + b.lesson.type +
                                " | " + b.lesson.day +
                                " | " + b.lesson.time +
                                " | Status: " + b.status
                );

                found = true;
            }
        }

        if (!found) {
            System.out.println("No bookings found.");
        }
    }

    private static void viewTimetable() {

        Scanner scanner = new Scanner(System.in);

        System.out.println("\nView Timetable By:");
        System.out.println("1. Day");
        System.out.println("2. Exercise Type");

        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                System.out.print("Enter day (Saturday/Sunday): ");
                String day = scanner.nextLine();
                BookingService.viewByDay(day);
                break;

            case 2:
                System.out.print("Enter exercise type (Yoga/Zumba/etc): ");
                String type = scanner.nextLine();
                BookingService.viewByType(type);
                break;

            default:
                System.out.println("Invalid choice.");
        }
    }

    private static void bookLesson(String email) {

        Scanner scanner = new Scanner(System.in);

        BookingService.viewTimetable();

        System.out.print("Enter Lesson ID to book: ");
        int lessonId = scanner.nextInt();
        scanner.nextLine();

        BookingService.bookLesson(email, lessonId);
    }

    private static void changeBooking(String email) {

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Booking ID to change: ");
        int bookingId = scanner.nextInt();
        scanner.nextLine();

        BookingService.viewTimetable();

        System.out.print("Enter new Lesson ID: ");
        int newLessonId = scanner.nextInt();
        scanner.nextLine();

        BookingService.changeBooking(email, bookingId, newLessonId);
    }

    public static void cancelBooking(String email) {

        Booking selectedBooking = null;
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Booking ID to Cancel: ");
        int bookingId = scanner.nextInt();

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

        if (selectedBooking.status.equals("cancelled")) {
            System.out.println("❌ Already cancelled.");
            return;
        }

        if (selectedBooking.status.equals("attended")) {
            System.out.println("❌ Cannot cancel attended lesson.");
            return;
        }

        selectedBooking.lesson.bookings.remove(selectedBooking);

        selectedBooking.status = "cancelled";

        System.out.println("✅ Booking cancelled successfully.");
    }

    private static void attendLesson(String email) {

        viewMyBookings(email);
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Booking ID to attend: ");
        int bookingId = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter rating (1-5): ");
        int rating = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter review: ");
        String review = scanner.nextLine();

        BookingService.attendLesson(email, bookingId, rating, review);
    }

}
