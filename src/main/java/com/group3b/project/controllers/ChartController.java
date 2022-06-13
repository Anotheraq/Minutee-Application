package com.group3b.project.controllers;

import com.group3b.project.models.User;
import com.group3b.project.repositories.ChartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.HashMap;


@Controller
public class ChartController {
    @Autowired
    ChartRepository chartRepository;

    @GetMapping("/oneDayChart")
    public String oneDayChart(@SessionAttribute(name="user", required = false) User user) {
        if(user == null){
            return "login";
        }
        return "report-daily";
    }
    @GetMapping("/oneWeekChart")
    public String oneWeekChart(@SessionAttribute(name="user", required = false) User user) {
        if(user == null){
            return "login";
        }
        return "oneWeekChart";
    }
    @GetMapping("/oneMonthChart")
    public String oneMonthChart(@SessionAttribute(name="user", required = false) User user) {
        if(user == null){
            return "login";
        }
        return "oneMonthChart";
    }
    @GetMapping("/oneYearChart")
    public String oneYearChart(@SessionAttribute(name="user", required = false) User user) {
        if(user == null){
            return "login";
        }
        return "oneYearChart";
    }
    @GetMapping("/oneDayChart/get")
    @ResponseBody
    public String oneDayChartGet(@SessionAttribute(name="user", required = false) User user) {
        return chartRepository.getTimeActivityOneDay(user).toString();
    }

    @GetMapping("/oneWeekChart/get")
    @ResponseBody
    public HashMap<String, Double> oneWeekChartGet(@SessionAttribute(name="user", required = false) User user) {
        return chartRepository.getTimeActivityOneWeek(user);
    }

    @GetMapping("/oneMonthChart/get")
    @ResponseBody
    public HashMap<String, Double> oneMonthChartGet(@SessionAttribute(name="user", required = false) User user) {
        return chartRepository.getTimeActivityOneMonth(user);
    }

    @GetMapping("/oneYearChart/get")
    @ResponseBody
    public HashMap<String, Double> oneYearChartGet(@SessionAttribute(name="user", required = false) User user) {
        return chartRepository.getTimeActivityOneYear(user);
    }
}
