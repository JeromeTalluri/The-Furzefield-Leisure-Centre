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
            System.out.println("3. View My Bookings");
            System.out.println("4. Change Booking");
            System.out.println("5. Write Review");
            System.out.println("6. Generate Reports");
            System.out.println("0. Logout");

            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    viewTimetable();
                    break;

                case 2:
//                    bookLesson(email);
                    break;
                case 3:
                    viewMyBookings(email);
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


}
