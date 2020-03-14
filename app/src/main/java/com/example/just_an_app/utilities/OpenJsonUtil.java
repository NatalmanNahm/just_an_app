package com.example.just_an_app.utilities;

import android.text.TextUtils;
import android.util.Log;

import com.example.just_an_app.Models.Categories;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Open Json class to get all data needed for our app.
 */
public class OpenJsonUtil {
    private static String TAG = OpenJsonUtil.class.getSimpleName();

    /**
     * create empty constructor, so no one can access it.
     */
    private OpenJsonUtil(){

    }

    /**
     * Getting data needed to build a list of all categories
     * @param json
     * @return
     */
    public static ArrayList<Categories> extractAllCategories(String json){
        //If the string Json is empty the return early
        if (TextUtils.isEmpty(json)){
            return null;
        }
        //Create an empty arraylist to be filled with our data
        ArrayList<Categories> categories = new ArrayList<>();

        //Try to parse the Json and if the is a problem the JSONException will be Thrown.
        //It will be then catch in the catch block, so the app doesn't crash
        try {
            JSONObject rootJson = new JSONObject(json);

            JSONArray catJsonArray = rootJson.getJSONArray("trivia_categories");

            for (int i = 0; i < catJsonArray.length(); i++){
                JSONObject jsonObject = catJsonArray.getJSONObject(i);

                int id = jsonObject.getInt("id");
                String category = jsonObject.getString("name");

                categories.add(new Categories(id, category));
            }

        }catch (JSONException e){
            Log.e(TAG, "Error parsing all category Json");
        }
        //return list of all categories
        return categories;
    }
}
