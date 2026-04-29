package model.users.students;

import enums.School;
import exceptions.CourseFailLimitException;
import exceptions.MaxCreditsException;
import model.academic.Course;
import model.academic.Mark;
import model.academic.StudentOrganization;
import model.academic.Transcript;
import model.users.User;
import model.users.employees.Teacher;

import java.util.*;

// студент бакалавриата
// не более 21 кредита одновременно (бросает MaxCreditsException)
// не может провалить один курс более 3 раз (бросает CourseFailLimitException)
// оценка = att1 + att2 + finalExam

public class Student extends User {
    private double gpa;
    private String major;
    private int yearOfStudy;
    private List<Course> courses;
    private Map<Course, Mark> marks;
    private int totalCredits;
    private Map<Course, Integer> failCount; // счётчик провалов по каждому курсу
    private School school;
    private double rating;                  // рейтинг преподавателей (выставляет студент)
    private List<StudentOrganization> organizations;

    public Student(String firstName, String lastName, String login, String password, School school, int yearOfStudy) {
        super(firstName, lastName, login, password);
        this.school = school;
        this.yearOfStudy = yearOfStudy;
        this.courses = new ArrayList<>();
        this.marks = new HashMap<>();
        this.failCount = new HashMap<>();
        this.organizations = new ArrayList<>();
        this.totalCredits = 0;
        this.gpa = 0.0;
    }

    // throws MaxCreditsException если credits > 21
    // throws CourseFailLimitException если курс провален 3+ раза
    public void registerCourse(Course course) throws MaxCreditsException, CourseFailLimitException {
        // добавить course в courses
        //totalCredits += course.getCredits()
        //course.enrollStudent(this)
    }

    public void dropCourse(Course course) {
        // удалить course из courses
        //totalCredits -= course.getCredits()
        //course.removeStudent(this)
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void viewMarks() {
        //  вывести все оценки из marks
    }

    public Map<Course, Mark> getMarks() {
        return marks;
    }

    public Mark getMarkForCourse(Course course) {
        // вернуть marks.get(course)
        return null;
    }

    public Transcript getTranscript() {
        // создать новый Transcript(this, new ArrayList<>(marks.values())), вызвать generate() и вернуть
        return null;
    }

    public void rateTeacher(Teacher teacher, int rating) {
        // вызвать teacher.updateRating(rating)
    }

    public void joinOrganization(StudentOrganization org) {
        //  добавить org в organizations, вызвать org.addMember(this)
    }

    public void leaveOrganization(StudentOrganization org) {
        //удалить org из organizations, вызвать org.removeMember(this)
    }

    public List<StudentOrganization> getOrganizations() {
        return organizations;
    }

    public double getGpa() {
        return gpa;
    }

    public void setGpa(double gpa) {
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
    }

    public int getYearOfStudy() {
        return yearOfStudy;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
    }

    public int getTotalCredits() {
        return totalCredits;
    }

    @Override
    public String toString() {
        return null;
    }

    @Override
    public boolean equals(Object o) {
        return false;
    }

    @Override
    public int hashCode() {
        return 0;
    }
}