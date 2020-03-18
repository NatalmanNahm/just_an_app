package com.example.just_an_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;

import com.example.just_an_app.Adapters.AllCategoriesAdapter;
import com.example.just_an_app.Models.Categories;
import com.example.just_an_app.utilities.NetworkUtil;

import java.net.MalformedURLException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AllCategoriesAdapter.CategoryClickHandler{

    private RecyclerView mRecyclerView;
    private AllCategoriesAdapter mCategoryAdapter;
    private ArrayList<Categories> mCategories;
    private LinearLayoutManager mLinearlayoutManager;
    private Parcelable mSavedLineearLayout;
    private static final String CATID = "id";
    private static final String NAME = "name";
    private static final String BUNDLE = "bundle";
    private static final String SAVED_INSTANCE_KEY = "position";

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
        intent.putExtra(BUNDLE, bundle);
        startActivity(intent);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(SAVED_INSTANCE_KEY, mLinearlayoutManager.onSaveInstanceState());
    }
}
