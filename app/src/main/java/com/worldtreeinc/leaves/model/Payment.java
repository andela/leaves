package com.worldtreeinc.leaves.model;

import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kamiye on 10/9/15.
 */
@ParseClassName("Payments")
public class Payment extends ParseObject {


    public static boolean isValidId(String paymentId) {
        List<Payment> payment = new ArrayList<>();
        ParseQuery<Payment> query = ParseQuery.getQuery(Payment.class);
        query.whereEqualTo("paymentId", paymentId);
        try {
            payment = query.find();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return (payment.size() == 0);
    }

}