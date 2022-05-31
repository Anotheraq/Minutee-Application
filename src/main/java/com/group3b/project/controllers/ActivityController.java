package com.group3b.project.controllers;

import com.group3b.project.models.Activity;
import com.group3b.project.repositories.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


@Controller
public class ActivityController {
    @Autowired
    ActivityRepository activityRepository;
    @PostMapping("/add-activity")
    public String activities(@RequestParam("description") Optional<String> description,
                             @RequestParam("total_time") Optional<Integer> totalTime,
                             @RequestParam("start_time") Optional<String> timeStarted,
                             @RequestParam("end_time") Optional<String> timeEnded,
                             Model model){

        if(totalTime.isPresent()){
            totalTimeHandler(totalTime.get());
        }else if(timeStarted.isPresent() && timeEnded.isPresent()){
            startedEndedHandler(timeStarted.get(),timeEnded.get());
        }

        return "add-activity";
    }

    private boolean startedEndedHandler(String timeStarted, String timeEnded){
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
                UUID.fromString("1821e344-36c2-4edf-82f3-d0fd0f12299b"),
                timestampSt,
                timestampEn
        );
        return activityRepository.addActivity(activity);
    }
    private boolean totalTimeHandler(int totalTime){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        timestamp.setTime(timestamp.getTime() - TimeUnit.MINUTES.toMillis(totalTime));
        Activity activity = new Activity(UUID.fromString("f8c3de3d-1fea-4d7c-a8b0-29f63c4c3454"),
                UUID.fromString("1821e344-36c2-4edf-82f3-d0fd0f12299b"),
                timestamp,
                new Timestamp(System.currentTimeMillis())
                );
        return activityRepository.addActivity(activity);
    }
}
