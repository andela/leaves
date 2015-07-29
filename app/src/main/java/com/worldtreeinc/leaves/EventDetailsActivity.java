package com.worldtreeinc.leaves;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;

import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class EventDetailsActivity extends AppCompatActivity {

    // declare class variables
    private ParseItemsAdapter m_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Events");
        query.whereEqualTo("objectId", "U5pHVj7Z4E");
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (object == null) {
                    //Log.d("score", "The getFirst request failed.");
                } else {
                    //Log.d("score", "Retrieved the object.");
                    final ImageView banner = (ImageView) findViewById(R.id.event_details_banner);

                    ParseFile eventBanner = (ParseFile) object.get("eventBanner");
                    eventBanner.getDataInBackground(new GetDataCallback() {
                        public void done(byte[] data, ParseException e) {
                            if (e == null) {
                                // data has the bytes for the resume
                                // set the image file
                                Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
                                //Bitmap songImage = Bitmap.createScaledBitmap(bmp, 100, 100, true);
                                banner.setImageBitmap(bmp);
                            } else {
                                // something went wrong

                            }
                        }
                    });
                }
            }
        });

        m_adapter = new ParseItemsAdapter(this);

        ListView listView = (ListView) findViewById(R.id.items_list);
        listView.setAdapter(m_adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_event_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
