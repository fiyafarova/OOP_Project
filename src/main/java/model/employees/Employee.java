package model.employees;

import model.users.User;

import java.io.Serializable;

public abstract class Employee extends User implements Serializable {
    private static final long serialVersionUID = 1L;

    public Employee() {}

    public Employee(String id, String name, String surname, String email, String password) {
        super(id, name, surname, email, password);
    }

    public void logout() {
        System.out.println(getName() + " " + getSurname() + " logged out.");
    }
}
