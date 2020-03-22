package com.example.just_an_app.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.lang.reflect.Array;

/**
 * Model for questions
 */
public class Quiz implements Parcelable {
    private String mType;
    private String question;
    private String mCorrectAnswer;
    private String [] mAnswersArray = new String [0];

    /**
     * Constructor for the quiz object
     * @param mType
     * @param question
     * @param mCorrectAnswer
     * @param mAnswersArray
     */
    public Quiz(String mType, String question, String mCorrectAnswer, String[] mAnswersArray) {
        this.mType = mType;
        this.question = question;
        this.mCorrectAnswer = mCorrectAnswer;
        this.mAnswersArray = mAnswersArray;
    }

    protected Quiz(Parcel in) {
        mType = in.readString();
        question = in.readString();
        mCorrectAnswer = in.readString();
        mAnswersArray = in.createStringArray();
    }

    public static final Creator<Quiz> CREATOR = new Creator<Quiz>() {
        @Override
        public Quiz createFromParcel(Parcel in) {
            return new Quiz(in);
        }

        @Override
        public Quiz[] newArray(int size) {
            return new Quiz[size];
        }
    };

    public String getmType() {
        return mType;
    }

    public String getQuestion() {
        return question;
    }

    public String getmCorrectAnswer() {
        return mCorrectAnswer;
    }

    public String[] getmAnswersArray() {
        return mAnswersArray;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mType);
        parcel.writeString(question);
        parcel.writeString(mCorrectAnswer);
        parcel.writeStringArray(mAnswersArray);
    }
}
