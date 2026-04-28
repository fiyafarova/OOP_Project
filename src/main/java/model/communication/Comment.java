package model.communication;

import model.users.User;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class Comment implements Serializable {
    private final String id;
    private String text;
    private final User author;
    private final LocalDateTime createdAt;

    public Comment(User author, String text) {
        this.id = UUID.randomUUID().toString();
        this.author = author;
        this.text = text;
        this.createdAt = LocalDateTime.now();
    }

    public String getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public User getAuthor() {
        return author;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        String authorName = author == null ? "Unknown" : author.getFirstName() + " " + author.getLastName();
        return "Comment{" +
                "id='" + id + '\'' +
                ", text='" + text + '\'' +
                ", author=" + authorName +
                ", createdAt=" + createdAt +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Comment comment)) return false;
        return Objects.equals(id, comment.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}