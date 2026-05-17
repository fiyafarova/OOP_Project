package model.academic;

import model.users.students.Student;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class StudentOrganization implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private List<Student> members;
    private Student head;

    public StudentOrganization() { this.members = new ArrayList<>(); }

    public StudentOrganization(String name) {
        this.name = name;
        this.members = new ArrayList<>();
    }

    public void addMember(Student student) {
        if (student != null && !members.contains(student)) {
            members.add(student);
        }
    }

    public void removeMember(Student student) { members.remove(student); }

    public void setHead(Student student) {
        if (!members.contains(student)) {
            members.add(student);
        }
        this.head = student;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public List<Student> getMembers() { return members; }
    public void setMembers(List<Student> members) { this.members = members; }

    public Student getHead() { return head; }

    @Override
    public String toString() {
        return "StudentOrganization[name=" + name
                + ", head=" + (head != null ? head.getFullName() : "none")
                + ", members=" + members.size() + "]";
    }
}