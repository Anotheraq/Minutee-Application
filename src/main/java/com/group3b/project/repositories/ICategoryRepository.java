package com.group3b.project.repositories;

import com.group3b.project.models.Category;

import java.util.UUID;

public interface ICategoryRepository {
    boolean addCategory(Category category);
    Category getCategories(UUID user_id);
}
