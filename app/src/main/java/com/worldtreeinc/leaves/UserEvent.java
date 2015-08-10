package com.worldtreeinc.leaves;

/**
 * Created by andela on 7/24/15.
 */
public class UserEvent {

    private String eventDescription;
    private String eventDate;
    private String eventCategory;
    private String eventBanner;
    private String eventName;
    private String eventVenue;

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }
    public  String getEventDescription() { return eventDescription; }

    public String getEventDate() {
        return eventDate;
    }
    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventCategory() {
        return eventCategory;
    }
    public void setEventCategory(String eventCategory) {
        this.eventCategory = eventCategory;}

    public String getEventBanner() {
        return eventBanner;
    }
    public void setEventBanner(String eventBanner) {
        this.eventBanner = eventBanner;
    }

    public  String getEventName() {
        return eventName;
    }
    public void setEventName(String eventName){
        this.eventName = eventName;
    }


    public String getEventVenue(){
        return eventVenue;
    }
    public void setEventVenue(String eventVenue){
        this.eventVenue = eventVenue;
    }
}

