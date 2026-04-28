package model.academic;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Transcript implements Serializable {
    private static final long serialVersionUID = 1L;

    private Map<Course, Mark> grades;

    public Transcript() {
        this.grades = new HashMap<>();
    }

    public void addGrade(Course course, Mark mark) {
        grades.put(course, mark);
    }

    public Mark getGrade(Course course) {
        return grades.get(course);
    }

    public double calculateOverallGpa() {
        if (grades.isEmpty()) return 0.0;

        double totalWeightedGpa = 0.0;
        int totalCredits = 0;

        for (Map.Entry<Course, Mark> entry : grades.entrySet()) {
            int credits = entry.getKey().getCredits();
            double gpa = entry.getValue().getGpaPoints();
            totalWeightedGpa += gpa * credits;
            totalCredits += credits;
        }

        return totalCredits == 0 ? 0.0 : totalWeightedGpa / totalCredits;
    }

    public Map<Course, Mark> getGrades() {
        return grades;
    }

    public void setGrades(Map<Course, Mark> grades) {
        this.grades = grades;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Transcript:\n");
        for (Map.Entry<Course, Mark> entry : grades.entrySet()) {
            sb.append("  ").append(entry.getKey().getCourseName())
                    .append(": ").append(entry.getValue().getLetterGrade())
                    .append(" (").append(entry.getValue().getTotalScore()).append(")\n");
        }
        sb.append(String.format("Overall GPA: %.2f", calculateOverallGpa()));
        return sb.toString();
    }
}