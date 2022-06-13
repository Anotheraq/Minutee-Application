package com.group3b.project.models;

import java.util.UUID;

public class Category {
    private UUID category_id;
    private String color;
    private String title;

    public Category(UUID category_id, String color, String title) {
        this.category_id = category_id;
        this.color = color;
        this.title = title;
    }

    public UUID getCategory_id() {
        return category_id;
    }

    public void setCategory_id(UUID category_id) {
        this.category_id = category_id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
