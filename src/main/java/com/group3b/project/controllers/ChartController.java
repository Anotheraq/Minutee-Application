package com.group3b.project.controllers;

import com.group3b.project.models.User;
import com.group3b.project.repositories.ChartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

@RestController
public class ChartController {
    @Autowired
    ChartRepository chartRepository;

    @GetMapping("/oneDayChart")
    public String oneDayChart(@SessionAttribute(name="user", required = false) User user) {

        return chartRepository.getTimeActivityOneDay(user).toString();
    }

    @GetMapping("/oneWeekChart")
    public String oneWeekChart(@SessionAttribute(name="user", required = false) User user) {
        return chartRepository.getTimeActivityOneWeek(user).toString();
    }

    @GetMapping("/oneMonthChart")
    public String oneMonthChart(@SessionAttribute(name="user", required = false) User user) {
        return chartRepository.getTimeActivityOneMonth(user).toString();
    }

    @GetMapping("/oneYearChart")
    public String oneYearChart(@SessionAttribute(name="user", required = false) User user) {
        return chartRepository.getTimeActivityOneYear(user).toString();
    }
}
