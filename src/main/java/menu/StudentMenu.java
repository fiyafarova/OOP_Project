package menu;

import enums.Language;
import enums.UrgencyLevel;
import exceptions.CourseFailLimitException;
import exceptions.MaxCreditsException;
import model.academic.Course;
import model.academic.StudentOrganization;
import model.communication.Request;
import model.users.employees.Teacher;
import model.users.students.Student;
import patterns.DataStorage;
import util.LanguageManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class StudentMenu {
    private final Student student;
    private final Scanner scanner;

    public StudentMenu(Student student) {
        this.student = student;
        this.scanner = new Scanner(System.in);
    }

    private Language lang() { return student.getLanguage(); }

    public void show() {
        boolean running = true;
        while (running) {
            LanguageManager.print(lang(), "menu.student.title");
            System.out.println("1.  " + LanguageManager.get(lang(), "menu.student.1"));
            System.out.println("2.  " + LanguageManager.get(lang(), "menu.student.2"));
            System.out.println("3.  " + LanguageManager.get(lang(), "menu.student.3"));
            System.out.println("4.  " + LanguageManager.get(lang(), "menu.student.4"));
            System.out.println("5.  " + LanguageManager.get(lang(), "menu.student.5"));
            System.out.println("6.  " + LanguageManager.get(lang(), "menu.student.6"));
            System.out.println("7.  " + LanguageManager.get(lang(), "menu.student.7"));
            System.out.println("8.  " + LanguageManager.get(lang(), "menu.student.8"));
            System.out.println("9.  " + LanguageManager.get(lang(), "menu.student.9"));
            System.out.println("10. " + LanguageManager.get(lang(), "menu.student.10"));
            System.out.println("11. " + LanguageManager.get(lang(), "menu.student.11"));
            LanguageManager.prompt(lang(), "general.choose");

            try {
                int choice = Integer.parseInt(scanner.nextLine().trim());
                switch (choice) {
                    case 1:  viewAvailableCourses(); break;
                    case 2:  registerForCourse(); break;
                    case 3:  dropCourse(); break;
                    case 4:  viewMyCourses(); break;
                    case 5:  student.viewMarks(); break;
                    case 6:  student.getTranscript().print(); break;
                    case 7:  rateTeacher(); break;
                    case 8:  manageOrganizations(); break;
                    case 9:  sendRequest(); break;
                    case 10: switchLanguage(); break;
                    case 11: student.logout(); running = false; break;
                    default: LanguageManager.print(lang(), "general.invalid_choice");
                }
            } catch (NumberFormatException e) {
                LanguageManager.print(lang(), "general.invalid_input");
            }
        }
    }

    private void viewAvailableCourses() {
        List<Course> available = DataStorage.getInstance().getAvailableCoursesFor(student);
        if (available.isEmpty()) { LanguageManager.print(lang(), "course.no_available"); return; }
        System.out.println("\n-- Available Courses --");
        for (int i = 0; i < available.size(); i++) {
            Course c = available.get(i);
            System.out.printf("  %d. %s%n", i + 1, c);
            if (c.getLectureTeacher() != null)
                System.out.println("       Lecture:  " + c.getLectureTeacher().getFullName()
                        + " [" + c.getLectureTeacher().getTeacherPosition() + "]"
                        + " Rating: " + String.format("%.1f", c.getLectureTeacher().getRating()));
            if (c.getPracticeTeacher() != null)
                System.out.println("       Practice: " + c.getPracticeTeacher().getFullName()
                        + " [" + c.getPracticeTeacher().getTeacherPosition() + "]"
                        + " Rating: " + String.format("%.1f", c.getPracticeTeacher().getRating()));
        }
    }

    private void registerForCourse() {
        List<Course> available = DataStorage.getInstance().getAvailableCoursesFor(student);
        if (available.isEmpty()) { LanguageManager.print(lang(), "course.no_available"); return; }
        viewAvailableCourses();
        LanguageManager.prompt(lang(), "general.choose");
        try {
            int idx = Integer.parseInt(scanner.nextLine().trim()) - 1;
            if (idx < 0 || idx >= available.size()) { LanguageManager.print(lang(), "general.invalid_choice"); return; }
            student.registerCourse(available.get(idx));
            LanguageManager.print(lang(), "course.registered");
        } catch (MaxCreditsException | CourseFailLimitException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (NumberFormatException e) {
            LanguageManager.print(lang(), "general.invalid_input");
        }
    }

    private void dropCourse() {
        List<Course> myCourses = student.getCourses();
        if (myCourses.isEmpty()) { LanguageManager.print(lang(), "course.no_registered"); return; }
        for (int i = 0; i < myCourses.size(); i++)
            System.out.println((i + 1) + ". " + myCourses.get(i));
        LanguageManager.prompt(lang(), "general.choose");
        try {
            int idx = Integer.parseInt(scanner.nextLine().trim()) - 1;
            if (idx < 0 || idx >= myCourses.size()) { LanguageManager.print(lang(), "general.invalid_choice"); return; }
            student.dropCourse(myCourses.get(idx));
            LanguageManager.print(lang(), "course.dropped");
        } catch (NumberFormatException e) {
            LanguageManager.print(lang(), "general.invalid_input");
        }
    }

    private void viewMyCourses() {
        List<Course> myCourses = student.getCourses();
        if (myCourses.isEmpty()) { LanguageManager.print(lang(), "course.no_registered"); return; }
        System.out.println("\n-- My Courses --");
        myCourses.forEach(c -> {
            System.out.println(c);
            if (c.getLectureTeacher() != null)
                System.out.println("   Lecture:  " + c.getLectureTeacher().getFullName()
                        + " | Rating: " + String.format("%.1f", c.getLectureTeacher().getRating()));
            if (c.getPracticeTeacher() != null)
                System.out.println("   Practice: " + c.getPracticeTeacher().getFullName()
                        + " | Rating: " + String.format("%.1f", c.getPracticeTeacher().getRating()));
        });
        System.out.println("Total credits: " + student.getTotalCredits() + " / 21");
    }


    private void rateTeacher() {
        List<Teacher> myTeachers = student.getCourses().stream()
                .flatMap(c -> {
                    List<Teacher> ts = new ArrayList<>();
                    if (c.getLectureTeacher()  != null) ts.add(c.getLectureTeacher());
                    if (c.getPracticeTeacher() != null) ts.add(c.getPracticeTeacher());
                    return ts.stream();
                })
                .distinct()
                .collect(Collectors.toList());

        if (myTeachers.isEmpty()) {
            System.out.println("You have no enrolled courses. Rating all university teachers.");
            myTeachers = DataStorage.getInstance().getAllTeachers();
        }
        if (myTeachers.isEmpty()) { LanguageManager.print(lang(), "general.no_data"); return; }

        System.out.println("\n-- Rate a Teacher (from your courses) --");
        for (int i = 0; i < myTeachers.size(); i++) {
            Teacher t = myTeachers.get(i);
            System.out.printf("  %d. %-25s Current rating: %.1f%n",
                    i + 1, t.getFullName(), t.getRating());
        }
        LanguageManager.prompt(lang(), "general.choose");
        try {
            int idx = Integer.parseInt(scanner.nextLine().trim()) - 1;
            if (idx < 0 || idx >= myTeachers.size()) { LanguageManager.print(lang(), "general.invalid_choice"); return; }
            System.out.print("Rating (1-10): ");
            int rating = Integer.parseInt(scanner.nextLine().trim());
            if (rating < 1 || rating > 10) { System.out.println("Rating must be 1-10."); return; }
            student.rateTeacher(myTeachers.get(idx), rating);
            System.out.println("Rating submitted. Thank you!");
        } catch (NumberFormatException e) {
            LanguageManager.print(lang(), "general.invalid_input");
        }
    }

    private void manageOrganizations() {
        List<StudentOrganization> all = DataStorage.getInstance().getAllOrganizations();
        System.out.println("\n-- All Organizations --");
        for (int i = 0; i < all.size(); i++)
            System.out.println((i + 1) + ". " + all.get(i).getName()
                    + " (" + all.get(i).getMembers().size() + " members)"
                    + (all.get(i).getHead() != null ? "  Head: " + all.get(i).getHead().getFullName() : ""));
        System.out.println("\n-- My Organizations --");
        if (student.getOrganizations().isEmpty()) {
            System.out.println("  (none)");
        } else {
            student.getOrganizations().forEach(o -> System.out.println("  " + o.getName()));
        }

        System.out.println("\n1. Join  2. Leave  3. Back");
        LanguageManager.prompt(lang(), "general.choose");
        try {
            int choice = Integer.parseInt(scanner.nextLine().trim());
            if (choice == 1 && !all.isEmpty()) {
                LanguageManager.prompt(lang(), "general.choose");
                int idx = Integer.parseInt(scanner.nextLine().trim()) - 1;
                if (idx >= 0 && idx < all.size()) {
                    student.joinOrganization(all.get(idx));
                    System.out.println("Joined: " + all.get(idx).getName());
                }
            } else if (choice == 2) {
                List<StudentOrganization> myOrgs = student.getOrganizations();
                if (myOrgs.isEmpty()) { LanguageManager.print(lang(), "general.no_data"); return; }
                for (int i = 0; i < myOrgs.size(); i++) System.out.println((i + 1) + ". " + myOrgs.get(i).getName());
                LanguageManager.prompt(lang(), "general.choose");
                int idx = Integer.parseInt(scanner.nextLine().trim()) - 1;
                if (idx >= 0 && idx < myOrgs.size()) {
                    student.leaveOrganization(myOrgs.get(idx));
                    System.out.println("Left: " + myOrgs.get(idx).getName());
                }
            }
        } catch (NumberFormatException e) {
            LanguageManager.print(lang(), "general.invalid_input");
        }
    }

    private void sendRequest() {
        LanguageManager.prompt(lang(), "request.description_prompt");
        String description = scanner.nextLine();
        LanguageManager.prompt(lang(), "request.urgency_prompt");
        try {
            UrgencyLevel urgency = UrgencyLevel.valueOf(scanner.nextLine().trim().toUpperCase());
            Request request = new Request(student, description, urgency);
            DataStorage.getInstance().addRequest(request);
            System.out.println(LanguageManager.get(lang(), "request.sent") + request.getId());
        } catch (IllegalArgumentException e) {
            LanguageManager.print(lang(), "general.invalid_input");
        }
    }

    private void switchLanguage() {
        LanguageManager.print(lang(), "lang.switch_prompt");
        try {
            Language newLang = Language.valueOf(scanner.nextLine().trim().toUpperCase());
            student.switchLanguage(newLang);
            LanguageManager.print(newLang, "lang.switched");
        } catch (IllegalArgumentException e) {
            LanguageManager.print(lang(), "general.invalid_input");
        }
    }
}