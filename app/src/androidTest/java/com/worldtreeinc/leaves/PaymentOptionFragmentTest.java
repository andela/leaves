package com.worldtreeinc.leaves;

import android.app.Instrumentation;
import android.content.Intent;
import android.os.Bundle;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;

import com.paypal.android.sdk.payments.PaymentActivity;
import com.worldtreeinc.leaves.activity.PaymentOptionActivity;
import com.worldtreeinc.leaves.fragment.PaymentOptionFragment;
import com.worldtreeinc.leaves.model.Event;
import com.worldtreeinc.leaves.utility.ParseProxyObject;

/**
 * Created by kamiye on 10/5/15.
 */
public class PaymentOptionFragmentTest extends ActivityInstrumentationTestCase2<PaymentOptionActivity> {

    private PaymentOptionFragment paymentFragment = new PaymentOptionFragment();

    // payment buttons
    private Button payPalButton;
    private Button squareButton;
    private Button stripeButton;
    private PaymentOptionActivity activity;
    private Event event;
    private Intent intent;
    private Bundle bundle;
    private ParseProxyObject proxyObject;

    public PaymentOptionFragmentTest() {
        super(PaymentOptionActivity.class);
    }

    // SET UP METHODS
    private void setUpEventObject() {
        event = new Event();
        event.setName("TestEvent");
        event.setEntryFee(100);
    }

    public void setUp() {
        // create new event data to test with
        setUpEventObject();

        intent = new Intent();
        proxyObject = new ParseProxyObject(event);
        bundle = new Bundle();
        bundle.putSerializable("event", proxyObject);

        intent.putExtras(bundle);

        setActivityIntent(intent);
        activity = getActivity();

        // define fragment and add it to the activity
        Bundle bundle = new Bundle();
        bundle.putString("eventId", null);
        bundle.putString("itemId", null);

        activity.getFragmentManager()
                .beginTransaction()
                .add(R.id.payment_option_container, paymentFragment)
                .commit();
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                activity.getFragmentManager().executePendingTransactions();
            }
        });
        payPalButton = (Button) activity.findViewById(R.id.paypal_payment_button);
        squareButton = (Button) activity.findViewById(R.id.square_payment_button);
        stripeButton = (Button) activity.findViewById(R.id.stripe_payment_button);
    }

    public void testPreconditions() {
        assertNotNull(activity);
        assertNotNull(paymentFragment);
    }

    public void testPaymentButtons_texts() {
        String expected = "Pay With PayPal";
        String actual = payPalButton.getText().toString();

        assertEquals("PayPal button texts assertion error", expected, actual);

        expected = "Pay With Stripe";
        actual = stripeButton.getText().toString();

        assertEquals("Stripe button texts assertion error", expected, actual);

        expected = "Pay With Square";
        actual = squareButton.getText().toString();

        assertEquals("Square button texts assertion error", expected, actual);
    }

    public void testPayPalButton_clickAction() {
    // register next activity that need to be monitored.
        Instrumentation.ActivityMonitor activityMonitor = getInstrumentation().addMonitor(PaymentActivity.class.getName(), null, false);

        // open current activity.
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // click button and open next activity.
                payPalButton.performClick();
            }
        });

        PaymentActivity newActivity = (PaymentActivity) getInstrumentation().waitForMonitor(activityMonitor);
        assertNotNull(newActivity);
        newActivity.finish();
    }

}
