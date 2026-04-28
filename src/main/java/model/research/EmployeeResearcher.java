package model.research;

import model.employees.Employee;

public class EmployeeResearcher extends ResearcherDecorator {
    private final Employee employee;

    public EmployeeResearcher(Employee employee) {
        super(employee);
        this.employee = employee;
    }

    public Employee getEmployee() {
        return employee;
    }

    @Override
    public String toString() {
        return "EmployeeResearcher{" +
                "employee=" + employee +
                '}';
    }
}