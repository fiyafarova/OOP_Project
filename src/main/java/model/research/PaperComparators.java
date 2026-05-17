package model.research;

import java.util.Comparator;

public class PaperComparators {
    public static Comparator<ResearchPaper> byCitations() {
        return Comparator.comparingInt(ResearchPaper::getCitations).reversed();
    }

    public static Comparator<ResearchPaper> byDate() {
        return Comparator.comparing(ResearchPaper::getPublishedDate).reversed();
    }

    public static Comparator<ResearchPaper> byPages() {
        return Comparator.comparingInt(ResearchPaper::getPages).reversed();
    }
}