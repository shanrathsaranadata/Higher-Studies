package com.imagixit.higherstudies.model;

public class Courses {

    private String  course_code,course_name,description,image,major,rating,type,universities;

    public Courses() {
    }

    public Courses(String course_code, String course_name, String description, String image, String major, String rating, String type, String universities) {
        this.course_code = course_code;
        this.course_name = course_name;
        this.description = description;
        this.image = image;
        this.major = major;
        this.rating = rating;
        this.type = type;
        this.universities = universities;
    }

    public String getCourse_code() {
        return course_code;
    }

    public void setCourse_code(String course_code) {
        this.course_code = course_code;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
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

    public String getUniversities() {
        return universities;
    }

    public void setUniversities(String universities) {
        this.universities = universities;
    }
}
