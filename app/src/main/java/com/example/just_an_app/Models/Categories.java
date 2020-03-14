package com.example.just_an_app.Models;

/**
 * Model for all categories
 */

public class Categories {

    //initializer
    private int mCatId;
    private String mCategory;

    /**
     * Constructor for the categories
     * @param id
     * @param category
     */
    public Categories(int id, String category){
        this.mCatId = id;
        this.mCategory = category;
    }

    public int getmCatId() {
        return mCatId;
    }

    public String getmCategory(){
        return mCategory;
    }
}
