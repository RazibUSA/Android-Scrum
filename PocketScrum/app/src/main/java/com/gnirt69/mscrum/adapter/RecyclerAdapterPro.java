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
import com.gnirt69.mscrum.model.User;
import com.gnirt69.mscrum.utils.JSONObjectManager;
import com.gnirt69.mscrum.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class RecyclerAdapterPro extends RecyclerView.Adapter<RecyclerAdapterPro.ViewHolder> {

    private List<Project> projectList;
    private Activity activity;


    public RecyclerAdapterPro(Activity activity, List<Project> projects) {
        this.projectList = projects;
        this.activity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        //inflate your layout and pass it to view holder
        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.item_recycler_pro, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        //setting data to view holder elements
        viewHolder.proName.setText("Project Name:"+ projectList.get(position).getName());
        viewHolder.startDate.setText("Start Date: 02/10/2016");
        viewHolder.endDate.setText("End Date: 06/10/2016");
        viewHolder.proSM.setText("Assigned To:None");
        // Need work later razib
//        viewHolder.startDate.setText("Start Date:"+ projectList.get(position).getStartDate().toString());
//        viewHolder.endDate.setText("End Date:"+ projectList.get(position).getEndDate().toString());
//        viewHolder.proSM.setText("Scrum Master:"+projectList.get(position).getManagedBy().getFirstName());


        final int currentPosition = position;
        viewHolder. bdChartBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                /// button click event
                List<CharSequence> list = new ArrayList<CharSequence>();
                for (int i=0;i<20;i++){

                    list.add("test " + i);  // Add the item in the list
                }

                Utils.checkBoxDialog(list,activity);
            }
        });
        viewHolder. assignSMBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                /// button click event
                final Dialog dialog = new Dialog(activity);
                dialog.setTitle("Button test " );
                dialog.setCancelable(true);
                dialog.show();

            }
        });
        viewHolder.backlogBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                /// button click event
                getBacklogList(currentPosition);

            }
        });

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


        viewHolder.delete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                confirmationWarning(currentPosition);
            }
        });



        //set on click listener for each element
//        viewHolder.container.setOnClickListener(onClickListener(position));
    }

    private void confirmationWarning(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        builder.setTitle("Confirm");
        builder.setMessage("Are you sure?");

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                // Do nothing but close the dialog
                removeAt(position);
//                swtichToBacklog();
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


    private void getBacklogList(int pos){


        final ProgressDialog progressDialog = new ProgressDialog(activity,
                R.style.AppTheme);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Search User Story...");
        progressDialog.show();

        Project currentProject = projectList.get(pos);
        DataHolder.getInstance().setCurrentProject(currentProject);

        String URL = MSConstants.PROJECT_URL+"/"+currentProject.getId();
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
                                bcList = (JSONArray) userData.get("backlogList");
                                Log.d("bcList", "Success"+bcList.length());
                                if (bcList != null && bcList.length() > 0) {
                                    DataHolder.getInstance().setUsList(JSONObjectManager.parseBacklogData(bcList));
                                }
                            }catch (JSONException e) {
                                Log.d("JSONException", ""+e.toString());
//                            e.printStackTrace();
                            }

                        }
                        switchToBacklog();
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




    public void switchToBacklog(){

        ((MainActivity)activity). replaceFragment(2);
    }

    public void removeAt(int position) {
        projectList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, projectList.size());
    }


    @Override
    public int getItemCount() {
        return (null != projectList ? projectList.size() : 0);
    }


    /**
     * View holder to display each RecylerView item
     */
    protected class ViewHolder extends RecyclerView.ViewHolder {


        private ImageView imageView;
        private TextView proName;
        private TextView startDate;
        private TextView endDate;
        private TextView proSM;

        private View container;
        private ImageButton bdChartBtn;
        private ImageButton assignSMBtn;
        private ImageButton backlogBtn;
        private ImageButton edit;
        private ImageButton delete;

        public ViewHolder(View view) {
            super(view);
            imageView = (ImageView) view.findViewById(R.id.image);
            proName = (TextView) view.findViewById(R.id.pro_name);
            startDate = (TextView) view.findViewById(R.id.start_date);
            endDate = (TextView) view.findViewById(R.id.end_date);
            proSM = (TextView) view.findViewById(R.id.pro_sm);

            container = view.findViewById(R.id.card_view);
            backlogBtn =(ImageButton)view.findViewById(R.id.back_log);
            bdChartBtn =(ImageButton)view.findViewById(R.id.chart_bd);
            assignSMBtn =(ImageButton)view.findViewById(R.id.add_sm);
            edit =(ImageButton)view.findViewById(R.id.edit);
            delete =(ImageButton)view.findViewById(R.id.delete);
        }
    }
}