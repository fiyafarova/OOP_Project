package model.users;

import enums.School;
import exceptions.CourseFailLimitException;
import exceptions.MaxCreditsException;
import model.academic.Course;
import model.academic.Mark;
import model.academic.StudentOrganization;
import model.academic.Transcript;
import model.employees.Employee;
import model.employees.Teacher;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Student extends Employee implements Serializable {
    // Required for Java serialization — ensures class version compatibility
    // when saving/loading DataStorage objects to disk.
    private static final long serialVersionUID = 1L;

    private double gpa;
    private String major;
    private School school;
    private int yearOfStudy;
    private List<Course> courses;
    private Map<Course, Mark> marks;
    private int totalCredits;
    private Map<Course, Integer> failCount;
    private List<StudentOrganization> organizations;

    public Student() {
        this.courses = new ArrayList<>();
        this.marks = new HashMap<>();
        this.failCount = new HashMap<>();
        this.organizations = new ArrayList<>();
    }

    public Student(String id, String name, String surname, String email, String password,
                   String major, School school, int yearOfStudy) {
        super(id, name, surname, email, password);
        this.major = major;
        this.school = school;
        this.yearOfStudy = yearOfStudy;
        this.courses = new ArrayList<>();
        this.marks = new HashMap<>();
        this.failCount = new HashMap<>();
        this.organizations = new ArrayList<>();
    }

    public void registerCourse(Course course) throws MaxCreditsException, CourseFailLimitException {
        if (totalCredits + course.getCredits() > 21) {
            throw new MaxCreditsException(
                "Cannot register: adding " + course.getCredits()
                + " credits would exceed the 21-credit limit (current: " + totalCredits + ")."
            );
        }
        if (failCount.getOrDefault(course, 0) >= 3) {
            throw new CourseFailLimitException(
                "Cannot register for '" + course.getName()
                + "': failed 3 times already."
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
        organizations.add(org);
        org.addMember(this);
    }

    public void leaveOrganization(StudentOrganization org) {
        organizations.remove(org);
        org.removeMember(this);
    }

    public void addMark(Course course, Mark mark) {
        marks.put(course, mark);
        if (!mark.isPassed()) {
            failCount.merge(course, 1, Integer::sum);
        }
        if (!marks.isEmpty()) {
            gpa = marks.values().stream()
                .mapToDouble(Mark::getGpaPoints)
                .average()
                .orElse(0.0);
        }
    }

    public String getFullName() { return getName() + " " + getSurname(); }

    public double getGpa() { return gpa; }
    public void setGpa(double gpa) { this.gpa = gpa; }

    public String getMajor() { return major; }
    public void setMajor(String major) { this.major = major; }

    public School getSchool() { return school; }
    public void setSchool(School school) { this.school = school; }

    public int getYearOfStudy() { return yearOfStudy; }
    public void setYearOfStudy(int yearOfStudy) { this.yearOfStudy = yearOfStudy; }

    public List<Course> getCourses() { return courses; }
    public void setCourses(List<Course> courses) { this.courses = courses; }

    public Map<Course, Mark> getMarks() { return marks; }
    public void setMarks(Map<Course, Mark> marks) { this.marks = marks; }

    public int getTotalCredits() { return totalCredits; }

    public Map<Course, Integer> getFailCount() { return failCount; }

    public List<StudentOrganization> getOrganizations() { return organizations; }
    public void setOrganizations(List<StudentOrganization> organizations) { this.organizations = organizations; }

    @Override
    public boolean equals(Object o) { return super.equals(o); }

    @Override
    public int hashCode() { return super.hashCode(); }

    private void recalculateGpa() {
        gpa = marks.values().stream()
            .mapToDouble(Mark::getGpaPoints)
            .average()
            .orElse(0.0);
    }

    public void retake(Course course, double newFinalExam) {
        Mark current = marks.get(course);
        if (current == null) {
            System.out.println("No mark found for course: " + course.getName());
            return;
        }
        if (!current.isFX()) {
            System.out.println("Retake not allowed. Grade is " + current.getLetterGrade()
                + ". Only FX grade allows exam retake.");
            return;
        }
        Mark retakeMark = new Mark(current.getAtt1(), current.getAtt2(), newFinalExam);
        marks.put(course, retakeMark);
        if (!retakeMark.isPassed()) {
            failCount.merge(course, 1, Integer::sum);
            System.out.println("Retake failed. New grade: " + retakeMark.getLetterGrade());
        } else {
            System.out.println("Retake passed! New grade: " + retakeMark.getLetterGrade());
        }
        recalculateGpa();
    }

    @Override
    public String toString() {
        return "Student[name=" + getName() + " " + getSurname()
            + ", gpa=" + String.format("%.2f", gpa)
            + ", school=" + school
            + ", credits=" + totalCredits + "]";
    }

}