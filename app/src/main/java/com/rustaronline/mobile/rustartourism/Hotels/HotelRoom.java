package com.rustaronline.mobile.rustartourism.Hotels;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by georgenebieridze on 8/24/16.
 */
public class HotelRoom {

    String hotelid;
    String roomCategoryName;

    Date startDate, endDate;
    Date bookingStDate, bookingEndDate;

    int adults, teens, childrens, infants;
    int teensWithExtraBed, childrensWithExtraBed, infantsWithExtraBed;

    float maxTeenAge, maxChildrenAge, maxInfantAge;

    int price;
    String currency;

    public HotelRoom() {

    }

}
