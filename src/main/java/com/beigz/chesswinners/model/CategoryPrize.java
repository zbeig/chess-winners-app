package com.beigz.chesswinners.model;

/**
 * Created by Zaheer Beig on 05/03/2018.
 */
public class CategoryPrize {

    private String category;
    private String prize;

    public CategoryPrize(String category, String prize) {
        this.category = category;
        this.prize = prize;
    }

    @Override
    public String toString() {
        return "CategoryPrize{" +
                "category='" + category + '\'' +
                ", prize='" + prize + '\'' +
                '}';
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPrize() {
        return prize;
    }

    public void setPrize(String prize) {
        this.prize = prize;
    }
}
