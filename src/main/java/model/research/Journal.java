package model.research;

import model.users.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Journal implements Serializable {
    private final String id;
    private String name;
    private final List<ResearchPaper> papers;
    private final List<User> subscribers;

    public Journal(String name) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.papers = new ArrayList<>();
        this.subscribers = new ArrayList<>();
    }

    public void publishPaper(ResearchPaper paper) {
        if (paper != null && !papers.contains(paper)) {
            papers.add(paper);
            notifySubscribers(paper);
        }
    }

    public void subscribe(User user) {
        if (user != null && !subscribers.contains(user)) {
            subscribers.add(user);
        }
    }

    public void unsubscribe(User user) {
        subscribers.remove(user);
    }

    public void notifySubscribers(ResearchPaper paper) {
        for (User user : subscribers) {
            System.out.println(
                    "Notification for " + user.getFirstName() + " " + user.getLastName() +
                    ": new paper published -> " + paper.getTitle() +
                    " in journal " + name
            );
        }
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<ResearchPaper> getPapers() {
        return papers;
    }

    public List<User> getSubscribers() {
        return subscribers;
    }

    @Override
    public String toString() {
        return "Journal{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", papers=" + papers.size() +
                ", subscribers=" + subscribers.size() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Journal journal)) return false;
        return Objects.equals(id, journal.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}