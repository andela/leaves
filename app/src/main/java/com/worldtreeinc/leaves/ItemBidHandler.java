package com.worldtreeinc.leaves;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParsePush;
import com.parse.ParseUser;

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


    private void setDialogDetails(final ImageView itemImage, TextView minBid, TextView itemName, View view, EditText bidAmount) {
        minBid.setText(NumberFormat.getCurrencyInstance().format(item.getNewBid()));
        itemName.setText(item.getName());
        item.getImage().getDataInBackground(new GetDataCallback() {
            @Override
            public void done(byte[] bytes, ParseException e) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                itemImage.setImageBitmap(bitmap);
            }
        });
        showDialog(view, bidAmount);
    }

    private void setDialogElement(View view) {
        ImageView itemImage = (ImageView) view.findViewById(R.id.item_image);
        TextView minBid = (TextView) view.findViewById(R.id.min_bid);
        TextView itemName = (TextView) view.findViewById(R.id.item_name);
        EditText bidAmount = (EditText) view.findViewById(R.id.enter_amount);
        setDialogDetails(itemImage, minBid, itemName, view, bidAmount);
    }

    private void showDialog(View view, final EditText bidAmount) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(activity).setView(view);
        builder.setPositiveButton("Bid", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                double amount = Double.parseDouble(bidAmount.getText().toString());
                if (performBid(amount)) {
                    dialog.cancel();
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

    private boolean performBid(double amount) {
        double bid = Double.parseDouble(item.getNewBid().toString());
        boolean isBidded = false;
        int duration = Toast.LENGTH_SHORT;
        String text = null;
        if (amount > bid) {
            isBidded = true;
            item = items.get(currentPosition);
            item.setPreviousBid(bid);
            item.setNewBid(amount);
            item.saveInBackground();
            ParsePush.subscribeInBackground(item.getName() + "-" + item.getObjectId());
            String message = ParseUser.getCurrentUser().getUsername() +
                    " placed a bid of " + NumberFormat.getCurrencyInstance().format(amount) + " on " + item.getName();
            ParsePush push = new ParsePush();
            push.setChannel(item.getName() + "-" + item.getObjectId());
            push.setMessage(message);
            push.sendInBackground();
            text = "You have successfully place your Bid";
        } else if (amount <= bid) {
            text = "Your bid must be greater than minimum bid";
        }
        Toast toast = Toast.makeText(activity, text, duration);
        toast.show();
        return isBidded;
    }
}
