package com.worldtreeinc.leaves;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.TextView;

import com.worldtreeinc.leaves.activity.PaymentOptionActivity;

/**
 * Created by kamiye on 10/2/15.
 */
public class PaymentOptionActivityTest extends ActivityInstrumentationTestCase2<PaymentOptionActivity> {

    private PaymentOptionActivity activity;
    private TextView payment_name_title;
    private TextView payment_name;
    private TextView payment_amount_title;
    private TextView payment_amount;
    private TextView payment_activity_title;

    public PaymentOptionActivityTest() {
        super(PaymentOptionActivity.class);
    }

    public void setUp() throws Exception {
        super.setUp();

        Intent intent = new Intent();
        activity = getActivity();

        // GET ACTIVITY VIEWS
        payment_activity_title = (TextView) activity.findViewById(R.id.payment_title);
        payment_amount_title = (TextView) activity.findViewById(R.id.payment_amount_title);
        payment_name_title = (TextView) activity.findViewById(R.id.payment_name_title);
        payment_amount = (TextView) activity.findViewById(R.id.payment_amount);
        payment_name = (TextView) activity.findViewById(R.id.payment_name);

    }
}
