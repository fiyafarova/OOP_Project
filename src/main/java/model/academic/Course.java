package model.academic;

import enums.CourseType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Course implements Serializable {
    private static final long serialVersionUID = 1L;

    private String courseCode;
    private String courseName;
    private int credits;
    private CourseType courseType;
    private String targetSchool;
    private List<Course> prerequisites;

    public Course() {
        this.prerequisites = new ArrayList<>();
    }

    public Course(String courseCode, String courseName, int credits,
                  CourseType courseType, String targetSchool) {
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.credits = credits;
        this.courseType = courseType;
        this.targetSchool = targetSchool;
        this.prerequisites = new ArrayList<>();
    }

    public String getCourseCode() { return courseCode; }
    public void setCourseCode(String courseCode) { this.courseCode = courseCode; }

    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }

    public int getCredits() { return credits; }
    public void setCredits(int credits) { this.credits = credits; }

    public CourseType getCourseType() { return courseType; }
    public void setCourseType(CourseType courseType) { this.courseType = courseType; }

    public String getTargetSchool() { return targetSchool; }
    public void setTargetSchool(String targetSchool) { this.targetSchool = targetSchool; }

    public List<Course> getPrerequisites() { return prerequisites; }
    public void setPrerequisites(List<Course> prerequisites) { this.prerequisites = prerequisites; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Course)) return false;
        Course course = (Course) o;
        return Objects.equals(courseCode, course.courseCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseCode);
    }

    @Override
    public String toString() {
        return courseCode + " - " + courseName + " (" + credits + " credits)";
    }
}