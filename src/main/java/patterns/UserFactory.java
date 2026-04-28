package patterns;

import enums.DegreeType;
import enums.ManagerType;
import enums.School;
import enums.TeacherPosition;
import model.employees.Admin;
import model.employees.Manager;
import model.employees.TechSupportSpecialist;
import model.employees.Teacher;
import model.users.GraduateStudent;
import model.users.Student;

import java.util.Map;

//Factory
//Создаёт нужный тип User по данным

public class UserFactory {

    public static Student createStudent(Map<String, String> data, School school) {
        return null;
    }

    public static GraduateStudent createGraduateStudent(Map<String, String> data, School school, DegreeType degree) {
        return null;
    }

    public static Teacher createTeacher(Map<String, String> data, School school) {
        return null;
    }

    public static Admin createAdmin(Map<String, String> data) {
        return null;
    }

    public static Manager createManager(Map<String, String> data, School school, ManagerType type) {
        return null;
    }

    public static TechSupportSpecialist createTechSupport(Map<String, String> data) {
        return null;
    }
}