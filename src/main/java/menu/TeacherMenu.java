package menu;

import enums.Language;
import enums.LessonType;
import enums.UrgencyLevel;
import model.academic.Course;
import model.academic.Lesson;
import model.academic.Mark;
import model.users.User;
import model.users.employees.Employee;
import model.users.employees.Manager;
import model.users.employees.Teacher;
import model.users.students.Student;
import patterns.DataStorage;
import util.LanguageManager;

import java.util.List;
import java.util.Scanner;

public class TeacherMenu {
    private final Teacher teacher;
    private final Scanner scanner;

    public TeacherMenu(Teacher teacher) {
        this.teacher = teacher;
        this.scanner = new Scanner(System.in);
    }

    private Language lang() { return teacher.getLanguage(); }

    public void show() {
        boolean running = true;
        while (running) {
            LanguageManager.print(lang(), "menu.teacher.title");
            System.out.println("1. " + LanguageManager.get(lang(), "menu.teacher.1"));
            System.out.println("2. " + LanguageManager.get(lang(), "menu.teacher.2"));
            System.out.println("3. " + LanguageManager.get(lang(), "menu.teacher.3"));
            System.out.println("4. " + LanguageManager.get(lang(), "menu.teacher.4"));
            System.out.println("5. " + LanguageManager.get(lang(), "menu.teacher.5"));
            System.out.println("6. " + LanguageManager.get(lang(), "menu.teacher.6"));
            System.out.println("7. " + LanguageManager.get(lang(), "menu.teacher.7"));
            System.out.println("8. " + LanguageManager.get(lang(), "menu.teacher.8"));
            System.out.println("9. " + LanguageManager.get(lang(), "menu.teacher.9"));
            LanguageManager.prompt(lang(), "general.choose");

            try {
                int choice = Integer.parseInt(scanner.nextLine().trim());
                switch (choice) {
                    case 1: teacher.viewCourses(); break;
                    case 2: viewStudentsInCourse(); break;
                    case 3: putMark(); break;
                    case 4: sendComplaint(); break;
                    case 5: sendMessage(); break;
                    case 6: addLesson(); break;
                    case 7: viewLessonsInCourse(); break;
                    case 8: switchLanguage(); break;
                    case 9: teacher.logout(); running = false; break;
                    default: LanguageManager.print(lang(), "general.invalid_choice");
                }
            } catch (NumberFormatException e) {
                LanguageManager.print(lang(), "general.invalid_input");
            }
        }
    }

    private Course selectCourse() {
        List<Course> courses = teacher.getCourses();
        if (courses.isEmpty()) {
            System.out.println("No courses assigned.");
            return null;
        }
        for (int i = 0; i < courses.size(); i++) {
            System.out.println((i + 1) + ". " + courses.get(i));
        }
        LanguageManager.prompt(lang(), "general.choose");
        try {
            int idx = Integer.parseInt(scanner.nextLine().trim()) - 1;
            if (idx < 0 || idx >= courses.size()) {
                LanguageManager.print(lang(), "general.invalid_choice");
                return null;
            }
            return courses.get(idx);
        } catch (NumberFormatException e) {
            LanguageManager.print(lang(), "general.invalid_input");
            return null;
        }
    }

    private void viewStudentsInCourse() {
        Course course = selectCourse();
        if (course == null) return;
        List<Student> students = course.getEnrolledStudents();
        if (students.isEmpty()) {
            System.out.println("No students enrolled.");
            return;
        }
        System.out.println("\n-- Students in " + course.getName() + " --");
        for (int i = 0; i < students.size(); i++) {
            Student s = students.get(i);
            System.out.printf("  %d. %-25s GPA: %.2f  School: %s%n",
                    i + 1, s.getFullName(), s.getGpa(), s.getSchool());
        }
        System.out.println("\nCourse info:");
        if (course.getLectureTeacher() != null)
            System.out.println("  Lecture teacher : " + course.getLectureTeacher().getFullName()
                    + " [" + course.getLectureTeacher().getTeacherPosition() + "]");
        if (course.getPracticeTeacher() != null)
            System.out.println("  Practice teacher: " + course.getPracticeTeacher().getFullName()
                    + " [" + course.getPracticeTeacher().getTeacherPosition() + "]");
    }

    private void putMark() {
        Course course = selectCourse();
        if (course == null) return;
        List<Student> students = course.getEnrolledStudents();
        if (students.isEmpty()) {
            System.out.println("No students enrolled.");
            return;
        }
        for (int i = 0; i < students.size(); i++) {
            System.out.println((i + 1) + ". " + students.get(i).getFullName());
        }
        LanguageManager.prompt(lang(), "general.choose");
        try {
            int idx = Integer.parseInt(scanner.nextLine().trim()) - 1;
            if (idx < 0 || idx >= students.size()) {
                LanguageManager.print(lang(), "general.invalid_choice");
                return;
            }
            Student student = students.get(idx);

            System.out.print("Attestation 1 (0-30): ");
            double att1 = Double.parseDouble(scanner.nextLine().trim());
            System.out.print("Attestation 2 (0-30): ");
            double att2 = Double.parseDouble(scanner.nextLine().trim());
            System.out.print("Final exam (0-40): ");
            double finalExam = Double.parseDouble(scanner.nextLine().trim());

            Mark mark = new Mark(att1, att2, finalExam);
            teacher.putMark(student, course, mark);
            System.out.println("Mark recorded: " + mark.getLetterGrade()
                    + " (" + mark.getTotalScore() + ")");
        } catch (NumberFormatException e) {
            LanguageManager.print(lang(), "general.invalid_input");
        }
    }

