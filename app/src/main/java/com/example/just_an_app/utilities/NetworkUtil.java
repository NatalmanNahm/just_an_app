package com.example.just_an_app.utilities;

import android.net.Uri;
import android.util.Log;

import com.example.just_an_app.Models.Categories;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Utility class that will serves as network requester to the data base
 * https://opentdb.com
 */

public class NetworkUtil {

    private static final String TAG = NetworkUtil.class.getSimpleName();
    //Base URL
    private static final String baseUrl = "https://opentdb.com";
    //Other path
    private static final String CATEGORY = "api_category.php";

    /**
     * this is the method that build the category url
     * @return url
     */
    private static URL build_all_categories(){

        //Build the URL with the query parameter
        Uri uriBuilder = Uri.parse(baseUrl).buildUpon()
                .appendPath(CATEGORY)
                .build();

        URL url = null;

        try {
            url = new URL(uriBuilder.toString());
        }catch (MalformedURLException e){
            Log.e(TAG, "Error building Url: " + url);
        }

        return url;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException{
        StringBuilder output = new StringBuilder();

        if (inputStream != null){
            InputStreamReader inputStreamReader =
                    new InputStreamReader(inputStream, Charset.forName("UTF-8"));

            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = bufferedReader.readLine();

            while (line != null){
                output.append(line);
                line = bufferedReader.readLine();
            }
        }
        return output.toString();
    }

    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */
    private static String getResponseFromHttpUrl (URL url) throws IOException{
        String jsonResponse = "";

        //if the url is null the return early
        if (url == null){
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        //Trying to get connection the server and if the status is 200
        //That means the connection was a success and then we can retrieve data needed
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200){
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(TAG, "Error response code " + urlConnection.getResponseCode());
            }
        } catch (IOException e){
            Log.e(TAG, "MakeHttpRequest: Problem retrieving data from Json result" + e);
        }finally {
            if (urlConnection != null){
                urlConnection.disconnect();
            }

            if (inputStream != null){
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Query the trivia question database and return a list of {@link Categories}.
     * @return
     */
    public static ArrayList<Categories> fetchAllCategories(){
        //create a Url Object
        URL url = build_all_categories();

        String jsonResponse = "";

        //Perform HTTP request to the url and return JSON response back
        try {
            jsonResponse = getResponseFromHttpUrl(url);
        }catch (IOException e){
            Log.e(TAG, "Error making Http Request");
        }

        //Extract data needed from the json response
        ArrayList<Categories> allCategories = OpenJsonUtil.extractAllCategories(jsonResponse);
        return allCategories;
    }


}
