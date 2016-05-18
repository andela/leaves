package com.worldtreeinc.leaves.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.worldtreeinc.leaves.R;
import com.worldtreeinc.leaves.model.Event;
import com.worldtreeinc.leaves.model.EventItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by suadahaji.
 */
public class ReportAdapter extends ArrayAdapter<Event> {
    private List<Event> eventList;
    Context context;

    public ReportAdapter(Context context, List<Event> list) {
        super(context, 0, list);
        this.context = context;
        this.eventList= list;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Event event;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.event_report_layout, parent, false);
        }

        event = eventList.get(position);
        TextView eventName = (TextView) convertView.findViewById(R.id.report_eventName);
        eventName.setText(event.getName());
        TextView entry_fee = (TextView) convertView.findViewById(R.id.entry_fee_text);
        entry_fee.setText(event.getEntryFee().toString());
        TextView entryNumber = (TextView) convertView.findViewById(R.id.number_of_entries_text);
        double total = event.getEntryFee().doubleValue() * event.getEntries();
        entryNumber.setText(event.getEntries() + "");
        TextView totalAmount = (TextView) convertView.findViewById(R.id.entry_total_text);
        totalAmount.setText(total + "");

        final ImageView eventImage = (ImageView) convertView.findViewById(R.id.eventBanner);
        event.getBanner().getDataInBackground(new GetDataCallback() {
            @Override
            public void done(byte[] bytes, ParseException e) {
                if(bytes.length !=0) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                    eventImage.setImageBitmap(bitmap);
                }
            }
        });

        return convertView;
    }
}
