package com.worldtreeinc.leaves.test;


import android.widget.TextView;

import com.parse.ParseObject;
import com.worldtreeinc.leaves.ParseObjectLoader;

import junit.framework.TestCase;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


/**
 * Created by kamiye on 8/4/15.
 */
public class ParseObjectLoaderTest extends TestCase {

    ParseObjectLoader parseObjectLoader = new ParseObjectLoader();
    ParseObject parseObject = mock(ParseObject.class); // dependency injection
    TextView textView = mock(TextView.class); // dependency injection

    public void testPreconditions() {
        textView.setText("stuff");
        assertNotNull("Null ParseObjectLoader", parseObjectLoader);
        assertNotNull("Null TextView", textView);
        assertNotNull("Null ParseObject", parseObject);
    }

    public void testTextViewSetter() {
        when(parseObject.getString("string")).thenReturn("string");
        textView.setText(parseObject.getString("string"));

        String expected = "string";
        String actual = parseObject.getString("string");
        assertEquals("Unmatching strings", expected, actual);
    }
}
