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
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.gnirt69.mscrum.R;
import com.gnirt69.mscrum.adapter.RecyclerAdapterPro;
import com.gnirt69.mscrum.constant.MSConstants;
import com.gnirt69.mscrum.model.DataHolder;
import com.gnirt69.mscrum.model.Project;
import com.gnirt69.mscrum.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class Fragment4 extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerAdapterPro adapter;
    private FloatingActionButton fab;

    private List<Project> projectArrayList;


    private EditText projectName;
    private DatePicker startDp;
    private DatePicker endDp;

    String proName;
    String startDate;
    String endDate;



    private int userType;
    private String userTypeStr;


    public Fragment4() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment1, container, false);


        projectArrayList = DataHolder.getInstance().getProjectList();

        Log.d("userArrayList size:", ""+projectArrayList.size());

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyle_view);
        fab = (FloatingActionButton) rootView.findViewById(R.id.fab);

        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

//        setRecyclerViewData(); //adding data to array list
        adapter = new RecyclerAdapterPro(getActivity(), projectArrayList);
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
                dialog.setContentView(R.layout.dialog_add_project); //layout for dialog
                dialog.setTitle("Add A New Project:");
                dialog.setCancelable(false); //none-dismiss when touching outside Dialog

                // set the custom dialog components - texts and image
                projectName = (EditText) dialog.findViewById(R.id.project_name);
                startDp = (DatePicker)dialog.findViewById(R.id.dp_start) ;
                endDp = (DatePicker)dialog.findViewById(R.id.dp_end) ;

                View btnAdd = dialog.findViewById(R.id.btn_ok);
                View btnCancel = dialog.findViewById(R.id.btn_cancel);




                btnAdd.setOnClickListener(onConfirmListener(dialog));
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

    private View.OnClickListener onConfirmListener( final Dialog dialog) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startDate = Utils.getDateFromDatePicker(startDp);
                endDate = Utils.getDateFromDatePicker(endDp);
                proName = projectName.getText().toString().trim();
                Log.d("Date:", ""+startDate+"###"+endDate+"###"+proName);

                createProject();

//                registerUser();
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



    private void createProject(){


        final ProgressDialog progressDialog = new ProgressDialog(getActivity(),
                R.style.AppTheme);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Project...");
        progressDialog.show();
        JSONObject Obj = new JSONObject();

        try {
            Obj.put("name", proName);

            // Need to open later - razib
//            Obj.put("lastName", lName);
//            Obj.put("email", emailAddress);

            JSONObject loggerOBject = new JSONObject();

            loggerOBject.put("id", String.valueOf(DataHolder.getInstance().getLogger().getId()));
            Obj.put("owner", loggerOBject);

            Log.d("userObj:", Obj.toString());


        } catch (JSONException e) {
            Log.d("JSONException", "JSONException");
            e.printStackTrace();
        }

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, MSConstants.PROJECT_URL, Obj,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
//                        Toast.makeText(getActivity(),response,Toast.LENGTH_LONG).show();
                        Log.d("response", "Success");

                        Project pro = new Project();
                        pro.setName(proName);
                        projectArrayList.add(pro);

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


        };

        Log.d("stringRequest", ":" +stringRequest);



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
