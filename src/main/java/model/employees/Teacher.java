package model.employees;

import enums.School;
import enums.TeacherPosition;
import enums.UrgencyLevel;
import model.academic.Course;
import model.academic.Mark;
import model.users.Student;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

// преподаватель
// может быть Researcher (через TeacherResearcher декоратор)
// PROFESSOR автоматически является Researcher
// может вести лекции и/или практики по курсу

public class Teacher extends Employee {
    private List<Course> courses;
    private TeacherPosition teacherPosition;
    private double rating; // средняя оценка от студентов

    public Teacher(String firstName, String lastName, String login,
                   String password, School school, TeacherPosition teacherPosition) {
        super(firstName, lastName, login, password, school);
        this.teacherPosition = teacherPosition;
        this.courses = new ArrayList<>();
    }

    public void viewCourses() {
        // вывести все курсы
    }

    public void manageCourse(Course course) {
        //вывести информацию о курсе для управления
    }

    public void addCourse(Course course) {
    }

    public void removeCourse(Course course) {
    }

    public void putMark(Student student, Course course, Mark mark) {
        // добавить оценку студенту
    }

    public void viewStudents() {
        // вывести всех студентов со всех своих курсов
    }

    public void viewStudents(Comparator<Student> comparator) {
        // вывести студентов, отсортированных по comparator
    }

    // отправляет жалобу декану
    public void sendComplaint(Student student, Manager dean, UrgencyLevel urgency) {
        // создать Request или Message с жалобой на студента
    }

    public List<Course> getCourses() {
        return courses;
    }

    public TeacherPosition getTeacherPosition() {
        return teacherPosition;
    }

    public void setTeacherPosition(TeacherPosition position) {
    }

    @Override
    public double getRating() {
        return rating;
    }

    public void updateRating(int newRating) {
        //  обновить средний рейтинг
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