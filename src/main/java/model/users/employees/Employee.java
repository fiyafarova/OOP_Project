package model.users.employees;

import enums.School;
import model.communication.Message;
import model.users.User;

import java.io.Serializable;
import java.util.Objects;

public abstract class Employee extends User implements Serializable {
    private static final long serialVersionUID = 1L;

    private double salary;
    private String department;
    private String position;
    private double rating;
    private School school;

    public Employee(String firstName, String lastName, String login, String password, School school) {
        super(firstName, lastName, login, password);
        this.school = school;
    }

    public void sendMessage(Employee receiver, String text) {
        Message message = new Message(this, receiver, text);
        System.out.println("[Message] " + getFirstName() + " → " + receiver.getFirstName() + ": " + text);
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    @Override
    public String toString() {
        return "Employee[name=" + getFirstName() + " " + getLastName()
                + ", position=" + position + ", school=" + school + "]";
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}