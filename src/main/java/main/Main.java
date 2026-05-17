package main;

import enums.*;
import menu.*;
import model.academic.Course;
import model.academic.StudentOrganization;
import model.users.User;
import model.users.employees.*;
import model.users.students.Student;
import patterns.DataStorage;
import patterns.UserFactory;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {

    private static final String DATA_FILE = "data.ser";
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // 1. Load or initialize DataStorage
        if (new File(DATA_FILE).exists()) {
            DataStorage.load(DATA_FILE);
        } else {
            System.out.println("First launch — creating test data...");
            createTestData();
        }

        // 2. Auth loop
        while (true) {
            System.out.println("\n╔══════════════════════════════╗");
            System.out.println("║   UNIVERSITY SYSTEM LOGIN    ║");
            System.out.println("╚══════════════════════════════╝");
            System.out.print("Login: ");
            String login = scanner.nextLine().trim();
            System.out.print("Password: ");
            String password = scanner.nextLine().trim();

            User user = DataStorage.getInstance().getUserByLogin(login);

            if (user == null || !user.login(login, password)) {
                System.out.println("Invalid login or password. Try again.");
                continue;
            }

            DataStorage.getInstance().addLog("User logged in: " + user.getLogin());
            System.out.println("\nWelcome, " + user.getFullName() + "!");

            // 3. Route to correct menu
            openMenu(user);

            // 4. Save on exit
            DataStorage.getInstance().save(DATA_FILE);

            // Ask if another user wants to log in
            System.out.print("\nLog in as another user? (y/n): ");
            if (!scanner.nextLine().trim().equalsIgnoreCase("y")) {
                break;
            }
        }

        System.out.println("Goodbye!");
    }

    private static void openMenu(User user) {
        if (user instanceof Admin) {
            new AdminMenu((Admin) user).show();
        } else if (user instanceof Manager) {
            new ManagerMenu((Manager) user).show();
        } else if (user instanceof TechSupportSpecialist) {
            new TechSupportMenu((TechSupportSpecialist) user).show();
        } else if (user instanceof Student) {
            new StudentMenu((Student) user).show();
        } else if (user instanceof Teacher) {
            new TeacherMenu((Teacher) user).show();
        } else {
            System.out.println("No menu available for user type: "
                    + user.getClass().getSimpleName());
        }
    }

    //  Test Data

    private static void createTestData() {
        DataStorage ds = DataStorage.getInstance();

        // Admin
        Map<String, String> adminData = new HashMap<>();
        adminData.put("firstName", "Sofia");
        adminData.put("lastName",  "Admin");
        adminData.put("login",     "admin");
        adminData.put("password",  "admin123");
        UserFactory.createAdmin(adminData);

        // Manager
        Map<String, String> mgrData = new HashMap<>();
        mgrData.put("firstName", "Almas");
        mgrData.put("lastName",  "Manager");
        mgrData.put("login",     "manager");
        mgrData.put("password",  "mgr123");
        UserFactory.createManager(mgrData, School.SITE, ManagerType.OR);

        // TechSupport
        Map<String, String> tsData = new HashMap<>();
        tsData.put("firstName", "Amir");
        tsData.put("lastName",  "Support");
        tsData.put("login",     "support");
        tsData.put("password",  "sup123");
        UserFactory.createTechSupport(tsData);

        // Teachers
        Map<String, String> t1Data = new HashMap<>();
        t1Data.put("firstName", "John");
        t1Data.put("lastName",  "Smith");
        t1Data.put("login",     "jsmith");
        t1Data.put("password",  "pass123");
        t1Data.put("position",  "LECTOR");
        Teacher teacher1 = UserFactory.createTeacher(t1Data, School.SITE);

        Map<String, String> t2Data = new HashMap<>();
        t2Data.put("firstName", "Anna");
        t2Data.put("lastName",  "Ivanova");
        t2Data.put("login",     "aivanova");
        t2Data.put("password",  "pass123");
        t2Data.put("position",  "PROFESSOR");
        Teacher teacher2 = UserFactory.createTeacher(t2Data, School.SB);

        // Students
        Map<String, String> s1Data = new HashMap<>();
        s1Data.put("firstName", "Bekzat");
        s1Data.put("lastName",  "Akhmetov");
        s1Data.put("login",     "bakhmetov");
        s1Data.put("password",  "stu123");
        s1Data.put("year",      "2");
        Student student1 = UserFactory.createStudent(s1Data, School.SITE);

        Map<String, String> s2Data = new HashMap<>();
        s2Data.put("firstName", "Dana");
        s2Data.put("lastName",  "Seitkali");
        s2Data.put("login",     "dseitkali");
        s2Data.put("password",  "stu123");
        s2Data.put("year",      "1");
        Student student2 = UserFactory.createStudent(s2Data, School.SB);

        // Courses
        Course c1 = new Course("CS101", "Introduction to Programming", 5, School.SITE, CourseType.MAJOR);
        Course c2 = new Course("MATH101", "Calculus", 5, School.SB, CourseType.MAJOR);
        Course c3 = new Course("ENG101", "Academic English", 3, School.SEDU, CourseType.FREE_ELECTIVE);

        c1.setLectureTeacher(teacher1);
        teacher1.addCourse(c1);
        c2.setLectureTeacher(teacher2);
        teacher2.addCourse(c2);

        ds.addCourse(c1);
        ds.addCourse(c2);
        ds.addCourse(c3);

        // Student Organizations
        StudentOrganization org1 = new StudentOrganization("ACM Student Chapter");
        StudentOrganization org2 = new StudentOrganization("Debate Club");
        ds.addOrganization(org1);
        ds.addOrganization(org2);

        ds.addLog("Test data initialized.");
        System.out.println("Test data created successfully.");
        System.out.println("  Admin    → login: admin    / password: admin123");
        System.out.println("  Manager  → login: manager  / password: mgr123");
        System.out.println("  Support  → login: support  / password: sup123");
        System.out.println("  Teacher  → login: jsmith   / password: pass123");
        System.out.println("  Student  → login: bakhmetov / password: stu123");
    }
}