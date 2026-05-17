package model.research;

import exceptions.NotResearcherException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ResearchProject implements Serializable {
    private String topic;
    private List<ResearcherDecorator> participants;
    private List<ResearchPaper> publishedPapers;

    public ResearchProject(String topic) {
        this.topic = topic;
        this.participants = new ArrayList<>();
        this.publishedPapers = new ArrayList<>();
    }

    public void addParticipant(Object user) throws NotResearcherException {
        if (!(user instanceof ResearcherDecorator)) {
            throw new NotResearcherException("Only ResearcherDecorator can join research project");
        }
        ResearcherDecorator researcher = (ResearcherDecorator) user;
        if (!participants.contains(researcher)) {
            participants.add(researcher);
        }
    }

    public void addPaper(ResearchPaper paper) {
        if (paper != null && !publishedPapers.contains(paper)) {
            publishedPapers.add(paper);
        }
    }

    public String getTopic() {
        return topic;
    }

    public List<ResearcherDecorator> getParticipants() {
        return participants;
    }

    public List<ResearchPaper> getPublishedPapers() {
        return publishedPapers;
    }

    @Override
    public String toString() {
        return "ResearchProject{" +
                "topic='" + topic + '\'' +
                ", participants=" + participants.size() +
                ", publishedPapers=" + publishedPapers.size() +
                '}';
    }
}