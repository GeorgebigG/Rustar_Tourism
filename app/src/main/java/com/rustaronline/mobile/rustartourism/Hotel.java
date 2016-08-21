package com.rustaronline.mobile.rustartourism;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

/**
 * Created by gio on 6/6/16.
 */
public class Hotel {
    int maxPrice, minPrice, price;
    Random rand;
    String imageURL;
    String checkIn, checkOut, name, meal;
    int star, nights;
    Calendar checkInCal, checkOutCal;

    public Hotel(String imageURL, int maxPrice, int minPrice, String checkIn, String checkOut, String name, boolean[] stars, int nights, Calendar checkInCal, Calendar checkOutCal, String meal) {
        this.maxPrice = maxPrice;
        this.minPrice = minPrice;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.name = name;
        this.nights = nights;
        this.checkInCal = checkInCal;
        this.checkOutCal = checkOutCal;
        rand = new Random();
        this.imageURL = imageURL;
        if (!meal.equals("Any"))
            this.meal = meal;
        else {
            String[] meals = new String[] { "RO", "BB", "HB", "FB", "AL" };
            this.meal = meals[rand.nextInt(meals.length)];
        }

        price = maxPrice == 0 ? minPrice + rand.nextInt(201) : minPrice + rand.nextInt(maxPrice - minPrice + 1);

        if ((stars[0] == false && stars[1] == false && stars[2] == false && stars[3] == false && stars[4] == false) ||
                (stars[0] == true && stars[1] == true && stars[2] == true && stars[3] == true && stars[4] == true))
            star = 1 + rand.nextInt(5);

        else {
            ArrayList<Integer> integers = new ArrayList<>();

            for (int i = 0; i < stars.length; i++)
                if (stars[i] == true) integers.add((i + 1));

            star = integers.get(rand.nextInt(integers.size()));
        }
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