package model.users.employees;

import enums.School;
import enums.TeacherPosition;
import enums.UrgencyLevel;
import model.academic.Course;
import model.academic.Lesson;
import model.academic.Mark;
import model.communication.Request;
import model.users.students.Student;
import patterns.DataStorage;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Teacher extends Employee {
    private static final long serialVersionUID = 1L;

    private List<Course> courses;
    private TeacherPosition teacherPosition;
    private double rating;
    private int ratingCount;

    public Teacher(String firstName, String lastName, String login,
                   String password, School school, TeacherPosition teacherPosition) {
        super(firstName, lastName, login, password, school);
        this.teacherPosition = teacherPosition;
        this.courses = new ArrayList<>();
    }

    public void viewCourses() {
        if (courses.isEmpty()) {
            System.out.println("No courses assigned.");
            return;
        }
        courses.forEach(c -> {
            System.out.println(c);
            if (c.getLectureTeacher() != null)
                System.out.println("   Lecture:  " + c.getLectureTeacher().getFullName());
            if (c.getPracticeTeacher() != null)
                System.out.println("   Practice: " + c.getPracticeTeacher().getFullName());
        });
    }

    public void addCourse(Course course) {
        if (!courses.contains(course)) {
            courses.add(course);
        }
    }

    public void removeCourse(Course course) {
        courses.remove(course);
    }

    public void putMark(Student student, Course course, Mark mark) {
        student.addMark(course, mark);
        DataStorage.getInstance().addLog(
                "Teacher " + getFullName() + " put mark " + mark.getLetterGrade()
                        + " for " + student.getFullName() + " in " + course.getName());
    }

    public void viewStudents(Comparator<Student> comparator) {
        courses.stream()
                .flatMap(c -> c.getEnrolledStudents().stream())
                .distinct()
                .sorted(comparator)
                .forEach(System.out::println);
    }


    public void sendComplaint(Student student, Manager dean, UrgencyLevel urgency, String description) {
        String desc = "[Complaint from " + getFullName()
                + " about student " + student.getFullName() + "] " + description;
        Request request = new Request(this, desc, urgency);
        DataStorage.getInstance().addRequest(request);
        DataStorage.getInstance().addLog("Teacher " + getFullName()
                + " sent complaint about " + student.getFullName()
                + " with urgency " + urgency);
        if (dean != null) {
            sendMessage(dean, "Complaint filed. Request ID: " + request.getId()
                    + ". Urgency: " + urgency + ". " + description);
        }
        System.out.println("Complaint sent. Request ID: " + request.getId());
    }

    public void sendComplaint(Student student, Manager dean, UrgencyLevel urgency) {
        sendComplaint(student, dean, urgency, "");
    }

    public List<Course> getCourses() { return courses; }

    public TeacherPosition getTeacherPosition() { return teacherPosition; }
    public void setTeacherPosition(TeacherPosition position) { this.teacherPosition = position; }

    @Override
    public double getRating() { return rating; }

    public void updateRating(int newRating) {
        rating = (rating * ratingCount + newRating) / (++ratingCount);
    }

    public void addLesson(Course course, Lesson lesson) {
        course.addLesson(lesson);
        DataStorage.getInstance().addLog(
                "Teacher " + getFullName() + " added lesson '"
                        + lesson.getTitle() + "' to course " + course.getName());
        System.out.println("Lesson added: " + lesson);
    }

    public void viewLessons(Course course) {
        if (course.getLessons().isEmpty()) {
            System.out.println("No lessons in this course.");
            return;
        }
        course.getLessons().forEach(System.out::println);
    }

    @Override
    public String toString() {
        return "Teacher[name=" + getFullName()
                + ", position=" + teacherPosition
                + ", school=" + getSchool()
                + ", rating=" + String.format("%.1f", rating) + "]";
    }

    @Override
    public boolean equals(Object o) { return super.equals(o); }

    @Override
    public int hashCode() { return super.hashCode(); }
}