package com.group3b.project.controllers;

import com.group3b.project.models.Activity;
import com.group3b.project.models.User;
import com.group3b.project.repositories.ActivityRepository;
import jdk.jfr.Frequency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;


@Controller
public class ActivityController {
    @Autowired
    ActivityRepository activityRepository;

    @GetMapping("/recent-activities")
    public String recentActivities(Model model, @SessionAttribute(name="user", required = false) User user) {
        if(user == null){
            return "login";
        }
        List<Activity> activityList = activityRepository.getActivities(user.getId());
        model.addAttribute("activities", activityList);
        return "recent-activities";
    }
    @GetMapping("/activities-in-progress")
    public String activitiesInProgress(Model model, @SessionAttribute(name="user", required = false) User user) {
        if(user == null){
            return "login";
        }
        List<Activity> activityList = activityRepository.getUnifnishedActivities(user.getId());
        model.addAttribute("activities", activityList);
        return "activities-in-progress";
    }
    @PostMapping("/add-activity")
    public String activities(@RequestParam(name="description", required = false )String description,
                             @RequestParam(name="total_time", required = false )Integer totalTime,
                             @RequestParam(name="start_time", required = false) String timeStarted,
                             @RequestParam(name="end_time", required = false) String timeEnded,
                             @RequestParam(value="sg", required = false) String timeAutoBox,
                             Model model,
                             @SessionAttribute(name="user", required = false) User user){

        if(user == null){
            return "login";
        }

        UUID id = user.getId();
        System.out.println("desc= "+description);
        System.out.println("totaltime= "+totalTime);
        System.out.println("timeSt= "+timeStarted);
        System.out.println("timeEn="+timeEnded);
        System.out.println("timeAuto="+timeAutoBox);
        if(totalTime!=null){
            System.out.println("qweqwewq");
            totalTimeHandler(totalTime, description, id);
        }else if(!timeStarted.isEmpty() && !timeEnded.isEmpty()){
            startedEndedHandler(timeStarted,timeEnded,description,id);
        }else if(timeAutoBox != null&& timeAutoBox.equals("autoTime")){
            autoHandler(description,id);
        }

        return "add-activity";
    }

    private boolean autoHandler(String description, UUID user_id){
        Activity activity = new Activity(UUID.fromString("f8c3de3d-1fea-4d7c-a8b0-29f63c4c3454"),
                user_id,
                description,
                new Timestamp(System.currentTimeMillis())
        );
        return activityRepository.addActivity(activity);
    }
    private boolean startedEndedHandler(String timeStarted, String timeEnded, String description,UUID user_id){
        System.out.println(1);
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
        Activity activity = new Activity(UUID.fromString("f8c3de3d-1fea-4d7c-a8b0-29f63c4c3454"),
                user_id,
                description,
                timestampSt,
                timestampEn
        );
        return activityRepository.addActivity(activity);
    }
    private boolean totalTimeHandler(int totalTime, String description, UUID user_id){
        System.out.println(2);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        timestamp.setTime(timestamp.getTime() - TimeUnit.MINUTES.toMillis(totalTime));
        Activity activity = new Activity(UUID.fromString("f8c3de3d-1fea-4d7c-a8b0-29f63c4c3454"),
                user_id,
                description,
                timestamp,
                new Timestamp(System.currentTimeMillis())
                );
        return activityRepository.addActivity(activity);
    }
}
