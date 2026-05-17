package patterns;

import enums.DegreeType;
import enums.ManagerType;
import enums.School;
import enums.TeacherPosition;
import model.users.employees.Admin;
import model.users.employees.Manager;
import model.users.employees.TechSupportSpecialist;
import model.users.employees.Teacher;
import model.users.students.GraduateStudent;
import model.users.students.Student;

import java.util.Map;

public class UserFactory {

    public static Student createStudent(Map<String, String> data, School school) {
        Student student = new Student(
                data.get("firstName"),
                data.get("lastName"),
                data.get("login"),
                data.get("password"),
                school,
                Integer.parseInt(data.getOrDefault("year", "1"))
        );
        DataStorage.getInstance().addUser(student);
        return student;
    }

    public static GraduateStudent createGraduateStudent(Map<String, String> data,
                                                        School school, DegreeType degree) {
        GraduateStudent gs = new GraduateStudent(
                data.get("firstName"),
                data.get("lastName"),
                data.get("login"),
                data.get("password"),
                school,
                degree,
                Integer.parseInt(data.getOrDefault("year", "1"))
        );
        DataStorage.getInstance().addUser(gs);
        return gs;
    }

    public static Teacher createTeacher(Map<String, String> data, School school) {
        TeacherPosition pos = TeacherPosition.valueOf(
                data.getOrDefault("position", "LECTOR"));
        Teacher teacher = new Teacher(
                data.get("firstName"),
                data.get("lastName"),
                data.get("login"),
                data.get("password"),
                school,
                pos
        );
        DataStorage.getInstance().addUser(teacher);
        return teacher;
    }

    public static Admin createAdmin(Map<String, String> data) {
        Admin admin = new Admin(
                data.get("firstName"),
                data.get("lastName"),
                data.get("login"),
                data.get("password")
        );
        DataStorage.getInstance().addUser(admin);
        return admin;
    }

    public static Manager createManager(Map<String, String> data,
                                        School school, ManagerType type) {
        Manager manager = new Manager(
                data.get("firstName"),
                data.get("lastName"),
                data.get("login"),
                data.get("password"),
                school,
                type
        );
        DataStorage.getInstance().addUser(manager);
        return manager;
    }

    public static TechSupportSpecialist createTechSupport(Map<String, String> data) {
        TechSupportSpecialist ts = new TechSupportSpecialist(
                data.get("firstName"),
                data.get("lastName"),
                data.get("login"),
                data.get("password")
        );
        DataStorage.getInstance().addUser(ts);
        return ts;
    }
}