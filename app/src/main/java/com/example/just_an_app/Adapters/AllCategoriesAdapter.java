package com.example.just_an_app.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.just_an_app.Models.Categories;
import com.example.just_an_app.R;

import java.util.ArrayList;
import java.util.zip.Inflater;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Adapter for all categories to be displayed on the home page/main activity
 */
public class AllCategoriesAdapter extends
        RecyclerView.Adapter<AllCategoriesAdapter.CategoriesViewHolder> {
    private Context mContext;
    ArrayList<Categories> mCategories;

    /**
     * Constructor for the adapter
     * @param mContext
     * @param mCategories
     */
    public AllCategoriesAdapter(Context mContext, ArrayList<Categories> mCategories) {
        this.mContext = mContext;
        this.mCategories = mCategories;
    }

    @NonNull
    @Override
    public AllCategoriesAdapter.CategoriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutForListItem = R.layout.category_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttacheImmediatelyToParent = false;

        View view = inflater.inflate(layoutForListItem, parent, shouldAttacheImmediatelyToParent);
        return new CategoriesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllCategoriesAdapter.CategoriesViewHolder holder, int position) {
        holder.bindCategories(mCategories.get(position));
    }

    @Override
    public int getItemCount() {
       if (mCategories == null) return 0;
       return mCategories.size();
    }

    public void setCategories(ArrayList<Categories> mCategories) {
        this.mCategories = mCategories;
        notifyDataSetChanged();
    }

    public class CategoriesViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.category_name) TextView mCategoryName;
        Context mContext;

        private CategoriesViewHolder(@NonNull View itemView) {
            super(itemView);
            mContext = itemView.getContext();
            ButterKnife.bind(this, itemView);
        }

        public void bindCategories(Categories categories){
            mCategoryName.setText(categories.getmCategory());
        }
    }
}
