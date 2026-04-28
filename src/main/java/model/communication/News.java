package model.communication;

import enums.NewsTopic;
import model.users.User;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class News implements Serializable {
    private final String id;
    private String title;
    private String content;
    private NewsTopic topic;
    private final LocalDate date;
    private final User author;
    private final List<Comment> comments;
    private boolean isPinned;

    public News(String title, String content, NewsTopic topic, User author) {
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.content = content;
        this.topic = topic;
        this.author = author;
        this.date = LocalDate.now();
        this.comments = new ArrayList<>();
        this.isPinned = topic == NewsTopic.RESEARCH;
    }

    public void addComment(Comment comment) {
        if (comment != null) {
            comments.add(comment);
        }
    }

    public List<Comment> getComments() {
        return comments;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public NewsTopic getTopic() {
        return topic;
    }

    public LocalDate getDate() {
        return date;
    }

    public User getAuthor() {
        return author;
    }

    public boolean isPinned() {
        return isPinned;
    }

    public void setPinned(boolean pinned) {
        isPinned = pinned;
    }

    @Override
    public String toString() {
        return "News{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", topic=" + topic +
                ", date=" + date +
                ", isPinned=" + isPinned +
                ", comments=" + comments.size() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof News news)) return false;
        return Objects.equals(id, news.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}