package model.users;

import enums.Language;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public abstract class User implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String firstName;
    private String lastName;
    private String login;
    private String passwordHash;
    private Language language;

    public User(String firstName, String lastName, String login, String password) {
        this.id = UUID.randomUUID().toString();
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.passwordHash = String.valueOf(password.hashCode());
        this.language = Language.EN;
    }

    public boolean login(String login, String password) {
        return this.login.equals(login)
                && this.passwordHash.equals(String.valueOf(password.hashCode()));
    }

    public void logout() {
        System.out.println("User " + firstName + " " + lastName + " logged out.");
    }

    public void switchLanguage(Language lang) {
        this.language = lang;
    }

    public Language getLanguage() {
        return language;
    }

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    @Override
    public String toString() {
        return "User[id=" + id + ", name=" + firstName + " " + lastName + ", login=" + login + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User other = (User) o;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}