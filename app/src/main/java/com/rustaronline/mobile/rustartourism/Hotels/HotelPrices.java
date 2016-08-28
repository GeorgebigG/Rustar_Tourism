package com.rustaronline.mobile.rustartourism.Hotels;

import java.util.Date;

/**
 * Created by georgenebieridze on 8/24/16.
 */
public class HotelPrices {

    String hotelid;

    String roomCategoryName;
    String meal;

    Date startDate, endDate;

    int price;
    String currency;

    public HotelPrices(String hotelid, String roomCategoryName, String meal, Date startDate, Date endDate, int price, String currency) {

        this.hotelid = hotelid;

        this.roomCategoryName = roomCategoryName;
        this.meal = meal;

        this.startDate = startDate;
        this.endDate = endDate;

        this.price = price;
        this.currency = currency;
    }

    public String getRoomCategoryName() {
        return roomCategoryName;
    }

    public String getMeal() {
        return meal;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public int getPrice() {
        return price;
    }

    public String getCurrency() {
        return currency;
    }
}
