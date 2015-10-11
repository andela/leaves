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
import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.worldtreeinc.leaves.appConfig.ParseApplication;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kamiye on 10/9/15.
 */
@ParseClassName("Payments")
public class Payment extends ParseObject {
/*
    private static final String REST_API_URL = "https://api.sandbox.paypal.com/v1/payments/payment/";
    private static final String TOKEN_REQUEST_URL = "https://api.sandbox.paypal.com/v1/oauth2/token";
    private static final String PAYPAL_CLIENT_ID = "ATMzxMuhDbJj2r7A2aNNyZSp7irs0opd1kjMVYicHQJ1U_yiH86ozVMyCrvRpFuocOUz3MVncxnZMtrG";
    private static final String PAYPAL_SECRET = "ENnNAijN7HwLjGP8VEj1pZ9u9hjD74Oj-9uwZ4TLDuYp6wjx5Ue3ZXl9Uv5xf1gF_yF7de8-fd3_IQZe";
    private static String base64 = "";
    private static RequestQueue requestQueue;
    private static String token = "";*/

    /*public static void setRequestQueue(Context context) {
        requestQueue = Volley.newRequestQueue(context);
    }

    private static void getToken() {
        try {
            base64 = Base64.encodeToString((PAYPAL_CLIENT_ID + ":" + PAYPAL_SECRET).getBytes("UTF-8"), Base64.URL_SAFE | Base64.NO_WRAP);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        StringRequest tokenRequest = new StringRequest(Request.Method.POST,
                TOKEN_REQUEST_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                generateToken(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error request", error.toString());
                requestQueue.stop();
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                params.put("Authorization", "Basic " + base64);

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
    }*/

    /*private static void generateToken(String string) {
        try {
            token = new JSONObject(string).get("access_token").toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.i("TTOOKKEENN after set", token);
    }*/

    public static boolean validId(String paymentId) {
        List<Payment> payment = null;
        ParseQuery<Payment> query = ParseQuery.getQuery(Payment.class);
        query.whereEqualTo("paymentId", paymentId);
        try {
            payment = query.find();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return (payment == null || payment.size() == 0);
    }

    /*public static boolean confirmPayPalPayment(String paymentId) {
        getToken();
        if (validId(paymentId)) {
            // check payment with paypal rest api
            JsonObjectRequest paymentConfirmationRequest = new JsonObjectRequest(Request.Method.GET,
                    REST_API_URL + paymentId, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.i("ConfirmationResponse", response.toString());
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("Error request", error.toString());
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("Accept", "application/json");
                    Log.i("TTOOKKEENN before used", token);
                    params.put("Authorization", "Bearer " + token);
                    Log.i("TTOOKKEENN after used", token);
                    return params;
                }
            };

            requestQueue.add(paymentConfirmationRequest);
        }
        return false;
    }
*/

    public static void confirmPayPalPayment(String paymentId) {

    }
}
