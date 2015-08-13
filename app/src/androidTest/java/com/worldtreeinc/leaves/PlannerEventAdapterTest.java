package com.worldtreeinc.leaves;

import android.content.Context;
import android.test.AndroidTestCase;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andela on 7/29/15.
 */
public class PlannerEventAdapterTest extends AndroidTestCase {

    Context context;

    private List<Event> data = new ArrayList<Event>();

    private PlannerEventAdapter mAdapter;
    Event event = new Event();

    public PlannerEventAdapterTest() {
        super();
    }

    protected void setUp() throws Exception {
        super.setUp();
        mAdapter = new PlannerEventAdapter(getContext(), data);
        event.setDescription("Technology");
        event.setVenue("Amity");
        event.setName("cheapest car");
        event.setCategory("Bizz");
        event.setDate("30/08/1987");
        data.add(event);
    }

    public void testGetItemId() {
        assertEquals("Wrong ID.", 0, mAdapter.getItemId(0));
    }

    public void testGetCount() {
        assertEquals("Contacts amount incorrect.", 5, mAdapter.getCount());
    }

    public void testGetView() {
        View view = mAdapter.getView(0, null, null);

        if (view == null) {

            TextView eventDescription = (TextView) view
                    .findViewById(R.id.eventDescription);
            TextView eventDate = (TextView) view
                    .findViewById(R.id.eventDate);
            TextView eventCategory = (TextView) view
                    .findViewById(R.id.eventCategory);
            TextView eventName = (TextView) view
                    .findViewById(R.id.eventName);
            TextView eventVenue = (TextView) view
                    .findViewById(R.id.eventVenue);
            com.pkmmte.view.CircularImageView eventBanner = (com.pkmmte.view.CircularImageView) view
                    .findViewById(R.id.eventBanner);


            assertNull("View is null. ", view);
            assertNull("Description TextView is null. ", eventDescription);
            assertNull("Date TextView is null. ", eventDate);
            assertNull("Category TextView is null. ", eventCategory);
            assertNull("Name TextView is null. ", eventName);
            assertNull("Venue TextView is null. ", eventVenue);
            assertNull("Venue ImageView is null. ", eventBanner);

            assertEquals("Description doesn't match.", event.getField("eventDescription"), eventDescription.getText());
            assertEquals("Date doesn't match.", event.getField("eventDate"),
                    eventDate.getText());

            final ViewGroup.LayoutParams DescriptionlayoutParams =
                    eventDescription.getLayoutParams();
            assertNotNull(DescriptionlayoutParams);
            assertEquals(DescriptionlayoutParams.width, WindowManager.LayoutParams.WRAP_CONTENT);
            assertEquals(DescriptionlayoutParams.height, WindowManager.LayoutParams.WRAP_CONTENT);


            final ViewGroup.LayoutParams DatelayoutParams =
                    eventDate.getLayoutParams();
            assertNotNull(DatelayoutParams);
            assertEquals(DatelayoutParams.width, WindowManager.LayoutParams.WRAP_CONTENT);
            assertEquals(DatelayoutParams.height, WindowManager.LayoutParams.WRAP_CONTENT);

            final ViewGroup.LayoutParams CategorylayoutParams =
                    eventCategory.getLayoutParams();
            assertNotNull(CategorylayoutParams);
            assertEquals(CategorylayoutParams.width, WindowManager.LayoutParams.WRAP_CONTENT);
            assertEquals(CategorylayoutParams.height, WindowManager.LayoutParams.WRAP_CONTENT);

            final ViewGroup.LayoutParams NamelayoutParams =
                    eventName.getLayoutParams();
            assertNotNull(NamelayoutParams);
            assertEquals(NamelayoutParams.width, WindowManager.LayoutParams.WRAP_CONTENT);
            assertEquals(NamelayoutParams.height, WindowManager.LayoutParams.WRAP_CONTENT);

            final ViewGroup.LayoutParams VenuelayoutParams =
                    eventVenue.getLayoutParams();
            assertNotNull(VenuelayoutParams);
            assertEquals(VenuelayoutParams.width, WindowManager.LayoutParams.WRAP_CONTENT);
            assertEquals(VenuelayoutParams.height, WindowManager.LayoutParams.WRAP_CONTENT);

            final ViewGroup.LayoutParams BannerlayoutParams =
                    eventBanner.getLayoutParams();
            assertNotNull(BannerlayoutParams);
            assertEquals(BannerlayoutParams.width, WindowManager.LayoutParams.WRAP_CONTENT);
            assertEquals(BannerlayoutParams.height, WindowManager.LayoutParams.WRAP_CONTENT);

            assertEquals("Category doesn't match.", event.getField("eventCategory"), eventCategory.getText());
            assertEquals("Name doesn't match.", event.getField("EventName"),
                    eventName.getText());

            assertEquals("Venue doesn't match.", event.getField("EventVenue"),
                    eventVenue.getText());
        }

    }
}

