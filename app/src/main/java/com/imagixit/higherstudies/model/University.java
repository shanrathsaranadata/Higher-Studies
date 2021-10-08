package com.imagixit.higherstudies.model;

public class University {
    private String ID,image,name,rating,type;

    public University() {

    }

    public University(String ID, String image, String name, String rating, String type) {

        this.ID = ID;
        this.image = image;
        this.name = name;
        this.rating = rating;
        this.type = type;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
