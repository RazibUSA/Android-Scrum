package com.gnirt69.mscrum.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.gnirt69.mscrum.R;
import com.gnirt69.mscrum.model.Project;

import java.util.List;


public class RecyclerAdapterSprint extends RecyclerView.Adapter<RecyclerAdapterSprint.ViewHolder> {

    private List<Project> projectList;
    private Activity activity;


    public RecyclerAdapterSprint(Activity activity, List<Project> projects) {
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
        private ImageButton bdChart;
        private ImageButton assignSM;
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
            bdChart =(ImageButton)view.findViewById(R.id.chart_bd);
            assignSM =(ImageButton)view.findViewById(R.id.add_sm);
            edit =(ImageButton)view.findViewById(R.id.edit);
            delete =(ImageButton)view.findViewById(R.id.delete);
        }
    }
}