package menu;

import enums.CitationFormat;
import enums.Format;
import enums.Language;
import model.research.Journal;
import model.research.PaperComparators;
import model.research.ResearchPaper;
import model.research.ResearchProject;
import model.research.ResearcherDecorator;
import model.users.User;
import patterns.DataStorage;
import util.LanguageManager;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class ResearcherMenu {

    private static Language lang(User user) {
        return user.getLanguage();
    }

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
            LanguageManager.print(lang(user), "menu.researcher.title");
            System.out.println("1. " + LanguageManager.get(lang(user), "menu.researcher.1"));
            System.out.println("2. " + LanguageManager.get(lang(user), "menu.researcher.2"));
            System.out.println("3. " + LanguageManager.get(lang(user), "menu.researcher.3"));
            System.out.println("4. " + LanguageManager.get(lang(user), "menu.researcher.4"));
            System.out.println("5. " + LanguageManager.get(lang(user), "menu.researcher.5"));
            System.out.println("6. " + LanguageManager.get(lang(user), "menu.researcher.6"));
            System.out.println("7. " + LanguageManager.get(lang(user), "menu.researcher.7"));
            System.out.println("8. " + LanguageManager.get(lang(user), "menu.researcher.8"));
            System.out.println("9. " + LanguageManager.get(lang(user), "menu.researcher.9"));
            System.out.println("10. " + LanguageManager.get(lang(user), "menu.researcher.10"));
            LanguageManager.prompt(lang(user), "general.choose");

            String input = sc.nextLine().trim();

            switch (input) {
                case "1":
                    addResearchPaper(researcher, sc);
                    break;
                case "2":
                    viewMyPapers(researcher, sc, user);
                    break;
                case "3":
                    createProject(researcher, sc);
                    break;
                case "4":
                    addParticipantToProject(researcher, sc);
                    break;
                case "5":
                    viewMyProjects(researcher, user);
                    break;
                case "6":
                    subscribeToJournal(researcher, sc);
                    break;
                case "7":
                    publishPaperToJournal(researcher, sc);
                    break;
                case "8":
                    viewTopCitedResearcher(user);
                    break;
                case "9":
                    return;
                case "10":
                    printCitation(researcher, sc, user);
                    break;
                case "11":
                    return;
                default:
                    LanguageManager.print(lang(user), "general.invalid_choice");
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

    private static void viewMyPapers(ResearcherDecorator researcher, Scanner sc, User user) {
        System.out.println("1. Citations");
        System.out.println("2. Date");
        System.out.println("3. Pages");
        LanguageManager.prompt(lang(user), "general.choose");
        String choice = sc.nextLine().trim();

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
                LanguageManager.print(lang(user), "general.invalid_choice");
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

    private static void viewMyProjects(ResearcherDecorator researcher, User user) {
        if (researcher.getProjects().isEmpty()) {
            LanguageManager.print(lang(user), "general.no_data");
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

    private static void viewTopCitedResearcher(User user) {
        System.out.println("1. Top researcher (all university)");
        System.out.println("2. Top researchers by school");
        System.out.println("3. All papers sorted");
        String choice = new java.util.Scanner(System.in).nextLine().trim();

        switch (choice) {
            case "1":
                ResearcherDecorator top = DataStorage.getInstance().getTopCitedResearcher();
                if (top == null) { LanguageManager.print(lang(user), "general.no_data"); return; }
                System.out.println("Top cited researcher: "
                        + top.getFirstName() + " " + top.getLastName()
                        + " | H-index = " + top.calculateHIndex()
                        + " | Papers = " + top.getPapers().size());
                break;
            case "2":
                System.out.println("School (SITE/SB/SEDU/SHI/SBS/SAM/SMSCI): ");
                try {
                    enums.School school = enums.School.valueOf(
                            new java.util.Scanner(System.in).nextLine().trim().toUpperCase());
                    java.util.List<ResearcherDecorator> bySchool =
                            DataStorage.getInstance().getTopCitedBySchool(school);
                    if (bySchool.isEmpty()) { System.out.println("No researchers in this school."); return; }
                    bySchool.forEach(r -> System.out.println(
                            r.getFirstName() + " " + r.getLastName()
                                    + " | H-index = " + r.calculateHIndex()));
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid school.");
                }
                break;
            case "3":
                System.out.println("Sort by: 1=Citations  2=Date  3=Pages");
                String sort = new java.util.Scanner(System.in).nextLine().trim();
                java.util.Comparator<ResearchPaper> cmp;
                switch (sort) {
                    case "2": cmp = PaperComparators.byDate(); break;
                    case "3": cmp = PaperComparators.byPages(); break;
                    default:  cmp = PaperComparators.byCitations();
                }
                DataStorage.getInstance().printAllPapers(cmp);
                break;
            default:
                LanguageManager.print(lang(user), "general.invalid_choice");
        }
    }

    private static void printCitation(ResearcherDecorator researcher, Scanner sc, User user) {
        List<ResearchPaper> papers = researcher.getPapers();
        if (papers.isEmpty()) { System.out.println("No papers."); return; }

        for (int i = 0; i < papers.size(); i++) {
            System.out.println((i + 1) + ". " + papers.get(i).getTitle());
        }
        System.out.print("Choose paper #: ");
        int idx;
        try { idx = Integer.parseInt(sc.nextLine().trim()) - 1; }
        catch (NumberFormatException e) { return; }
        if (idx < 0 || idx >= papers.size()) return;

        System.out.println("Format: 1=APA  2=MLA  3=IEEE  4=CHICAGO  5=PLAIN_TEXT  6=BIBTEX");
        String fmt = sc.nextLine().trim();
        ResearchPaper paper = papers.get(idx);
        switch (fmt) {
            case "1": System.out.println(paper.getCitation(CitationFormat.APA)); break;
            case "2": System.out.println(paper.getCitation(CitationFormat.MLA)); break;
            case "3": System.out.println(paper.getCitation(CitationFormat.IEEE)); break;
            case "4": System.out.println(paper.getCitation(CitationFormat.CHICAGO)); break;
            case "5": System.out.println(paper.getCitation(Format.PLAIN_TEXT)); break;
            case "6": System.out.println(paper.getCitation(Format.BIBTEX)); break;
            default: LanguageManager.print(lang(user), "general.invalid_choice");
        }
    }
}