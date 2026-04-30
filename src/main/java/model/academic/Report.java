package model.academic;

import model.users.Student;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Report implements Serializable {
    private static final long serialVersionUID = 1L;

    private String title;
    private int totalStudents;
    private int failingStudents;
    private double universityAverageGpa;
    private Map<Course, Double> averageGradesByCourse;

    public Report(String title) {
        this.title = title;
        this.averageGradesByCourse = new HashMap<>();
    }

    public void generate(List<Student> students, List<Course> courses) {
        totalStudents = students.size();
        failingStudents = (int) students.stream()
            .filter(s -> s.getGpa() < 2.0)
            .count();
        universityAverageGpa = students.stream()
            .mapToDouble(Student::getGpa)
            .average()
            .orElse(0.0);

        averageGradesByCourse = new HashMap<>();
        for (Course course : courses) {
            List<Student> enrolled = course.getEnrolledStudents();
            if (!enrolled.isEmpty()) {
                double avg = enrolled.stream()
                    .map(s -> s.getMarkForCourse(course))
                    .filter(m -> m != null)
                    .mapToDouble(Mark::getTotalScore)
                    .average()
                    .orElse(0.0);
                averageGradesByCourse.put(course, avg);
            }
        }
    }

    public void print() {
        System.out.println("  REPORT: " + title);
        System.out.println("------------------------------------------------------------");
        System.out.println("  Total students    : " + totalStudents);
        System.out.println("  Failing students  : " + failingStudents);
        System.out.printf( "  University avg GPA: %.2f%n", universityAverageGpa);
        System.out.println("------------------------------------------------------------");
        System.out.printf("  %-35s %s%n", "Course", "Avg Score");
        System.out.println("------------------------------------------------------------");
        averageGradesByCourse.forEach((course, avg) ->
            System.out.printf("  %-35s %.1f%n", course.getName(), avg));
    }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public int getTotalStudents() { return totalStudents; }
    public int getFailingStudents() { return failingStudents; }
    public double getUniversityAverageGpa() { return universityAverageGpa; }
    public Map<Course, Double> getAverageGradesByCourse() { return averageGradesByCourse; }
}