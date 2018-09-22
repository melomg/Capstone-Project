package com.projects.melih.wonderandwander.ui.citydetail;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.graphics.Palette;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.projects.melih.wonderandwander.R;
import com.projects.melih.wonderandwander.common.CollectionUtils;
import com.projects.melih.wonderandwander.common.Utils;
import com.projects.melih.wonderandwander.databinding.ActivityCityDetailBinding;
import com.projects.melih.wonderandwander.model.Category;
import com.projects.melih.wonderandwander.model.City;
import com.projects.melih.wonderandwander.model.FavoritedCity;
import com.projects.melih.wonderandwander.repository.remote.ErrorState;
import com.projects.melih.wonderandwander.ui.base.BaseActivity;
import com.projects.melih.wonderandwander.ui.base.BaseFragment;
import com.projects.melih.wonderandwander.ui.cities.CitiesViewModel;
import com.projects.melih.wonderandwander.ui.user.CompareViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by Melih Gültekin on 07.07.2018
 */
public class CityDetailActivity extends BaseActivity implements View.OnClickListener, OnMapReadyCallback {
    public static final String VIEW_NAME_HEADER_IMAGE = "detail:header:image";
    private static final String KEY_CITY = "key_city";
    private static final String KEY_FAVORITED_CITY = "favorited_key_city";
    private static final int DEFAULT_LABEL_COUNT = 30;
    private static final int OUT_OF_10 = 10;
    @Inject
    public ViewModelProvider.Factory viewModelFactory;
    private ActivityCityDetailBinding binding;
    private CompareViewModel compareViewModel;
    private CitiesViewModel citiesViewModel;
    private List<Category> scores;
    private GoogleMap googleMap;

    public static Intent newIntent(@NonNull Context context, @NonNull City city) {
        Intent intent = new Intent(context, CityDetailActivity.class);
        intent.putExtra(CityDetailActivity.KEY_CITY, city);
        return intent;
    }

