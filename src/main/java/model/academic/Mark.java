package model.academic;

import java.io.Serializable;

public class Mark implements Serializable {
    private static final long serialVersionUID = 1L;

    private double attestation1;  // max 30
    private double attestation2;  // max 30
    private double finalExam;     // max 40
    private double totalScore;

    public Mark() {
    }

    public Mark(double attestation1, double attestation2, double finalExam) {
        this.attestation1 = attestation1;
        this.attestation2 = attestation2;
        this.finalExam = finalExam;
        this.totalScore = attestation1 + attestation2 + finalExam;
    }

    public double getGpaPoints() {
        if (totalScore >= 90) return 4.0;
        if (totalScore >= 80) return 3.0;
        if (totalScore >= 70) return 2.0;
        if (totalScore >= 60) return 1.0;
        return 0.0;
    }

    public String getLetterGrade() {
        if (totalScore >= 90) return "A";
        if (totalScore >= 80) return "B";
        if (totalScore >= 70) return "C";
        if (totalScore >= 60) return "D";
        return "F";
    }

    public boolean isPassed() {
        return totalScore >= 50;
    }

    public double getAttestation1() {
        return attestation1;
    }

    public void setAttestation1(double attestation1) {
        this.attestation1 = attestation1;
        recalculate();
    }

    public double getAttestation2() {
        return attestation2;
    }

    public void setAttestation2(double attestation2) {
        this.attestation2 = attestation2;
        recalculate();
    }

    public double getFinalExam() {
        return finalExam;
    }

    public void setFinalExam(double finalExam) {
        this.finalExam = finalExam;
        recalculate();
    }

    public double getTotalScore() {
        return totalScore;
    }

    private void recalculate() {
        this.totalScore = this.attestation1 + this.attestation2 + this.finalExam;
    }

    @Override
    public String toString() {
        return "Mark{att1=" + attestation1 + ", att2=" + attestation2 +
                ", final=" + finalExam + ", total=" + totalScore +
                ", grade=" + getLetterGrade() + "}";
    }
}