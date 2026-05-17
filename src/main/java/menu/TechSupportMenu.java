package menu;

import enums.Language;
import model.communication.Request;
import model.users.employees.TechSupportSpecialist;
import util.LanguageManager;

import java.util.List;
import java.util.Scanner;

public class TechSupportMenu {
    private final TechSupportSpecialist specialist;
    private final Scanner scanner;

    public TechSupportMenu(TechSupportSpecialist specialist) {
        this.specialist = specialist;
        this.scanner = new Scanner(System.in);
    }

    private Language lang() { return specialist.getLanguage(); }

    public void show() {
        boolean running = true;
        while (running) {
            LanguageManager.print(lang(), "menu.tech.title");
            System.out.println("1. " + LanguageManager.get(lang(), "menu.tech.1"));
            System.out.println("2. " + LanguageManager.get(lang(), "menu.tech.2"));
            System.out.println("3. " + LanguageManager.get(lang(), "menu.tech.3"));
            System.out.println("4. " + LanguageManager.get(lang(), "menu.tech.4"));
            System.out.println("5. " + LanguageManager.get(lang(), "menu.tech.5"));
            System.out.println("6. " + LanguageManager.get(lang(), "menu.tech.6"));
            System.out.println("7. " + LanguageManager.get(lang(), "menu.tech.7"));
            LanguageManager.prompt(lang(), "general.choose");

            try {
                int choice = Integer.parseInt(scanner.nextLine().trim());
                switch (choice) {
                    case 1: specialist.viewNewRequests(); break;
                    case 2: handleRequest("accept"); break;
                    case 3: handleRequest("reject"); break;
                    case 4: handleRequest("done"); break;
                    case 5: viewMyRequests(); break;
                    case 6: switchLanguage(); break;
                    case 7: specialist.logout(); running = false; break;
                    default: LanguageManager.print(lang(), "general.invalid_choice");
                }
            } catch (NumberFormatException e) {
                LanguageManager.print(lang(), "general.invalid_input");
            }
        }
    }

    private void handleRequest(String action) {
        List<Request> requests = specialist.viewRequests();
        if (requests.isEmpty()) {
            System.out.println("No assigned requests. View new requests first.");
            return;
        }
        for (int i = 0; i < requests.size(); i++)
            System.out.println((i + 1) + ". " + requests.get(i));
        LanguageManager.prompt(lang(), "general.choose");
        try {
            int idx = Integer.parseInt(scanner.nextLine().trim()) - 1;
            if (idx < 0 || idx >= requests.size()) {
                LanguageManager.print(lang(), "general.invalid_choice"); return;
            }
            Request request = requests.get(idx);
            switch (action) {
                case "accept": specialist.acceptRequest(request); break;
                case "reject": specialist.rejectRequest(request); break;
                case "done":   specialist.markDone(request); break;
            }
        } catch (NumberFormatException e) {
            LanguageManager.print(lang(), "general.invalid_input");
        }
    }

    private void viewMyRequests() {
        List<Request> requests = specialist.viewRequests();
        if (requests.isEmpty()) { System.out.println("No assigned requests."); return; }
        requests.forEach(System.out::println);
    }

    private void switchLanguage() {
        LanguageManager.print(lang(), "lang.switch_prompt");
        try {
            Language newLang = Language.valueOf(scanner.nextLine().trim().toUpperCase());
            specialist.switchLanguage(newLang);
            LanguageManager.print(newLang, "lang.switched");
        } catch (IllegalArgumentException e) {
            LanguageManager.print(lang(), "general.invalid_input");
        }
    }
}