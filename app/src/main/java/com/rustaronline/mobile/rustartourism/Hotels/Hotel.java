package com.rustaronline.mobile.rustartourism.Hotels;

import java.util.ArrayList;

/**
 * Created by gio on 6/6/16.
 */
public class Hotel {
    String hotelid;
    String imageURL;
    String name;
    int star;
    int nights;
    ArrayList<HotelRoom> rooms;

    public static final int APARTMENT = -1;

    public Hotel(String hotelid, String imageURL, String name, int star, int nights) {
        this.name = name;
        this.nights = nights;
        this.imageURL = imageURL;
        this.star = star;

        rooms = new ArrayList<HotelRoom>();
    }

    public void addRoom() {

    }

    public String getImageURL() {
        return imageURL;
    }

    public String getName() {
        return name;
    }

    public int getStar() {
        return star;
    }

    public int getAmountOfNights() {
        return nights;
    }

    public String getDescription() {
        return "Description";
    }
}