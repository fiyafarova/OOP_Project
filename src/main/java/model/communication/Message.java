package model.communication;

import model.users.employees.Employee;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class Message implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String id;
    private final Employee sender;
    private final Employee receiver;
    private final String text;
    private final LocalDateTime sentAt;
    private boolean isRead;

    public Message(Employee sender, Employee receiver, String text) {
        this.id = UUID.randomUUID().toString();
        this.sender = sender;
        this.receiver = receiver;
        this.text = text;
        this.sentAt = LocalDateTime.now();
        this.isRead = false;
    }

    public void markRead() { this.isRead = true; }

    public String getId() { return id; }
    public Employee getSender() { return sender; }
    public Employee getReceiver() { return receiver; }
    public String getText() { return text; }
    public LocalDateTime getSentAt() { return sentAt; }
    public boolean isRead() { return isRead; }

    @Override
    public String toString() {
        String from = sender == null ? "Unknown" : sender.getFullName();
        String to = receiver == null ? "Unknown" : receiver.getFullName();
        return "From " + from + " to " + to + ": " + text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Message)) return false;
        return Objects.equals(id, ((Message) o).id);
    }

    @Override
    public int hashCode() { return Objects.hash(id); }
}