package com.worldtreeinc.leaves.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.parse.ParseUser;
import com.rey.material.widget.FloatingActionButton;
import com.worldtreeinc.leaves.form.ItemForm;
import com.worldtreeinc.leaves.R;

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
    private String eventName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        eventId = bundle.getString("eventId");
        itemId = bundle.getString("itemId");
        eventName = bundle.getString("eventName");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.item_form, container, false);
        userId = ParseUser.getCurrentUser().getObjectId();
        form = new ItemForm(getActivity(), view, eventId, userId, floatingActionButton, itemId, eventName);

        return view;
    }



    public void setResource(FloatingActionButton fButton) {
        this.floatingActionButton = fButton;
    }
}
