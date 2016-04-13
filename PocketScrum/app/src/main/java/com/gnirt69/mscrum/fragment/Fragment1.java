package com.gnirt69.mscrum.fragment;/**
 * Created by NgocTri on 10/18/2015.
 */

import android.app.Dialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.*;
import com.android.volley.*;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gnirt69.mscrum.R;
import com.gnirt69.mscrum.adapter.RecyclerAdapter;
import com.gnirt69.mscrum.comm.CustomJSONObjectRequest;
import com.gnirt69.mscrum.constant.MSConstants;
import com.gnirt69.mscrum.model.DataHolder;
import com.gnirt69.mscrum.model.Role;
import com.gnirt69.mscrum.model.User;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Fragment1 extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerAdapter adapter;
    private List<User> userArrayList;
    private FloatingActionButton fab;
    private boolean gender;

    private EditText firstName;
    private EditText lastName;
    private EditText email;
    private EditText pass;
    private  Spinner role;
    private int userType;
    private String userTypeStr;


    public Fragment1() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment1, container, false);

    // Razib from

//        setContentView(R.layout.activity_main);
        userArrayList = DataHolder.getInstance().getUserList();

        Log.d("userArrayList size:", ""+userArrayList.size());

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyle_view);
        fab = (FloatingActionButton) rootView.findViewById(R.id.fab);

        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

//        setRecyclerViewData(); //adding data to array list
        adapter = new RecyclerAdapter(getActivity(), userArrayList);
        recyclerView.setAdapter(adapter);

        fab.setOnClickListener(onAddingListener());




        return rootView;
    }

    //razib
    private void setRecyclerViewData() {
//        friendArrayList.add(new User("Razib", "Mollick", false, "raz@gmail.com", "1234"));
//        friendArrayList.add(new User("Nadim", "Sumon", true, "Dev@hmail.com", "1234"));

    }

    private View.OnClickListener onAddingListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.dialog_add); //layout for dialog
                dialog.setTitle("Add a new User");
                dialog.setCancelable(false); //none-dismiss when touching outside Dialog

                // set the custom dialog components - texts and image
                 firstName = (EditText) dialog.findViewById(R.id.first_name);
                 lastName = (EditText) dialog.findViewById(R.id.last_name);
                 email = (EditText) dialog.findViewById(R.id.email);
                 pass = (EditText) dialog.findViewById(R.id.password);
                role = (Spinner) dialog.findViewById(R.id.gender);
                View btnAdd = dialog.findViewById(R.id.btn_ok);
                View btnCancel = dialog.findViewById(R.id.btn_cancel);

                //set spinner adapter
                ArrayList<String> gendersList = new ArrayList<>();
                gendersList.add("Sys");
                gendersList.add("PO");
                gendersList.add("SM");
                gendersList.add("Dev");

                ArrayAdapter<String> spnAdapter = new ArrayAdapter<String>(getActivity(),
                        android.R.layout.simple_dropdown_item_1line, gendersList);
                role.setAdapter(spnAdapter);

                //set handling event for 2 buttons and spinner
                role.setOnItemSelectedListener(onItemSelectedListener());
                btnAdd.setOnClickListener(onConfirmListener(firstName,lastName,email,pass, dialog));
                btnCancel.setOnClickListener(onCancelListener(dialog));

                dialog.show();
            }
        };
    }

    private AdapterView.OnItemSelectedListener onItemSelectedListener() {
        return new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                if (position == 0) {
//                    gender = true;
//                } else {
//                    gender = false;
//                }
                userType = position+1;
                userTypeStr = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };
    }

    private View.OnClickListener onConfirmListener(final EditText firstName, final EditText lastName,
                                                   final EditText email, final EditText pass, final Dialog dialog) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
//                getUser();

