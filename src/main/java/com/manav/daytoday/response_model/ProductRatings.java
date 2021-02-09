package com.manav.daytoday.response_model;

import java.util.List;

public class ProductRatings {
    private String productName;
    private List<UserRating> users;
    private double avgRating;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public List<UserRating> getUsers() {
        return users;
    }

    public void setUsers(List<UserRating> users) {
        this.users = users;
    }

    public double getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(double avgRating) {
        this.avgRating = avgRating;
    }
}
