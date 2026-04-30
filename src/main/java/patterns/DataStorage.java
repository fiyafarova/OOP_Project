package patterns;

import model.academic.Course;
import model.academic.StudentOrganization;
import model.communication.Request;
import model.employees.Employee;
import model.employees.Teacher;
import model.users.Student;
import model.users.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DataStorage implements Serializable {
    private static final long serialVersionUID = 1L;

    private static DataStorage instance;

    private List<User> users = new ArrayList<>();
    private List<Course> courses = new ArrayList<>();
    private List<Request> requests = new ArrayList<>();
    private List<StudentOrganization> organizations = new ArrayList<>();

    private DataStorage() {}

    public static DataStorage getInstance() {
        if (instance == null) {
            instance = new DataStorage();
        }
        return instance;
    }

    public List<User> getAllUsers() { return users; }

    public List<Teacher> getAllTeachers() {
        return users.stream()
            .filter(u -> u instanceof Teacher)
            .map(u -> (Teacher) u)
            .collect(Collectors.toList());
    }

    public List<Student> getAllStudents() {
        return users.stream()
            .filter(u -> u instanceof Student)
            .map(u -> (Student) u)
            .collect(Collectors.toList());
    }

    public List<Employee> getAllEmployees() {
        return users.stream()
            .filter(u -> u instanceof Employee)
            .map(u -> (Employee) u)
            .collect(Collectors.toList());
    }

    public List<Course> getAvailableCoursesFor(Student student) {
        return courses.stream()
            .filter(c -> !student.getCourses().contains(c) && c.isAvailableFor(student))
            .collect(Collectors.toList());
    }

    public void addRequest(Request request) { requests.add(request); }
    public List<Request> getAllRequests() { return requests; }

    public List<StudentOrganization> getAllOrganizations() { return organizations; }
    public List<Course> getAllCourses() { return courses; }

    public void addUser(User user) { users.add(user); }
    public void addCourse(Course course) { courses.add(course); }
    public void addOrganization(StudentOrganization org) { organizations.add(org); }
}
