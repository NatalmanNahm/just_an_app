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
    private ArrayList<Categories> mCategories;

    private final CategoryClickHandler mClickHandler;

    public interface CategoryClickHandler{
        void onClick(int id, String name);
    }

    /**
     * Constructor for the adapter
     * @param mContext
     * @param mCategories
     */
    public AllCategoriesAdapter(Context mContext, ArrayList<Categories> mCategories,
                                CategoryClickHandler clickHandler) {
        this.mContext = mContext;
        this.mCategories = mCategories;
        this.mClickHandler = clickHandler;
    }

    @NonNull
    @Override
    public AllCategoriesAdapter.CategoriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutForListItem = R.layout.category_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutForListItem, parent, shouldAttachToParentImmediately);
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

    public class CategoriesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @Bind(R.id.category_name) TextView mCategoryName;
        Context mContext;

        private CategoriesViewHolder(@NonNull View itemView) {
            super(itemView);
            mContext = itemView.getContext();
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        public void bindCategories(Categories categories){
            mCategoryName.setText(categories.getmCategory());
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            Categories categories = mCategories.get(adapterPosition);

            int id = categories.getmCatId();
            String name = categories.getmCategory();

            mClickHandler.onClick(id, name);
        }
    }
}
