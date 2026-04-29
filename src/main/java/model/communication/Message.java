package model.communication;

import model.users.employees.Employee;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class Message implements Serializable {
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

    public void markRead() {
        this.isRead = true;
    }

    public String getId() {
        return id;
    }

    public Employee getSender() {
        return sender;
    }

    public Employee getReceiver() {
        return receiver;
    }

    public String getText() {
        return text;
    }

    public LocalDateTime getSentAt() {
        return sentAt;
    }

    public boolean isRead() {
        return isRead;
    }

    @Override
    public String toString() {
        String senderName = sender == null ? "Unknown" : sender.getFirstName() + " " + sender.getLastName();
        String receiverName = receiver == null ? "Unknown" : receiver.getFirstName() + " " + receiver.getLastName();

        return "Message{" +
                "id='" + id + '\'' +
                ", sender=" + senderName +
                ", receiver=" + receiverName +
                ", text='" + text + '\'' +
                ", sentAt=" + sentAt +
                ", isRead=" + isRead +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Message message)) return false;
        return Objects.equals(id, message.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}