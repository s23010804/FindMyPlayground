package com.s23010804.findmyplayground.model;

public class Playground {
    private String name;
    private String location;
    private String imageName;

    public Playground(String name, String location, String imageName) {
        this.name = name;
        this.location = location;
        this.imageName = imageName;
    }

    public String getName() { return name; }
    public String getLocation() { return location; }
    public String getImageName() { return imageName; }
}
