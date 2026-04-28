package model.employees;

import enums.School;
import model.communication.Message;
import model.users.User;

import java.util.Objects;

// абстрактный класс для всех сотрудников универа
// Employee может отправить сообщение любому другому Employee

public abstract class Employee extends User {
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
        // создать новый Message и сохранить/вывести
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
    }

    public double getRating() {
        return rating;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
    }

    @Override
    public String toString() {
        return null;
    }

    @Override
    public boolean equals(Object o) {
        return false;
    }

    @Override
    public int hashCode() {
        return 0;
    }
}