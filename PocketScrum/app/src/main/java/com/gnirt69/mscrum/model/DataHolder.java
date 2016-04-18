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
        private User logger = new User();

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
