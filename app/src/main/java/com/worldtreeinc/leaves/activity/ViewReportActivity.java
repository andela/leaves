package com.worldtreeinc.leaves.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.worldtreeinc.leaves.R;
import com.worldtreeinc.leaves.helper.MyToolbar;

public class ViewReportActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_report);
        MyToolbar.setToolbar(this);
        setTitle("Report");
    }
}
