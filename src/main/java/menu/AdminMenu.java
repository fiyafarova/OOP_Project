package menu;

import enums.Language;
import enums.School;
import model.users.User;
import model.users.employees.Admin;
import patterns.DataStorage;
import patterns.UserFactory;
import util.LanguageManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class AdminMenu {
    private final Admin admin;
    private final Scanner scanner;

    public AdminMenu(Admin admin) {
        this.admin = admin;
        this.scanner = new Scanner(System.in);
    }

    private Language lang() { return admin.getLanguage(); }

    public void show() {
        boolean running = true;
        while (running) {
            LanguageManager.print(lang(), "menu.admin.title");
            System.out.println("1. " + LanguageManager.get(lang(), "menu.admin.1"));
            System.out.println("2. " + LanguageManager.get(lang(), "menu.admin.2"));
            System.out.println("3. " + LanguageManager.get(lang(), "menu.admin.3"));
            System.out.println("4. " + LanguageManager.get(lang(), "menu.admin.4"));
            System.out.println("5. " + LanguageManager.get(lang(), "menu.admin.5"));
            System.out.println("6. " + LanguageManager.get(lang(), "menu.admin.6"));
            LanguageManager.prompt(lang(), "general.choose");

            try {
                int choice = Integer.parseInt(scanner.nextLine().trim());
                switch (choice) {
                    case 1: addUser(); break;
                    case 2: removeUser(); break;
                    case 3: viewAllUsers(); break;
                    case 4: admin.viewLogs(); break;
                    case 5: switchLanguage(); break;
                    case 6: admin.logout(); running = false; break;
                    default: LanguageManager.print(lang(), "general.invalid_choice");
                }
            } catch (NumberFormatException e) {
                LanguageManager.print(lang(), "general.invalid_input");
            }
        }
    }

    private void addUser() {
        System.out.println("\nUser type: 1=Student  2=Teacher  3=Manager  4=TechSupport");
        LanguageManager.prompt(lang(), "general.choose");
        int type;
        try {
            type = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            LanguageManager.print(lang(), "general.invalid_input");
            return;
        }

        Map<String, String> data = new HashMap<>();
        System.out.print("First name: ");  data.put("firstName", scanner.nextLine().trim());
        System.out.print("Last name: ");   data.put("lastName",  scanner.nextLine().trim());
        System.out.print("Login: ");       data.put("login",     scanner.nextLine().trim());
        System.out.print("Password: ");    data.put("password",  scanner.nextLine().trim());

        School school = promptSchool();
        if (school == null && (type == 1 || type == 2 || type == 3)) {
            System.out.println("Invalid school.");
            return;
        }

        switch (type) {
            case 1:
                System.out.print("Year of study: ");
                data.put("year", scanner.nextLine().trim());
                UserFactory.createStudent(data, school);
                System.out.println("Student created.");
                break;
            case 2:
                System.out.println("Position (TUTOR/LECTOR/SENIOR_LECTOR/PROFESSOR): ");
                data.put("position", scanner.nextLine().trim().toUpperCase());
                UserFactory.createTeacher(data, school);
                System.out.println("Teacher created.");
                break;
            case 3:
                System.out.println("Manager type (OR/DEPARTMENT/DEAN_OFFICE): ");
                String mt = scanner.nextLine().trim().toUpperCase();
                UserFactory.createManager(data, school, enums.ManagerType.valueOf(mt));
                System.out.println("Manager created.");
                break;
            case 4:
                UserFactory.createTechSupport(data);
                System.out.println("TechSupport created.");
                break;
            default:
                System.out.println("Unknown type.");
        }
    }

    private School promptSchool() {
        System.out.println("School (SITE/SB/SEDU/SHI/SBS/SAM/SMSCI): ");
        try {
            return School.valueOf(scanner.nextLine().trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    private void removeUser() {
        List<User> users = admin.getAllUsers();
        if (users.isEmpty()) { System.out.println("No users."); return; }
        for (int i = 0; i < users.size(); i++)
            System.out.println((i + 1) + ". " + users.get(i).getLogin()
                    + " [" + users.get(i).getClass().getSimpleName() + "]");
        LanguageManager.prompt(lang(), "general.choose");
        try {
            int idx = Integer.parseInt(scanner.nextLine().trim()) - 1;
            if (idx < 0 || idx >= users.size()) { LanguageManager.print(lang(), "general.invalid_choice"); return; }
            admin.removeUser(users.get(idx).getId());
            System.out.println("User removed.");
        } catch (NumberFormatException e) {
            LanguageManager.print(lang(), "general.invalid_input");
        }
    }

    private void viewAllUsers() {
        List<User> users = admin.getAllUsers();
        if (users.isEmpty()) { System.out.println("No users registered."); return; }
        System.out.println("\n-- All Users (" + users.size() + ") --");
        users.forEach(u -> System.out.println(
                "  [" + u.getClass().getSimpleName() + "] "
                        + u.getFullName() + " | login: " + u.getLogin()));
    }

    private void switchLanguage() {
        LanguageManager.print(lang(), "lang.switch_prompt");
        try {
            Language newLang = Language.valueOf(scanner.nextLine().trim().toUpperCase());
            admin.switchLanguage(newLang);
            LanguageManager.print(newLang, "lang.switched");
        } catch (IllegalArgumentException e) {
            LanguageManager.print(lang(), "general.invalid_input");
        }
    }
}