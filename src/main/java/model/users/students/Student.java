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

public class Student extends User {
    private static final long serialVersionUID = 1L;

    private double gpa;
    private String major;
    private int yearOfStudy;
    private List<Course> courses;
    private Map<Course, Mark> marks;
    private int totalCredits;
    private Map<Course, Integer> failCount;
    private School school;
    private List<StudentOrganization> organizations;

    public Student(String firstName, String lastName, String login,
                   String password, School school, int yearOfStudy) {
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

    // throws MaxCreditsException if credits > 21
    // throws CourseFailLimitException if course failed 3+ times
    public void registerCourse(Course course) throws MaxCreditsException, CourseFailLimitException {
        if (totalCredits + course.getCredits() > 21) {
            throw new MaxCreditsException(
                    "Cannot register: adding " + course.getCredits()
                            + " credits would exceed the 21-credit limit (current: " + totalCredits + ")."
            );
        }
        if (failCount.getOrDefault(course, 0) >= 3) {
            throw new CourseFailLimitException(
                    "Cannot register for '" + course.getName() + "': failed 3 times already."
            );
        }
        courses.add(course);
        totalCredits += course.getCredits();
        course.enrollStudent(this);
    }

    public void dropCourse(Course course) {
        if (courses.remove(course)) {
            totalCredits -= course.getCredits();
            course.removeStudent(this);
        }
    }

    public void viewMarks() {
        if (marks.isEmpty()) {
            System.out.println("No marks yet.");
            return;
        }
        marks.forEach((course, mark) ->
                System.out.println(course.getName() + ": " + mark.getLetterGrade()
                        + " (" + mark.getTotalScore() + ")"));
    }

    public Mark getMarkForCourse(Course course) {
        return marks.get(course);
    }

    public Transcript getTranscript() {
        Transcript transcript = new Transcript(this, new ArrayList<>(marks.values()));
        transcript.generate();
        return transcript;
    }

    public void rateTeacher(Teacher teacher, int rating) {
        teacher.updateRating(rating);
    }

    public void joinOrganization(StudentOrganization org) {
        if (!organizations.contains(org)) {
            organizations.add(org);
            org.addMember(this);
        }
    }

    public void leaveOrganization(StudentOrganization org) {
        organizations.remove(org);
        org.removeMember(this);
    }

    // Called by Teacher.putMark
    public void addMark(Course course, Mark mark) {
        marks.put(course, mark);
        if (!mark.isPassed()) {
            failCount.merge(course, 1, Integer::sum);
        }
        recalculateGpa();
    }

    private void recalculateGpa() {
        gpa = marks.values().stream()
                .mapToDouble(Mark::getGpaPoints)
                .average()
                .orElse(0.0);
    }

    public double getGpa() {
        return gpa;
    }

    public void setGpa(double gpa) {
        this.gpa = gpa;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public int getYearOfStudy() {
        return yearOfStudy;
    }

    public void setYearOfStudy(int year) {
        this.yearOfStudy = year;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public Map<Course, Mark> getMarks() {
        return marks;
    }

    public int getTotalCredits() {
        return totalCredits;
    }

    public Map<Course, Integer> getFailCount() {
        return failCount;
    }

    public List<StudentOrganization> getOrganizations() {
        return organizations;
    }

    @Override
    public String toString() {
        return "Student[name=" + getFullName()
                + ", gpa=" + String.format("%.2f", gpa)
                + ", school=" + school
                + ", credits=" + totalCredits + "]";
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}