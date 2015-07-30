package com.worldtreeinc.leaves;

import android.content.Context;
import android.test.AndroidTestCase;

import java.io.File;

/**
 * Created by andela on 7/30/15.
 */
public class FileCacheTest extends AndroidTestCase {
    private File cacheDir;
    private Context context;
    private String tString = "test";


    protected void setUp() throws Exception {
        super.setUp();

        FileCache fileCache = new FileCache(getContext());
        try {
            cacheDir = fileCache.getFile("test");


        }catch(Exception e){
            //Log.v("")
            tString = "come";
        }finally {
            assertEquals(tString == "test", true);
        }
    }
}
