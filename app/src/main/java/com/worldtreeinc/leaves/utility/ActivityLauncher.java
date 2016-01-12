package com.worldtreeinc.leaves.utility;

import android.content.Context;
import android.content.Intent;

/**
 * Created by Semiu on 06/01/2016.
 */
public class ActivityLauncher {

    public static void runIntent(Context context, Class<?> activityClass) {
        Intent intent = new Intent(context, activityClass);
        context.startActivity(intent);
    }
}
