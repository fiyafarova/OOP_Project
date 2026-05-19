package patterns;

import enums.RequestStatus;
import enums.School;
import model.academic.Course;
import model.academic.StudentOrganization;
import model.communication.News;
import model.communication.Request;
import model.research.Journal;
import model.research.ResearchPaper;
import model.research.ResearcherDecorator;
import model.users.User;
import model.users.students.Student;
import model.users.employees.Teacher;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class DataStorage implements Serializable {
    private static final long serialVersionUID = 1L;

    private static DataStorage instance;

    private List<User> users;
    private List<Course> courses;
    private List<News> news;
    private List<Journal> journals;
    private List<Request> requests;
    private List<ResearcherDecorator> researchers;
    private List<StudentOrganization> organizations;
    private List<String> logs;

    private DataStorage() {
        users = new ArrayList<>();
        courses = new ArrayList<>();
        news = new ArrayList<>();
        journals = new ArrayList<>();
        requests = new ArrayList<>();
        researchers = new ArrayList<>();
        organizations = new ArrayList<>();
        logs = new ArrayList<>();
    }

    public static DataStorage getInstance() {
        if (instance == null) {
            instance = new DataStorage();
        }
        return instance;
    }


    // Serialization
    public void save(String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(this);
            System.out.println("Data saved to " + filename);
        } catch (IOException e) {
            System.out.println("Failed to save data: " + e.getMessage());
        }
    }

    public static DataStorage load(String filename) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            instance = (DataStorage) ois.readObject();
            System.out.println("Data loaded from " + filename);
            return instance;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Could not load data, starting fresh. (" + e.getMessage() + ")");
            return getInstance();
        }
    }

    // Users
    public void addUser(User user) {
        if (user != null && getUserById(user.getId()) == null) {
            users.add(user);
        }
    }

    public void removeUser(String userId) {
        users.removeIf(u -> u.getId().equals(userId));
    }

    public User getUserById(String id) {
        return users.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public User getUserByLogin(String login) {
        return users.stream()
                .filter(u -> u.getLogin().equals(login))
                .findFirst()
                .orElse(null);
    }

    public List<User> getAllUsers() {
        return users;
    }

    public List<Student> getAllStudents() {
        return users.stream()
                .filter(u -> u instanceof Student)
                .map(u -> (Student) u)
                .collect(Collectors.toList());
    }

    public List<Teacher> getAllTeachers() {
        return users.stream()
                .filter(u -> u instanceof Teacher)
                .map(u -> (Teacher) u)
                .collect(Collectors.toList());
    }

    public List<Student> getStudentsBySchool(School school) {
        return getAllStudents().stream()
                .filter(s -> s.getSchool() == school)
                .collect(Collectors.toList());
    }

    public List<Teacher> getTeachersBySchool(School school) {
        return getAllTeachers().stream()
                .filter(t -> t.getSchool() == school)
                .collect(Collectors.toList());
    }

    public List<User> getAllEmployees() {
        return users.stream()
                .filter(u -> u instanceof model.users.employees.Employee)
                .collect(Collectors.toList());
    }

    // Courses
    public void addCourse(Course course) {
        if (course != null && getCourseByCode(course.getCode()) == null) {
            courses.add(course);
        }
    }

    public void removeCourse(String courseCode) {
        courses.removeIf(c -> c.getCode().equals(courseCode));
    }

    public Course getCourseByCode(String code) {
        return courses.stream()
                .filter(c -> c.getCode().equals(code))
                .findFirst()
                .orElse(null);
    }

    public List<Course> getAllCourses() {
        return courses;
    }

    public List<Course> getCoursesBySchool(School school) {
        return courses.stream()
                .filter(c -> c.getSchool() == school)
                .collect(Collectors.toList());
    }

    public List<Course> getAvailableCoursesFor(Student student) {
        return courses.stream()
                .filter(c -> c.isAvailableFor(student))
                .collect(Collectors.toList());
    }

    // News
    public void addNews(News n) {
        if (n != null) {
            news.add(n);
        }
    }

    public void removeNews(String newsId) {
        news.removeIf(n -> n.getId().equals(newsId));
    }

    public List<News> getAllNews() {
        return news;
    }

    public List<News> getSortedNews() {
        return news.stream()
                .sorted((a, b) -> {
                    if (a.isPinned() && !b.isPinned()) return -1;
                    if (!a.isPinned() && b.isPinned()) return 1;
                    return b.getCreatedAt().compareTo(a.getCreatedAt());
                })
                .collect(Collectors.toList());
    }

    // Journals
    public void addJournal(Journal journal) {
        if (journal != null && !journals.contains(journal)) {
            journals.add(journal);
        }
    }

    public Journal getJournalByName(String name) {
        return journals.stream()
                .filter(j -> j.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    public List<Journal> getAllJournals() {
        return journals;
    }

    // Requests
    public void addRequest(Request request) {
        if (request != null) {
            requests.add(request);
        }
    }

    public List<Request> getAllRequests() {
        return requests;
    }

    public List<Request> getNewRequests() {
        return requests.stream()
                .filter(r -> r.getStatus() == RequestStatus.NEW)
                .collect(Collectors.toList());
    }

    // Researchers
    public void addResearcher(ResearcherDecorator researcher) {
        if (researcher != null && !researchers.contains(researcher)) {
            researchers.add(researcher);
        }
    }

    public List<ResearcherDecorator> getAllResearchers() {
        return researchers;
    }

    public ResearcherDecorator getResearcherByUser(User user) {
        return researchers.stream()
                .filter(r -> r.getWrapped().equals(user))
                .findFirst()
                .orElse(null);
    }

    public ResearcherDecorator getTopCitedResearcher() {
        return researchers.stream()
                .max(Comparator.comparingInt(ResearcherDecorator::calculateHIndex))
                .orElse(null);
    }

    public List<ResearcherDecorator> getTopCitedBySchool(School school) {
        return researchers.stream()
                .filter(r -> {
                    User u = r.getWrapped();
                    if (u instanceof Student) {
                        return ((Student) u).getSchool() == school;
                    }
                    if (u instanceof Teacher) {
                        return ((Teacher) u).getSchool() == school;
                    }
                    return false;
                })
                .sorted(Comparator.comparingInt(ResearcherDecorator::calculateHIndex).reversed())
                .collect(Collectors.toList());
    }

    public void printAllPapers(Comparator<ResearchPaper> comparator) {
        researchers.stream()
                .flatMap(r -> r.getPapers().stream())
                .distinct()
                .sorted(comparator)
                .forEach(System.out::println);
    }

    // Organizations
    public void addOrganization(StudentOrganization org) {
        if (org != null && !organizations.contains(org)) {
            organizations.add(org);
        }
    }

    public List<StudentOrganization> getAllOrganizations() {
        return organizations;
    }

    // Logs
    public void addLog(String entry) {
        logs.add(LocalDateTime.now() + ": " + entry);
    }

    public List<String> getLogs() {
        return logs;
    }

    @Override
    public String toString() {
        return "DataStorage[users=" + users.size()
                + ", courses=" + courses.size()
                + ", news=" + news.size()
                + ", journals=" + journals.size()
                + ", requests=" + requests.size()
                + ", researchers=" + researchers.size()
                + ", organizations=" + organizations.size() + "]";
    }
}