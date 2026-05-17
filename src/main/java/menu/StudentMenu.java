package menu;

import enums.UrgencyLevel;
import exceptions.CourseFailLimitException;
import exceptions.MaxCreditsException;
import model.academic.Course;
import model.academic.StudentOrganization;
import model.communication.Request;
import model.employees.Teacher;
import model.users.Student;
import patterns.DataStorage;

import java.util.List;
import java.util.Scanner;

public class StudentMenu {
    private final Student student;
    private final Scanner scanner;

    public StudentMenu(Student student) {
        this.student = student;
        this.scanner = new Scanner(System.in);
    }

    public void show() {
        boolean running = true;
        while (running) {
            System.out.println("\n=== Student Menu ===");
            System.out.println("1.  View available courses");
            System.out.println("2.  Register for course");
            System.out.println("3.  Drop course");
            System.out.println("4.  View my courses");
            System.out.println("5.  View marks");
            System.out.println("6.  Get transcript");
            System.out.println("7.  Rate teacher");
            System.out.println("8.  View/join student organizations");
            System.out.println("9.  Send request to tech support");
            System.out.println("10. Logout");
            System.out.print("Choose: ");

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
                    case 10: student.logout(); running = false; break;
                    default: System.out.println("Invalid choice.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number.");
            }
        }
    }

    private void viewAvailableCourses() {
        List<Course> available = DataStorage.getInstance().getAvailableCoursesFor(student);
        if (available.isEmpty()) {
            System.out.println("No available courses.");
            return;
        }
        for (int i = 0; i < available.size(); i++) {
            System.out.println((i + 1) + ". " + available.get(i));
        }
    }

    private void registerForCourse() {
        List<Course> available = DataStorage.getInstance().getAvailableCoursesFor(student);
        if (available.isEmpty()) {
            System.out.println("No available courses.");
            return;
        }
        viewAvailableCourses();
        System.out.print("Select course number: ");
        try {
            int idx = Integer.parseInt(scanner.nextLine().trim()) - 1;
            if (idx < 0 || idx >= available.size()) {
                System.out.println("Invalid selection.");
                return;
            }
            student.registerCourse(available.get(idx));
            System.out.println("Registered successfully.");
        } catch (MaxCreditsException | CourseFailLimitException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input.");
        }
    }

    private void dropCourse() {
        List<Course> myCourses = student.getCourses();
        if (myCourses.isEmpty()) {
            System.out.println("You have no registered courses.");
            return;
        }
        for (int i = 0; i < myCourses.size(); i++) {
            System.out.println((i + 1) + ". " + myCourses.get(i));
        }
        System.out.print("Select course to drop: ");
        try {
            int idx = Integer.parseInt(scanner.nextLine().trim()) - 1;
            if (idx < 0 || idx >= myCourses.size()) {
                System.out.println("Invalid selection.");
                return;
            }
            student.dropCourse(myCourses.get(idx));
            System.out.println("Course dropped.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid input.");
        }
    }

    private void viewMyCourses() {
        List<Course> myCourses = student.getCourses();
        if (myCourses.isEmpty()) {
            System.out.println("No registered courses.");
            return;
        }
        myCourses.forEach(System.out::println);
    }

    private void rateTeacher() {
        List<Teacher> teachers = DataStorage.getInstance().getAllTeachers();
        if (teachers.isEmpty()) {
            System.out.println("No teachers available.");
            return;
        }
        for (int i = 0; i < teachers.size(); i++) {
            System.out.println((i + 1) + ". " + teachers.get(i));
        }
        System.out.print("Select teacher: ");
        try {
            int idx = Integer.parseInt(scanner.nextLine().trim()) - 1;
            if (idx < 0 || idx >= teachers.size()) {
                System.out.println("Invalid selection.");
                return;
            }
            System.out.print("Rating (1-10): ");
            int rating = Integer.parseInt(scanner.nextLine().trim());
            if (rating < 1 || rating > 10) {
                System.out.println("Rating must be between 1 and 10.");
                return;
            }
            student.rateTeacher(teachers.get(idx), rating);
            System.out.println("Rating submitted.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid input.");
        }
    }

    private void manageOrganizations() {
        List<StudentOrganization> all = DataStorage.getInstance().getAllOrganizations();
        System.out.println("\n-- All Organizations --");
        if (all.isEmpty()) {
            System.out.println("No organizations available.");
        } else {
            for (int i = 0; i < all.size(); i++) {
                System.out.println((i + 1) + ". " + all.get(i).getName()
                    + " (members: " + all.get(i).getMembers().size() + ")");
            }
        }
        System.out.println("\n-- My Organizations --");
        student.getOrganizations().forEach(o -> System.out.println("  " + o.getName()));

        System.out.println("\n1. Join organization  2. Leave organization  3. Back");
        System.out.print("Choose: ");
        try {
            int choice = Integer.parseInt(scanner.nextLine().trim());
            if (choice == 1 && !all.isEmpty()) {
                System.out.print("Select organization number: ");
                int idx = Integer.parseInt(scanner.nextLine().trim()) - 1;
                if (idx >= 0 && idx < all.size()) {
                    student.joinOrganization(all.get(idx));
                    System.out.println("Joined " + all.get(idx).getName() + ".");
                } else {
                    System.out.println("Invalid selection.");
                }
            } else if (choice == 2) {
                List<StudentOrganization> myOrgs = student.getOrganizations();
                if (myOrgs.isEmpty()) {
                    System.out.println("You are not in any organization.");
                    return;
                }
                for (int i = 0; i < myOrgs.size(); i++) {
                    System.out.println((i + 1) + ". " + myOrgs.get(i).getName());
                }
                System.out.print("Select organization to leave: ");
                int idx = Integer.parseInt(scanner.nextLine().trim()) - 1;
                if (idx >= 0 && idx < myOrgs.size()) {
                    student.leaveOrganization(myOrgs.get(idx));
                    System.out.println("Left organization.");
                } else {
                    System.out.println("Invalid selection.");
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input.");
        }
    }

    private void sendRequest() {
        System.out.print("Enter request description: ");
        String description = scanner.nextLine();
        System.out.print("Urgency level (LOW / MEDIUM / HIGH): ");
        try {
            UrgencyLevel urgency = UrgencyLevel.valueOf(scanner.nextLine().trim().toUpperCase());
            Request request = new Request(student, description, urgency);
            DataStorage.getInstance().addRequest(request);
            System.out.println("Request sent. ID: " + request.getId());
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid urgency level.");
        }
    }
}