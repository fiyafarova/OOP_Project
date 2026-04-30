package model.academic;

import java.io.Serializable;
import java.util.Objects;

public class Mark implements Serializable {
    private static final long serialVersionUID = 1L;

    private double att1;
    private double att2;
    private double finalExam;

    public Mark() {}

    public Mark(double att1, double att2, double finalExam) {
        this.att1 = att1;
        this.att2 = att2;
        this.finalExam = finalExam;
    }

    public double getTotalScore() {
        return att1 + att2 + finalExam;
    }

    public boolean isFX() {
        return finalExam >= 10 && finalExam < 20;
    }

    public boolean isF() {
        return getTotalScore() < 50 && !isFX();
    }

    public boolean isPassed() {
        return getTotalScore() >= 50 && finalExam >= 20;
    }

    public boolean isRetakable() {
        return isFX();
    }

    public String getLetterGrade() {
        if (isFX()) return "FX";
        if (!isPassed()) return "F";
        double total = getTotalScore();
        if (total >= 95) return "A";
        if (total >= 90) return "A-";
        if (total >= 85) return "B+";
        if (total >= 80) return "B";
        if (total >= 75) return "B-";
        if (total >= 70) return "C+";
        if (total >= 65) return "C";
        if (total >= 60) return "C-";
        if (total >= 55) return "D+";
        return "D";
    }

    public double getGpaPoints() {
        switch (getLetterGrade()) {
            case "A":   return 4.0;
            case "A-":  return 3.67;
            case "B+":  return 3.33;
            case "B":   return 3.0;
            case "B-":  return 2.67;
            case "C+":  return 2.33;
            case "C":   return 2.0;
            case "C-":  return 1.67;
            case "D+":  return 1.33;
            case "D":   return 1.0;
            default:    return 0.0;
        }
    }

    public double getAtt1() { return att1; }
    public void setAtt1(double att1) { this.att1 = att1; }

    public double getAtt2() { return att2; }
    public void setAtt2(double att2) { this.att2 = att2; }

    public double getFinalExam() { return finalExam; }
    public void setFinalExam(double finalExam) { this.finalExam = finalExam; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Mark)) return false;
        Mark mark = (Mark) o;
        return Double.compare(mark.att1, att1) == 0
            && Double.compare(mark.att2, att2) == 0
            && Double.compare(mark.finalExam, finalExam) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(att1, att2, finalExam);
    }

    @Override
    public String toString() {
        return "Mark[att1=" + att1 + ", att2=" + att2 + ", final=" + finalExam
            + ", total=" + getTotalScore() + ", grade=" + getLetterGrade() + "]";
    }
}