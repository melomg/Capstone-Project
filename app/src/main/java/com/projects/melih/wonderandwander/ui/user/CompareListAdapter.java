package com.projects.melih.wonderandwander.ui.user;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.projects.melih.wonderandwander.R;
import com.projects.melih.wonderandwander.common.CollectionUtils;
import com.projects.melih.wonderandwander.databinding.ItemCompareBinding;
import com.projects.melih.wonderandwander.model.Category;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Melih Gültekin on 28.07.2018
 */
class CompareListAdapter extends RecyclerView.Adapter<CompareListAdapter.CategoryViewHolder> {
    private Map<String, List<String>> categoryPairList;
    private Context context;

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        return new CategoryViewHolder(DataBindingUtil.inflate(inflater, R.layout.item_compare, parent, false), context);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Iterator iterator = categoryPairList.keySet().iterator();
        int i = 0;
        while (iterator.hasNext() && (i != position)) {
            i++;
            iterator.next();
        }
        String key = (String) iterator.next();
        holder.bindTo(categoryPairList.get(key), key);
    }

    @Override
    public int getItemCount() {
        return (categoryPairList == null) ? 0 : categoryPairList.size();
    }

    void submitCategoryPairList(@Nullable Map<String, List<String>> categoryPairList) {
        this.categoryPairList = categoryPairList;
        notifyDataSetChanged();
    }

    static class CategoryViewHolder extends RecyclerView.ViewHolder {
        private final ItemCompareBinding binding;
        private final Context context;

        CategoryViewHolder(@NonNull ItemCompareBinding binding, Context context) {
            super(binding.getRoot());
            this.binding = binding;
            this.context = context;
        }

        void bindTo(@Nullable final List<String> categoryPair, @Nullable final String key) {
            if (categoryPair != null) {
                binding.firstCategoryValue.setText(context.getString(R.string.score_out_of_10, categoryPair.get(0)));
                binding.secondCategoryValue.setText(context.getString(R.string.score_out_of_10, categoryPair.get(1)));
            }
            binding.category.setText(key);
        }
    }
}