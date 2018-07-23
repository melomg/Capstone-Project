package com.projects.melih.wonderandwander.ui.user;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.recyclerview.extensions.AsyncListDiffer;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.projects.melih.wonderandwander.R;
import com.projects.melih.wonderandwander.common.CollectionUtils;
import com.projects.melih.wonderandwander.common.Utils;
import com.projects.melih.wonderandwander.databinding.ItemCityListBinding;
import com.projects.melih.wonderandwander.model.FavoritedCity;
import com.projects.melih.wonderandwander.ui.base.ItemClickListener;

import java.util.List;

/**
 * Created by Melih Gültekin on 30.06.2018
 */
class FavoriteListAdapter extends RecyclerView.Adapter<FavoriteListAdapter.FavoriteViewHolder> {
    private final FavoriteItemListener favoriteItemListener;
    private final AsyncListDiffer<FavoritedCity> differ = new AsyncListDiffer<>(this, new FavoriteListDiffCallback());
    private List<FavoritedCity> favoriteList;

    FavoriteListAdapter(@NonNull FavoriteItemListener favoriteItemListener) {
        this.favoriteItemListener = favoriteItemListener;
    }

    @NonNull
    @Override
    public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new FavoriteViewHolder(DataBindingUtil.inflate(inflater, R.layout.item_city_list, parent, false), favoriteItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteViewHolder holder, int position) {
        holder.bindTo(favoriteList.get(position));
    }

    @Override
    public int getItemCount() {
        return CollectionUtils.size(favoriteList);
    }

    void submitFavoriteList(@Nullable List<FavoritedCity> favoriteList) {
        this.favoriteList = favoriteList;
        notifyDataSetChanged();
    }

    static class FavoriteViewHolder extends RecyclerView.ViewHolder {
        private final ItemCityListBinding binding;
        private final FavoriteItemListener favoriteItemListener;

        FavoriteViewHolder(@NonNull ItemCityListBinding binding, @NonNull FavoriteItemListener favoriteItemListener) {
            super(binding.getRoot());
            this.binding = binding;
            this.favoriteItemListener = favoriteItemListener;
        }

        void bindTo(@Nullable final FavoritedCity favoritedCity) {
            if (favoritedCity != null) {
                binding.setCity(favoritedCity.toCity());
                binding.favoriteCheck.setChecked(true);
                itemView.setOnClickListener(v -> {
                    Utils.await(v);
                    favoriteItemListener.onItemClick(favoritedCity);
                });
                binding.favoriteArea.setOnClickListener(v -> {
                    Utils.await(v);
                    favoriteItemListener.onFavoriteDelete(favoritedCity);
                });
            }
        }
    }

    interface FavoriteItemListener extends ItemClickListener<FavoritedCity> {
        void onFavoriteDelete(@NonNull FavoritedCity city);
    }

    static class FavoriteListDiffCallback extends DiffUtil.ItemCallback<FavoritedCity> {
        @Override
        public boolean areItemsTheSame(@NonNull FavoritedCity oldCity, @NonNull FavoritedCity newCity) {
            return TextUtils.equals(oldCity.getGeoHash(), newCity.getGeoHash());
        }

        @Override
        public boolean areContentsTheSame(@NonNull FavoritedCity oldCity, @NonNull FavoritedCity newCity) {
            return oldCity.equals(newCity);
        }
    }
}