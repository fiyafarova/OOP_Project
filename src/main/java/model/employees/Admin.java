package model.employees;

import enums.School;
import model.users.User;
import patterns.DataStorage;

import java.util.List;

// админ системы
// управляет пользователями и видит логи

public class Admin extends Employee {
    public Admin(String firstName, String lastName, String login, String password) {
        super(firstName,lastName,login,password, School.ADMIN);
    }

    public void addUser(User user) {
        // DataStorage.getInstance().addUser(user)
    }

    public void removeUser(String userId) {
        // DataStorage.getInstance().removeUser(userId)
    }

    public void updateUser(User user) {
        // найти пользователя в DataStorage и обновить его данные
    }

    public List<User> getAllUsers() {
        // DataStorage.getInstance().getAllUsers()
        return null;
    }

    public void viewLogs() {
        //все логи из DataStorage
    }

    public List<String> getLogs() {
        // DataStorage.getInstance().getLogs()
        return null;
    }

    @Override
    public String toString() {
        return null;
    }
}