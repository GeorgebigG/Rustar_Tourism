package com.rustaronline.mobile.rustartourism.Hotels;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by gio on 6/6/16.
 */
public class Hotel {

    String hotelid;
    String imageURL;
    String name;
    int star;
    int nights;
    ArrayList<HotelPrices> rooms;

    public static final int APARTMENT = -1;

    public Hotel(String hotelid, String imageURL, String name, int star, int nights) {
        this.hotelid = hotelid;
        this.name = name;
        this.nights = nights;
        this.imageURL = imageURL;
        this.star = star;

        rooms = new ArrayList<HotelPrices>();
    }

    public void addRoom(String hotelid, String roomCategoryName, String meal, Date startDate, Date endDate, int price, String currency) {
        rooms.add(new HotelPrices(hotelid, roomCategoryName, meal, startDate, endDate, price, currency));
    }

    public ArrayList<HotelPrices> getRooms() {
        return rooms;
    }

    public String getImageURL() {
        return imageURL;
    }

    public String getName() {
        return name;
    }

    public String getHotelid() {
        return hotelid;
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