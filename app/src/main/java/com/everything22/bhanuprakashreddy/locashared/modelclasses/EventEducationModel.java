package com.everything22.bhanuprakashreddy.locashared.modelclasses;



public class EventEducationModel {

    String event_Name,evnt_desc,image,event_location;

    public EventEducationModel() {
    }

    public EventEducationModel(String event_Name, String evnt_desc, String image,String event_location) {
        this.event_Name = event_Name;
        this.evnt_desc = evnt_desc;
        this.image = image;
        this.event_location=event_location;
    }

    public String getEvent_location() {
        return event_location;
    }

    public void setEvent_location(String event_location) {
        this.event_location = event_location;
    }

    public String getEvent_Name() {
        return event_Name;
    }

    public void setEvent_Name(String event_Name) {
        this.event_Name = event_Name;
    }

    public String getEvnt_desc() {
        return evnt_desc;
    }

    public void setEvnt_desc(String evnt_desc) {
        this.evnt_desc = evnt_desc;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
