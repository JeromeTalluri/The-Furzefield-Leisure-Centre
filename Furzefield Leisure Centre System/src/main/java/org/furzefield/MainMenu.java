package org.furzefield;

import java.util.Scanner;

public class MainMenu {

    public static void showMenu(String email) {

        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n===== Main Menu =====");
            System.out.println("1. View Timetable");
            System.out.println("2. Book Lesson");
            System.out.println("3. Change Booking");
            System.out.println("4. Write Review");
            System.out.println("5. Generate Reports");
            System.out.println("0. Logout");

            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
//                    viewTimetable();
                    break;

                case 2:
//                    bookLesson(email);
                    break;

                case 0:
                    System.out.println("Logging out...");
                    break;

                default:
                    System.out.println("Invalid option.");
            }

        } while (choice != 0);
    }

}