    public static Intent newIntent(@NonNull Context context, @NonNull FavoritedCity city) {
        Intent intent = new Intent(context, CityDetailActivity.class);
        intent.putExtra(CityDetailActivity.KEY_FAVORITED_CITY, city);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_city_detail);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.lite_map);
        mapFragment.getMapAsync(this);

        citiesViewModel = ViewModelProviders.of(this, viewModelFactory).get(CitiesViewModel.class);
        compareViewModel = ViewModelProviders.of(this, viewModelFactory).get(CompareViewModel.class);
        compareViewModel.getErrorLiveData().observe(this, errorState -> {
            if (errorState != null) {
                switch (errorState) {
                    case ErrorState.ALREADY_ADDED_TO_COMPARE_LIST:
                        showToast(R.string.already_added_to_compare_list);
                        break;
                    case ErrorState.EXCEEDS_MAX_COMPARE_SIZE:
                        showToast(R.string.exceeds_max_compare_size);
                        break;
                    case ErrorState.NO_ERROR:
                        showToast(R.string.success_add_to_compare);
                        break;
                }
            }
        });
        citiesViewModel.getCityLiveData().observe(this, city -> {
            if (city != null) {
                scores = city.getScoresOfCategories();
                updateUI(city);
            }
        });
        citiesViewModel.getLocationAvailableLiveData().observe(this, isLocationAvailable -> {
            if ((isLocationAvailable != null) && isLocationAvailable) {
                City city = citiesViewModel.getCityLiveData().getValue();
                if ((googleMap != null) && (city != null)) {
                    LatLng latLng = new LatLng(city.getLatitude(), city.getLongitude());
                    googleMap.addMarker(new MarkerOptions().position(latLng));
                    googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                }
            }
        });
        citiesViewModel.getLoadingLiveData().observe(this, isLoading -> {
            if ((isLoading != null) && isLoading) {
                binding.progress.setVisibility(View.VISIBLE);
                binding.progress.show();
            } else {
                binding.progress.setVisibility(View.GONE);
                binding.progress.hide();
            }
        });

        initChart();
        final Intent intent = getIntent();
        City city = intent.getParcelableExtra(KEY_CITY);
        if (city != null) {
            citiesViewModel.setCity(city);
        } else {
            FavoritedCity favoritedCity = intent.getParcelableExtra(KEY_FAVORITED_CITY);
            final String name = favoritedCity.getName();
            if (name != null) {
                citiesViewModel.search(name);
            }
        }
        binding.compare.setOnClickListener(this);
    }

    private void initToolbar(@NonNull City city) {
        binding.back.setOnClickListener(this);
        binding.collapsingToolbar.setTitle(city.getName());
    }

    private void initChart() {
        int labelCount = CollectionUtils.size(scores);
        @ColorInt int blackColor = ContextCompat.getColor(this, R.color.black);

        binding.barChart.setAutoScaleMinMaxEnabled(false);
        binding.barChart.getAxisRight().setEnabled(false);
        binding.barChart.getDescription().setEnabled(false);
        binding.barChart.setDragEnabled(true);
        binding.barChart.setDrawBarShadow(false);
        binding.barChart.setDrawGridBackground(false);
        binding.barChart.setDrawValueAboveBar(false);
        binding.barChart.getLegend().setEnabled(false);
        binding.barChart.setNoDataText(getString(R.string.no_data_to_show));
        binding.barChart.setNoDataTextColor(ContextCompat.getColor(this, R.color.orange));
        binding.barChart.setPinchZoom(true);
        binding.barChart.setScaleEnabled(true);
        binding.barChart.setTouchEnabled(true);

        XAxis xAxis = binding.barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextSize(10f);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount((labelCount <= 0) ? DEFAULT_LABEL_COUNT : labelCount);
        xAxis.setTextColor(blackColor);
        xAxis.setValueFormatter((value, axis) -> {
            int index = (int) value;
            if (CollectionUtils.isNotEmpty(scores) && (index >= 0) && (index < CollectionUtils.size(scores))) {
                return scores.get(index).getName();
            }
            return "";
        });
        YAxis yAxis = binding.barChart.getAxisLeft();
        yAxis.setTextColor(blackColor);
        yAxis.setTextSize(10f);
        yAxis.setLabelCount(OUT_OF_10 + 1, true);
        yAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        yAxis.setSpaceTop(0f);
        yAxis.setAxisMinimum(0f);
        yAxis.setDrawAxisLine(true);
        yAxis.setGridColor(blackColor);

        binding.barChart.notifyDataSetChanged();
    }

    private void updateUI(@NonNull City city) {
        initToolbar(city);

        updateImage(city.getImageUrl());

        binding.score.setText(getString(R.string.score_out_of_100, city.getTeleportCityScore()));

        citiesViewModel.setLocationAvailable(true);

        final List<Category> scoresOfCategories = city.getScoresOfCategories();
        List<BarEntry> entries = new ArrayList<>();
        int[] colors = new int[CollectionUtils.size(scoresOfCategories) * 2];
        if (CollectionUtils.isNotEmpty(scoresOfCategories)) {
            int i = 0;
            for (Category score : scoresOfCategories) {
                double percentage = score.getScoreOutOf10();
                double ratio = percentage / OUT_OF_10;
                entries.add(new BarEntry(i, ((float) percentage)));
                @ColorRes int color;
                if (ratio < 0.25f) {
                    color = R.color.chart_red;
                } else if (ratio < 0.5f) {
                    color = R.color.chart_yellow;
                } else if (ratio < 0.75f) {
                    color = R.color.chart_blue;
                } else {
                    color = R.color.chart_green;
                }
                colors[i] = ContextCompat.getColor(this, color);
                i++;
            }

            BarDataSet dataSet;
            BarData data = binding.barChart.getData();
            if ((data != null) && (data.getDataSetCount() > 0)) {
                dataSet = (BarDataSet) data.getDataSetByIndex(0);
                dataSet.setValues(entries);
                dataSet.notifyDataSetChanged();
            } else {
                dataSet = new BarDataSet(entries, "");
                data = new BarData(dataSet);
                data.setDrawValues(true);
                data.setValueTextSize(18f);
                data.setHighlightEnabled(true);
            }
            dataSet.setColors(colors);
            data.notifyDataChanged();
            binding.barChart.setData(data);
            binding.barChart.notifyDataSetChanged();
            binding.barChart.invalidate();
        }
    }

    private void updateImage(String imageUrl) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontAnimate()
                .placeholder(R.color.gray_dark)
                .error(R.color.gray_dark);
        final CityDetailActivity context = CityDetailActivity.this;
        Glide.with(context)
                .asBitmap()
                .apply(options)
                .listener(new RequestListener<Bitmap>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                        createPaletteAsync(resource);
                        return false;
                    }

                    private void createPaletteAsync(Bitmap bitmap) {
                        if (bitmap != null) {
                            Palette.from(bitmap).generate(palette -> {
                                @ColorInt final int scrimColor = palette.getVibrantColor(ContextCompat.getColor(context, R.color.colorPrimary));
                                binding.collapsingToolbar.setContentScrimColor(scrimColor);

                                @ColorInt final int statusBarColor = palette.getDarkVibrantColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));
                                updateStatusBar(statusBarColor);
                            });
                        }
                    }

                    private void updateStatusBar(@ColorInt int color) {
                        final Window window = CityDetailActivity.this.getWindow();
                        if (window == null) {
                            return;
                        }
                        // clear FLAG_TRANSLUCENT_STATUS flag:
                        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
                        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                        // finally change the color
                        window.setStatusBarColor(color);
                    }
                })
                .load(imageUrl)
                .thumbnail(0.1f)
                .into(binding.backdrop.image);
    }

    @Override
    public void replaceFragment(@NonNull BaseFragment newFragment) {
        super.replaceFragment(newFragment, R.id.container);
    }

    @Override
    public void replaceFragment(@NonNull BaseFragment newFragment, int animType, boolean addToBackStack) {
        super.replaceFragment(newFragment, animType, addToBackStack, R.id.container);
    }

    @Override
    public void onClick(View v) {
        Utils.await(v);
        switch (v.getId()) {
            case R.id.compare:
                compareViewModel.addToCompareList(citiesViewModel.getCityLiveData().getValue());
                break;
            case R.id.back:
                onBackPressed();
                break;
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (googleMap != null) {
            UiSettings uiSettings = googleMap.getUiSettings();
            uiSettings.setMyLocationButtonEnabled(false);
            uiSettings.setCompassEnabled(false);
            uiSettings.setMapToolbarEnabled(false);
            this.googleMap = googleMap;
            citiesViewModel.setLocationAvailable(true);
        }
    }
}