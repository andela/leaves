package com.worldtreeinc.leaves.model;

import android.content.Context;
import android.util.Base64;
import android.util.Log;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.worldtreeinc.leaves.utility.ContextProvider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kamiye on 10/9/15.
 */
public class PayPalConfirmation {

    // Fixed Constants
    public static final String REST_API_URL = "https://api.sandbox.paypal.com/v1/payments/payment/";
    public static final String TOKEN_REQUEST_URL = "https://api.sandbox.paypal.com/v1/oauth2/token";
    public static final String PAYPAL_CLIENT_ID = "ATMzxMuhDbJj2r7A2aNNyZSp7irs0opd1kjMVYicHQJ1U_yiH86ozVMyCrvRpFuocOUz3MVncxnZMtrG";
    public static final String PAYPAL_SECRET = "ENnNAijN7HwLjGP8VEj1pZ9u9hjD74Oj-9uwZ4TLDuYp6wjx5Ue3ZXl9Uv5xf1gF_yF7de8-fd3_IQZe";

    // Confirmation properties
    private String base64;
    private RequestQueue requestQueue;
    private String token;

    // expected amount and currency
    private double amount;
    private String paymentId;

    private Context context = ContextProvider.getContext();

    public PayPalConfirmation(String paymentId, double amount) {
        requestQueue = Volley.newRequestQueue(context);
        this.amount = amount;
        this.paymentId = paymentId;
    }

    public void confirmPayment(final PayPalConfirmation.ConfirmationCallback callback) {
        generateAccessResponse();
        if (Payment.validId(paymentId)) {
            // check payment with paypal rest api
            JsonObjectRequest paymentConfirmationRequest = new JsonObjectRequest(Request.Method.GET,
                    REST_API_URL + paymentId, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    verifyPayment(response, callback);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("Error request", error.toString());
                    // set error message to the user
                    callback.onFailure();
                    requestQueue.stop();
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("Accept", "application/json");
                    params.put("Authorization", "Bearer " + token);
                    return params;
                }
            };

            requestQueue.add(paymentConfirmationRequest);
        }
    }

    private String getBase64Authorization() {
        try {
            base64 = Base64.encodeToString((PAYPAL_CLIENT_ID + ":" + PAYPAL_SECRET).getBytes("UTF-8"), Base64.URL_SAFE | Base64.NO_WRAP);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return "Basic "+base64;
    }

    private void generateAccessResponse() {
        StringRequest tokenRequest = new StringRequest(Request.Method.POST,
                TOKEN_REQUEST_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                generateToken(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                requestQueue.stop();
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                params.put("Authorization", getBase64Authorization());

                return params;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("grant_type", "client_credentials");
                return params;
            }
        };

        requestQueue.add(tokenRequest);
    }

    private void generateToken(String string) {
        try {
            token = new JSONObject(string).get("access_token").toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void verifyPayment(JSONObject jsonObject, PayPalConfirmation.ConfirmationCallback callback) {
        try {
            JSONArray array = jsonObject.getJSONArray("transactions");
            JSONObject transactionObject = array.getJSONObject(0).getJSONObject("amount");

            String responseId = jsonObject.get("id").toString();
            String currency = transactionObject.getString("currency");
            String amount = transactionObject.getString("total");

            if (currency.equals("USD") && this.amount == Double.valueOf(amount) && paymentId.equals(responseId)) {
                callback.onSuccess();
            }
            else
                callback.onFailure();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public interface ConfirmationCallback {
        void onSuccess();

        void onFailure();
    }

}
