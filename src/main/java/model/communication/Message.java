package model.communication;

import model.users.employees.Employee;

import java.io.Serializable;
import java.time.LocalDate;

public class Message implements Serializable {
    private Employee sender;
    private Employee receiver;
    private String text;
    private LocalDate sentAt;

    public Message(Employee sender, Employee receiver, String text) {
        this.sender = sender;
        this.receiver = receiver;
        this.text = text;
        this.sentAt = LocalDate.now();
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

    public LocalDate getSentAt() {
        return sentAt;
    }

    @Override
    public String toString() {
        String from = sender != null ? sender.getFirstName() : "Unknown";
        String to = receiver != null ? receiver.getFirstName() : "Unknown";
        return "From " + from + " to " + to + ": " + text;
    }
}