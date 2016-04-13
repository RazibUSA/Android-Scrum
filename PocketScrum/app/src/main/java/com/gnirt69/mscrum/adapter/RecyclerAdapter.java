package com.gnirt69.mscrum.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.test.AndroidTestRunner;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.gnirt69.mscrum.R;
import com.gnirt69.mscrum.model.User;

import java.util.List;

import static com.gnirt69.mscrum.adapter.RecyclerAdapter.ViewHolder.*;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private List<User> friends;
    private Activity activity;


    public RecyclerAdapter(Activity activity, List<User> friends) {
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
        viewHolder.name.setText("Name:"+friends.get(position).getFirstName() + " " + friends.get(position).getLastName());
//        ViewHolder.userIDView.setTest("ID:");
        viewHolder.emailView.setText("E-Mail:"+friends.get(position).getEmail());
        viewHolder.passView.setText("Password:"+friends.get(position).getPassword());

        int userType =(int)friends.get(position).getRole().getId();
        if (userType == 1) {
            viewHolder.imageView.setImageResource(R.drawable.sys);
        } else if(userType == 2) {
            viewHolder.imageView.setImageResource(R.drawable.po);
        }else if(userType == 3) {
            viewHolder.imageView.setImageResource(R.drawable.sm);
        }else if(userType == 4) {
            viewHolder.imageView.setImageResource(R.drawable.dev);
        }

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
        name.setText("Name:"+friends.get(position).getFirstName() + " " + friends.get(position).getLastName());
        job.setText("E-Mail:"+friends.get(position).getEmail());
//        if (friends.get(position).isGender()) {
//            genderIcon.setImageResource(R.drawable.po);
//        } else {
//            genderIcon.setImageResource(R.drawable.po);  //razib checnge the code
//        }

        int userType =(int)friends.get(position).getRole().getId();
        if (userType == 1) {
            genderIcon.setImageResource(R.drawable.sys);;
        } else if(userType == 2) {
            genderIcon.setImageResource(R.drawable.po);;
        }else if(userType == 3) {
            genderIcon.setImageResource(R.drawable.sm);;
        }else if(userType == 4) {
            genderIcon.setImageResource(R.drawable.dev);;
        }


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

        private final TextView userIDView;
        private ImageView imageView;
        private TextView name;
        private TextView userID;
        private TextView emailView;
        private TextView passView;
        private View container;
        private ImageButton edit;
        private ImageButton delete;

        public ViewHolder(View view) {
            super(view);
            imageView = (ImageView) view.findViewById(R.id.image);
            name = (TextView) view.findViewById(R.id.user_name);
            userIDView = (TextView) view.findViewById(R.id.user_id);
            emailView = (TextView) view.findViewById(R.id.user_email);
            passView = (TextView) view.findViewById(R.id.user_pass);
            container = view.findViewById(R.id.card_view);
            edit =(ImageButton)view.findViewById(R.id.edit);
            delete =(ImageButton)view.findViewById(R.id.delete);
        }
    }
}