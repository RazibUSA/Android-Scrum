package com.gnirt69.mscrum.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 984834 on 4/12/2016.
 */
public class DataHolder {
        private List<User> userList = new ArrayList<>();
        private List<Project> projectList = new ArrayList<>();
        private List<UserStory> usList = new ArrayList<>();
        private List<UserStory> sprintUSList = new ArrayList<>();
        private List<Sprint> sprintList = new ArrayList<>();
        private List<User> devList = new ArrayList<>();
        private User logger = new User();
        private Project currentProject = new Project();
        private Sprint currentSprint = new Sprint();
        private List<UserStory> devUSList = new ArrayList<>();

    public List<UserStory> getDevUSList() {
        return devUSList;
    }

    public void setDevUSList(List<UserStory> devUSList) {
        this.devUSList = devUSList;
    }

    public List<UserStory> getSprintUSList() {
        return sprintUSList;
    }

    public void setSprintUSList(List<UserStory> sprintUSList) {
        this.sprintUSList = sprintUSList;
    }

    private List<User> scrumMasterList = new ArrayList<>();


    public Sprint getCurrentSprint() {
        return currentSprint;
    }

    public void setCurrentSprint(Sprint currentSprint) {
        this.currentSprint = currentSprint;
    }

    public List<Sprint> getSprintList() {
        return sprintList;
    }

    public void setSprintList(List<Sprint> sprintList) {
        this.sprintList = sprintList;
    }




    public List<User> getScrumMasterList() {
        return scrumMasterList;
    }

    public void setScrumMasterList(List<User> scrumMasterList) {
        this.scrumMasterList = scrumMasterList;
    }

    public List<User> getDevList() {
        return devList;
    }

    public void setDevList(List<User> devList) {
        this.devList = devList;
    }





    public Project getCurrentProject() {
        return currentProject;
    }

    public void setCurrentProject(Project currentProject) {
        this.currentProject = currentProject;
    }

    public List<UserStory> getUsList() {
        return usList;
    }

    public void setUsList(List<UserStory> usList) {
        this.usList = usList;
    }



    public User getLogger() {
        return logger;
    }

    public void setLogger(User logger) {
        this.logger = logger;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public void setProjectList(List<Project> projectList) {
        this.projectList = projectList;
    }

    public void setUserStoryList(List<UserStory> userStoryList) {
        this.userStoryList = userStoryList;
    }

    private List<UserStory> userStoryList = new ArrayList<>();

    public List<User> getUserList() {
        return userList;
    }

    public List<Project> getProjectList() {
        return projectList;
    }

    public List<UserStory> getUserStoryList() {
        return userStoryList;
    }

    private static final DataHolder holder = new DataHolder();
        public static DataHolder getInstance() {return holder;}
}
