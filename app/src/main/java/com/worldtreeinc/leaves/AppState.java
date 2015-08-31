package com.worldtreeinc.leaves;

import android.app.Activity;
import android.content.Intent;

/**
 * Created by andela on 8/28/15.
 */
public class AppState {

    public static void minimize(Activity activity) {
        Intent minimize = new Intent(Intent.ACTION_MAIN);
        minimize.addCategory(Intent.CATEGORY_HOME);
        minimize.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(minimize);
    }
}
