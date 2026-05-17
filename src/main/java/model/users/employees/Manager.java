package model.users.employees;

import enums.ManagerType;
import enums.RequestStatus;
import enums.School;
import model.academic.Course;
import model.academic.Report;
import model.communication.News;
import model.communication.Request;
import model.users.students.Student;
import patterns.DataStorage;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Manager extends Employee {
    private static final long serialVersionUID = 1L;

    private ManagerType managerType;

    public Manager(String firstName, String lastName, String login,
                   String password, School school, ManagerType managerType) {
        super(firstName, lastName, login, password, school);
        this.managerType = managerType;
    }

    public void assignTeacher(Teacher teacher, Course course) {
        if (course.getLectureTeacher() == null) {
            course.setLectureTeacher(teacher);
            teacher.addCourse(course);
            System.out.println("Assigned " + teacher.getFullName() + " as lecture teacher for " + course.getName());
        } else if (course.getPracticeTeacher() == null) {
            course.setPracticeTeacher(teacher);
            teacher.addCourse(course);
            System.out.println("Assigned " + teacher.getFullName() + " as practice teacher for " + course.getName());
        } else {
            System.out.println("Course " + course.getName() + " already has both teachers assigned.");
        }
        DataStorage.getInstance().addLog("Manager " + getFullName()
                + " assigned teacher " + teacher.getFullName() + " to course " + course.getName());
    }

    public void addCourseForRegistration(Course course) {
        DataStorage.getInstance().addCourse(course);
        DataStorage.getInstance().addLog("Manager " + getFullName()
                + " added course for registration: " + course.getName());
    }

    public void approveCourseRegistration(Student student, Course course) {
        try {
            student.registerCourse(course);
            DataStorage.getInstance().addLog("Manager " + getFullName()
                    + " approved registration of " + student.getFullName()
                    + " for course " + course.getName());
            System.out.println("Registration approved: " + student.getFullName() + " → " + course.getName());
        } catch (Exception e) {
            System.out.println("Cannot approve registration: " + e.getMessage());
        }
    }

    public void viewStudents(Comparator<Student> comparator) {
        List<Student> students = DataStorage.getInstance().getAllStudents();
        if (students.isEmpty()) {
            System.out.println("No students found.");
            return;
        }
        students.stream()
                .sorted(comparator)
                .forEach(System.out::println);
    }

    public void viewTeachers(Comparator<Teacher> comparator) {
        List<Teacher> teachers = DataStorage.getInstance().getAllTeachers();
        if (teachers.isEmpty()) {
            System.out.println("No teachers found.");
            return;
        }
        teachers.stream()
                .sorted(comparator)
                .forEach(System.out::println);
    }

    public Report createReport() {
        Report report = new Report("University Statistics Report");
        report.generate(
                DataStorage.getInstance().getAllStudents(),
                DataStorage.getInstance().getAllCourses()
        );
        DataStorage.getInstance().addLog("Manager " + getFullName() + " generated a report.");
        return report;
    }

    public void addNews(News news) {
        DataStorage.getInstance().addNews(news);
        DataStorage.getInstance().addLog("Manager " + getFullName() + " added news: " + news.getTitle());
    }

    public void removeNews(String newsId) {
        DataStorage.getInstance().removeNews(newsId);
        DataStorage.getInstance().addLog("Manager " + getFullName() + " removed news id: " + newsId);
    }

    public void manageNews() {
        List<News> allNews = DataStorage.getInstance().getSortedNews();
        if (allNews.isEmpty()) {
            System.out.println("No news available.");
            return;
        }
        allNews.forEach(n -> System.out.println(
                "[" + (n.isPinned() ? "PINNED" : "      ") + "] "
                        + n.getTitle() + " (" + n.getTopic() + ")"));
    }

    public void viewRequests() {
        List<Request> requests = DataStorage.getInstance().getAllRequests();
        if (requests.isEmpty()) {
            System.out.println("No requests.");
            return;
        }
        requests.forEach(System.out::println);
    }

    public List<Request> getSignedRequests() {
        return DataStorage.getInstance().getAllRequests().stream()
                .filter(r -> r.getStatus() == RequestStatus.ACCEPTED)
                .collect(Collectors.toList());
    }

    public ManagerType getManagerType() {
        return managerType;
    }

    public void setManagerType(ManagerType type) {
        this.managerType = type;
    }

    @Override
    public String toString() {
        return "Manager[name=" + getFullName() + ", type=" + managerType + "]";
    }
}