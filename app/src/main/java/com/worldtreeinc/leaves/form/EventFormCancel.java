package com.worldtreeinc.leaves.form;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;

import com.worldtreeinc.leaves.activity.PlannerEventActivity;

/**
 * Created by tunde on 8/11/15.
 */
public class EventFormCancel {

    public EventFormCancel() {}

    public void dialog(Activity activity) {
        final Activity eventActivity = activity;
        // build up the dialog
        new AlertDialog.Builder(eventActivity)
                .setTitle("Cancel Event")
                .setMessage("Are you sure you want to cancel event? You will lose all data entered.")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        // close the form and return to the dashboard
                        backToEventList(eventActivity);
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    public void backToEventList(Activity activity) {
        Intent intent = new Intent(activity, PlannerEventActivity.class);
        activity.startActivity(intent);
        activity.finish();
    }
}
