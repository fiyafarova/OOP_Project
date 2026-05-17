package model.communication;

import enums.NewsTopic;
import model.users.User;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class News implements Serializable {
    private String id;
    private String title;
    private String content;
    private NewsTopic topic;
    private User author;
    private LocalDate createdAt;
    private List<Comment> comments;
    private boolean isPinned;

    public News(String title, String content, NewsTopic topic, User author) {
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.content = content;
        this.topic = topic;
        this.author = author;
        this.createdAt = LocalDate.now();
        this.comments = new ArrayList<>();
        this.isPinned = (topic == NewsTopic.RESEARCH);
    }

    public void addComment(Comment comment) {
        if (comment != null) {
            comments.add(comment);
        }
    }

    public String getId() {
        return id;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public boolean isPinned() {
        return isPinned;
    }

    public NewsTopic getTopic() {
        return topic;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public User getAuthor() {
        return author;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return (isPinned ? "[PINNED] " : "") +
                "News{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", topic=" + topic +
                ", createdAt=" + createdAt +
                '}';
    }
}