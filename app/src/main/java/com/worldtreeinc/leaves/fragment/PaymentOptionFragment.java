package com.worldtreeinc.leaves.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.SaveCallback;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;
import com.worldtreeinc.leaves.R;
import com.worldtreeinc.leaves.activity.EventDetailsActivity;
import com.worldtreeinc.leaves.helper.LeavesNotification;
import com.worldtreeinc.leaves.model.Event;
import com.worldtreeinc.leaves.model.PayPalConfirmation;
import com.worldtreeinc.leaves.model.User;
import com.worldtreeinc.leaves.utility.ContextProvider;
import com.worldtreeinc.leaves.utility.DialogBox;

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

    private ProgressDialog progressDialog;

    // PayPal Configuration
    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId(PayPalConfirmation.PAYPAL_CLIENT_ID);

    private Button paypalButton;
    private double amount;
    private String paymentName;
    private String eventId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            amount = getArguments().getDouble("amount");
            paymentName = getArguments().getString("paymentName");
            eventId = getArguments().getString("eventId");
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
        startProgressDialog();
        if (resultCode == Activity.RESULT_OK) {
            PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
            if (confirm != null) {
                try {
                    JSONObject responseObject = confirm.toJSONObject().getJSONObject("response");
                    String paymentID = responseObject.get("id").toString();

                    // confirm Payment
                    PayPalConfirmation confirmation = new PayPalConfirmation(paymentID, amount);
                    confirmation.confirmPayment(new PayPalConfirmation.ConfirmationCallback() {
                        @Override
                        public void onSuccess() {
                            setEventAsEntered();
                            LeavesNotification.subscribeBidderToEventChannel(eventId);
                        }

                        @Override
                        public void onFailure() {
                            Context context = getActivity();
                            new DialogBox().dialog(context, context.getString(R.string.payment_error), context.getString(R.string.payment_error_text));
                            stopProgressDialog();
                        }
                    });

                    // TODO: send 'confirm' to your server for verification.
                    // see https://developer.paypal.com/webapps/developer/docs/integration/mobile/verify-mobile-payment/
                    // for more details.
                } catch (JSONException e) {
                    Log.e("paymentExample", "an extremely unlikely failure occurred: ", e);
                    stopProgressDialog();
                }
            }
        }
        else if (resultCode == Activity.RESULT_CANCELED) {
            Log.i("paymentExample", "The user canceled.");
            stopProgressDialog();
        }
        else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
            Log.i("paymentExample", "An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
            stopProgressDialog();
        }
    }

    private void setEventAsEntered() {
        // SET PAYMENT LOGIC IN PARSE
        User.enterEvent(eventId, new SaveCallback() {
            @Override
            public void done(ParseException e) {
                Toast.makeText(getActivity(), "Event entered successfully", Toast.LENGTH_LONG).show();
                Event.getOne(eventId).incrementEntries();
                startPaidEvent();
                stopProgressDialog();
            }
        });

    }

    private void startPaidEvent() {
        Intent intent = new Intent(ContextProvider.getContext(), EventDetailsActivity.class);
        intent.putExtra("OBJECT_ID", eventId);
        intent.putExtra("IS_PLANNER", false);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
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

    private void startProgressDialog() {
        // Create a progressdialog
        progressDialog = new ProgressDialog(getActivity());
        // Set progressdialog message
        progressDialog.setMessage(getActivity().getString(R.string.event_list_progress_loading));
        progressDialog.setIndeterminate(false);
        // Show progressdialog
        progressDialog.show();
    }

    private void stopProgressDialog() {
        progressDialog.dismiss();
    }

}
