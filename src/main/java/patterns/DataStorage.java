package patterns;

import enums.School;
import model.academic.Course;
import model.academic.Report;
import model.communication.News;
import model.communication.Request;
import model.research.Journal;
import model.research.ResearchPaper;
import model.research.ResearcherDecorator;
import model.users.Student;
import model.users.User;
import model.employees.Teacher;

import java.io.*;
import java.util.Comparator;
import java.util.List;

public class DataStorage implements Serializable {
    // singleton
    private static DataStorage instance;


    private List<User> users;
    private List<Course> courses;
    private List<News> news;
    private List<Journal> journals;
    //private List<Request> requests;
    private List<ResearcherDecorator> researchers;
    private List<String> logs; // лог действий пользователей

    private DataStorage() {
        // инициализировать все списки как новые ArrayList
    }

    public static DataStorage getInstance() {
        //  если instance == null, создать новый DataStorage
        return null;
    }

    // сериализация
    public void save(String filename) {
    }

    public static DataStorage load(String filename) {
        // десериализовать DataStorage из файла filename
        return null;
    }

    public void addUser(User user) {
    }

    public void removeUser(String userId) {
        // найти пользователя по id и удалить
    }

    public User getUserById(String id) {
        return null;
    }

    public User getUserByLogin(String login) {
        return null;
    }

    public List<User> getAllUsers() {
        return null;
    }

    public List<Student> getAllStudents() {
        // отфильтровать users по типу Student
        return null;
    }

    public List<Teacher> getAllTeachers() {
        //  отфильтровать users по типу Teacher
        return null;
    }

    public List<Student> getStudentsBySchool(School school) {
        return null;
    }

    public List<Teacher> getTeachersBySchool(School school) {
        return null;
    }


    public void addCourse(Course course) {
    }

    public void removeCourse(String courseCode) {
    }

    public Course getCourseByCode(String code) {
        return null;
    }

    public List<Course> getAllCourses() {
        return null;
    }

    public List<Course> getCoursesBySchool(School school) {
        return null;
    }

    public List<Course> getAvailableCoursesFor(Student student) {
        //  использовать course.isAvailableFor(student)
        return null;
    }

    public void addNews(News news) {
    }

    public void removeNews(String newsId) {
    }

    public List<News> getAllNews() {
        return null;
    }

    public List<News> getSortedNews() {
        // RESEARCH новости идут первыми (isPinned = true)
        return null;
    }


    public void addJournal(Journal journal) {
        //
    }

    public Journal getJournalByName(String name) {
        return null;
    }

    public List<Journal> getAllJournals() {
        return null;
    }


    public void addRequest(Request request) {
    }

    public List<Request> getAllRequests() {
        return null;
    }

    public List<Request> getNewRequests() {
        // отфильтровать по RequestStatus.NEW
        return null;
    }

    public void addResearcher(ResearcherDecorator researcher) {
    }

    public List<ResearcherDecorator> getAllResearchers() {
        return null;
    }

    public ResearcherDecorator getTopCitedResearcher() {
        // найти исследователя с максимальным h-index
        return null;
    }

    public List<ResearcherDecorator> getTopCitedBySchool(School school) {
        return null;
    }

    public void printAllPapers(Comparator<ResearchPaper> comparator) {
        // собрать все статьи со всех исследователей, отсортировать и вывести
    }

    public void addLog(String logEntry) {
    }

    public List<String> getLogs() {
        return null;
    }

    @Override
    public String toString() {
        return null;
    }
}