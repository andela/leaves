package com.worldtreeinc.leaves.helper;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.SaveCallback;
import com.worldtreeinc.leaves.R;
import com.worldtreeinc.leaves.model.EventItem;
import com.worldtreeinc.leaves.model.User;

import java.text.NumberFormat;
import java.util.List;

/**
 * Created by andela on 9/23/15.
 */

public class ItemBidHandler extends Activity {
    private Activity activity;
    private List<EventItem> items;
    private EventItem item;
    private int currentPosition;

    public ItemBidHandler(Activity activity, List<EventItem> items, int position) {
        this.activity = activity;
        this.items = items;
        currentPosition = position;
        item = items.get(position);
    }

    public void bidItem() {
        final LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.bid_layout, null);
        setDialogElement(view);
    }

    private void setDialogDetails(TextView minBid, TextView itemName, View view, EditText bidAmount) {
        if (item.getIncrement() == null) {
            item.setIncrement(0);
        }
        minBid.setText(NumberFormat.getCurrencyInstance().format((Integer)item.getNewBid() + (Integer)item.getIncrement()));
        itemName.setText(item.getName());
        showDialog(view, bidAmount);
    }

    private void setDialogElement(View view) {
        TextView minBid = (TextView) view.findViewById(R.id.min_bid);
        TextView itemName = (TextView) view.findViewById(R.id.item_name);
        EditText bidAmount = (EditText) view.findViewById(R.id.enter_amount);
        setDialogDetails(minBid, itemName, view, bidAmount);
    }

    private void showDialog(View view, final EditText bidAmount) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(activity).setView(view);
        builder.setPositiveButton("Bid", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                if(!bidAmount.getText().toString().matches("")){
                    double amount = Double.parseDouble(bidAmount.getText().toString());
                    performBid(amount, dialog);
                } else {
                    Toast toast = Toast.makeText(activity, "Enter a number", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        builder.show();
    }

    private void performBid(final double amount, final DialogInterface dialog) {
        item = items.get(currentPosition);
        item.refreshItem(new EventItem.ItemRefreshCallBack() {
            @Override
            public void onRefresh(EventItem item) {
                double get_bid = Double.parseDouble(item.getNewBid().toString());
                double incrementValue = Double.parseDouble(item.getIncrement().toString());
                double bid = get_bid + incrementValue;

                int duration = Toast.LENGTH_SHORT;
                String text = null;
                if (amount > bid) {
                    item = items.get(currentPosition);
                    item.setPreviousBid(get_bid);
                    item.setNewBid(amount);
                    item.saveInBackground();
                    text = "You have successfully place your Bid";
                    LeavesNotification.sendItemBidNotification(amount, item);

                    User.setItemsBiddedOn(item.getObjectId(), new SaveCallback() {
                        @Override
                        public void done(ParseException e) {

                        }
                    });

                } else if (amount <= bid) {
                    text = "Your bid must be greater than minimum bid";
                }
                Toast toast = Toast.makeText(activity, text, duration);
                toast.show();
                dialog.cancel();
            }
        });

       refreshActivity();
    }

    private void refreshActivity(){
        Intent intent = activity.getIntent();
        activity.finish();
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        activity.startActivity(intent);
    }

}