package model.research;

import java.util.Comparator;
import java.util.List;

public interface IResearchable {
    int calculateHIndex();
    void printPapers(Comparator<ResearchPaper> comparator);
    List<ResearchProject> getResearchProjects();
    List<ResearchPaper> getPapers();
}