package model.academic;

import model.users.Student;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class StudentOrganization implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private Student head;
    private List<Student> members;

    public StudentOrganization() {
        this.members = new ArrayList<>();
    }

    public StudentOrganization(String name, Student head) {
        this.name = name;
        this.head = head;
        this.members = new ArrayList<>();
    }

    public void addMember(Student student) {
        if (!members.contains(student)) {
            members.add(student);
        }
    }

    public void removeMember(Student student) {
        members.remove(student);
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Student getHead() { return head; }
    public void setHead(Student head) { this.head = head; }

    public List<Student> getMembers() { return members; }
    public void setMembers(List<Student> members) { this.members = members; }

    @Override
    public String toString() {
        return "StudentOrganization{name='" + name + "', head=" + head +
               ", members=" + members.size() + "}";
    }
}