    private void sendComplaint() {
        List<Course> courses = teacher.getCourses();
        if (courses.isEmpty()) {
            System.out.println("No courses — no students to complain about.");
            return;
        }

        java.util.Set<Student> studentSet = new java.util.LinkedHashSet<>();
        for (Course c : courses) studentSet.addAll(c.getEnrolledStudents());
        java.util.List<Student> students = new java.util.ArrayList<>(studentSet);

        if (students.isEmpty()) {
            System.out.println("No students enrolled in your courses.");
            return;
        }

        System.out.println("\n-- Select student to complain about --");
        for (int i = 0; i < students.size(); i++) {
            System.out.println((i + 1) + ". " + students.get(i).getFullName());
        }
        LanguageManager.prompt(lang(), "general.choose");
        int si;
        try {
            si = Integer.parseInt(scanner.nextLine().trim()) - 1;
        } catch (NumberFormatException e) {
            LanguageManager.print(lang(), "general.invalid_input");
            return;
        }
        if (si < 0 || si >= students.size()) {
            LanguageManager.print(lang(), "general.invalid_choice");
            return;
        }
        Student student = students.get(si);

        List<User> allUsers = DataStorage.getInstance().getAllUsers();
        java.util.List<Manager> managers = new java.util.ArrayList<>();
        for (User u : allUsers) {
            if (u instanceof Manager) managers.add((Manager) u);
        }

        Manager dean = null;
        if (!managers.isEmpty()) {
            System.out.println("\n-- Select dean/manager to send complaint to --");
            for (int i = 0; i < managers.size(); i++) {
                System.out.println((i + 1) + ". " + managers.get(i).getFullName()
                        + " [" + managers.get(i).getManagerType() + "]");
            }
            LanguageManager.prompt(lang(), "general.choose");
            try {
                int mi = Integer.parseInt(scanner.nextLine().trim()) - 1;
                if (mi >= 0 && mi < managers.size()) {
                    dean = managers.get(mi);
                }
            } catch (NumberFormatException _) {}
        }

        System.out.println("Urgency (LOW / MEDIUM / HIGH): ");
        UrgencyLevel urgency;
        try {
            urgency = UrgencyLevel.valueOf(scanner.nextLine().trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid urgency. Using MEDIUM.");
            urgency = UrgencyLevel.MEDIUM;
        }

        System.out.print("Complaint description: ");
        String desc = scanner.nextLine();

        teacher.sendComplaint(student, dean, urgency, desc);
    }

    private void sendMessage() {
        List<User> employees = DataStorage.getInstance().getAllEmployees();
        if (employees.isEmpty()) {
            System.out.println("No employees available.");
            return;
        }
        for (int i = 0; i < employees.size(); i++) {
            System.out.println((i + 1) + ". " + employees.get(i).getFullName()
                    + " [" + employees.get(i).getClass().getSimpleName() + "]");
        }
        LanguageManager.prompt(lang(), "general.choose");
        try {
            int idx = Integer.parseInt(scanner.nextLine().trim()) - 1;
            if (idx < 0 || idx >= employees.size()) {
                LanguageManager.print(lang(), "general.invalid_choice");
                return;
            }
            User recipient = employees.get(idx);
            if (!(recipient instanceof Employee)) {
                System.out.println("Recipient must be an Employee.");
                return;
            }
            System.out.print("Message: ");
            String message = scanner.nextLine();
            teacher.sendMessage((Employee) recipient, message);
        } catch (NumberFormatException e) {
            LanguageManager.print(lang(), "general.invalid_input");
        }
    }


    private void switchLanguage() {
        LanguageManager.print(lang(), "lang.switch_prompt");
        try {
            enums.Language newLang = enums.Language.valueOf(scanner.nextLine().trim().toUpperCase());
            teacher.switchLanguage(newLang);
            LanguageManager.print(newLang, "lang.switched");
        } catch (IllegalArgumentException e) {
            LanguageManager.print(lang(), "general.invalid_input");
        }
    }

    private void addLesson() {
        Course course = selectCourse();
        if (course == null) return;

        System.out.print("Lesson title: ");
        String title = scanner.nextLine().trim();

        System.out.println("Lesson type (LECTURE / PRACTICE): ");
        LessonType type;
        try {
            type = LessonType.valueOf(scanner.nextLine().trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid type.");
            return;
        }

        System.out.print("Description: ");
        String desc = scanner.nextLine().trim();

        System.out.print("Room (e.g. A301): ");
        String room = scanner.nextLine().trim();

        System.out.print("Schedule (e.g. Mon 09:00): ");
        String schedule = scanner.nextLine().trim();

        Lesson lesson = new Lesson(title, type, course,
                java.time.LocalDateTime.now(), desc, room, schedule);
        teacher.addLesson(course, lesson);
    }

    private void viewLessonsInCourse() {
        Course course = selectCourse();
        if (course == null) return;
        teacher.viewLessons(course);
    }
}