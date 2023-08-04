package com.group3b.project.controllers;

import com.group3b.project.models.User;
import com.group3b.project.repositories.ChartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;


@Controller
public class ChartController {
    @Autowired
    ChartRepository chartRepository;

    @GetMapping("/oneDayChart")
    public String oneDayChart(@SessionAttribute(name="user", required = false) User user,
                              Model model) {
        if(user == null){
            return "login";
        }
        model.addAttribute("email", user.getEmail());
        return "report-daily";
    }
    @GetMapping("/oneWeekChart")
    public String oneWeekChart(@SessionAttribute(name="user", required = false) User user,
                                Model model) {
        if(user == null){
            return "login";
        }
        model.addAttribute("email", user.getEmail());
        return "report-weekly";
    }
    @GetMapping("/oneMonthChart")
    public String oneMonthChart(@SessionAttribute(name="user", required = false) User user,
                                Model model) {
        if(user == null){
            return "login";
        }
        model.addAttribute("email", user.getEmail());
        return "report-monthly";
    }
    @GetMapping("/oneYearChart")
    public String oneYearChart(@SessionAttribute(name="user", required = false) User user,
                               Model model) {
        if(user == null){
            return "login";
        }
        model.addAttribute("email", user.getEmail());
        return "report-yearly";
    }
    @GetMapping("/oneDayChart/get")
    @ResponseBody
    public String oneDayChartGet(@SessionAttribute(name="user", required = false) User user) {
        return chartRepository.getTimeActivityOneDay(user).toString();
    }

    @GetMapping("/oneWeekChart/get")
    @ResponseBody
    public String oneWeekChartGet(@SessionAttribute(name="user", required = false) User user) {
        return chartRepository.getTimeActivityOneWeek(user).toString();
    }

    @GetMapping("/oneMonthChart/get")
    @ResponseBody
    public String oneMonthChartGet(@SessionAttribute(name="user", required = false) User user) {
        return chartRepository.getTimeActivityOneMonth(user).toString();
    }

    @GetMapping("/oneYearChart/get")
    @ResponseBody
    public String oneYearChartGet(@SessionAttribute(name="user", required = false) User user) {
        return chartRepository.getTimeActivityOneYear(user).toString();
    }
}
