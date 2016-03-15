package com.worldtreeinc.leaves.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.worldtreeinc.leaves.R;
import com.worldtreeinc.leaves.helper.ItemBidHandler;
import com.worldtreeinc.leaves.model.EventItem;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by andela on 09/03/2016.
 */
public class MyBidsAdapter extends ArrayAdapter<EventItem> implements PopupMenu.OnMenuItemClickListener {
    private List<EventItem> eventItemList;
    Context context;
    private ImageView popupImage;
    private int thePosition;


    public MyBidsAdapter(Context context, List<EventItem> eventItems) {
        super(context, 0, eventItems);
        this.context = context;
        this.eventItemList = eventItems;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        EventItem item;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.event_details_items, parent, false);
        }

        item = eventItemList.get(position);
        TextView itemName = (TextView) convertView.findViewById(R.id.event_details_item_name);
        itemName.setText(item.getName());
        TextView itemDescription = (TextView) convertView.findViewById(R.id.event_details_item_description);
        itemDescription.setText(item.getDescription());
        TextView previousBid = (TextView) convertView.findViewById(R.id.event_details_previous_bid);
        previousBid.setText(item.getPreviousBid().toString());
        TextView newBid = (TextView) convertView.findViewById(R.id.event_details_new_bid);
        //
        popupImage = (ImageView) convertView.findViewById(R.id.popMenu);
        popupImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thePosition = position;
                showMenu(v);
            }
        });

        newBid.setText(item.getNewBid().toString());
        final ImageView itemImage = (ImageView) convertView.findViewById(R.id.event_details_item_image);
        item.getImage().getDataInBackground(new GetDataCallback() {
            @Override
            public void done(byte[] bytes, ParseException e) {
                if (bytes.length != 0) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    itemImage.setImageBitmap(bitmap);
                }
            }
        });
        return convertView;

    }

    public void showMenu(View v) {
        PopupMenu popup = new PopupMenu(getContext(), v);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.bidder_menu_item_list);

        Object menuHelper;
        Class[] argTypes;
        try {
            Field fMenuHelper = PopupMenu.class.getDeclaredField("mPopup");
            fMenuHelper.setAccessible(true);
            menuHelper = fMenuHelper.get(popup);
            argTypes = new Class[]{boolean.class};
            menuHelper.getClass().getDeclaredMethod("setForceShowIcon", argTypes).invoke(menuHelper, true);
        } catch (Exception e) {
            popup.show();
            return;
        }
        popup.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        new ItemBidHandler((Activity) context, eventItemList, thePosition).bidItem();
        return false;
    }
}
