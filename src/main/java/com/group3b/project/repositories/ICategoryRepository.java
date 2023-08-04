package com.group3b.project.repositories;

import com.group3b.project.models.Category;

import java.util.List;
import java.util.UUID;

public interface ICategoryRepository {
    Category getCategoryByTitle(String title);
    List<Category> getCategories();
}
