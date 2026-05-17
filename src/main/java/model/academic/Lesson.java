package model.academic;

import enums.LessonType;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Lesson implements Serializable {
    private static final long serialVersionUID = 1L;

    private String title;
    private LessonType lessonType;
    private Course course;
    private LocalDateTime date;
    private String description;
    private String room;
    private String schedule;

    public Lesson() {}

    public Lesson(String title, LessonType lessonType, Course course, LocalDateTime date, String description) {
        this.title = title;
        this.lessonType = lessonType;
        this.course = course;
        this.date = date;
        this.description = description;
    }

    public Lesson(String title, LessonType lessonType, Course course, LocalDateTime date,
                  String description, String room, String schedule) {
        this.title = title;
        this.lessonType = lessonType;
        this.course = course;
        this.date = date;
        this.description = description;
        this.room = room;
        this.schedule = schedule;
    }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public LessonType getLessonType() { return lessonType; }
    public void setLessonType(LessonType lessonType) { this.lessonType = lessonType; }

    public Course getCourse() { return course; }
    public void setCourse(Course course) { this.course = course; }

    public LocalDateTime getDate() { return date; }
    public void setDate(LocalDateTime date) { this.date = date; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getRoom() { return room; }
    public void setRoom(String room) { this.room = room; }

    public String getSchedule() { return schedule; }
    public void setSchedule(String schedule) { this.schedule = schedule; }

    @Override
    public String toString() {
        return "Lesson[title=" + title + ", type=" + lessonType + ", date=" + date + "]";
    }
}