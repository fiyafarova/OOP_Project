package model.research;

import enums.CitationFormat;
import enums.Format;
import model.users.User;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ResearchPaper implements Serializable, Comparable<ResearchPaper> {
    private String title;
    private List<User> authors;
    private String journal;
    private int pages;
    private LocalDate publishedDate;
    private String doi;
    private int citations;

    public ResearchPaper(String title, String journal, int pages, LocalDate publishedDate, String doi) {
        this.title = title;
        this.journal = journal;
        this.pages = pages;
        this.publishedDate = publishedDate;
        this.doi = doi;
        this.citations = 0;
        this.authors = new ArrayList<>();
    }

    public ResearchPaper(String title, List<User> authors, String journal, int pages,
                         LocalDate publishedDate, String doi, int citations) {
        this.title = title;
        this.authors = authors != null ? authors : new ArrayList<User>();
        this.journal = journal;
        this.pages = pages;
        this.publishedDate = publishedDate;
        this.doi = doi;
        this.citations = citations;
    }

    public void addAuthor(User author) {
        if (author != null && !authors.contains(author)) {
            authors.add(author);
        }
    }

    public String getCitation(CitationFormat format) {
        String authorsStr = formatAuthors();
        String year = publishedDate != null ? String.valueOf(publishedDate.getYear()) : "n.d.";

        switch (format) {
            case APA:
                return authorsStr + ". (" + year + "). " + title + ". " + journal + ", " + pages + " pages.";
            case IEEE:
                return "[1] " + authorsStr + ", \"" + title + ",\" " + journal + ", " + pages + " pages, " + year + ".";
            case MLA:
                return authorsStr + ". \"" + title + ".\" " + journal + ", " + year + ", " + pages + " pages.";
            case CHICAGO:
                return authorsStr + ". " + year + ". \"" + title + ".\" " + journal + ".";
            default:
                return authorsStr + ". " + title;
        }
    }

    public String getCitation(Format format) {
        String authorsStr = formatAuthors();
        String year = publishedDate != null ? String.valueOf(publishedDate.getYear()) : "n.d.";

        switch (format) {
            case PLAIN_TEXT:
                return authorsStr + ". " + title + ". " + journal + ". " + year + ". DOI: " + doi;
            case BIBTEX:
                return "@article{" + buildBibKey() + ",\n" +
                        "  author={" + authorsStr + "},\n" +
                        "  title={" + title + "},\n" +
                        "  journal={" + journal + "},\n" +
                        "  year={" + year + "},\n" +
                        "  pages={" + pages + "},\n" +
                        "  doi={" + doi + "}\n" +
                        "}";
            default:
                return authorsStr + ". " + title;
        }
    }

    private String formatAuthors() {
        if (authors == null || authors.isEmpty()) {
            return "Unknown Author";
        }
        return authors.stream()
                .map(a -> a.getFirstName() + " " + a.getLastName())
                .collect(Collectors.joining(", "));
    }

    private String buildBibKey() {
        String year = publishedDate != null ? String.valueOf(publishedDate.getYear()) : "nodate";
        String first = (authors != null && !authors.isEmpty()) ? authors.get(0).getLastName() : "unknown";
        return first + year;
    }

    @Override
    public int compareTo(ResearchPaper other) {
        return Integer.compare(other.citations, this.citations);
    }

    public String getTitle() {
        return title;
    }

    public List<User> getAuthors() {
        return authors;
    }

    public String getJournal() {
        return journal;
    }

    public int getPages() {
        return pages;
    }

    public LocalDate getPublishedDate() {
        return publishedDate;
    }

    public String getDoi() {
        return doi;
    }

    public int getCitations() {
        return citations;
    }

    public void setJournal(String journal) {
        this.journal = journal;
    }

    public void setPublishedDate(LocalDate publishedDate) {
        this.publishedDate = publishedDate;
    }

    public void setCitations(int citations) {
        this.citations = citations;
    }

    @Override
    public String toString() {
        return "ResearchPaper{" +
                "title='" + title + '\'' +
                ", authors=" + formatAuthors() +
                ", journal='" + journal + '\'' +
                ", pages=" + pages +
                ", publishedDate=" + publishedDate +
                ", doi='" + doi + '\'' +
                ", citations=" + citations +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ResearchPaper)) return false;
        ResearchPaper that = (ResearchPaper) o;
        return Objects.equals(doi, that.doi);
    }

    @Override
    public int hashCode() {
        return Objects.hash(doi);
    }
}