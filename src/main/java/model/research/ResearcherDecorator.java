package model.research;

import enums.NewsTopic;
import model.communication.News;
import model.users.User;
import patterns.DataStorage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public abstract class ResearcherDecorator implements IResearchable, Serializable {
    protected User wrappedUser;
    protected List<ResearchPaper> papers;
    protected List<ResearchProject> projects;

    public ResearcherDecorator(User wrappedUser) {
        this.wrappedUser = wrappedUser;
        this.papers = new ArrayList<>();
        this.projects = new ArrayList<>();
    }

    @Override
    public void addPaper(ResearchPaper paper) {
        if (paper == null) return;

        if (!paper.getAuthors().contains(wrappedUser)) {
            paper.addAuthor(wrappedUser);
        }

        if (!papers.contains(paper)) {
            papers.add(paper);
        }
    }

    @Override
    public List<ResearchPaper> getPapers() {
        return papers;
    }

    @Override
    public int calculateHIndex() {
        List<ResearchPaper> sorted = new ArrayList<>(papers);
        sorted.sort(PaperComparators.byCitations());

        int h = 0;
        for (ResearchPaper paper : sorted) {
            if (paper.getCitations() >= h + 1) {
                h++;
            } else {
                break;
            }
        }
        return h;
    }

    @Override
    public void printPapers(Comparator<ResearchPaper> comparator) {
        papers.stream()
                .sorted(comparator)
                .forEach(System.out::println);
    }

    public void addProject(ResearchProject project) {
        if (project != null && !projects.contains(project)) {
            projects.add(project);
        }
    }

    public List<ResearchProject> getProjects() {
        return projects;
    }

    public void publishPaperToJournal(ResearchPaper paper, Journal journal) {
        if (paper == null || journal == null) return;

        paper.setJournal(journal.getName());
        addPaper(paper);
        journal.publishPaper(paper);

        News news = new News(
                "New research paper",
                wrappedUser.getFirstName() + " " + wrappedUser.getLastName() +
                        " published: " + paper.getTitle(),
                NewsTopic.RESEARCH,
                wrappedUser
        );
        DataStorage.getInstance().addNews(news);
    }

    public void subscribeToJournal(Journal journal) {
        if (journal != null) {
            journal.subscribe(wrappedUser);
        }
    }

    public User getWrappedUser() {
        return wrappedUser;
    }

    public User getWrapped() {
        return wrappedUser;
    }

    public String getId() {
        return wrappedUser.getId();
    }

    public String getFirstName() {
        return wrappedUser.getFirstName();
    }

    public String getLastName() {
        return wrappedUser.getLastName();
    }

    public String getLogin() {
        return wrappedUser.getLogin();
    }

    @Override
    public String toString() {
        return "ResearcherDecorator[" + wrappedUser + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ResearcherDecorator)) return false;
        ResearcherDecorator that = (ResearcherDecorator) o;
        return Objects.equals(wrappedUser, that.wrappedUser);
    }

    @Override
    public int hashCode() {
        return Objects.hash(wrappedUser);
    }
}