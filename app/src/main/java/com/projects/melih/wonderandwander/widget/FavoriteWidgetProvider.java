package com.projects.melih.wonderandwander.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.RemoteViews;

import com.projects.melih.wonderandwander.R;
import com.projects.melih.wonderandwander.common.AppExecutors;
import com.projects.melih.wonderandwander.model.FavoritedCity;
import com.projects.melih.wonderandwander.repository.local.LocalFavoritesDataSource;
import com.projects.melih.wonderandwander.repository.local.WonderAndWanderDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Melih Gültekin on 28.07.2018
 */
public class FavoriteWidgetProvider extends AppWidgetProvider {

    public static final String EXTRA_FAVORITES = "extra_user";
    public static final String BUNDLE = "bundle";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, ArrayList<FavoritedCity> favorites, int appWidgetId) {
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.favorites_app_widget);

        Intent intent = new Intent(context, GridWidgetService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(EXTRA_FAVORITES, favorites);
        intent.putExtra(BUNDLE, bundle);
        views.setRemoteAdapter(R.id.list_view_favorites, intent);

        views.setEmptyView(R.id.list_view_favorites, R.id.empty_view);
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        //Start the intent service takes care of updating the widgets UI
        //TODO
        WonderAndWanderDatabase database = WonderAndWanderDatabase.getInstance(context);
        LocalFavoritesDataSource dataSource = new LocalFavoritesDataSource(database.favoritedCityDao());

        AppExecutors appExecutors = new AppExecutors();
        final ArrayList<FavoritedCity> favorites = new ArrayList<>();
        appExecutors.diskIO().execute(() -> {
            final List<FavoritedCity> favoritedCities = dataSource.getFavorites();
            appExecutors.mainThread().execute(() -> {
                favorites.clear();
                favorites.addAll(favoritedCities);
                for (int appWidgetId : appWidgetIds) {
                    updateAppWidget(context, appWidgetManager, favorites, appWidgetId);
                }
            });
        });
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        //no-op
    }

    @Override
    public void onEnabled(Context context) {
        //no-op
    }

    @Override
    public void onDisabled(Context context) {
        //no-op
    }
}