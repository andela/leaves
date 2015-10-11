package com.worldtreeinc.leaves.utility;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import com.worldtreeinc.leaves.activity.PlannerEventActivity;

/**
 * Created by tunde on 8/11/15.
 */
public class Dialog {

    public Dialog() {}

    public interface CallBack {
        void onFinished();
    }

    public void dialog(Context context, String title, String message,final CallBack... callbacks) {
//        final Activity eventActivity = activity;
        // build up the dialog
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        // close the form and return to the dashboard
                        if (callbacks.length > 0)
                            callbacks[0].onFinished();
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