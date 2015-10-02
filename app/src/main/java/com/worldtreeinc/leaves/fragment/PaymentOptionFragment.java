package com.worldtreeinc.leaves.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.worldtreeinc.leaves.R;

import java.math.BigDecimal;

/**
 * PaymentOptionFragment to show the bidder about the payment options
 *
 * Start with mock environment.  When ready, switch to sandbox (ENVIRONMENT_SANDBOX)
 * or live (ENVIRONMENT_PRODUCTION)
 * ENVIRONMENT_NO_NETWORK
 */
public class PaymentOptionFragment extends Fragment implements View.OnClickListener {

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
