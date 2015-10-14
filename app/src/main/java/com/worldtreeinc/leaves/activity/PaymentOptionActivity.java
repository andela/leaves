package com.worldtreeinc.leaves.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.paypal.android.sdk.payments.PayPalService;
import com.worldtreeinc.leaves.R;
import com.worldtreeinc.leaves.fragment.PaymentOptionFragment;
import com.worldtreeinc.leaves.model.Event;
import com.worldtreeinc.leaves.utility.ParseProxyObject;


public class PaymentOptionActivity extends AppCompatActivity {

    private Event event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        ParseProxyObject proxy = (ParseProxyObject) getIntent().getSerializableExtra("event");
        event = proxy.getParseObject(Event.class);
        init();
    }

    private void init() {
        TextView paymentName = (TextView) findViewById(R.id.payment_name);
        TextView paymentAmount = (TextView) findViewById(R.id.payment_amount);

        double amount = Double.parseDouble(String.valueOf(event.getEntryFee()));
        String eventName = event.getField("eventName");
        String paymentString = getResources().getString(R.string.payment_amount_text)+ amount;

        paymentName.setText(eventName);
        paymentAmount.setText(paymentString);

        // launch fragment with properties
        String eventId = event.getObjectId();
        PaymentOptionFragment paymentOptionFragment = new PaymentOptionFragment();
        Bundle bundle = new Bundle();
        bundle.putString("eventId", eventId);
        bundle.putString("paymentName", eventName);
        bundle.putDouble("amount", amount);
        paymentOptionFragment.setArguments(bundle);

        getFragmentManager()
                .beginTransaction()
                .add(R.id.payment_option_container, paymentOptionFragment)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_payment, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy() {
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }
}
