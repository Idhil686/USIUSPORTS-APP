package com.example.linearlayout;

public class Player {
    private String name;
    private String position;
    private String jerseyNumber;

    public Player(String name, String position, String jerseyNumber) {
        this.name = name;
        this.position = position;
        this.jerseyNumber = jerseyNumber;
    }

    public String getName() {
        return name;
    }

    public String getPosition() {
        return position;
    }

    public String getJerseyNumber() {
        return jerseyNumber;
    }
}