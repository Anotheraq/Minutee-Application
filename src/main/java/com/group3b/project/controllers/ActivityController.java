package com.group3b.project.controllers;

import com.group3b.project.models.Activity;
import com.group3b.project.models.Category;
import com.group3b.project.models.User;
import com.group3b.project.repositories.ActivityRepository;
import com.group3b.project.repositories.CategoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.TimeUnit;


@Controller
public class ActivityController {
    @Autowired
    ActivityRepository activityRepository;
    @Autowired
    CategoryRepository categoryRepository;

    @GetMapping("/recent-activities")
    public String recentActivities(Model model, @SessionAttribute(name="user", required = false) User user) {
        if(user == null){
            return "login";
        }
        model.addAttribute("email", user.getEmail());

        List<Activity> activityList = activityRepository.getFinishedActivities(user.getId());
        model.addAttribute("activities", activityList);
        return "recent-activities";
    }
    @GetMapping("/activities-in-progress")
    public String activitiesInProgress(Model model, @SessionAttribute(name="user", required = false) User user) {
        if(user == null){
            return "login";
        }
        model.addAttribute("email", user.getEmail());

        List<Activity> activityList = activityRepository.getUnifnishedActivities(user.getId());
        model.addAttribute("activities", activityList);
        return "activities-in-progress";
    }
    @PostMapping("/add-activity")
    public String activities(@RequestParam(name="subject", required = false )String title,
                             @RequestParam(name="description", required = false )String description,
                             @RequestParam(name="total_time", required = false )Integer totalTime,
                             @RequestParam(name="start_time", required = false) String timeStarted,
                             @RequestParam(name="end_time", required = false) String timeEnded,
                             @RequestParam(value="sg", required = false) String timeAutoBox,
                             Model model,
                             @SessionAttribute(name="user", required = false) User user){

        if(user == null){
            return "login";
        }
        if(title.equals("Choose category")){
            return "add-activity";
        }
        UUID id = user.getId();
        model.addAttribute("email", user.getEmail());
        if(totalTime != null){
            if(totalTime <= 0){
                return "add-activity";
            }
            totalTimeHandler(totalTime, description, id, title);
        }else if(!timeStarted.isEmpty() && !timeEnded.isEmpty()){
            LocalTime local1 = LocalTime.parse(timeStarted);
            LocalTime local2 = LocalTime.parse(timeEnded);
            if(local1.getHour() > local2.getHour()){
                return "add-activity";
            }else if(local1.getHour() == local2.getHour() && local2.getMinute() < local1.getMinute()){
                return "add-activity";
            }


            startedEndedHandler(timeStarted,timeEnded,description,id, title);
        }else if(timeAutoBox != null&& timeAutoBox.equals("autoTime")){
            autoHandler(description,id, title);
        }

        return "add-activity";
    }

    private boolean autoHandler(String description, UUID user_id,String title){
        Category category = categoryRepository.getCategoryByTitle(title);
        Activity activity = new Activity(category.getCategory_id(),
                user_id,
                description,
                new Timestamp(System.currentTimeMillis()),
                category
        );
        return activityRepository.addActivity(activity);
    }
    private boolean startedEndedHandler(String timeStarted, String timeEnded, String description,UUID user_id, String title){
        Category category = categoryRepository.getCategoryByTitle(title);
        Timestamp timestampSt =
                Timestamp.valueOf(
                        new SimpleDateFormat("yyyy-MM-dd ")
                                .format(new Date())
                                .concat(timeStarted + ":00")
                );
        Timestamp timestampEn =
                Timestamp.valueOf(
                        new SimpleDateFormat("yyyy-MM-dd ")
                                .format(new Date())
                                .concat(timeEnded + ":00")
                );
        Activity activity = new Activity(category.getCategory_id(),
                user_id,
                description,
                timestampSt,
                timestampEn,
                category
        );
        return activityRepository.addActivity(activity);
    }
    private boolean totalTimeHandler(int totalTime, String description, UUID user_id, String title){
        Category category = categoryRepository.getCategoryByTitle(title);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        timestamp.setTime(timestamp.getTime() - TimeUnit.MINUTES.toMillis(totalTime));
        Activity activity = new Activity(category.getCategory_id(),
                user_id,
                description,
                timestamp,
                new Timestamp(System.currentTimeMillis()),
                category
                );
        return activityRepository.addActivity(activity);
    }

    @PostMapping("/finishActivity")
    public String finishActivity(@RequestParam(value="fin-button") int activity_id,
                                 @SessionAttribute(name="user", required = false) User user
                                 ){
        activityRepository.updateActivity(user.getId(), activity_id);
        return "redirect:/activities-in-progress";
    }
}
