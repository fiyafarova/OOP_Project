package model.research;

import model.users.employees.Employee;

public class EmployeeResearcher extends ResearcherDecorator {
    public EmployeeResearcher(Employee employee) {
        super(employee);
    }

    public Employee getEmployee() {
        return (Employee) wrappedUser;
    }

    @Override
    public String toString() {
        return "EmployeeResearcher[" + wrappedUser + "]";
    }
}