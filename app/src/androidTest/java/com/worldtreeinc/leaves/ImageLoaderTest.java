package com.worldtreeinc.leaves;

import android.content.Context;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by andela on 7/30/15.
 */
@RunWith(AndroidJUnit4.class)
public class ImageLoaderTest {

    public ImageLoader imageLoader;
    Context context;



    @Before
    public void createLogHistory() {
        imageLoader = new ImageLoader();
    }

    @Test
    public void testDisplayImage() throws Exception{

        MemoryCache memoryCache = new MemoryCache();
        ImageLoader imageLoader = new ImageLoader(context);

    }
}
