package model.communication;

import enums.RequestStatus;
import enums.UrgencyLevel;
import model.users.User;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

public class Request implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private User sender;
    private String description;
    private UrgencyLevel urgencyLevel;
    private RequestStatus status;
    private LocalDate createdAt;

    public Request() {
        this.id = UUID.randomUUID().toString();
        this.status = RequestStatus.NEW;
        this.createdAt = LocalDate.now();
    }

    public Request(User sender, String description, UrgencyLevel urgencyLevel) {
        this.id = UUID.randomUUID().toString();
        this.sender = sender;
        this.description = description;
        this.urgencyLevel = urgencyLevel;
        this.status = RequestStatus.NEW;
        this.createdAt = LocalDate.now();
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public User getSender() { return sender; }
    public void setSender(User sender) { this.sender = sender; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public UrgencyLevel getUrgencyLevel() { return urgencyLevel; }
    public void setUrgencyLevel(UrgencyLevel urgencyLevel) { this.urgencyLevel = urgencyLevel; }

    public RequestStatus getStatus() { return status; }
    public void setStatus(RequestStatus status) { this.status = status; }

    public LocalDate getCreatedAt() { return createdAt; }

    @Override
    public String toString() {
        return "Request[id=" + id.substring(0, 8) + "..., status=" + status
            + ", urgency=" + urgencyLevel + ", desc=" + description + "]";
    }
}
