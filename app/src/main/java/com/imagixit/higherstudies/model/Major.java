package com.imagixit.higherstudies.model;

public class Major {

    private String image,name,university_code,university_name;

    public Major() {
    }

    public Major(String image, String name, String university_code, String university_name) {
        this.image = image;
        this.name = name;
        this.university_code = university_code;
        this.university_name = university_name;
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

    public String getUniversity_code() {
        return university_code;
    }

    public void setUniversity_code(String university_code) {
        this.university_code = university_code;
    }

    public String getUniversity_name() {
        return university_name;
    }

    public void setUniversity_name(String university_name) {
        this.university_name = university_name;
    }
}