//                User friend = new User(firstName.getText().toString().trim(), lastName.getText().toString().trim(), gender,
//                        email.getText().toString().trim(), pass.getText().toString().trim());
//
//                //adding new object to arraylist
//                friendArrayList.add(friend);
//
//                //notify data set changed in RecyclerView adapter
//                adapter.notifyDataSetChanged();

                //close dialog after all
                dialog.dismiss();
            }
        };
    }

    private void registerUserJSON(){

        Map<String, String> params = new HashMap<String, String>();
        params.put("firstName","BBC1");
        params.put("lastName", "London");

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        CustomJSONObjectRequest jsObjRequest = new CustomJSONObjectRequest(Request.Method.POST, MSConstants.REGISTER_URL, params,
                new Response.Listener<JSONObject>()
                {
                    @Override
                public void onResponse(JSONObject response)
                { Log.d("onResponse", "Worked?"); } },
                new Response.ErrorListener()
                { @Override public void onErrorResponse(VolleyError error)
                { Log.d("Error", "Nope"+error.toString()); } });

        requestQueue.add(jsObjRequest);


    }

    private void getUser() {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest  getRequest = new StringRequest (Request.Method.GET, MSConstants.USER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // display response
                        Log.d("Response", response.toString());
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", error.toString());
                    }
                });


// add it to the RequestQueue
        requestQueue.add(getRequest);
    }



    private void registerUser(){
        final String fName = firstName.getText().toString().trim();
        final String lName = lastName.getText().toString().trim();
        final String emailAddress = email.getText().toString().trim();
        final String passwordText = pass.getText().toString().trim();

        final ProgressDialog progressDialog = new ProgressDialog(getActivity(),
                R.style.AppTheme);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating User...");
        progressDialog.show();
        JSONObject userObj = new JSONObject();

        try {
            userObj.put("firstName", fName);
            userObj.put("lastName", lName);
            userObj.put("email", emailAddress);
            userObj.put("password", passwordText);
            JSONObject roleOBject = new JSONObject();

            roleOBject.put("id", userType);
            userObj.put("role", roleOBject);

            Log.d("userObj:", userObj.toString());


        } catch (JSONException e) {
            Log.d("JSONException", "JSONException");
            e.printStackTrace();
        }

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, MSConstants.REGISTER_URL, userObj,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
//                        Toast.makeText(getActivity(),response,Toast.LENGTH_LONG).show();
                        Log.d("response", "Success");
//                        User friend = new User(fName, lName, gender,
//                                emailAddress, passwordText);

                        //adding new object to arraylist
//                        friendArrayList.add(friend);
                        User user = new User();
                        user.setFirstName(fName);
                        user.setLastName(lName);
                        user.setEmail(emailAddress);
                        user.setPassword(passwordText);

                        Role role = new Role();
                        role.setId(userType);
                        role.setName(userTypeStr);
                        user.setRole(role);

                        userArrayList.add(user);

                        //notify data set changed in RecyclerView adapter
                        adapter.notifyDataSetChanged();

                        progressDialog.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(),error.toString(), Toast.LENGTH_LONG).show();
                        Log.d("Error", "Nope"+error.toString());
                        progressDialog.dismiss();
                    }
                }){
//            @Override
//            protected Map<String,String> getParams(){
//                Map<String,String> params = new HashMap<String, String>();
//                params.put("firstName", "gggg");
////                params.put(MSConstants.KEY_LASTNAME, lName);
////                params.put(MSConstants.KEY_EMAIL, emailAddress);
////                params.put(MSConstants.KEY_PASSWORD,passwordText);
////                Log.d("params", ":" +params.get());
//
//                return params;
//            }

//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                HashMap<String, String> headers = new HashMap<String, String>();
//                headers.put("Content-Type", "application/json");
////                headers.put("Accept", "application/json");
//                Log.d("headers", ":" +headers.get("Content-Type"));
//                return headers;
//            }

        };

        Log.d("stringRequest", ":" +stringRequest);
//        stringRequest.setRetryPolicy(new RetryPolicy() {
//            @Override
//            public int getCurrentTimeout() {
//                return 50000;
//            }
//
//            @Override
//            public int getCurrentRetryCount() {
//                return 50000;
//            }
//
//            @Override
//            public void retry(VolleyError error) throws VolleyError {
//
//            }
//        });


        requestQueue.add(stringRequest);
    }

    private View.OnClickListener onCancelListener(final Dialog dialog) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        };
    }




}
