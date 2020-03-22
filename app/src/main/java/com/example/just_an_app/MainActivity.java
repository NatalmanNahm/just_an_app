package com.example.just_an_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;
import androidx.preference.PreferenceScreen;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.just_an_app.Adapters.AllCategoriesAdapter;
import com.example.just_an_app.Models.Categories;
import com.example.just_an_app.utilities.NetworkUtil;

import java.net.MalformedURLException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements
        AllCategoriesAdapter.CategoryClickHandler, SharedPreferences.OnSharedPreferenceChangeListener {

    private RecyclerView mRecyclerView;
    private AllCategoriesAdapter mCategoryAdapter;
    private ArrayList<Categories> mCategories;
    private LinearLayoutManager mLinearlayoutManager;
    private Parcelable mSavedLineearLayout;
    private static final String CATID = "id";
    private static final String NAME = "name";
    private static final String BUNDLE = "bundle";
    private static final String DIF = "difficulty";
    private static final String TYPE = "type";
    private static final String NUM = "number of question";
    private static final String SAVED_INSTANCE_KEY = "position";
    private String mDif;
    private String mType;
    private int mNumOfQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.category_rec);

        mLinearlayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLinearlayoutManager);
        mRecyclerView.setHasFixedSize(true);

        mCategoryAdapter = new AllCategoriesAdapter(getApplicationContext(), mCategories, this);
        mRecyclerView.setAdapter(mCategoryAdapter);

        //This is to ensure that whenever the screen is rotated or we are back to this activity
        //we always get back to where we left off.
        if (savedInstanceState != null){
            mSavedLineearLayout = savedInstanceState.getParcelable(SAVED_INSTANCE_KEY);
            mLinearlayoutManager.onRestoreInstanceState(mSavedLineearLayout);
        }

        setupSharedPreferences();

        new FetchAllCategories().execute();
    }

    public class FetchAllCategories extends AsyncTask<String, Void, ArrayList<Categories>>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ArrayList<Categories> doInBackground(String... strings) {

            mCategories = NetworkUtil.fetchAllCategories();
            return mCategories;
        }

        @Override
        protected void onPostExecute(ArrayList<Categories> categories) {
            super.onPostExecute(categories);

            if (categories != null && !categories.isEmpty()){
                mCategoryAdapter.setCategories(categories);
                mLinearlayoutManager.onRestoreInstanceState(mSavedLineearLayout);
            }
        }
    }

    /**
     * On click of an item start an intent an pass data (id and name)
     * to the activity we open.
     * @param id
     * @param name
     */
    @Override
    public void onClick(int id, String name) {
        Intent intent = new Intent(MainActivity.this, QuestionActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(CATID, id);
        bundle.putString(NAME, name);
        bundle.putString(DIF, mDif);
        bundle.putString(TYPE, mType);
        bundle.putInt(NUM, mNumOfQuestion);
        intent.putExtra(BUNDLE, bundle);
        startActivity(intent);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(SAVED_INSTANCE_KEY, mLinearlayoutManager.onSaveInstanceState());
    }

    /**
     * Helper method the retrieve difficulty data save inside the sharedPreference
     * @param sharedPreferences
     * @return
     */
    private String loadDifFromSharedPreferences(SharedPreferences sharedPreferences){
        String dif = sharedPreferences.getString(getString
                (R.string.pref_dif_key), getString(R.string.pref_dif_easy));
        return dif;
    }

    /**
     * Helper method to retrieve type data save inside the sharedPreference
     * @param sharedPreferences
     * @return
     */
    private String loadTypeFromSharedPreferences(SharedPreferences sharedPreferences){
        String type = sharedPreferences.getString
                (getString(R.string.pref_type_key), getString(R.string.pref_any_type));
        return type;

    }

    /**
     * Helper method to get number of questions provided by the sharedPreference
     * @param sharedPreferences
     * @return
     */
    private int loadNumFromSharedPreferences(SharedPreferences sharedPreferences){
        int number = Integer.parseInt(sharedPreferences.getString
                (getString(R.string.pref_num_key), getString(R.string.pref_num_questions)));
        return number;
    }

    /**
     * Helper method to load all data from the shared preference
     */
    private void setupSharedPreferences(){
        // Get all of the values from shared preferences to set it up
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(this);

        mDif = loadDifFromSharedPreferences(sharedPreferences);
        mType = loadTypeFromSharedPreferences(sharedPreferences);
        mNumOfQuestion = loadNumFromSharedPreferences(sharedPreferences);

        // Register the listener
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(getString(R.string.pref_dif_key))){
            mDif = loadDifFromSharedPreferences(sharedPreferences);
        } else if (key.equals(getString(R.string.pref_type_key))){
            mType = loadTypeFromSharedPreferences(sharedPreferences);
        } else if (key.equals(getString(R.string.pref_num_key))){
            mNumOfQuestion = loadNumFromSharedPreferences(sharedPreferences);
        }
    }

    /**
     * Methods for setting up the menu
     **/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /* Use AppCompatActivity's method getMenuInflater to get a handle on the menu inflater */
        MenuInflater menuInflater = getMenuInflater();
        /* Use the inflater's inflate method to inflate our menu layout to this menu */
        menuInflater.inflate(R.menu.menu, menu);
        /* Return true so that the visualizer_menu is displayed in the Toolbar */
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.settings){
            Intent intent = new Intent(this, SettingActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
