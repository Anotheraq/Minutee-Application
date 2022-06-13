package com.group3b.project.repositories;

import com.group3b.project.models.Activity;

import java.util.List;
import java.util.UUID;

public interface IActivityRepository {
    boolean addActivity(Activity activity);
    List<Activity> getActivities(UUID user_id);
    List<Activity> getFinishedActivities(UUID user_id);
    public List<Activity> getUnifnishedActivities(UUID user_id);
}
