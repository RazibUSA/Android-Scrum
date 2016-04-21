package com.gnirt69.mscrum.utils;

import android.util.Log;

import com.gnirt69.mscrum.constant.MSConstants;
import com.gnirt69.mscrum.model.DataHolder;
import com.gnirt69.mscrum.model.DataModel;
import com.gnirt69.mscrum.model.Project;
import com.gnirt69.mscrum.model.Role;
import com.gnirt69.mscrum.model.Sprint;
import com.gnirt69.mscrum.model.User;
import com.gnirt69.mscrum.model.UserStory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Razib Mollick on 4/12/2016.
 */
public class JSONObjectManager {

    public JSONObject createJsonObject(DataModel dModel){

        JSONObject root = null;


        return root;

    }


    public DataModel parseJsonObject(JSONObject responseObj, ModelType type){

        DataModel dataModel = null;



        return dataModel;

    }


    public static List<User> parseUserListData(JSONArray userArr) throws JSONException {
         List<User> userList = new ArrayList<>();
         List<User> smList = new ArrayList<>();
         List<User> devList = new ArrayList<>();

       for(int i = 0; i < userArr.length(); i++) {
           JSONObject obj = userArr.getJSONObject(i);
           User user = parseUser(obj);
           userList.add(user);

           if(user.getRole().getId() == 3){
               smList.add(user);
           }else if(user.getRole().getId() == 4){
               devList.add(user);
           }

        }

        Log.d("SM", "smList.size()");
        if(smList.size() > 0) {
            DataHolder.getInstance().setScrumMasterList(smList);
        }

        if(devList.size() > 0) {
            DataHolder.getInstance().setDevList(devList);
        }

        return userList;
    }

    public static User parseUser(JSONObject obj)throws JSONException{
        User user = new User();

        user.setFirstName(obj.getString(MSConstants.KEY_FIRSTNAME));
        user.setLastName(obj.getString(MSConstants.KEY_LASTNAME));
        user.setEmail(obj.getString(MSConstants.KEY_EMAIL));
        user.setId(obj.getInt(MSConstants.KEY_ID));
//            user.setPassword(obj.getString(MSConstants.KEY_PASSWORD));

        JSONObject roleObj = obj.getJSONObject(MSConstants.KEY_ROLE);
        Role role = new Role();
        role.setId(roleObj.getInt(MSConstants.KEY_ID));
        role.setName(roleObj.getString(MSConstants.KEY_NAME));
        user.setRole(role);

        return user;

    }

    public static int parseUserType(JSONObject indObj) throws JSONException{

        int roleID=0;

        if(indObj != null){

            JSONObject roleObj = indObj.getJSONObject(MSConstants.KEY_ROLE);
            roleID = roleObj.getInt(MSConstants.KEY_ID);

        }

        return roleID;
    }

    public static List<Project> parseProjectData(JSONArray projectList) throws JSONException{

        List<Project> proList = new ArrayList<>();

        for(int i = 0; i < projectList.length(); i++) {
            JSONObject obj = projectList.getJSONObject(i);

            proList.add(parseProject(obj));

        }

        return proList;


    }

public static Project parseProject(JSONObject obj) throws JSONException{


//            "id": 2,
//            "name": "MumScrum",
//            "startDate": null,
//            "endDate": null,
//            "owner": null,
//            "managedBy": null

            Project pro = new Project();

            pro.setId(obj.getLong("id"));
            pro.setName(obj.getString("name"));

            if(obj.has("managedBy") &&!obj.isNull("managedBy")) {

                Log.d("managedBy","managedBy");


                    JSONObject smObj = obj.getJSONObject("managedBy");
                    if (smObj != null) {

                        pro.setManagedBy(parseUser(smObj));

                    }

            }



    return pro;

}

    public  static List<UserStory> parseBacklogData(JSONArray usArr) throws JSONException{

        List<UserStory> proList = new ArrayList<>();

        for(int i = 0; i < usArr.length(); i++) {
            JSONObject obj = usArr.getJSONObject(i);

            proList.add(parseUserStory(obj));

        }

        return proList;
    }

    public static UserStory parseUserStory(JSONObject obj) throws JSONException {

        UserStory us = new UserStory();
        us.setId(obj.optLong("id"));
        us.setTitle(obj.getString("title"));
        us.setEstimation(obj.getString("estimation"));
//        us.setProject();
        return us;

    }
    public  static List<Sprint> parseSprintData(JSONArray usArr) throws JSONException{

        List<Sprint> sprList = new ArrayList<>();

        for(int i = 0; i < usArr.length(); i++) {
            JSONObject obj = usArr.getJSONObject(i);

            sprList.add(parseSprint(obj));

        }

        return sprList;
    }

    public static Sprint parseSprint(JSONObject obj) throws JSONException {

        Sprint us = new Sprint();
        us.setId(obj.optLong("id"));
        us.setName(obj.getString("name"));


        return us;

    }


}
