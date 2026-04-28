package model.users;

import enums.School;
import exceptions.MaxCreditsException;
import model.academic.Course;
import model.academic.Transcript;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Student extends User implements Serializable {
    private static final int MAX_CREDITS = 21;

    private School school;
    private int yearOfStudy;
    private int credits;
    private Transcript transcript;
    private List<Course> registeredCourses;

    public Student(String name, String surname, String email, String password,
                   School school, int yearOfStudy) {
        super(name, surname, email, password);
        this.school = school;
        this.yearOfStudy = yearOfStudy;
        this.credits = 0;
        this.registeredCourses = new ArrayList<>();
        this.transcript = new Transcript();
    }

    public void registerToCourse(Course course) throws MaxCreditsException {
        if (this.credits + course.getCredits() > MAX_CREDITS) {
            throw new MaxCreditsException(
                    "Cannot register for '" + course.getCourseName() +
                            "': adding " + course.getCredits() + " credits would bring total to " +
                            (this.credits + course.getCredits()) + ", exceeding the " + MAX_CREDITS + "-credit limit."
            );
        }

        List<Course> prerequisites = course.getPrerequisites();
        if (prerequisites != null && !prerequisites.isEmpty()) {
            for (Course prereq : prerequisites) {
                if (!transcript.getGrades().containsKey(prereq)) {
                    throw new IllegalStateException(
                            "Prerequisite not met: '" + prereq.getCourseName() + "' must be completed before registering for '" +
                                    course.getCourseName() + "'."
                    );
                }
            }
        }

        registeredCourses.add(course);
        this.credits += course.getCredits();
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public int getYearOfStudy() {
        return yearOfStudy;
    }

    public void setYearOfStudy(int yearOfStudy) {
        this.yearOfStudy = yearOfStudy;
    }

    public int getCredits() {
        return credits;
    }

    public Transcript getTranscript() {
        return transcript;
    }

    public void setTranscript(Transcript transcript) {
        this.transcript = transcript;
    }

    public List<Course> getRegisteredCourses() {
        return registeredCourses;
    }

    public void setRegisteredCourses(List<Course> registeredCourses) {
        this.registeredCourses = registeredCourses;
    }

    @Override
    public String toString() {
        return "Student{" + getFirstName() + " " + getLastName() +
                ", school=" + school + ", year=" + yearOfStudy + ", credits=" + credits + "}";
    }
}