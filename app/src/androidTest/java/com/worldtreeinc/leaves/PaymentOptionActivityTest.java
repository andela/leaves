package com.worldtreeinc.leaves;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.TextView;

import com.parse.ParseFile;
import com.worldtreeinc.leaves.activity.PaymentOptionActivity;
import com.worldtreeinc.leaves.fragment.PaymentOptionFragment;
import com.worldtreeinc.leaves.model.Event;
import com.worldtreeinc.leaves.utility.ParseProxyObject;

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
    private Fragment fragment;
    private Event event;
    private Intent intent;
    private Bundle bundle;
    private ParseProxyObject proxyObject;

    public PaymentOptionActivityTest() {
        super(PaymentOptionActivity.class);
    }

    // SET UP METHODS
    private void setUpEventObject() {
        event = new Event();
        event.setName("TestEvent");
        event.setEntryFee(100);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();

        // create new event data to test with
        setUpEventObject();

        intent = new Intent();
        proxyObject = new ParseProxyObject(event);
        bundle = new Bundle();
        bundle.putSerializable("event", proxyObject);

        intent.putExtras(bundle);

        setActivityIntent(intent);
        activity = getActivity();

        // GET ACTIVITY VIEWS
        payment_activity_title = (TextView) activity.findViewById(R.id.payment_title);
        payment_amount_title = (TextView) activity.findViewById(R.id.payment_amount_title);
        payment_name_title = (TextView) activity.findViewById(R.id.payment_name_title);
        payment_amount = (TextView) activity.findViewById(R.id.payment_amount);
        payment_name = (TextView) activity.findViewById(R.id.payment_name);

        // other components
        fragment = (Fragment) getActivity().getSupportFragmentManager().getFragments();

    }

    // test preconditions for all view items to be tested
    public void testPreconditions() {
        assertNotNull("Activity is null", payment_activity_title);
        assertNotNull("Banner ImageView is null", payment_amount_title);
        assertNotNull("Location TextView is null", payment_name_title);
        assertNotNull("Category TextView is null", payment_amount);
        assertNotNull("Date TextView is null", payment_name);
    }

    public void testEventAmount() {
        String expected = "$100.0";
        String actual = payment_amount.getText().toString();

        assertEquals("Amount setting assertion error", expected, actual);
    }

    public void testEventName() {
        String expected = "TestEvent";
        String actual = payment_name.getText().toString();

        assertEquals("Name setting assertion error", expected, actual);
    }

    public void testFragment_notNull() {
        PaymentOptionFragment paymentFragment = (PaymentOptionFragment) activity.getFragmentManager()
                .findFragmentById(R.id.payment_option_container);
        assertNotNull("PaymentOptionsActivity fragment missing", paymentFragment);

        // Test fragment visibility
        assertTrue("PaymentOptionFragment not visible", paymentFragment.isVisible());
    }
}
