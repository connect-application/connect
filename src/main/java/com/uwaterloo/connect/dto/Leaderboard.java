package com.uwaterloo.connect.dto;

import com.uwaterloo.connect.model.User;

public class Leaderboard {
    User user;
    Integer activitiesInProgress;
    Integer activitiesFinished;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public Integer getActivitiesInProgress() {
        return activitiesInProgress;
    }

    public void setActivitiesInProgress(Integer activitiesInProgress) {
        this.activitiesInProgress = activitiesInProgress;
    }

    public Integer getActivitiesFinished() {
        return activitiesFinished;
    }

    public void setActivitiesFinished(Integer activitiesFinished) {
        this.activitiesFinished = activitiesFinished;
    }
}
