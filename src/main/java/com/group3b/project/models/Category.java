package com.group3b.project.models;

import java.util.UUID;

public class Category {
    private UUID category_id;
    private UUID user_id;
    private int color_id;
    private String title;

    public Category(UUID user_id, int color_id, String title) {
        this(UUID.randomUUID(), user_id, color_id, title);
    }

    public Category(UUID category_id, UUID user_id, int color_id, String title) {
        this.category_id = category_id;
        this.user_id = user_id;
        this.color_id = color_id;
        this.title = title;
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

    public int getColor_id() {
        return color_id;
    }

    public void setColor_id(int color_id) {
        this.color_id = color_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
