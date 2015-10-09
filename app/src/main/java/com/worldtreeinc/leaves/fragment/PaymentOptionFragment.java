package com.worldtreeinc.leaves.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;
import com.worldtreeinc.leaves.R;
import com.worldtreeinc.leaves.model.PayPalConfirmation;
import com.worldtreeinc.leaves.model.Payment;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;

/**
 * PaymentOptionFragment to show the bidder about the payment options
 *
 * Start with mock environment.  When ready, switch to sandbox (ENVIRONMENT_SANDBOX)
 * or live (ENVIRONMENT_PRODUCTION)
 * ENVIRONMENT_NO_NETWORK
 */
public class PaymentOptionFragment extends Fragment implements View.OnClickListener {

    private Payment payment;

    // PayPal Configuration
    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId("ATMzxMuhDbJj2r7A2aNNyZSp7irs0opd1kjMVYicHQJ1U_yiH86ozVMyCrvRpFuocOUz3MVncxnZMtrG");

    private Button paypalButton;
    private double amount;
    private String paymentName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            amount = getArguments().getDouble("amount");
            paymentName = getArguments().getString("paymentName");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.payment_options, container, false);

        paypalButton = (Button) view.findViewById(R.id.paypal_payment_button);
        paypalButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.paypal_payment_button:
                payWithPayPal();
                break;
        }
    }

    @Override
    public void onDestroy() {
        getActivity().stopService(new Intent(getActivity(), PayPalService.class));
        super.onDestroy();
    }

    @Override
    public void onActivityResult (int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
            if (confirm != null) {
                try {
                    JSONObject responseObject = confirm.toJSONObject().getJSONObject("response");
                    String paymentID = responseObject.get("id").toString();
                    Log.i("paymentID", responseObject.get("id").toString());

                    // confirm Payment
                    PayPalConfirmation confirmation = new PayPalConfirmation(getActivity(), paymentID, amount);
                    confirmation.confirmPayment(new PayPalConfirmation.ConfirmationCallback() {
                        @Override
                        public void done() {
                            Toast.makeText(getActivity(), "Successful Payment CallBack", Toast.LENGTH_LONG).show();
                        }
                    });

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

    private void payWithPayPal() {
        PayPalPayment payment = new PayPalPayment(new BigDecimal(amount), "USD", paymentName,
                PayPalPayment.PAYMENT_INTENT_SALE);

        Intent intent = new Intent(getActivity(), PaymentActivity.class);

        // send the same configuration for restart resiliency
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);

        startActivityForResult(intent, 0);
    }

}
