package model.academic;

import enums.CourseType;
import enums.School;
import model.users.employees.Teacher;
import model.users.students.Student;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Course implements Serializable {
    private static final long serialVersionUID = 1L;

    private String code;
    private String name;
    private int credits;
    private School school;
    private CourseType courseType;
    private Teacher lectureTeacher;
    private Teacher practiceTeacher;
    private List<Student> enrolledStudents;
    private List<Lesson> lessons;

    public Course() {
        this.enrolledStudents = new ArrayList<>();
        this.lessons = new ArrayList<>();
    }

    public Course(String code, String name, int credits, School school, CourseType courseType) {
        this.code = code;
        this.name = name;
        this.credits = credits;
        this.school = school;
        this.courseType = courseType;
        this.enrolledStudents = new ArrayList<>();
        this.lessons = new ArrayList<>();
    }

    public void enrollStudent(Student student) {
        if (!enrolledStudents.contains(student)) {
            enrolledStudents.add(student);
        }
    }

    public void removeStudent(Student student) {
        enrolledStudents.remove(student);
    }

    public boolean isAvailableFor(Student student) {
        switch (courseType) {
            case MAJOR:         return student.getSchool() == this.school;
            case MINOR:         return student.getSchool() != this.school;
            case FREE_ELECTIVE: return true;
            default:            return false;
        }
    }

    public void addLesson(Lesson lesson) { lessons.add(lesson); }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getCredits() { return credits; }
    public void setCredits(int credits) { this.credits = credits; }

    public School getSchool() { return school; }
    public void setSchool(School school) { this.school = school; }

    public CourseType getCourseType() { return courseType; }
    public void setCourseType(CourseType courseType) { this.courseType = courseType; }

    public Teacher getLectureTeacher() { return lectureTeacher; }
    public void setLectureTeacher(Teacher teacher) { this.lectureTeacher = teacher; }

    public Teacher getPracticeTeacher() { return practiceTeacher; }
    public void setPracticeTeacher(Teacher teacher) { this.practiceTeacher = teacher; }

    public List<Student> getEnrolledStudents() { return enrolledStudents; }
    public void setEnrolledStudents(List<Student> students) { this.enrolledStudents = students; }

    public List<Lesson> getLessons() { return lessons; }
    public void setLessons(List<Lesson> lessons) { this.lessons = lessons; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Course)) return false;
        return Objects.equals(code, ((Course) o).code);
    }

    @Override
    public int hashCode() { return Objects.hash(code); }

    @Override
    public String toString() {
        return "Course[code=" + code + ", name=" + name + ", credits=" + credits
                + ", school=" + school + ", type=" + courseType + "]";
    }
}