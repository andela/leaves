package com.worldtreeinc.leaves;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.Toast;

import com.parse.DeleteCallback;
import com.parse.ParseException;

import java.util.List;

/**
 * Created by tunde on 8/11/15.
 */
public class Dialog {

    public Dialog() {}
    private List<Event> userEventList = null;

    interface CallBack {
        void onFinished();
    }

    public void dialog(Context context, String title, String message,final CallBack callback) {
//        final Activity eventActivity = activity;
        // build up the dialog
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        // close the form and return to the dashboard
                        callback.onFinished();
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