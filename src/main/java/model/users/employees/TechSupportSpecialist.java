package model.users.employees;

import enums.RequestStatus;
import enums.School;
import model.communication.Request;
import patterns.DataStorage;

import java.util.ArrayList;
import java.util.List;

public class TechSupportSpecialist extends Employee {
    private static final long serialVersionUID = 1L;

    private List<Request> assignedRequests;

    public TechSupportSpecialist(String firstName, String lastName,
                                 String login, String password) {
        super(firstName, lastName, login, password, School.SITE);
        this.assignedRequests = new ArrayList<>();
    }

    public List<Request> viewNewRequests() {
        List<Request> newRequests = DataStorage.getInstance().getNewRequests();
        newRequests.forEach(r -> r.setStatus(RequestStatus.VIEWED));
        if (newRequests.isEmpty()) {
            System.out.println("No new requests.");
        } else {
            newRequests.forEach(System.out::println);
        }
        return newRequests;
    }

    public void acceptRequest(Request request) {
        request.setStatus(RequestStatus.ACCEPTED);
        if (!assignedRequests.contains(request)) {
            assignedRequests.add(request);
        }
        System.out.println("Request accepted: " + request.getId());
    }

    public void rejectRequest(Request request) {
        request.setStatus(RequestStatus.REJECTED);
        System.out.println("Request rejected: " + request.getId());
    }

    public void markDone(Request request) {
        request.setStatus(RequestStatus.DONE);
        System.out.println("Request marked as done: " + request.getId());
    }

    public List<Request> viewRequests() {
        return assignedRequests;
    }

    @Override
    public String toString() {
        return "TechSupport[name=" + getFullName() + "]";
    }
}