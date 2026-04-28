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
    protected User wrapped;
    protected List<ResearchPaper> papers;
    protected List<ResearchProject> projects;

    public ResearcherDecorator(User wrapped) {
        if (wrapped == null) {
            throw new IllegalArgumentException("Wrapped user cannot be null");
        }
        this.wrapped = wrapped;
        this.papers = new ArrayList<>();
        this.projects = new ArrayList<>();
    }

    @Override
    public int calculateHIndex() {
        List<Integer> citationCounts = papers.stream()
                .map(ResearchPaper::getCitations)
                .sorted(Comparator.reverseOrder())
                .toList();

        int h = 0;
        for (int i = 0; i < citationCounts.size(); i++) {
            if (citationCounts.get(i) >= i + 1) {
                h = i + 1;
            } else {
                break;
            }
        }
        return h;
    }

    @Override
    public void printPapers(Comparator<ResearchPaper> comparator) {
        List<ResearchPaper> copy = new ArrayList<>(papers);
        if (comparator != null) {
            copy.sort(comparator);
        } else {
            copy.sort(Comparator.naturalOrder());
        }

        for (ResearchPaper paper : copy) {
            System.out.println(paper);
        }
    }

    @Override
    public List<ResearchProject> getResearchProjects() {
        return projects;
    }

    @Override
    public List<ResearchPaper> getPapers() {
        return papers;
    }

    public void publishPaper(ResearchPaper paper, Journal journal) {
        if (paper == null || journal == null) {
            throw new IllegalArgumentException("Paper and journal must not be null");
        }

        if (!paper.getAuthors().contains(this)) {
            paper.addAuthor(this);
        }

        if (!papers.contains(paper)) {
            papers.add(paper);
        }

        journal.publishPaper(paper);

        News news = new News(
                "New research paper published",
                wrapped.getFirstName() + " " + wrapped.getLastName()
                        + " published paper: " + paper.getTitle(),
                NewsTopic.RESEARCH,
                wrapped
        );

        DataStorage.getInstance().addNews(news);
    }

    public void addProject(ResearchProject project) {
        if (project != null && !projects.contains(project)) {
            projects.add(project);
        }
    }

    public User getWrapped() {
        return wrapped;
    }

    @Override
    public String toString() {
        return "ResearcherDecorator{" +
                "wrapped=" + wrapped +
                ", papers=" + papers.size() +
                ", projects=" + projects.size() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ResearcherDecorator that)) return false;
        return Objects.equals(wrapped, that.wrapped);
    }

    @Override
    public int hashCode() {
        return Objects.hash(wrapped);
    }
}