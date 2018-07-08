package com.projects.melih.wonderandwander.ui.cities;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.recyclerview.extensions.AsyncListDiffer;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.projects.melih.wonderandwander.R;
import com.projects.melih.wonderandwander.common.Utils;
import com.projects.melih.wonderandwander.databinding.ItemCityListBinding;
import com.projects.melih.wonderandwander.model.City;
import com.projects.melih.wonderandwander.ui.base.ItemClickListener;

import java.util.List;

/**
 * Created by Melih Gültekin on 05.05.2018
 */
class CityListAdapter extends RecyclerView.Adapter<CityListAdapter.CityViewHolder> {
    private final CityItemListener cityItemListener;
    private final AsyncListDiffer<City> differ = new AsyncListDiffer<>(this, City.DIFF_CALLBACK);

    CityListAdapter(@NonNull CityItemListener cityItemListener) {
        this.cityItemListener = cityItemListener;
    }

    @NonNull
    @Override
    public CityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new CityViewHolder(DataBindingUtil.inflate(inflater, R.layout.item_city_list, parent, false), cityItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CityViewHolder holder, int position) {
        holder.bindTo(differ.getCurrentList().get(position));
    }

    @Override
    public int getItemCount() {
        return differ.getCurrentList().size();
    }

    void submitCityList(@Nullable List<City> cityList) {
        differ.submitList(cityList);
    }

    static class CityViewHolder extends RecyclerView.ViewHolder {
        private final ItemCityListBinding binding;
        private final CityItemListener cityItemListener;

        CityViewHolder(@NonNull ItemCityListBinding binding, @NonNull CityItemListener cityItemListener) {
            super(binding.getRoot());
            this.binding = binding;
            this.cityItemListener = cityItemListener;
        }

        void bindTo(@Nullable final City city) {
            if (city != null) {
                binding.setCity(city);
                itemView.setOnClickListener(v -> {
                    Utils.await(v);
                    cityItemListener.onItemClick(city);
                });
                binding.favoriteArea.setOnClickListener(v -> {
                    Utils.await(v);
                    if (binding.favoriteCheck.isChecked()) {
                        binding.favoriteCheck.setChecked(false);
                        cityItemListener.onFavoriteDelete(city);
                    } else {
                        binding.favoriteCheck.setChecked(true);
                        cityItemListener.onFavoriteAdded(city);
                    }
                });
            }
        }
    }

    interface CityItemListener extends ItemClickListener<City> {
        void onFavoriteDelete(@NonNull City city);

        void onFavoriteAdded(@NonNull City city);
    }
}