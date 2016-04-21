package com.gnirt69.mscrum.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.gnirt69.mscrum.MainActivity;
import com.gnirt69.mscrum.R;
import com.gnirt69.mscrum.constant.MSConstants;
import com.gnirt69.mscrum.model.DataHolder;
import com.gnirt69.mscrum.model.Project;
import com.gnirt69.mscrum.model.Sprint;
import com.gnirt69.mscrum.utils.JSONObjectManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;


public class RecyclerAdapterSprint extends RecyclerView.Adapter<RecyclerAdapterSprint.ViewHolder> {

    private List<Sprint> sprintList;
    private Activity activity;


    public RecyclerAdapterSprint(Activity activity, List<Sprint> sprints) {
        this.sprintList = sprints;
        this.activity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        //inflate your layout and pass it to view holder
        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.item_recycler_sprint, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        //setting data to view holder elements
        viewHolder.sprintName.setText("Sprint Name:"+ sprintList.get(position).getName());
        viewHolder.startDate.setText("Start Date: 02/10/2016");
        viewHolder.endDate.setText("End Date: 06/10/2016");
        viewHolder.proSM.setText("Assigned To:None");
        // Need work later razib
//        viewHolder.startDate.setText("Start Date:"+ projectList.get(position).getStartDate().toString());
//        viewHolder.endDate.setText("End Date:"+ projectList.get(position).getEndDate().toString());
//        viewHolder.proSM.setText("Scrum Master:"+projectList.get(position).getManagedBy().getFirstName());


        viewHolder.edit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                /// button click event
                final Dialog dialog = new Dialog(activity);
                dialog.setTitle("Button test " );
                dialog.setCancelable(true);
                dialog.show();

            }
        });

        final int currentPosition = position;
        viewHolder.delete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                confirmationWarning(currentPosition);
            }
        });

        viewHolder.bcBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                goToAnotherFragment(currentPosition, 2);

            }
        });

        viewHolder.bdChart.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {


            }
        });

        //set on click listener for each element
//        viewHolder.container.setOnClickListener(onClickListener(position));
    }


    private void goToAnotherFragment(int pos, final int fragmentNo){


        final ProgressDialog progressDialog = new ProgressDialog(activity,
                R.style.AppTheme);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Search User Story...");
        progressDialog.show();

        Sprint currentSprint = sprintList.get(pos);
        DataHolder.getInstance().setCurrentSprint(currentSprint);

        String URL = MSConstants.SPRINT_URL+"/"+currentSprint.getId();
        Log.d("USL:", URL);

        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, URL,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
//                        Toast.makeText(getActivity(),response,Toast.LENGTH_LONG).show();
                        Log.d("response", "Success");


                        if(response.has("data")) {
                            JSONArray bcList;
                            try {
                                JSONObject userData  = response.getJSONObject("data");
                                bcList = (JSONArray) userData.get("userStoryList");
                                Log.d("bcList", "Success"+bcList.length());
                                if (bcList != null && bcList.length() > 0) {
                                    if(DataHolder.getInstance().getLogger().getRole().getId() == 4){
                                        DataHolder.getInstance().setDevUSList(JSONObjectManager.parseBacklogData(bcList));
                                    }else {
                                        DataHolder.getInstance().setSprintUSList(JSONObjectManager.parseBacklogData(bcList));
                                    }
                                }

//                                JSONArray sprintArr = (JSONArray) userData.get("sprintList");
//                                Log.d("sprintArr", "Success"+sprintArr.length());
//                                if (sprintArr != null && sprintArr.length() > 0) {
//                                    DataHolder.getInstance().setSprintList(JSONObjectManager.parseSprintData(sprintArr));
//                                }

                            }catch (JSONException e) {
                                Log.d("JSONException", ""+e.toString());
//                            e.printStackTrace();
                            }

                        }
                        switchScreen(fragmentNo);
                        progressDialog.dismiss();


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(activity,error.toString(), Toast.LENGTH_LONG).show();
                        Log.d("Error", "Nope"+error.toString());
                        progressDialog.dismiss();
                    }
                }){


        };

        Log.d("stringRequest", ":" +stringRequest);

        requestQueue.add(stringRequest);
    }


    public void switchScreen(int pos){

        ((MainActivity)activity). replaceFragment(pos);
    }



    private void confirmationWarning(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        builder.setTitle("Confirm");
        builder.setMessage("Are you sure?");

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                // Do nothing but close the dialog
                removeAt(position);
                dialog.dismiss();
            }

        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do nothing
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }


    public void removeAt(int position) {
        sprintList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, sprintList.size());
    }


    @Override
    public int getItemCount() {
        return (null != sprintList ? sprintList.size() : 0);
    }


    /**
     * View holder to display each RecylerView item
     */
    protected class ViewHolder extends RecyclerView.ViewHolder {


        private ImageView imageView;
        private TextView sprintName;
        private TextView startDate;
        private TextView endDate;
        private TextView proSM;

        private View container;
        private ImageButton bdChart;
        private ImageButton bcBtn;
        private ImageButton edit;
        private ImageButton delete;

        public ViewHolder(View view) {
            super(view);
            imageView = (ImageView) view.findViewById(R.id.image);
            sprintName = (TextView) view.findViewById(R.id.sprint_name);
            startDate = (TextView) view.findViewById(R.id.start_date);
            endDate = (TextView) view.findViewById(R.id.end_date);
            proSM = (TextView) view.findViewById(R.id.pro_sm);

            container = view.findViewById(R.id.card_view);
            bdChart =(ImageButton)view.findViewById(R.id.chart_bd);
            bcBtn =(ImageButton)view.findViewById(R.id.back_log);
            edit =(ImageButton)view.findViewById(R.id.edit);
            delete =(ImageButton)view.findViewById(R.id.delete);
        }
    }
}