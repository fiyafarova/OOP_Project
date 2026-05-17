package model.academic;

import enums.Language;
import model.users.students.Student;
import util.LanguageManager;

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
        print(student != null ? student.getLanguage() : Language.EN);
    }


    public void print(Language lang) {
        // Regenerate if not yet done
        if (generatedAt == null) generate();

        System.out.println(LanguageManager.get(lang, "transcript.header"));
        System.out.println(LanguageManager.get(lang, "transcript.student") + student.getFullName());
        System.out.println(LanguageManager.get(lang, "transcript.generated") + generatedAt);
        System.out.println("--------------------------------");

        if (student.getMarks().isEmpty()) {
            System.out.println("  " + LanguageManager.get(lang, "marks.no_marks"));
        } else {
            for (Map.Entry<Course, Mark> entry : student.getMarks().entrySet()) {
                System.out.printf("  %-30s %s (%.0f pts)%n",
                        entry.getKey().getName(),
                        entry.getValue().getLetterGrade(),
                        entry.getValue().getTotalScore());
            }
        }

        System.out.println("--------------------------------");
        System.out.printf(LanguageManager.get(lang, "transcript.gpa") + "%.2f%n", gpa);
        System.out.println("================================");
    }

    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }

    public List<Mark> getMarks() { return marks; }
    public void setMarks(List<Mark> marks) { this.marks = marks; }

    public double getGpa() { return gpa; }
    public LocalDate getGeneratedAt() { return generatedAt; }

    @Override
    public String toString() {
        return "Transcript[student=" + (student != null ? student.getFullName() : "null")
                + ", gpa=" + String.format("%.2f", gpa)
                + ", date=" + generatedAt + "]";
    }
}