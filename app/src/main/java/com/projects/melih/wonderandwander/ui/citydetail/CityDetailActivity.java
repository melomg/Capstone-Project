package com.projects.melih.wonderandwander.ui.citydetail;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;

import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.projects.melih.wonderandwander.R;
import com.projects.melih.wonderandwander.common.CollectionUtils;
import com.projects.melih.wonderandwander.databinding.ActivityCityDetailBinding;
import com.projects.melih.wonderandwander.model.Category;
import com.projects.melih.wonderandwander.model.City;
import com.projects.melih.wonderandwander.model.FavoritedCity;
import com.projects.melih.wonderandwander.ui.base.BaseActivity;
import com.projects.melih.wonderandwander.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Melih Gültekin on 07.07.2018
 */
public class CityDetailActivity extends BaseActivity {
    private static final String KEY_CITY = "key_city";
    private static final int DEFAULT_LABEL_COUNT = 30;
    private static final int OUT_OF_10 = 10;
    private ActivityCityDetailBinding binding;

    public static Intent newIntent(@NonNull Context context, @NonNull City city) {
        Intent intent = new Intent(context, CityDetailActivity.class);
        intent.putExtra(CityDetailActivity.KEY_CITY, city);
        return intent;
    }

    public static Intent newIntent(@NonNull Context context, @NonNull FavoritedCity city) {
        Intent intent = new Intent(context, CityDetailActivity.class);
        intent.putExtra(CityDetailActivity.KEY_CITY, city);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_detail);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_city_detail);
        initChart();
        final Intent intent = getIntent();
        City city = intent.getParcelableExtra(KEY_CITY);
        if (city != null) {
            updateUI(city);
        } else {
            FavoritedCity favoritedCity = intent.getParcelableExtra(KEY_CITY);
            //TODO get city detail from favoritedCity.getName()
        }
    }

    private void updateUI(City city) {
        initToolbar(city);
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
        }
        BarData data = binding.barChart.getData();
        BarDataSet dataSet = (BarDataSet) data.getDataSetByIndex(0);
        dataSet.setColors(colors);
        dataSet.setValues(entries);
        dataSet.notifyDataSetChanged();
        data.notifyDataChanged();
        binding.barChart.notifyDataSetChanged();
        binding.barChart.invalidate();
    }

    private void initToolbar(City city) {
        final ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setTitle(city.getName());
        }
    }

    private void initChart() {
        int labelCount = CollectionUtils.size(city.getScoresOfCategories());
        @ColorInt int blackColor = ContextCompat.getColor(this, R.color.black);

        binding.barChart.getAxisRight().setEnabled(false);
        binding.barChart.getDescription().setEnabled(false);
        binding.barChart.setDragEnabled(true);
        binding.barChart.setDrawBarShadow(false);
        binding.barChart.setDrawGridBackground(false);
        binding.barChart.setDrawValueAboveBar(false);
        binding.barChart.getLegend().setEnabled(false);
        binding.barChart.setNoDataText(getString(R.string.no_data_to_show));
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
            final List<Category> scores = city.getScoresOfCategories();
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

        BarDataSet dataSet = new BarDataSet(new ArrayList<>(), "");
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);

        BarData data = new BarData(dataSet);
        data.setDrawValues(true);
        data.setValueTextSize(18f);
        data.setHighlightEnabled(true);
        data.notifyDataChanged();
        binding.barChart.setData(data);
        binding.barChart.notifyDataSetChanged();
    }

    @Override
    public void replaceFragment(@NonNull BaseFragment newFragment) {
        super.replaceFragment(newFragment, R.id.container);
    }

    @Override
    public void replaceFragment(@NonNull BaseFragment newFragment, int animType, boolean addToBackStack) {
        super.replaceFragment(newFragment, animType, addToBackStack, R.id.container);
    }
}