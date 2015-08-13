package com.worldtreeinc.leaves;

/**
 * Created by kamiye on 7/27/15.
 */

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;


public class ParseItemsAdapter extends ParseQueryAdapter {

    /*
    * here we must override the constructor for ParseQueryAdapter
	*/
    // NOTE THAT USER ID IN THE CONSTRUCTOR SHOULD BE EVENTID
    public ParseItemsAdapter(Activity context, final Event eventId) {

        // Use the QueryFactory to construct a PQA that will only show
        // Todos marked as high-pri
        super(context, new ParseQueryAdapter.QueryFactory<ParseObject>() {
            public ParseQuery create() {
                ParseQuery query = ParseQuery.getQuery("Items");
                query.whereEqualTo("eventId", eventId);
                //query.orderByDescending("eventDate"); // order item by preferable order
                return query;
            }
        });

    }

    /*
     * Create setImage method to handle individual items image setting
     */
    public void setImage(final com.worldtreeinc.leaves.CircularImageView imageView, ParseObject object) {

        ParseFile eventBanner = (ParseFile) object.get("eventBanner");
        eventBanner.getDataInBackground(new GetDataCallback() {
            public void done(byte[] data, ParseException e) {
                // check change default image for the item only if no Exception was thrown
                if (e == null) {
                    // data has the bytes for the resume
                    // set the image file
                    Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
                    Bitmap songImage = Bitmap.createScaledBitmap(bmp, 80, 80, true);
                    imageView.setImageBitmap(songImage);
                }
            }
        });
    }

    @Override
    /*
	 * we are overriding the getView method here - this is what defines how each
	 * list item will look.
	 */
    public View getItemView(ParseObject object, View convertView, ViewGroup parent) {

        // assign the view we are converting to a local variable
        View v = convertView;

        // first check to see if the view is null. if so, we have to inflate it.
        // to inflate it basically means to render, or show, the view.
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.event_details_items, null);
        }

		/*
		 * Iterate through the list of objects and set views depending on
		 */

        if (object!= null) {

            // This is how you obtain a reference to the TextViews.
            // These TextViews are created in the XML files we defined.

            com.worldtreeinc.leaves.CircularImageView itemImage = (com.worldtreeinc.leaves.CircularImageView) v.findViewById(R.id.ed_item_image);
            TextView itemName = (TextView) v.findViewById(R.id.ed_item_name);
            TextView itemDescription = (TextView) v.findViewById(R.id.ed_item_description);
            TextView startingBid = (TextView) v.findViewById(R.id.ed_starting_bid);
            TextView presentBid = (TextView) v.findViewById(R.id.ed_present_bid);
            TextView presentBidTitle = (TextView) v.findViewById(R.id.ed_present_bid_title);
            TextView startingBidTitle = (TextView) v.findViewById(R.id.ed_starting_bid_title);

            // check to see if each individual textview is null.
            // if not, assign some text!
            if (itemImage != null) {
                setImage(itemImage, object);
            }
            if (itemName != null) {
                itemName.setText(object.getString("eventName")); // edit key strings as necessary
            }
            if (itemDescription != null) {
                itemDescription.setText(object.getString("eventDescription")); // edit key strings as necessary
            }
            if (startingBidTitle != null) {
                startingBidTitle.setText("Starting Bid: "); // edit key strings as necessary
            }
            if (startingBid != null) {
                startingBid.setText("$ 200"); // edit key strings as necessary
            }
            if (presentBidTitle != null) {
                presentBidTitle.setText("Present Bid: "); // edit key strings as necessary
            }
            if (presentBid != null) {
                presentBid.setText("$ 250"); // edit key strings as necessary
            }
        }

        // the view must be returned to our activity
        return v;


    }

}