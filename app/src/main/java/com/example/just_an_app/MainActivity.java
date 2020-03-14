package com.example.just_an_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.example.just_an_app.Adapters.AllCategoriesAdapter;
import com.example.just_an_app.Models.Categories;
import com.example.just_an_app.utilities.NetworkUtil;

import java.net.MalformedURLException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private AllCategoriesAdapter mCategoryAdapter;
    private ArrayList<Categories> mCategories;
    private LinearLayoutManager mLinearlayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.category_rec);

        mLinearlayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLinearlayoutManager);
        mRecyclerView.setHasFixedSize(true);

        mCategoryAdapter = new AllCategoriesAdapter(getApplicationContext(), mCategories);
        mRecyclerView.setAdapter(mCategoryAdapter);

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
            }
        }
    }
}
