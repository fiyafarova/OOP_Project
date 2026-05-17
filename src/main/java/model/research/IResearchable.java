package model.research;

import java.util.Comparator;
import java.util.List;

public interface IResearchable {
    void addPaper(ResearchPaper paper);
    List<ResearchPaper> getPapers();
    int calculateHIndex();
    void printPapers(Comparator<ResearchPaper> comparator);
}