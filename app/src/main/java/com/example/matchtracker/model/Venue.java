package com.example.matchtracker.model;

import java.util.List;

public class Venue {
    private String id;
    private String name;
    private Location location;
    private List<Category> categories;

    public Venue() {}

    public Venue(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Location getLocation() { return location; }
    public void setLocation(Location location) { this.location = location; }

    public List<Category> getCategories() { return categories; }
    public void setCategories(List<Category> categories) { this.categories = categories; }
}
