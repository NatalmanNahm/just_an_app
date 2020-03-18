package com.example.just_an_app.Models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Model for all categories
 */

public class Categories implements Parcelable {

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

    private Categories(Parcel in) {
        mCatId = in.readInt();
        mCategory = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mCatId);
        dest.writeString(mCategory);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Categories> CREATOR = new Creator<Categories>() {
        @Override
        public Categories createFromParcel(Parcel in) {
            return new Categories(in);
        }

        @Override
        public Categories[] newArray(int size) {
            return new Categories[size];
        }
    };

    public int getmCatId() {
        return mCatId;
    }

    public String getmCategory(){
        return mCategory;
    }
}
