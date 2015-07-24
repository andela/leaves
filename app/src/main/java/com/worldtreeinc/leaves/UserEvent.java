package com.worldtreeinc.leaves;

/**
 * Created by andela on 7/24/15.
 */
public class UserEvent {

    private String description;
    private String date;
    private String category;
    private String eventImage;

    public void setDescription(String description) {
        this.description = description;
    }
    public  String getDescription() { return description; }

    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }

    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {this.category = category;}

    public String getEventImage() {
        return eventImage;
    }
    public void setEventImage(String eventImage) {
        this.eventImage = eventImage;
    }
}
