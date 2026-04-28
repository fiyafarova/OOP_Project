package model.research;

import enums.Format;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

public class ResearchPaper implements Serializable, Comparable<ResearchPaper> {
    private final String id;
    private String title;
    private final List<ResearcherDecorator> authors;
    private String journal;
    private int pages;
    private LocalDate datePublished;
    private String doi;
    private int citations;

    public ResearchPaper(String title, String journal, int pages, LocalDate datePublished, String doi) {
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.journal = journal;
        this.pages = pages;
        this.datePublished = datePublished;
        this.doi = doi;
        this.citations = 0;
        this.authors = new ArrayList<>();
    }

    public String getCitation(Format format) {
        String authorsLine = authors.isEmpty()
                ? "Unknown Author"
                : authors.stream()
                    .map(a -> a.getWrapped().getFirstName() + " " + a.getWrapped().getLastName())
                    .collect(Collectors.joining(", "));

        if (format == Format.BIBTEX) {
            return "@article{" + doi + ",\n" +
                    "  title={" + title + "},\n" +
                    "  author={" + authorsLine + "},\n" +
                    "  journal={" + journal + "},\n" +
                    "  year={" + (datePublished == null ? "" : datePublished.getYear()) + "},\n" +
                    "  pages={" + pages + "},\n" +
                    "  doi={" + doi + "}\n" +
                    "}";
        }

        String year = datePublished == null ? "" : String.valueOf(datePublished.getYear());
        return authorsLine + ". \"" + title + "\". " + journal + ", " + year + ". DOI: " + doi;
    }

    public void addAuthor(ResearcherDecorator author) {
        if (author != null && !authors.contains(author)) {
            authors.add(author);
        }
    }

    public List<ResearcherDecorator> getAuthors() {
        return authors;
    }

    @Override
    public int compareTo(ResearchPaper other) {
        return Integer.compare(other.citations, this.citations);
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getJournal() {
        return journal;
    }

    public int getPages() {
        return pages;
    }

    public LocalDate getDatePublished() {
        return datePublished;
    }

    public String getDoi() {
        return doi;
    }

    public int getCitations() {
        return citations;
    }

    public void setCitations(int citations) {
        this.citations = citations;
    }

    @Override
    public String toString() {
        return "ResearchPaper{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", journal='" + journal + '\'' +
                ", pages=" + pages +
                ", datePublished=" + datePublished +
                ", doi='" + doi + '\'' +
                ", citations=" + citations +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ResearchPaper that)) return false;
        return Objects.equals(doi, that.doi);
    }

    @Override
    public int hashCode() {
        return Objects.hash(doi);
    }
}