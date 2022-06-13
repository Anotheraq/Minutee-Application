package com.group3b.project.controllers;

import com.group3b.project.models.User;
import com.group3b.project.repositories.ChartRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

@RestController
public class ChartController {
    @Autowired
    ChartRepository chartRepository;

    @GetMapping("/oneDayChart")
    public JSONArray oneDayChart(@SessionAttribute(name="user", required = false) User user) {
        return chartRepository.getTimeActivityOneDay(user);
    }

    @GetMapping("/oneWeekChart")
    public JSONObject oneWeekChart(@SessionAttribute(name="user", required = false) User user) {
        return chartRepository.getTimeActivityOneWeek(user);
    }

    @GetMapping("/oneMonthChart")
    public JSONObject oneMonthChart(@SessionAttribute(name="user", required = false) User user) {
        return chartRepository.getTimeActivityOneMonth(user);
    }

    @GetMapping("/oneYearChart")
    public JSONObject oneYearChart(@SessionAttribute(name="user", required = false) User user) {
        return chartRepository.getTimeActivityOneYear(user);
    }
}
