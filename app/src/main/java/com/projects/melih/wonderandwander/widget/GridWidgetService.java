package com.projects.melih.wonderandwander.widget;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.projects.melih.wonderandwander.R;
import com.projects.melih.wonderandwander.common.AppExecutors;
import com.projects.melih.wonderandwander.common.CollectionUtils;
import com.projects.melih.wonderandwander.model.FavoritedCity;
import com.projects.melih.wonderandwander.repository.local.LocalFavoritesDataSource;
import com.projects.melih.wonderandwander.repository.local.WonderAndWanderDatabase;

import java.util.ArrayList;
import java.util.List;

import static com.projects.melih.wonderandwander.widget.FavoriteWidgetProvider.BUNDLE;
import static com.projects.melih.wonderandwander.widget.FavoriteWidgetProvider.EXTRA_FAVORITES;

/**
 * Created by Melih Gültekin on 28.07.2018
 */
public class GridWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        Bundle bundle = intent.getBundleExtra(BUNDLE);
        ArrayList<FavoritedCity> favorites = bundle.getParcelableArrayList(EXTRA_FAVORITES);
        return new GridRemoteViewsFactory(this.getApplicationContext(), favorites);
    }
}

class GridRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private Context context;
    private List<FavoritedCity> favorites;
    private final AppExecutors appExecutors;

    GridRemoteViewsFactory(Context applicationContext, ArrayList<FavoritedCity> favorites) {
        context = applicationContext;
        this.favorites = favorites;
        appExecutors = new AppExecutors();
    }

    @Override
    public void onCreate() {
        //no-op
    }

    //called on start and when notifyAppWidgetViewDataChanged is called
    @Override
    public void onDataSetChanged() {
        WonderAndWanderDatabase database = WonderAndWanderDatabase.getInstance(context);
        LocalFavoritesDataSource dataSource = new LocalFavoritesDataSource(database.favoritedCityDao());

        appExecutors.diskIO().execute(() -> {
            final List<FavoritedCity> favoritedCities = dataSource.getFavorites();
            appExecutors.mainThread().execute(() -> this.favorites = favoritedCities);
        });
    }

    @Override
    public void onDestroy() {
        //no-op
    }

    @Override
    public int getCount() {
        return CollectionUtils.size(favorites);
    }

    /**
     * This method acts like the onBindViewHolder method in an Adapter
     *
     * @param position The current position of the item in the ListView to be displayed
     * @return The RemoteViews object to display for the provided position
     */
    @Override
    public RemoteViews getViewAt(int position) {
        if (CollectionUtils.size(favorites) == 0) {
            return null;
        }

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.item_widget_favorite_list);

        FavoritedCity favoritedCity = favorites.get(position);
        final String cityName = favoritedCity.getName();
        //TODO improve UI
        views.setTextViewText(R.id.favorite_name, cityName);
        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1; // Treat all items in the GridView the same
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}