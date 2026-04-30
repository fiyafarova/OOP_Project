package model.employees;

import model.academic.Course;
import model.academic.Mark;
import model.users.Student;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Teacher extends Employee implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<Course> courses;
    private double rating;
    private int ratingCount;

    public Teacher() {
        this.courses = new ArrayList<>();
    }

    public Teacher(String id, String name, String surname, String email, String password) {
        super(id, name, surname, email, password);
        this.courses = new ArrayList<>();
    }

    public void updateRating(int newRating) {
        rating = (rating * ratingCount + newRating) / (++ratingCount);
    }

    public void putMark(Student student, Course course, Mark mark) {
        student.addMark(course, mark);
    }

    public void sendComplaint(String complaint) {
        System.out.println("[Complaint to Dean] " + getName() + " " + getSurname() + ": " + complaint);
    }

    public void sendMessage(Employee recipient, String message) {
        System.out.println("[Message to " + recipient.getName() + " " + recipient.getSurname() + "] " + message);
    }

    public List<Course> getCourses() { return courses; }
    public void addCourse(Course course) { courses.add(course); }

    public double getRating() { return rating; }
    public int getRatingCount() { return ratingCount; }

    @Override
    public String toString() {
        return "Teacher[" + getName() + " " + getSurname()
            + ", rating=" + String.format("%.1f", rating) + "]";
    }
}

