package menu;

import model.research.Journal;
import model.research.PaperComparators;
import model.research.ResearchPaper;
import model.research.ResearchProject;
import model.research.ResearcherDecorator;
import model.users.User;
import patterns.DataStorage;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class ResearcherMenu {

    public static void open(User user, Scanner sc) {
        ResearcherDecorator researcher = DataStorage.getInstance()
                .getAllResearchers()
                .stream()
                .filter(r -> r.getWrapped().equals(user))
                .findFirst()
                .orElse(null);

        if (researcher == null) {
            System.out.println("You are not a researcher.");
            return;
        }

        while (true) {
            System.out.println("\n=== RESEARCHER MENU ===");
            System.out.println("1. Add research paper");
            System.out.println("2. View my papers");
            System.out.println("3. Create research project");
            System.out.println("4. Add participant to project");
            System.out.println("5. View my projects");
            System.out.println("6. Subscribe to journal");
            System.out.println("7. Publish paper to journal");
            System.out.println("8. View top cited researcher");
            System.out.println("9. Logout");

            String input = sc.nextLine();

            switch (input) {
                case "1":
                    addResearchPaper(researcher, sc);
                    break;
                case "2":
                    viewMyPapers(researcher, sc);
                    break;
                case "3":
                    createProject(researcher, sc);
                    break;
                case "4":
                    addParticipantToProject(researcher, sc);
                    break;
                case "5":
                    viewMyProjects(researcher);
                    break;
                case "6":
                    subscribeToJournal(researcher, sc);
                    break;
                case "7":
                    publishPaperToJournal(researcher, sc);
                    break;
                case "8":
                    viewTopCitedResearcher();
                    break;
                case "9":
                    return;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    private static void addResearchPaper(ResearcherDecorator researcher, Scanner sc) {
        System.out.print("Title: ");
        String title = sc.nextLine();

        System.out.print("Journal name: ");
        String journalName = sc.nextLine();

        System.out.print("Pages: ");
        int pages = Integer.parseInt(sc.nextLine());

        System.out.print("DOI: ");
        String doi = sc.nextLine();

        System.out.print("Citations: ");
        int citations = Integer.parseInt(sc.nextLine());

        ResearchPaper paper = new ResearchPaper(title, journalName, pages, LocalDate.now(), doi);
        paper.setCitations(citations);
        researcher.addPaper(paper);

        System.out.println("Paper added.");
    }

    private static void viewMyPapers(ResearcherDecorator researcher, Scanner sc) {
        System.out.println("Sort by:");
        System.out.println("1. Citations");
        System.out.println("2. Date");
        System.out.println("3. Pages");
        String choice = sc.nextLine();

        switch (choice) {
            case "1":
                researcher.printPapers(PaperComparators.byCitations());
                break;
            case "2":
                researcher.printPapers(PaperComparators.byDate());
                break;
            case "3":
                researcher.printPapers(PaperComparators.byPages());
                break;
            default:
                System.out.println("Invalid choice");
        }
    }

    private static void createProject(ResearcherDecorator researcher, Scanner sc) {
        System.out.print("Project topic: ");
        String topic = sc.nextLine();

        ResearchProject project = new ResearchProject(topic);
        try {
            project.addParticipant(researcher);
            researcher.addProject(project);
            System.out.println("Project created.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void addParticipantToProject(ResearcherDecorator researcher, Scanner sc) {
        List<ResearchProject> projects = researcher.getProjects();
        if (projects.isEmpty()) {
            System.out.println("No projects.");
            return;
        }

        for (int i = 0; i < projects.size(); i++) {
            System.out.println((i + 1) + ". " + projects.get(i));
        }

        System.out.print("Choose project number: ");
        int pIndex = Integer.parseInt(sc.nextLine()) - 1;

        System.out.print("Enter participant login: ");
        String login = sc.nextLine();

        ResearcherDecorator target = DataStorage.getInstance()
                .getAllResearchers()
                .stream()
                .filter(r -> r.getWrappedUser().getLogin().equals(login))
                .findFirst()
                .orElse(null);

        if (target == null) {
            System.out.println("Researcher not found.");
            return;
        }

        try {
            projects.get(pIndex).addParticipant(target);
            target.addProject(projects.get(pIndex));
            System.out.println("Participant added.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void viewMyProjects(ResearcherDecorator researcher) {
        if (researcher.getProjects().isEmpty()) {
            System.out.println("No projects.");
            return;
        }

        for (ResearchProject project : researcher.getProjects()) {
            System.out.println(project);
        }
    }

    private static void subscribeToJournal(ResearcherDecorator researcher, Scanner sc) {
        List<Journal> journals = DataStorage.getInstance().getAllJournals();
        if (journals.isEmpty()) {
            System.out.println("No journals found.");
            return;
        }

        for (int i = 0; i < journals.size(); i++) {
            System.out.println((i + 1) + ". " + journals.get(i).getName());
        }

        System.out.print("Choose journal number: ");
        int index = Integer.parseInt(sc.nextLine()) - 1;

        if (index >= 0 && index < journals.size()) {
            researcher.subscribeToJournal(journals.get(index));
            System.out.println("Subscribed.");
        }
    }

    private static void publishPaperToJournal(ResearcherDecorator researcher, Scanner sc) {
        List<ResearchPaper> papers = researcher.getPapers();
        List<Journal> journals = DataStorage.getInstance().getAllJournals();

        if (papers.isEmpty()) {
            System.out.println("No papers.");
            return;
        }

        if (journals.isEmpty()) {
            System.out.println("No journals.");
            return;
        }

        for (int i = 0; i < papers.size(); i++) {
            System.out.println((i + 1) + ". " + papers.get(i).getTitle());
        }
        System.out.print("Choose paper number: ");
        int paperIndex = Integer.parseInt(sc.nextLine()) - 1;

        for (int i = 0; i < journals.size(); i++) {
            System.out.println((i + 1) + ". " + journals.get(i).getName());
        }
        System.out.print("Choose journal number: ");
        int journalIndex = Integer.parseInt(sc.nextLine()) - 1;

        researcher.publishPaperToJournal(papers.get(paperIndex), journals.get(journalIndex));
        System.out.println("Paper published to journal.");
    }

    private static void viewTopCitedResearcher() {
        ResearcherDecorator top = DataStorage.getInstance().getTopCitedResearcher();
        if (top == null) {
            System.out.println("No researchers found.");
            return;
        }

        System.out.println("Top cited researcher: " +
                top.getFirstName() + " " + top.getLastName() +
                " | H-index = " + top.calculateHIndex());
    }
}