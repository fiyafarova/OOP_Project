package menu;

import enums.CourseType;
import enums.Language;
import enums.NewsTopic;
import enums.School;
import model.academic.Course;
import model.academic.Report;
import model.communication.News;
import model.communication.Request;
import model.research.ResearcherDecorator;
import model.users.employees.Manager;
import model.users.employees.Teacher;
import model.users.students.Student;
import patterns.DataStorage;
import util.LanguageManager;

import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class ManagerMenu {
    private final Manager manager;
    private final Scanner scanner;

    public ManagerMenu(Manager manager) {
        this.manager = manager;
        this.scanner = new Scanner(System.in);
    }

    private Language lang() { return manager.getLanguage(); }

    public void show() {
        boolean running = true;
        while (running) {
            LanguageManager.print(lang(), "menu.manager.title");
            System.out.println("1.  " + LanguageManager.get(lang(), "menu.manager.1"));
            System.out.println("2.  " + LanguageManager.get(lang(), "menu.manager.2"));
            System.out.println("3.  " + LanguageManager.get(lang(), "menu.manager.3"));
            System.out.println("4.  " + LanguageManager.get(lang(), "menu.manager.4"));
            System.out.println("5.  " + LanguageManager.get(lang(), "menu.manager.5"));
            System.out.println("6.  " + LanguageManager.get(lang(), "menu.manager.6"));
            System.out.println("7.  " + LanguageManager.get(lang(), "menu.manager.7"));
            System.out.println("8.  " + LanguageManager.get(lang(), "menu.manager.8"));
            System.out.println("9.  " + LanguageManager.get(lang(), "menu.manager.9"));
            System.out.println("10. " + LanguageManager.get(lang(), "menu.manager.10"));
            LanguageManager.prompt(lang(), "general.choose");

            try {
                int choice = Integer.parseInt(scanner.nextLine().trim());
                switch (choice) {
                    case 1:  addCourse(); break;
                    case 2:  assignTeacher(); break;
                    case 3:  approveRegistration(); break;
                    case 4:  viewStudents(); break;
                    case 5:  viewTeachers(); break;
                    case 6:  createReport(); break;
                    case 7:  manageNews(); break;
                    case 8:  manager.viewRequests(); break;
                    case 9:  switchLanguage(); break;
                    case 10: manager.logout(); running = false; break;
                    default: LanguageManager.print(lang(), "general.invalid_choice");
                }
            } catch (NumberFormatException e) {
                LanguageManager.print(lang(), "general.invalid_input");
            }
        }
    }

    private void addCourse() {
        System.out.print("Course code: "); String code = scanner.nextLine().trim();
        System.out.print("Course name: "); String name = scanner.nextLine().trim();
        System.out.print("Credits: ");
        int credits;
        try { credits = Integer.parseInt(scanner.nextLine().trim()); }
        catch (NumberFormatException e) { System.out.println("Invalid credits."); return; }
        System.out.println("School (SITE/SB/SEDU/SHI/SBS/SAM/SMSCI): ");
        School school;
        try { school = School.valueOf(scanner.nextLine().trim().toUpperCase()); }
        catch (IllegalArgumentException e) { System.out.println("Invalid school."); return; }
        System.out.println("Type (MAJOR/MINOR/FREE_ELECTIVE): ");
        CourseType type;
        try { type = CourseType.valueOf(scanner.nextLine().trim().toUpperCase()); }
        catch (IllegalArgumentException e) { System.out.println("Invalid type."); return; }
        Course course = new Course(code, name, credits, school, type);
        manager.addCourseForRegistration(course);
        System.out.println("Course added: " + course);
    }

    private void assignTeacher() {
        List<Teacher> teachers = DataStorage.getInstance().getAllTeachers();
        List<Course> courses = DataStorage.getInstance().getAllCourses();
        if (teachers.isEmpty()) { System.out.println("No teachers available."); return; }
        if (courses.isEmpty())  { System.out.println("No courses available."); return; }

        System.out.println("-- Teachers --");
        for (int i = 0; i < teachers.size(); i++) System.out.println((i + 1) + ". " + teachers.get(i));
        System.out.println("-- Courses --");
        for (int i = 0; i < courses.size(); i++) System.out.println((i + 1) + ". " + courses.get(i));
        try {
            System.out.print("Select teacher #: ");
            int ti = Integer.parseInt(scanner.nextLine().trim()) - 1;
            System.out.print("Select course #: ");
            int ci = Integer.parseInt(scanner.nextLine().trim()) - 1;
            if (ti < 0 || ti >= teachers.size() || ci < 0 || ci >= courses.size()) {
                LanguageManager.print(lang(), "general.invalid_choice"); return;
            }
            manager.assignTeacher(teachers.get(ti), courses.get(ci));
        } catch (NumberFormatException e) {
            LanguageManager.print(lang(), "general.invalid_input");
        }
    }

    private void approveRegistration() {
        List<Student> students = DataStorage.getInstance().getAllStudents();
        List<Course> courses   = DataStorage.getInstance().getAllCourses();
        if (students.isEmpty()) { System.out.println("No students available."); return; }
        if (courses.isEmpty())  { System.out.println("No courses available."); return; }
        System.out.println("-- Students --");
        for (int i = 0; i < students.size(); i++) System.out.println((i + 1) + ". " + students.get(i));
        System.out.println("-- Courses --");
        for (int i = 0; i < courses.size(); i++) System.out.println((i + 1) + ". " + courses.get(i));
        try {
            System.out.print("Select student #: ");
            int si = Integer.parseInt(scanner.nextLine().trim()) - 1;
            System.out.print("Select course #: ");
            int ci = Integer.parseInt(scanner.nextLine().trim()) - 1;
            if (si < 0 || si >= students.size() || ci < 0 || ci >= courses.size()) {
                LanguageManager.print(lang(), "general.invalid_choice"); return;
            }
            manager.approveCourseRegistration(students.get(si), courses.get(ci));
        } catch (NumberFormatException e) {
            LanguageManager.print(lang(), "general.invalid_input");
        }
    }

    private void viewStudents() {
        System.out.println("Sort by: 1=GPA (desc)  2=Alphabetically");
        LanguageManager.prompt(lang(), "general.choose");
        try {
            int choice = Integer.parseInt(scanner.nextLine().trim());
            Comparator<Student> cmp = (choice == 1)
                    ? Comparator.comparingDouble(Student::getGpa).reversed()
                    : Comparator.comparing(Student::getFullName);
            manager.viewStudents(cmp);
        } catch (NumberFormatException e) {
            LanguageManager.print(lang(), "general.invalid_input");
        }
    }

    private void viewTeachers() {
        System.out.println("Sort by: 1=Rating (desc)  2=Alphabetically");
        LanguageManager.prompt(lang(), "general.choose");
        try {
            int choice = Integer.parseInt(scanner.nextLine().trim());
            Comparator<Teacher> cmp = (choice == 1)
                    ? Comparator.comparingDouble(Teacher::getRating).reversed()
                    : Comparator.comparing(Teacher::getFullName);
            manager.viewTeachers(cmp);
        } catch (NumberFormatException e) {
            LanguageManager.print(lang(), "general.invalid_input");
        }
    }

    private void createReport() {
        Report report = manager.createReport();
        report.print();

        generateTopResearcherNews();
    }

    private void generateTopResearcherNews() {
        ResearcherDecorator top = DataStorage.getInstance().getTopCitedResearcher();
        if (top == null) return;
        String title = "Top Cited Researcher: " + top.getWrapped().getFullName();
        String content = top.getWrapped().getFullName()
                + " is the top cited researcher in the university with h-index = "
                + top.calculateHIndex() + " and " + top.getPapers().size() + " published papers.";
        News news = new News(title, content, NewsTopic.RESEARCH, manager);
        DataStorage.getInstance().addNews(news);
        System.out.println("[News] Top cited researcher announcement added: " + title);
    }

    private void manageNews() {
        boolean running = true;
        while (running) {
            System.out.println("\n-- Manage News --");
            System.out.println("1. View all news");
            System.out.println("2. Add news");
            System.out.println("3. Remove news");
            System.out.println("4. Back");
            LanguageManager.prompt(lang(), "general.choose");
            try {
                int choice = Integer.parseInt(scanner.nextLine().trim());
                switch (choice) {
                    case 1:
                        manager.manageNews();
                        break;
                    case 2:
                        System.out.print("Title: ");   String title = scanner.nextLine().trim();
                        System.out.print("Content: "); String content = scanner.nextLine().trim();
                        System.out.println("Topic (RESEARCH/GENERAL/ANNOUNCEMENT): ");
                        try {
                            NewsTopic topic = NewsTopic.valueOf(scanner.nextLine().trim().toUpperCase());
                            News news = new News(title, content, topic, manager);
                            manager.addNews(news);
                            System.out.println("News added: " + news.getId());
                        } catch (IllegalArgumentException e) {
                            System.out.println("Invalid topic.");
                        }
                        break;
                    case 3:
                        List<News> allNews = DataStorage.getInstance().getAllNews();
                        if (allNews.isEmpty()) { System.out.println("No news."); break; }
                        for (int i = 0; i < allNews.size(); i++)
                            System.out.println((i + 1) + ". " + allNews.get(i).getTitle());
                        System.out.print("Select news #: ");
                        int idx = Integer.parseInt(scanner.nextLine().trim()) - 1;
                        if (idx >= 0 && idx < allNews.size()) {
                            manager.removeNews(allNews.get(idx).getId());
                            System.out.println("News removed.");
                        } else {
                            LanguageManager.print(lang(), "general.invalid_choice");
                        }
                        break;
                    case 4:
                        running = false;
                        break;
                    default:
                        LanguageManager.print(lang(), "general.invalid_choice");
                }
            } catch (NumberFormatException e) {
                LanguageManager.print(lang(), "general.invalid_input");
            }
        }
    }

    private void switchLanguage() {
        LanguageManager.print(lang(), "lang.switch_prompt");
        try {
            Language newLang = Language.valueOf(scanner.nextLine().trim().toUpperCase());
            manager.switchLanguage(newLang);
            LanguageManager.print(newLang, "lang.switched");
        } catch (IllegalArgumentException e) {
            LanguageManager.print(lang(), "general.invalid_input");
        }
    }
}