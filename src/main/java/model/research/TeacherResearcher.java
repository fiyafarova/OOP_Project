package model.research;

import model.users.employees.Teacher;

public class TeacherResearcher extends ResearcherDecorator {
    public TeacherResearcher(Teacher teacher) {
        super(teacher);
    }

    public Teacher getTeacher() {
        return (Teacher) wrappedUser;
    }

    @Override
    public String toString() {
        return "TeacherResearcher[" + wrappedUser + "]";
    }
}