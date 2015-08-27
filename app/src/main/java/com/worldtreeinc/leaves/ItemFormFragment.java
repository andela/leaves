package com.worldtreeinc.leaves;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.parse.ParseUser;
import com.rey.material.widget.FloatingActionButton;

/**
 * Created by tunde on 8/20/15.
 */
public class ItemFormFragment extends Fragment {

    FloatingActionButton floatingActionButton;

    View view;
    String eventId;
    String userId;
    ItemForm form;
    String itemId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        eventId = getArguments().getString("eventId");
        itemId = getArguments().getString("itemId");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.item_form, container, false);
        userId = ParseUser.getCurrentUser().getObjectId();
        form = new ItemForm(getActivity(), view, eventId, userId, floatingActionButton, itemId);

        return view;
    }



    public void setResource(FloatingActionButton fButton) {
        this.floatingActionButton = fButton;
    }
}
