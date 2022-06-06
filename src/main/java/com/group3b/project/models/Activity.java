package com.group3b.project.models;

import java.sql.Timestamp;
import java.util.UUID;

public class Activity {
    private int activity_id;
    private UUID category_id;
    private UUID user_id;
    private String description;
    private Timestamp timeStarted;
    private Timestamp timeEnded;


    public Activity(UUID category_id, UUID user_id, Timestamp timeStarted, Timestamp timeEnded) {
        this(0, category_id, user_id, null, timeStarted, timeEnded);
    }
    public Activity(UUID category_id, UUID user_id, String description, Timestamp timeStarted) {
        this(0, category_id, user_id, description, timeStarted, Timestamp.valueOf("1111-11-11 11:11:11"));
    }

    public Activity(UUID category_id, UUID user_id, String description, Timestamp timeStarted, Timestamp timeEnded) {
        this(0, category_id, user_id, description, timeStarted, timeEnded);
    }

    public Activity(int activity_id, UUID category_id, UUID user_id, String description, Timestamp timeStarted, Timestamp timeEnded) {
        this.activity_id = activity_id;
        this.category_id = category_id;
        this.user_id = user_id;
        this.description = description;
        this.timeStarted = timeStarted;
        this.timeEnded = timeEnded;
    }


    public int getActivity_id() {
        return activity_id;
    }

    public void setActivity_id(int activity_id) {
        this.activity_id = activity_id;
    }

    public UUID getCategory_id() {
        return category_id;
    }

    public void setCategory_id(UUID category_id) {
        this.category_id = category_id;
    }

    public UUID getUser_id() {
        return user_id;
    }

    public void setUser_id(UUID user_id) {
        this.user_id = user_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
