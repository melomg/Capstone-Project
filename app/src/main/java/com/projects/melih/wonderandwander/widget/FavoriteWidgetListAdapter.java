package com.projects.melih.wonderandwander.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.projects.melih.wonderandwander.R;
import com.projects.melih.wonderandwander.model.FavoritedCity;

import java.util.List;

/**
 * Created by Melih Gültekin on 28.07.2018
 */
public class FavoriteWidgetListAdapter extends ArrayAdapter<FavoritedCity> {
    private List<FavoritedCity> favorites;

    static class FavoriteViewHolder {
        TextView textViewFavoriteName;
    }

    FavoriteWidgetListAdapter(@NonNull Context context, List<FavoritedCity> favorites) {
        super(context, R.layout.item_widget_favorite_list, favorites);
        this.favorites = favorites;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            view = inflater.inflate(R.layout.item_widget_favorite_list, parent, false);
            // configure view holder
            FavoriteViewHolder viewHolder = new FavoriteViewHolder();
            viewHolder.textViewFavoriteName = view.findViewById(R.id.favorite_name);
            view.setTag(viewHolder);
        }

        // fill data
        FavoriteViewHolder holder = (FavoriteViewHolder) view.getTag();
        final FavoritedCity favoritedCity = favorites.get(position);
        if (favoritedCity != null) {
            holder.textViewFavoriteName.setText(favoritedCity.getName());
        }

        return view;
    }
}