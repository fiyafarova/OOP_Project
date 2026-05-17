package model.users.employees;

import enums.School;
import model.users.User;
import patterns.DataStorage;

import java.util.List;

public class Admin extends Employee {
    private static final long serialVersionUID = 1L;

    public Admin(String firstName, String lastName, String login, String password) {
        super(firstName, lastName, login, password, School.SITE);
    }

    public void addUser(User user) {
        DataStorage.getInstance().addUser(user);
        DataStorage.getInstance().addLog("Admin " + getFullName() + " added user: " + user.getLogin());
    }

    public void removeUser(String userId) {
        DataStorage.getInstance().removeUser(userId);
        DataStorage.getInstance().addLog("Admin " + getFullName() + " removed user id: " + userId);
    }

    public void updateUser(User updatedUser) {
        List<User> users = DataStorage.getInstance().getAllUsers();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId().equals(updatedUser.getId())) {
                users.set(i, updatedUser);
                DataStorage.getInstance().addLog("Admin " + getFullName() + " updated user: " + updatedUser.getLogin());
                return;
            }
        }
        System.out.println("User not found: " + updatedUser.getId());
    }

    public List<User> getAllUsers() {
        return DataStorage.getInstance().getAllUsers();
    }

    public void viewLogs() {
        List<String> logs = DataStorage.getInstance().getLogs();
        if (logs.isEmpty()) {
            System.out.println("No logs available.");
            return;
        }
        logs.forEach(System.out::println);
    }

    public List<String> getLogs() {
        return DataStorage.getInstance().getLogs();
    }

    @Override
    public String toString() {
        return "Admin[name=" + getFullName() + "]";
    }
}