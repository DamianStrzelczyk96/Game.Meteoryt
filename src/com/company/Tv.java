package com.company;

public class Tv {
    private  String name;
    private double price;
    private double inch;

    public Tv(String nazwa, double price, double inch ){
        this.name = nazwa;
        this.price = price;
        this.inch = inch;

    }

    public Tv(String nazwa, double price){
        this.name=nazwa;
        this.price = price;
        this.inch = 13.7;
    }
}
