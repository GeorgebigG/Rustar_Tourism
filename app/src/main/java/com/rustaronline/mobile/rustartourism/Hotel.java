package com.rustaronline.mobile.rustartourism;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

/**
 * Created by gio on 6/6/16.
 */
public class Hotel {
    int price;
    String imageURL;
    String checkIn, checkOut, name, meal;
    int star, nights;
    Calendar checkInCal, checkOutCal;

    public Hotel() {}

    public Hotel(String imageURL, int price, String checkIn, String checkOut, String name, int star, int nights, Calendar checkInCal, Calendar checkOutCal, String meal) {
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.name = name;
        this.nights = nights;
        this.checkInCal = checkInCal;
        this.checkOutCal = checkOutCal;
        this.imageURL = imageURL;
        this.meal = meal;
        this.price = price;
        this.star = star;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public void setCheckIn(String checkIn) {
        this.checkIn = checkIn;
    }

    public void setCheckOut(String checkOut) {
        this.checkOut = checkOut;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMeal(String meal) {
        this.meal = meal;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public void setNights(int nights) {
        this.nights = nights;
    }

    public void setCheckInCal(Calendar checkInCal) {
        this.checkInCal = checkInCal;
    }

    public void setCheckOutCal(Calendar checkOutCal) {
        this.checkOutCal = checkOutCal;
    }

    public String getImageURL() {
        return imageURL;
    }

    public int getPrice() {
        return price;
    }

    public String getCheckInDate() {
        return checkIn;
    }

    public String getCheckOutDate() {
        return checkOut;
    }

    public String getName() {
        return name;
    }

    public int getStar() {
        return star;
    }

    public static int getAmountOfHotels() {
        return 5;
    }

    public int getAmountOfNights() {
        return nights;
    }

    public String getDescription() {
        return "Description";
    }

    public String getMeal() {
        return meal;
    }

    public int getCheckInDayOfWeek() {
        return checkInCal.get(Calendar.DAY_OF_WEEK);
    }

    public int getCheckOutDayOfWeek() {
        return checkOutCal.get(Calendar.DAY_OF_WEEK);
    }

    public Calendar getCheckInCal() {
        return (Calendar) checkInCal.clone();
    }

    public Calendar getCheckOutCal() {
        return  (Calendar) checkOutCal.clone();
    }
}