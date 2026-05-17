package model.communication;

import model.users.User;

import java.io.Serializable;
import java.time.LocalDate;

public class Comment implements Serializable {
    private User author;
    private String text;
    private LocalDate createdAt;

    public Comment(User author, String text) {
        this.author = author;
        this.text = text;
        this.createdAt = LocalDate.now();
    }

    public User getAuthor() {
        return author;
    }

    public String getText() {
        return text;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        String name = author != null ? author.getFirstName() : "Unknown";
        return "[" + name + "]: " + text + " (" + createdAt + ")";
    }
}