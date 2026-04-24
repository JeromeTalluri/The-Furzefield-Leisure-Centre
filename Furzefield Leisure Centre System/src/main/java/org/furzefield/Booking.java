package org.furzefield;

public class Booking {

    int id;
    String memberEmail;
    Lesson lesson;

    String status; // booked, attended, cancelled, changed
    int rating;
    String review;

    public Booking(int id, String memberEmail, Lesson lesson) {
        this.id = id;
        this.memberEmail = memberEmail;
        this.lesson = lesson;
        this.status = "booked";
        this.rating = 0;
        this.review = "";
    }


    public void attendLesson(int rating, String review) {
        if (!status.equals("booked") && !status.equals("changed")) {
            System.out.println("Cannot attend this booking.");
            return;
        }

        this.status = "attended";
        this.rating = rating;
        this.review = review;
    }

    public void cancel() {
        if (status.equals("cancelled")) {
            System.out.println("Already cancelled.");
            return;
        }

        this.status = "cancelled";

        // remove from lesson
        lesson.bookings.remove(this);
    }

    public void changeLesson(Lesson newLesson) {

        if (newLesson.isFull()) {
            System.out.println("New lesson is full.");
            return;
        }

        // remove from old lesson
        lesson.bookings.remove(this);

        // assign new lesson
        this.lesson = newLesson;
        this.status = "changed";

        // add to new lesson
        newLesson.bookings.add(this);

        System.out.println("Booking updated.");
    }

    @Override
    public String toString() {
        return "Booking ID: " + id +
                ", Lesson: " + lesson.type +
                " (" + lesson.day + " " + lesson.time + ")" +
                ", Status: " + status;
    }
}
