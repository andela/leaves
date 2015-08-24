package com.worldtreeinc.leaves;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.rey.material.widget.FloatingActionButton;

/**
 * Created by tunde on 8/20/15.
 */
public class ItemFormFragment extends Fragment {

    ImageButton cancelItemAddButton;
    FloatingActionButton floatingActionButton;
    private boolean mShowingBack = false;
    String eventId;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.item_form, container, false);


        cancelItemAddButton = (ImageButton) view.findViewById(R.id.cancel_add_item_button);
        cancelItemAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flipCard();
                floatingActionButton.setVisibility(v.VISIBLE);
            }
        });


        return view;
    }

    private void flipCard() {
        if (mShowingBack) {
            getFragmentManager().popBackStack();
            mShowingBack = false;
            return;
        }

        mShowingBack = true;

        ItemListFragment itemListFragment = new ItemListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("eventId", eventId);
        itemListFragment.setArguments(bundle);

        getFragmentManager()
                .beginTransaction()
                .setCustomAnimations(
                        R.animator.card_flip_right_in, R.animator.card_flip_right_out,
                        R.animator.card_flip_left_in, R.animator.card_flip_left_out)
                .replace(R.id.container, itemListFragment)
                .addToBackStack(null)
                .commit();
    }

    public void setResource(FloatingActionButton fButton, String eventId) {
        this.floatingActionButton = fButton;
        this.eventId = eventId;
    }
}
