package model.users;

import enums.Language;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

// аутентификация обязательна
// любой пользователь должен залогиниться

public abstract class User implements Serializable {
    private String id;
    private String firstName;
    private String lastName;
    private String login;
    private String passwordHash;   // пароль хранится в хэшированном виде
    private Language language;     // текущий язык интерфейса

    public User(String firstName, String lastName, String login, String password) {
        // сгенерировать id
        // установить firstName, lastName, login
        // хэшировать пароль и сохранить в passwordHash
        // установить дефолтный язык EN
        this.id = UUID.randomUUID().toString();
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.passwordHash = String.valueOf(password.hashCode());
        this.language = Language.EN;
    }

    public boolean login(String login, String password) {
        //  проверить совпадение логина и хэша пароля
        return false;
    }

    public void logout() {
    }

    public void switchLanguage(Language lang) {
    }

    public Language getLanguage() {
        return null;
    }

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getLogin() {
        return login;
    }

    public void setFirstName(String firstName) {
    }

    public void setLastName(String lastName) {
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
