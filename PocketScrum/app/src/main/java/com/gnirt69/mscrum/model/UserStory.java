package com.gnirt69.mscrum.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UserStory extends DataModel implements Serializable {

    private String title;
    private String description;
    private String estimation;
    private Project project;
    private List<Sprint> sprints = new ArrayList<>();
    private List<LogTime> logTimes = new ArrayList<>();


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEstimation() {
        return estimation;
    }

    public void setEstimation(String estimation) {
        this.estimation = estimation;
    }


    public List<Sprint> getSprints() {
        return sprints;
    }

    public void setSprints(List<Sprint> sprints) {
        this.sprints = sprints;
    }


    public List<LogTime> getLogTimes() {
        return logTimes;
    }

    public void setLogTimes(List<LogTime> logTimes) {
        this.logTimes = logTimes;
    }


    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
