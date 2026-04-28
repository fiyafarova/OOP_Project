package model.academic;

import enums.LessonType;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Lesson implements Serializable {
    private static final long serialVersionUID = 1L;

    private LessonType lessonType;
    private LocalDateTime time;
    private String room;

    public Lesson() {
    }

    public Lesson(LessonType lessonType, LocalDateTime time, String room) {
        this.lessonType = lessonType;
        this.time = time;
        this.room = room;
    }

    public LessonType getLessonType() {
        return lessonType;
    }

    public void setLessonType(LessonType lessonType) {
        this.lessonType = lessonType;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    @Override
    public String toString() {
        return lessonType + " in room " + room + " at " + time;
    }
}