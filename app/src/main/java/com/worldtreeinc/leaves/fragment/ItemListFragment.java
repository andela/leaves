package com.worldtreeinc.leaves.fragment;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.rey.material.widget.ProgressView;
import com.worldtreeinc.leaves.R;
import com.worldtreeinc.leaves.adapter.EventDetailItemListAdapter;
import com.worldtreeinc.leaves.model.EventItem;
import com.worldtreeinc.leaves.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andela on 8/20/15.
 */
public class ItemListFragment extends Fragment {

    // declare class variables
    ListView itemList;
    FrameLayout frame;
    ProgressView loader;
    EventDetailItemListAdapter listAdapter;
    String eventId;
    String eventName;
    boolean isPlanner;
    boolean isEnteredEvent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            eventId = getArguments().getString("eventId");
            isPlanner = getArguments().getBoolean("isPlanner");
            eventName = getArguments().getString("eventName");
        } catch (Exception e) {
            e.printStackTrace();
        }
        isEnteredEvent = User.isEnteredEvent(eventId);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.item_list, container, false);

        itemList = (ListView) view.findViewById(R.id.items_list);
        frame = (FrameLayout) view.findViewById(R.id.frame_loader);
        loader = (ProgressView) view.findViewById(R.id.loading);
        new ItemAsyncTask().execute();

        return view;
    }


    private class ItemAsyncTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            List items = EventItem.getByEventId(eventId);
            listAdapter.clear();
            listAdapter.addAll(items);
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            listAdapter = new EventDetailItemListAdapter(getActivity(), new ArrayList<EventItem>(), isPlanner, isEnteredEvent, eventName);
            loader.start();
        }

        @Override
        protected void onPostExecute(Void result) {
            itemList.setAdapter(listAdapter);
            loader.stop();
            frame.setVisibility(View.GONE);
        }
    }
}
