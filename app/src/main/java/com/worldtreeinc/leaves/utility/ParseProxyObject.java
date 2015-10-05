package com.worldtreeinc.leaves.utility;

/**
 * Created by kamiye on 10/5/15.
 */
import com.parse.ParseFile;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;

public class ParseProxyObject implements Serializable {

    public static final String PARSE_LOCAL_OBJECT = "parse_local_object";

    private HashMap<String, Object> values = new HashMap<String, Object>();
    private HashMap<String, String> fileUrls = new HashMap<String, String>();
    private HashMap<String, double[]> geoPoints = new HashMap<String, double[]>();
    private String className;
    private Date createdAt;
    private String objectId;
    private Date updatedAt;

    public ParseProxyObject(ParseObject parseObject) {

        for(String key : parseObject.keySet()) {
            Class classType = parseObject.get(key).getClass();
            if(classType == Boolean.class ||
                    classType == byte[].class ||
                    classType == Date.class ||
                    classType == Double.class ||
                    classType == Integer.class ||
                    classType == Long.class ||
                    classType == Number.class ||
                    classType == String.class) {
                values.put(key, parseObject.get(key));
            } else if (classType == ParseFile.class) {
                // In the case of ParseFile, the url to the file will be retained as a String, since ParseFile is not serializable
                fileUrls.put(key, ((ParseFile) parseObject.get(key)).getUrl());
            } else if (classType == ParseGeoPoint.class) {
                // In the case of a ParseGeoPoint, the doubles values for lat, long will be retained in a double[], since ParseGeoPoint is not serializable
                double[] latlong = {((ParseGeoPoint)parseObject.get(key)).getLatitude(), ((ParseGeoPoint)parseObject.get(key)).getLongitude()};
                geoPoints.put(key, latlong);
            }

            this.className = parseObject.getClassName();
            this.createdAt = parseObject.getCreatedAt();
            this.objectId = parseObject.getObjectId();
            this.updatedAt = parseObject.getUpdatedAt();
        }
    }

    /**
     * Create a ParseObject copy with the same objectId
     */
    public <T extends ParseObject> T getParseObject(Class<T> className)
    {
        T object = ParseObject.createWithoutData(className, objectId);

        for (String key : values.keySet())
            object.put(key, values.get(key));

        return object;
    }
}
