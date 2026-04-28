package model.research;

import model.users.GraduateStudent;

public class StudentResearcher extends ResearcherDecorator {
    private final GraduateStudent student;

    public StudentResearcher(GraduateStudent student) {
        super(student);
        this.student = student;
    }

    public GraduateStudent getStudent() {
        return student;
    }

    @Override
    public String toString() {
        return "StudentResearcher{" +
                "student=" + student +
                '}';
    }
}