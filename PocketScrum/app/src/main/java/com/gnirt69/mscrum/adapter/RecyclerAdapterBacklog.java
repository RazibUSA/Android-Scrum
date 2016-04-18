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
import com.gnirt69.mscrum.model.User;
import com.gnirt69.mscrum.model.UserStory;

import java.util.List;


public class RecyclerAdapterBacklog extends RecyclerView.Adapter<RecyclerAdapterBacklog.ViewHolder> {

    private List<UserStory> friends;
    private Activity activity;


    public RecyclerAdapterBacklog(Activity activity, List<UserStory> friends) {
        this.friends = friends;
        this.activity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        //inflate your layout and pass it to view holder
        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.item_recycler, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        //setting data to view holder elements
        viewHolder.titleView.setText("Name:"+friends.get(position).getTitle());
        viewHolder.estimateView.setText("Estimate:"+friends.get(position).getEstimation());
        viewHolder.assignView.setText("Assign: None");
        viewHolder.SprintView.setText("Sprint: Not Yet");



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
        friends.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, friends.size());
    }


    private void setDataToView(TextView name, TextView job, ImageView genderIcon, int position) {
        name.setText("Name:"+friends.get(position).getTitle());
//        job.setText("E-Mail:"+friends.get(position).getEmail());



    }

    @Override
    public int getItemCount() {
        return (null != friends ? friends.size() : 0);
    }

    private View.OnClickListener onClickListener(final int position) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(activity);
                dialog.setContentView(R.layout.item_recycler);
                dialog.setTitle("Position " + position);
                dialog.setCancelable(true); // dismiss when touching outside Dialog

                // set the custom dialog components - texts and image
                TextView name = (TextView) dialog.findViewById(R.id.user_name);
                TextView job = (TextView) dialog.findViewById(R.id.user_id);
                ImageView icon = (ImageView) dialog.findViewById(R.id.image);

                setDataToView(name, job, icon, position);

                dialog.show();
            }
        };
    }

    /**
     * View holder to display each RecylerView item
     */
    protected class ViewHolder extends RecyclerView.ViewHolder {


        private ImageView imageView;
        private TextView titleView;
        private TextView estimateView;
        private TextView assignView;
        private TextView SprintView;
        private View container;
        private ImageButton edit;
        private ImageButton delete;

        public ViewHolder(View view) {
            super(view);
            imageView = (ImageView) view.findViewById(R.id.image);
            titleView = (TextView) view.findViewById(R.id.title_name);
            estimateView = (TextView) view.findViewById(R.id.esti_date);
            assignView = (TextView) view.findViewById(R.id.assign_to);
            SprintView = (TextView) view.findViewById(R.id.sprint_to);
            container = view.findViewById(R.id.card_view);
            edit =(ImageButton)view.findViewById(R.id.edit);
            delete =(ImageButton)view.findViewById(R.id.delete);
        }
    }
}