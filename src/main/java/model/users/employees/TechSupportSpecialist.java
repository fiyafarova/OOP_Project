package model.users.employees;

import enums.School;
import model.communication.Request;

import java.util.ArrayList;
import java.util.List;

// специалист тех поддержки
// видит заявки, принимает/отклоняет их
// статус заявки меняется автоматически при просмотре

public class TechSupportSpecialist extends Employee {
    private List<Request> assignedRequests;

    public TechSupportSpecialist(String firstName, String lastName,
                                 String login, String password) {
        super(firstName,lastName,login,password, School.SUPPORT);
        this.assignedRequests = new ArrayList<>();
    }

    // после вызова статус заявок меняется NEW на VIEWED
    public List<Request> viewNewRequests() {
        // получить новые заявки из DataStorage
        // для каждой заявки установить статус VIEWED
        // вернуть список
        return null;
    }

    public void acceptRequest(Request request) {
        // установить статус ACCEPTED
        // добавить в assignedRequests
    }

    public void rejectRequest(Request request) {
        // установить статус REJECTED
    }

    public void markDone(Request request) {
        // установить статус DONE
    }

    public List<Request> viewRequests() {
        // вернуть assignedRequests
        return null;
    }

    @Override
    public String toString() {
        // TODO
        return null;
    }
}