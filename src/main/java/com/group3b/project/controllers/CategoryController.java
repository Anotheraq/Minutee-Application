package com.group3b.project.controllers;

import com.group3b.project.models.Category;
import com.group3b.project.models.User;
import com.group3b.project.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.List;

@Controller
public class CategoryController {

    @Autowired
    CategoryRepository categoryRepository;

    @GetMapping("/categories")
    public String activities(@SessionAttribute(name="user", required = false) User user, Model model) {
        if(user == null){
            return "login";
        }
        List<Category> categoryList = categoryRepository.getCategories();
        model.addAttribute("categories", categoryList);
        return "categories";
    }
}
