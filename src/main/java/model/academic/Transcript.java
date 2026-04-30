package model.academic;

import model.users.Student;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class Transcript implements Serializable {
    private static final long serialVersionUID = 1L;

    private Student student;
    private List<Mark> marks;
    private double gpa;
    private LocalDate generatedAt;

    public Transcript() {}

    public Transcript(Student student, List<Mark> marks) {
        this.student = student;
        this.marks = marks;
    }

    public void generate() {
        gpa = marks.stream()
            .mapToDouble(Mark::getGpaPoints)
            .average()
            .orElse(0.0);
        generatedAt = LocalDate.now();
    }

    public void print() {
        System.out.println("========== TRANSCRIPT ==========");
        System.out.println("Student  : " + student.getFullName());
        System.out.println("Generated: " + generatedAt);
        System.out.println("--------------------------------");
        for (Map.Entry<Course, Mark> entry : student.getMarks().entrySet()) {
            System.out.printf("%-30s %s%n", entry.getKey().getName(), entry.getValue());
        }
        System.out.println("--------------------------------");
        System.out.printf("GPA      : %.2f%n", gpa);
        System.out.println("================================");
    }

    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }

    public List<Mark> getMarks() { return marks; }
    public void setMarks(List<Mark> marks) { this.marks = marks; }

    public double getGpa() { return gpa; }
    public LocalDate getGeneratedAt() { return generatedAt; }
}
