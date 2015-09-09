package com.worldtreeinc.leaves;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParsePush;

/**
 * Created by andela on 9/9/15.
 */
@ParseClassName("")
public class Channels extends ParseObject{

    ParsePush push;

    public Channels(){
        push = new ParsePush();
    }
    public void send(String name, String message){
        push.setChannel(name);
        push.setMessage(message);
        push.sendInBackground();
    }


}
