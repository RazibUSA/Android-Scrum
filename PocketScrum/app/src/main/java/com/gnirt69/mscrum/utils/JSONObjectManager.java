package com.gnirt69.mscrum.utils;

import com.gnirt69.mscrum.constant.MSConstants;
import com.gnirt69.mscrum.model.DataModel;
import com.gnirt69.mscrum.model.Role;
import com.gnirt69.mscrum.model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
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

       for(int i = 0; i < userArr.length(); i++) {
           JSONObject obj = userArr.getJSONObject(i);
           User user = new User();

            user.setFirstName(obj.getString(MSConstants.KEY_FIRSTNAME));
            user.setLastName(obj.getString(MSConstants.KEY_LASTNAME));
            user.setEmail(obj.getString(MSConstants.KEY_EMAIL));
            user.setId(obj.getInt(MSConstants.KEY_ID));
            user.setPassword(obj.getString(MSConstants.KEY_PASSWORD));

           JSONObject roleObj = obj.getJSONObject(MSConstants.KEY_ROLE);
            Role role = new Role();
           role.setId(roleObj.getInt(MSConstants.KEY_ID));
           role.setName(roleObj.getString(MSConstants.KEY_NAME));
           user.setRole(role);
           userList.add(user);

        }

        return userList;
    }

}
