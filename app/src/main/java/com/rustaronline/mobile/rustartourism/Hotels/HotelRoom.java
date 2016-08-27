package com.rustaronline.mobile.rustartourism.Hotels;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by georgenebieridze on 8/24/16.
 */
public class HotelRoom {

    String hotelid;

    String roomCategoryName;
    String meal;

    Date startDate, endDate;
    Date bookingStDate, bookingEndDate;

    int adults, teens, childrens, infants;
    int teensWithExtraBed, childrensWithExtraBed, infantsWithExtraBed;

    float maxTeenAge, maxChildrenAge, maxInfantAge;

    int price;
    String currency;

    public HotelRoom(String hotelid, String roomCategoryName, String meal, Date startDate, Date endDate, Date bookingStDate, Date bookingEndDate, int adults, int teens, int childrens, int infants,
                     int teensWithExtraBed, int childrensWithExtraBed, float maxTeenAge, float maxChildrenAge, float maxInfantAge, int price, String currency) {

        this.hotelid = hotelid;

        this.roomCategoryName = roomCategoryName;
        this.meal = meal;

        this.startDate = startDate;
        this.endDate = endDate;
        this.bookingStDate = bookingStDate;
        this.bookingEndDate = bookingEndDate;

        this.adults = adults;
        this.teens = teens;
        this.childrens = childrens;
        this.infants = infants;

        this.teensWithExtraBed = teensWithExtraBed;
        this.childrensWithExtraBed = childrensWithExtraBed;

        this.maxTeenAge = maxTeenAge;
        this.maxChildrenAge = maxChildrenAge;
        this.maxInfantAge = maxInfantAge;

        this.price = price;
        this.currency = currency;
    }
}
