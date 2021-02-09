package com.manav.daytoday.response_model;

public class UserRating {
    private String name;
    private int rating;

    public UserRating(String name, int rating) {
        this.name = name;
        this.rating = rating;
    }

    public UserRating() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
