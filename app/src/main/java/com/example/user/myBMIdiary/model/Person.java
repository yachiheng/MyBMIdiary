package com.example.user.myBMIdiary.model;

public class Person {
    public static String tableName = "person";
    private double height;
    private double weight;
    private String mesDate;
    private boolean gender;
    private double bmi;

    public Person() {
    }

    public Person(double height, double weight, String mesDate, boolean gender) {
        this.height = height;
        this.weight = weight;
        this.mesDate = mesDate;
        this.gender = gender;
        setBmi();
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
        setBmi();
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
        setBmi();
    }

    public String getMesDate() {
        return mesDate;
    }

    public void setMesDate(String mesDate) {
        this.mesDate = mesDate;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public double getBmi() {
        return bmi;
    }

    private void setBmi() {
        bmi = weight / Math.pow(height/100, 2);
    }
}
