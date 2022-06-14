package com.group3b.project.models;

import java.sql.Timestamp;

public class Chart {
    private String title;
    private long totalTime;
    private Timestamp timeStarted;
    private Timestamp timeEnded;
    private String color;
    public Chart(String title, long totalTime, String color) {
        this(title, totalTime, null, null, color);
    }

    public Chart(String title, Timestamp timeStarted, Timestamp timeEnded){
        this(title, 0, timeStarted, timeEnded, null);
    }
    private Chart(String title, long totalTime, Timestamp timeStarted, Timestamp timeEnded, String color) {
        this.title = title;
        this.totalTime = totalTime;
        this.timeStarted = timeStarted;
        this.timeEnded = timeEnded;
        this.color = color;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(long totalTime) {
        this.totalTime = totalTime;
    }

    public Timestamp getTimeStarted() {
        return timeStarted;
    }

    public void setTimeStarted(Timestamp timeStarted) {
        this.timeStarted = timeStarted;
    }

    public Timestamp getTimeEnded() {
        return timeEnded;
    }

    public void setTimeEnded(Timestamp timeEnded) {
        this.timeEnded = timeEnded;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
