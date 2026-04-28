package model.academic;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

// статистика по оценкам
// генерируется Manager

public class Report {
    private String title;
    private LocalDate createdAt;
    private Map<Course, Double> averageGradesByCourse;
    private double universityAverageGpa;
    private int totalStudents;
    private int failingStudents;

    public Report(String title) {
        // установить title
        // createdAt = LocalDate.now()
    }

    public void generate(List students, List courses) {
        // пройтись по всем студентам и курсам
        //  вычислить для каждого курса средний балл
        // вычислить среднее GPA всех студентов
        // заполнить totalStudents и failingStudents (GPA < 2.0 считается failing)
    }

    public void print() {
        // вывести заголовок, дату, средний GPA университета
        // вывести таблицу - курс - средний балл
        // вывести totalStudents и failingStudents
    }

    public String getTitle() {
        return title;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public double getUniversityAverageGpa() {
        return universityAverageGpa;
    }

    public Map<Course, Double> getAverageGradesByCourse() {
        return averageGradesByCourse;
    }

    public int getTotalStudents() {
        return totalStudents;
    }

    public int getFailingStudents() {
        return failingStudents;
    }

    @Override
    public String toString() {
        return null;
    }
}