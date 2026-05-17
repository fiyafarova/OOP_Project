package model.research;

import model.users.students.GraduateStudent;

public class StudentResearcher extends ResearcherDecorator {
    public StudentResearcher(GraduateStudent student) {
        super(student);
    }

    public GraduateStudent getStudent() {
        return (GraduateStudent) wrappedUser;
    }

    @Override
    public String toString() {
        return "StudentResearcher[" + wrappedUser + "]";
    }
}