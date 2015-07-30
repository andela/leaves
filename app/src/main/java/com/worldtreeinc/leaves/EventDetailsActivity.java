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

        // get ImageView of event banner to change
        ImageView banner = (ImageView) findViewById(R.id.event_details_banner);


        // create new ParseImageLoader passing in banner to it and eventId
        // and call setImage() method on it
        ParseImageLoader imageLoader = new ParseImageLoader(banner, "Events", "a5NzJWEExy");
        imageLoader.setImage();

        // instantiate m_adapter and pass in object ID
        m_adapter = new ParseItemsAdapter(this, "zNQS9XB8G5");

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
