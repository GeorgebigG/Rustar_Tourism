package com.rustaronline.mobile.rustartourism;

import java.util.Random;

/**
 * Created by gio on 6/6/16.
 */
public class Hotel {
    int maxPrice, minPrice, price;
    Random rand;
    String[] imageURL;
    String checkIn, checkOut, name;
    int star, amountOfStars;

    public Hotel(String[] imageURL, int maxPrice, int minPrice, String checkIn, String checkOut, String name, boolean[] stars) {
        this.imageURL = imageURL;
        this.maxPrice = maxPrice;
        this.minPrice = minPrice;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.name = name;
        rand = new Random();

        price = maxPrice == 0 ? minPrice + rand.nextInt(201) : minPrice + rand.nextInt(maxPrice - minPrice + 1);

        for (boolean star : stars)
            if (star == true) amountOfStars++;

        star = amountOfStars == 0 ? 1 + rand.nextInt(5) : 1 + rand.nextInt(amountOfStars);
    }

    public String getImageURL() {
        return imageURL[rand.nextInt(imageURL.length)];
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

    public int getAmountOfHotels() {
        return 5;
    }

    public void changePrice() {
        rand = new Random();
        price = maxPrice == 0 ? minPrice + rand.nextInt(201) : minPrice + rand.nextInt(maxPrice - minPrice + 1);
    }

    public void changeAmountOfStars() {
        rand = new Random();
        star = amountOfStars == 0 ? 1 + rand.nextInt(5) : 1 + rand.nextInt(amountOfStars);
    }

    public void changeHotel() {
        changePrice();
        changeAmountOfStars();
    }
}