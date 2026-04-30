package menu;

import model.academic.Course;
import model.academic.Mark;
import model.employees.Employee;
import model.employees.Teacher;
import model.users.Student;
import patterns.DataStorage;

import java.util.List;
import java.util.Scanner;

public class TeacherMenu {
    private final Teacher teacher;
    private final Scanner scanner;

    public TeacherMenu(Teacher teacher) {
        this.teacher = teacher;
        this.scanner = new Scanner(System.in);
    }

    public void show() {
        boolean running = true;
        while (running) {
            System.out.println("\n=== Teacher Menu ===");
            System.out.println("1. View my courses");
            System.out.println("2. View students in course");
            System.out.println("3. Put mark");
            System.out.println("4. Send complaint to dean");
            System.out.println("5. Send message to employee");
            System.out.println("6. Logout");
            System.out.print("Choose: ");

            try {
                int choice = Integer.parseInt(scanner.nextLine().trim());
                switch (choice) {
                    case 1: viewMyCourses(); break;
                    case 2: viewStudentsInCourse(); break;
                    case 3: putMark(); break;
                    case 4: sendComplaint(); break;
                    case 5: sendMessage(); break;
                    case 6: teacher.logout(); running = false; break;
                    default: System.out.println("Invalid choice.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number.");
            }
        }
    }

    private void viewMyCourses() {
        List<Course> courses = teacher.getCourses();
        if (courses.isEmpty()) {
            System.out.println("No courses assigned.");
            return;
        }
        courses.forEach(System.out::println);
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
        System.out.print("Select course: ");
        try {
            int idx = Integer.parseInt(scanner.nextLine().trim()) - 1;
            if (idx < 0 || idx >= courses.size()) {
                System.out.println("Invalid selection.");
                return null;
            }
            return courses.get(idx);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input.");
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
        for (int i = 0; i < students.size(); i++) {
            System.out.println((i + 1) + ". " + students.get(i).getName()
                + " " + students.get(i).getSurname());
        }
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
            System.out.println((i + 1) + ". " + students.get(i).getName()
                + " " + students.get(i).getSurname());
        }
        System.out.print("Select student: ");
        try {
            int idx = Integer.parseInt(scanner.nextLine().trim()) - 1;
            if (idx < 0 || idx >= students.size()) {
                System.out.println("Invalid selection.");
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
            System.out.println("Invalid input.");
        }
    }

    private void sendComplaint() {
        System.out.print("Enter complaint: ");
        String complaint = scanner.nextLine();
        teacher.sendComplaint(complaint);
    }

    private void sendMessage() {
        List<Employee> employees = DataStorage.getInstance().getAllEmployees();
        if (employees.isEmpty()) {
            System.out.println("No employees available.");
            return;
        }
        for (int i = 0; i < employees.size(); i++) {
            System.out.println((i + 1) + ". " + employees.get(i).getName()
                + " " + employees.get(i).getSurname());
        }
        System.out.print("Select recipient: ");
        try {
            int idx = Integer.parseInt(scanner.nextLine().trim()) - 1;
            if (idx < 0 || idx >= employees.size()) {
                System.out.println("Invalid selection.");
                return;
            }
            System.out.print("Message: ");
            String message = scanner.nextLine();
            teacher.sendMessage(employees.get(idx), message);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input.");
        }
    }
}
