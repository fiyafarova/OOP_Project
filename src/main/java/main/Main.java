package main;

import enums.*;
import menu.*;
import model.academic.Course;
import model.academic.Lesson;
import model.academic.StudentOrganization;
import model.communication.News;
import model.research.Journal;
import model.research.ResearchPaper;
import model.research.StudentResearcher;
import model.research.TeacherResearcher;
import model.users.User;
import model.users.employees.Admin;
import model.users.employees.Manager;
import model.users.employees.Teacher;
import model.users.employees.TechSupportSpecialist;
import model.users.students.GraduateStudent;
import model.users.students.Student;
import patterns.DataStorage;
import patterns.UserFactory;
import util.LanguageManager;

import java.io.File;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {

    private static final String DATA_FILE = "data.ser";
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        if (new File(DATA_FILE).exists()) {
            DataStorage.load(DATA_FILE);
        } else {
            System.out.println("First launch - creating test data");
            createTestData();
            DataStorage.getInstance().save(DATA_FILE);
        }

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
            System.out.println("\n" + LanguageManager.get(user.getLanguage(), "general.welcome") + user.getFullName() + "!");

            openMenu(user);

            DataStorage.getInstance().save(DATA_FILE);

            System.out.print("\n" + LanguageManager.get(user.getLanguage(), "login.another"));
            if (!scanner.nextLine().trim().equalsIgnoreCase("y")) {
                break;
            }
        }

        System.out.println(LanguageManager.get(Language.EN, "general.goodbye"));
    }

    private static void openMenu(User user) {
        while (true) {
            Language lang = user.getLanguage();

            boolean isResearcher = DataStorage.getInstance()
                    .getAllResearchers()
                    .stream()
                    .anyMatch(r -> r.getWrapped().equals(user));

            printMainMenu(lang, isResearcher);
            LanguageManager.prompt(lang, "general.choose");
            String choice = scanner.nextLine().trim();

            if ("1".equals(choice)) {
                openRoleMenu(user);
            } else if ("2".equals(choice)) {
                NewsMenu.open(user, scanner);
            } else if ("3".equals(choice) && isResearcher) {
                ResearcherMenu.open(user, scanner);
            } else if (("4".equals(choice) && isResearcher) || ("3".equals(choice) && !isResearcher)) {
                DataStorage.getInstance().addLog("User logged out: " + user.getLogin());
                user.logout();
                return;
            } else {
                LanguageManager.print(lang, "general.invalid_choice");
            }
        }
    }

    private static void printMainMenu(Language lang, boolean isResearcher) {
        System.out.println();
        System.out.println("╔══════════════════════════════╗");
        System.out.println("║" + centerText(getMainMenuTitle(lang), 30) + "║");
        System.out.println("╚══════════════════════════════╝");

        System.out.println("1. " + getMainRoleMenuLabel(lang));
        System.out.println("2. " + getNewsMenuLabel(lang));

        if (isResearcher) {
            System.out.println("3. " + getResearcherMenuLabel(lang));
            System.out.println("4. " + LanguageManager.get(lang, "general.logout"));
        } else {
            System.out.println("3. " + LanguageManager.get(lang, "general.logout"));
        }
    }

    private static String getMainMenuTitle(Language lang) {
        switch (lang) {
            case KZ:
                return "НЕГІЗГІ МӘЗІР";
            case RU:
                return "ГЛАВНОЕ МЕНЮ";
            case EN:
            default:
                return "MAIN MENU";
        }
    }

    private static String getMainRoleMenuLabel(Language lang) {
        switch (lang) {
            case KZ:
                return "Негізгі рөл мәзірі";
            case RU:
                return "Основное меню роли";
            case EN:
            default:
                return "Open main role menu";
        }
    }

    private static String getNewsMenuLabel(Language lang) {
        switch (lang) {
            case KZ:
                return "Жаңалықтар";
            case RU:
                return "Новости";
            case EN:
            default:
                return "Open news";
        }
    }

    private static String getResearcherMenuLabel(Language lang) {
        switch (lang) {
            case KZ:
                return "Зерттеуші мәзірі";
            case RU:
                return "Меню исследователя";
            case EN:
            default:
                return "Open researcher menu";
        }
    }

    private static String centerText(String text, int width) {
        if (text.length() >= width) return text;
        int leftPadding = (width - text.length()) / 2;
        int rightPadding = width - text.length() - leftPadding;
        return " ".repeat(leftPadding) + text + " ".repeat(rightPadding);
    }

    private static void openRoleMenu(User user) {
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

    private static void createTestData() {
        DataStorage ds = DataStorage.getInstance();

        // Admin
        Map<String, String> adminData = new HashMap<>();
        adminData.put("firstName", "Sofia");
        adminData.put("lastName", "Admin");
        adminData.put("login", "admin");
        adminData.put("password", "admin123");
        Admin admin = UserFactory.createAdmin(adminData);
        ds.addUser(admin);

        // Manager
        Map<String, String> mgrData = new HashMap<>();
        mgrData.put("firstName", "Almas");
        mgrData.put("lastName", "Manager");
        mgrData.put("login", "manager");
        mgrData.put("password", "mgr123");
        Manager manager = UserFactory.createManager(mgrData, School.SITE, ManagerType.OR);
        ds.addUser(manager);

        // TechSupport
        Map<String, String> tsData = new HashMap<>();
        tsData.put("firstName", "Amir");
        tsData.put("lastName", "Support");
        tsData.put("login", "support");
        tsData.put("password", "sup123");
        TechSupportSpecialist support = UserFactory.createTechSupport(tsData);
        ds.addUser(support);

        // Teachers
        Map<String, String> t1Data = new HashMap<>();
        t1Data.put("firstName", "John");
        t1Data.put("lastName", "Smith");
        t1Data.put("login", "jsmith");
        t1Data.put("password", "pass123");
        t1Data.put("position", "LECTOR");
        Teacher teacher1 = UserFactory.createTeacher(t1Data, School.SITE);
        ds.addUser(teacher1);

        Map<String, String> t2Data = new HashMap<>();
        t2Data.put("firstName", "Anna");
        t2Data.put("lastName", "Ivanova");
        t2Data.put("login", "aivanova");
        t2Data.put("password", "pass123");
        t2Data.put("position", "PROFESSOR");
        Teacher teacher2 = UserFactory.createTeacher(t2Data, School.BS);
        ds.addUser(teacher2);

        // Students
        Map<String, String> s1Data = new HashMap<>();
        s1Data.put("firstName", "Bekzat");
        s1Data.put("lastName", "Akhmetov");
        s1Data.put("login", "bakhmetov");
        s1Data.put("password", "stu123");
        s1Data.put("year", "2");
        Student student1 = UserFactory.createStudent(s1Data, School.SITE);
        ds.addUser(student1);

        Map<String, String> s2Data = new HashMap<>();
        s2Data.put("firstName", "Dana");
        s2Data.put("lastName", "Seitkali");
        s2Data.put("login", "dseitkali");
        s2Data.put("password", "stu123");
        s2Data.put("year", "1");
        Student student2 = UserFactory.createStudent(s2Data, School.BS);
        ds.addUser(student2);

        // Graduate Student (MASTER)
        GraduateStudent gradStudent = new GraduateStudent(
                "Aruzhan", "Researcher", "aresearcher", "grad123",
                School.SITE, DegreeType.MASTER, 1
        );
        ds.addUser(gradStudent);

        TeacherResearcher teacherResearcher = new TeacherResearcher(teacher2);
        StudentResearcher studentResearcher = new StudentResearcher(gradStudent);
        ds.addResearcher(teacherResearcher);
        ds.addResearcher(studentResearcher);

        // Research papers for professor
        ResearchPaper p1 = new ResearchPaper(
                "Machine Learning in Education", "KBTU Research Journal",
                10, LocalDate.of(2023, 5, 10), "10.1000/ml-edu");
        p1.setCitations(10);
        teacherResearcher.addPaper(p1);

        ResearchPaper p2 = new ResearchPaper(
                "Data Science for University Systems", "Science Bulletin",
                12, LocalDate.of(2022, 11, 5), "10.1000/ds-university");
        p2.setCitations(7);
        teacherResearcher.addPaper(p2);

        ResearchPaper p3 = new ResearchPaper(
                "AI-based Student Analytics", "KBTU Research Journal",
                8, LocalDate.of(2024, 2, 15), "10.1000/ai-analytics");
        p3.setCitations(4);
        teacherResearcher.addPaper(p3);

        ResearchPaper p4 = new ResearchPaper(
                "Educational Recommendation Systems", "KBTU Research Journal",
                9, LocalDate.of(2024, 4, 1), "10.1000/recommendation-systems");
        p4.setCitations(2);
        studentResearcher.addPaper(p4);
        gradStudent.addDiplomaPaper(p4);

        try {
            gradStudent.setSupervisor(teacherResearcher);
        } catch (Exception e) {
            System.out.println("Could not assign supervisor: " + e.getMessage());
        }

        GraduateStudent phdStudent = new GraduateStudent(
                "Daniyar", "PhDStudent", "daniyar", "phd123",
                School.SITE, DegreeType.PHD, 2
        );
        ds.addUser(phdStudent);

        StudentResearcher phdResearcher = new StudentResearcher(phdStudent);
        ds.addResearcher(phdResearcher);

        ResearchPaper phdPaper = new ResearchPaper(
                "Deep Learning for NLP", "Science Bulletin",
                15, LocalDate.of(2024, 1, 20), "10.1000/dl-nlp");
        phdPaper.setCitations(5);
        phdResearcher.addPaper(phdPaper);
        phdStudent.addDiplomaPaper(phdPaper);

        try {
            phdStudent.setSupervisor(teacherResearcher);
        } catch (Exception e) {
            System.out.println("Could not assign supervisor: " + e.getMessage());
        }

        // Courses
        Course c1 = new Course("CS101", "Introduction to Programming", 5, School.SITE, CourseType.MAJOR);
        Course c2 = new Course("MATH101", "Calculus", 5, School.BS, CourseType.MAJOR);
        Course c3 = new Course("ENG101", "Academic English", 3, School.SCE, CourseType.FREE_ELECTIVE);

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

        // Journals
        Journal j1 = new Journal("KBTU Research Journal");
        Journal j2 = new Journal("Science Bulletin");
        ds.addJournal(j1);
        ds.addJournal(j2);

        j1.subscribe(student1);
        j1.subscribe(student2);
        j1.subscribe(teacher1);

        teacherResearcher.publishPaperToJournal(p1, j1);

        // News
        ds.addNews(new News(
                "Welcome to the University System",
                "System initialized successfully with demo data.",
                NewsTopic.GENERAL, admin
        ));

        // Lessons
        Lesson l1 = new Lesson("Intro to OOP", LessonType.LECTURE, c1,
                java.time.LocalDateTime.of(2024, 9, 2, 9, 0),
                "Introduction to OOP concepts", "A201", "Mon 09:00-10:30");
        Lesson l2 = new Lesson("Practice: Classes", LessonType.PRACTICE, c1,
                java.time.LocalDateTime.of(2024, 9, 4, 11, 0),
                "Writing first Java classes", "Lab B104", "Wed 11:00-12:30");
        c1.addLesson(l1);
        c1.addLesson(l2);

        Lesson l3 = new Lesson("Limits and Derivatives", LessonType.LECTURE, c2,
                java.time.LocalDateTime.of(2024, 9, 3, 8, 0),
                "Introduction to calculus", "A305", "Tue 08:00-09:30");
        c2.addLesson(l3);

        ds.addLog("Test data initialized.");
        System.out.println("Test data created successfully.");
        System.out.println("  Admin         login: admin       / password: admin123");
        System.out.println("  Manager       login: manager     / password: mgr123");
        System.out.println("  Support       login: support     / password: sup123");
        System.out.println("  Teacher       login: jsmith      / password: pass123");
        System.out.println("  Professor     login: aivanova    / password: pass123");
        System.out.println("  Student       login: bakhmetov   / password: stu123");
        System.out.println("  Graduate      login: aresearcher / password: grad123");
        System.out.println("  PhD Student   login: daniyar     / password: phd123");
    }
}