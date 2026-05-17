package model.research;

import model.users.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Journal implements Serializable {
    private String name;
    private List<ResearchPaper> papers;
    private List<User> subscribers;

    public Journal(String name) {
        this.name = name;
        this.papers = new ArrayList<>();
        this.subscribers = new ArrayList<>();
    }

    public void subscribe(User user) {
        if (user != null && !subscribers.contains(user)) {
            subscribers.add(user);
        }
    }

    public void unsubscribe(User user) {
        subscribers.remove(user);
    }

    public void publishPaper(ResearchPaper paper) {
        if (paper != null && !papers.contains(paper)) {
            papers.add(paper);
            notifySubscribers(paper);
        }
    }

    public void notifySubscribers(ResearchPaper paper) {
        for (User u : subscribers) {
            System.out.println("Notification to " + u.getFirstName() +
                    ": New paper published: " + paper.getTitle());
        }
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
                "name='" + name + '\'' +
                ", papers=" + papers.size() +
                ", subscribers=" + subscribers.size() +
                '}';
    }
}