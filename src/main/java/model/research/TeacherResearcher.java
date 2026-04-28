package model.research;

import model.employees.Teacher;

public class TeacherResearcher extends ResearcherDecorator {
    private final Teacher teacher;

    public TeacherResearcher(Teacher teacher) {
        super(teacher);
        this.teacher = teacher;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    @Override
    public String toString() {
        return "TeacherResearcher{" +
                "teacher=" + teacher +
                '}';
    }
}