package model.research;

import exceptions.NotResearcherException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class ResearchProject implements Serializable {
    private final String id;
    private String topic;
    private final List<ResearchPaper> publishedPapers;
    private final List<ResearcherDecorator> participants;

    public ResearchProject(String topic) {
        this.id = UUID.randomUUID().toString();
        this.topic = topic;
        this.publishedPapers = new ArrayList<>();
        this.participants = new ArrayList<>();
    }

    public void addParticipant(Object person) throws NotResearcherException {
        if (!(person instanceof ResearcherDecorator researcher)) {
            throw new NotResearcherException("Only ResearcherDecorator can join a research project");
        }

        if (!participants.contains(researcher)) {
            participants.add(researcher);
        }
    }

    public void removeParticipant(ResearcherDecorator researcher) {
        participants.remove(researcher);
    }

    public List<ResearcherDecorator> getParticipants() {
        return participants;
    }

    public void publishPaper(ResearchPaper paper) {
        if (paper != null && !publishedPapers.contains(paper)) {
            publishedPapers.add(paper);
        }
    }

    public List<ResearchPaper> getPublishedPapers() {
        return publishedPapers;
    }

    public String getId() {
        return id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    @Override
    public String toString() {
        return "ResearchProject{" +
                "id='" + id + '\'' +
                ", topic='" + topic + '\'' +
                ", publishedPapers=" + publishedPapers.size() +
                ", participants=" + participants.size() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ResearchProject that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}