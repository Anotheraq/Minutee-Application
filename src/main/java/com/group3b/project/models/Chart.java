package com.group3b.project.models;

import java.sql.Timestamp;

public class Chart {
    private String title;
    private long totalTime;
    private Timestamp timeStarted;
    private Timestamp timeEnded;
    public Chart(String title, long totalTime) {
        this(title, totalTime, null, null);
    }

    public Chart(String title, Timestamp timeStarted, Timestamp timeEnded){
        this(title, 0, timeStarted, timeEnded);
    }
    private Chart(String title, long totalTime, Timestamp timeStarted, Timestamp timeEnded) {
        this.title = title;
        this.totalTime = totalTime;
        this.timeStarted = timeStarted;
        this.timeEnded = timeEnded;
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
}
