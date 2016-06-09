package com.rustaronline.mobile.rustartourism;

import android.widget.Spinner;

import java.util.ArrayList;

/**
 * Created by gio on 30/04/16.
 */
public class StaticClass {
    public static boolean accauntBlocked;
    public static String firstHotel = "https://www.rustaronline.com/images/pages/hotels/thumbnail/A9AF2DFE-73DC-441A-A972-CC6136534569.jpg";
    public static String secondHotel = "https://www.rustaronline.com/images/pages/hotels/thumbnail/78468066-FCA7-472C-AABD-2143DF1DC718.jpg";


    public static String getString(String name, String password) {
        if (checkPassword(name, password) && !isAccauntBlocked(name, password))
            return "Correct";
        else if (!checkPassword(name, password))
            return "password or username wrong";
        else
            return "User Blocked!";
    }

    private static boolean checkPassword(String name, String password) {
        if (name.equals("") || password.equals(""))
            return false;
        else
            return true;
    }

    private static boolean isAccauntBlocked(String name, String password)  {
        accauntBlocked = false;

        return accauntBlocked;
    }

    public static void searchRelust(String checkIn, int amountOfNight, String checkOut, String location, String hotel, String pax) {
    }

    public static Hotel advancedSearchRelust(String checkIn, int amountOfNight, String checkOut, String hotelname, String city, String district, int amountOfAdults, ArrayList<Spinner> childrensAgeSpinner, int dailyFrom, int dailyTo, int totalFrom, int totalTo, String type, String meal, boolean fiveStar, boolean fourStar, boolean threeStar, boolean twoStar, boolean oneStar, boolean apartment, boolean alcohol, boolean freeWifi, boolean pool, boolean metro, boolean mall) {
        int minPrice = dailyFrom == 0 ? totalFrom == 0 ? 0 : (totalFrom/amountOfNight) : dailyFrom;
        int maxPrice = dailyTo == 0 ? totalTo == 0 ? 0 : (totalTo/amountOfNight) : dailyTo;
        boolean[] stars = new boolean[] { oneStar, twoStar, threeStar, fourStar, fiveStar };

        if (hotelname.equals(""))
            hotelname = "Rixsos";
        Hotel findHotel = new Hotel(new String[] { firstHotel, secondHotel }, maxPrice, minPrice, checkIn, checkOut, hotelname, stars);
        return findHotel;
    }
}