package model.users.employees;

import enums.ManagerType;
import enums.School;
import model.academic.Course;
import model.academic.Report;
import model.communication.News;
import model.communication.Request;
import model.users.students.Student;

import java.util.Comparator;
import java.util.List;

// менеджер университета (OR, кафедра, деканат)
// управляет курсами, регистрацией студентов, новостями и отчётами

public class Manager extends Employee {
    private ManagerType managerType;

    public Manager(String firstName, String lastName, String login,
                   String password, School school, ManagerType managerType) {
        super(firstName, lastName, login, password, school);
        this.managerType = managerType;
    }

    public void assignTeacher(Teacher teacher, Course course) {
        // назначить teacher как lectureTeacher или practiceTeacher в course
    }

    public void addCourseForRegistration(Course course) {
        // добавить курс в DataStorage
    }

    public void approveCourseRegistration(Student student, Course course) {
        // одобрить регистрацию студента на курс
    }

    // по GPA, алфавитно, по школе
    public void viewStudents(Comparator<Student> comparator) {
    }

    public void viewTeachers(Comparator<Teacher> comparator) {
    }

    // статистика по оценкам (средний GPA, процент неуспевающих и тд)
    public Report createReport() {
        // создать новый Report, вызвать report.generate() и вернуть
        return null;
    }

    public void addNews(News news) {
        //DataStorage.getInstance().addNews(news)
    }

    public void removeNews(String newsId) {
        // DataStorage.getInstance().removeNews(newsId)
    }

    public void manageNews() {
        // вывести все новости для управления
    }

    public void viewRequests() {
        //  вывести все заявки из DataStorage
    }

    public List<Request> getSignedRequests() {
        // вернуть заявки со статусом ACCEPTED
        return null;
    }

    public ManagerType getManagerType() {
        return managerType;
    }

    public void setManagerType(ManagerType type) {
    }

    @Override
    public String toString() {
        return null;
    }
}