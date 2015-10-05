package com.worldtreeinc.leaves.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;
import com.worldtreeinc.leaves.model.Event;
import com.worldtreeinc.leaves.R;
import com.worldtreeinc.leaves.fragment.PaymentOptionFragment;
import com.worldtreeinc.leaves.utility.ParseProxyObject;

import org.json.JSONException;


public class PaymentOptionActivity extends AppCompatActivity {


    private double amount;
    private Event event;
    private ParseProxyObject proxy;
    private TextView paymentName;
    private TextView paymentAmount;
    private String eventName;
    private PaymentOptionFragment paymentOptionFragment;
    private Bundle bundle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        proxy = (ParseProxyObject) getIntent().getSerializableExtra("event");
        event = proxy.getParseObject(Event.class);
        init();
    }

    private void init() {
        paymentName = (TextView) findViewById(R.id.payment_name);
        paymentAmount = (TextView) findViewById(R.id.payment_amount);

        amount = Double.parseDouble(String.valueOf(event.getEntryFee()));
        eventName = event.getField("eventName");
        String paymentString = getResources().getString(R.string.payment_amount_text)+amount;

        paymentName.setText(eventName);
        paymentAmount.setText(paymentString);

        // launch fragment with properties
        paymentOptionFragment = new PaymentOptionFragment();
        bundle = new Bundle();
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

    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
            if (confirm != null) {
                try {
                    Log.i("paymentExample", confirm.toJSONObject().toString(4));
                    // TODO: send 'confirm' to your server for verification.
                    // see https://developer.paypal.com/webapps/developer/docs/integration/mobile/verify-mobile-payment/
                    // for more details.
                } catch (JSONException e) {
                    Log.e("paymentExample", "an extremely unlikely failure occurred: ", e);
                }
            }
        }
        else if (resultCode == Activity.RESULT_CANCELED) {
            Log.i("paymentExample", "The user canceled.");
        }
        else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
            Log.i("paymentExample", "An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
        }
    }
}
