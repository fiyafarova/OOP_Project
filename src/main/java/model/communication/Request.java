package model.communication;

import enums.RequestStatus;
import enums.UrgencyLevel;
import model.users.User;

import java.io.Serializable;
import java.util.UUID;

public class Request implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private User sender;
    private String content;
    private UrgencyLevel urgencyLevel;
    private RequestStatus status;

    public Request() {
        this.id = UUID.randomUUID().toString();
        this.status = RequestStatus.NEW;
    }

    public Request(User sender, String content, UrgencyLevel urgencyLevel) {
        this.id = UUID.randomUUID().toString();
        this.sender = sender;
        this.content = content;
        this.urgencyLevel = urgencyLevel;
        this.status = RequestStatus.NEW;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public UrgencyLevel getUrgencyLevel() {
        return urgencyLevel;
    }

    public void setUrgencyLevel(UrgencyLevel urgencyLevel) {
        this.urgencyLevel = urgencyLevel;
    }

    public RequestStatus getStatus() {
        return status;
    }

    public void setStatus(RequestStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Request{id=" + id + ", sender=" + sender +
                ", urgency=" + urgencyLevel + ", status=" + status + "}";
    }
}