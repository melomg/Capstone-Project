package com.projects.melih.wonderandwander.ui.user;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.projects.melih.wonderandwander.R;
import com.projects.melih.wonderandwander.common.CollectionUtils;
import com.projects.melih.wonderandwander.databinding.FragmentCompareBinding;
import com.projects.melih.wonderandwander.model.Category;
import com.projects.melih.wonderandwander.model.City;
import com.projects.melih.wonderandwander.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import static com.github.mikephil.charting.utils.ColorTemplate.MATERIAL_COLORS;

/**
 * Created by Melih Gültekin on 02.07.2018
 */
public class CompareFragment extends BaseFragment {
    private static final int DEFAULT_LABEL_COUNT = 10;
    private static final int OUT_OF_10 = 10;
    @Inject
    public ViewModelProvider.Factory viewModelFactory;
    private FragmentCompareBinding binding;
    private List<Category> scores;

    public static CompareFragment newInstance() {
        return new CompareFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_compare, container, false);
        CompareViewModel compareViewModel = ViewModelProviders.of(this, viewModelFactory).get(CompareViewModel.class);
        compareViewModel.getComparedCitiesLiveData().observe(this, cities -> {
            if (cities != null) {
                updateUI(cities);
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initChart();
    }

    private void initChart() {
        //int labelCount = CollectionUtils.size(scores);
        binding.barChart.setAutoScaleMinMaxEnabled(false);
        binding.barChart.getAxisRight().setEnabled(false);
        binding.barChart.getDescription().setEnabled(false);
        binding.barChart.setDragEnabled(true);
        binding.barChart.setDrawBarShadow(false);
        binding.barChart.setDrawGridBackground(false);
        binding.barChart.setDrawValueAboveBar(false);
        binding.barChart.getLegend().setEnabled(false);
        binding.barChart.setNoDataText(getString(R.string.no_data_to_show));
        binding.barChart.setNoDataTextColor(ContextCompat.getColor(context, R.color.orange));
        binding.barChart.setPinchZoom(true);
        binding.barChart.setScaleEnabled(true);
        binding.barChart.setTouchEnabled(true);
        //TODO binding.barChart.setMarker(new MarkerView(context, R.layout.marker_view));

        initXAxis(17);
        initYAxis();
        initLegend();

        binding.barChart.notifyDataSetChanged();
    }

    private void initXAxis(int labelCount) {
        @ColorInt int blackColor = ContextCompat.getColor(context, R.color.black);
        XAxis xAxis = binding.barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextSize(10f);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);
        xAxis.setCenterAxisLabels(true);
        xAxis.setLabelCount((labelCount <= 0) ? DEFAULT_LABEL_COUNT : labelCount);
        xAxis.setTextColor(blackColor);
        xAxis.setValueFormatter((value, axis) -> {
            int index = (int) value;
            if (CollectionUtils.isNotEmpty(scores) && (index >= 0) && (index < CollectionUtils.size(scores))) {
                return scores.get(index).getName();
            }
            return "";
        });
    }

    private void initYAxis() {
        @ColorInt int blackColor = ContextCompat.getColor(context, R.color.black);
        YAxis yAxis = binding.barChart.getAxisLeft();
        yAxis.setTextColor(blackColor);
        yAxis.setTextSize(10f);
        yAxis.setLabelCount(OUT_OF_10 + 1, true);
        yAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        yAxis.setSpaceTop(0f);
        yAxis.setAxisMinimum(0f);
        yAxis.setAxisMaximum(10f);
        yAxis.setDrawAxisLine(true);
        yAxis.setAxisMinimum(0f);
        int labelCount = 10;
        yAxis.setLabelCount((labelCount <= 0) ? DEFAULT_LABEL_COUNT : labelCount);
        yAxis.setSpaceTop(35f);
        yAxis.setGridColor(blackColor);
        yAxis.setValueFormatter((value, axis) -> {
            return String.valueOf(value);
        });
    }

    private void initLegend() {
        binding.barChart.getLegend().setEnabled(true);
        Legend l = binding.barChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(true);
        l.setYOffset(0f);
        l.setXOffset(10f);
        l.setYEntrySpace(0f);
        l.setTextSize(8f);
    }

    private void updateUI(@NonNull List<City> cities) {
        float groupSpace = 0.08f;
        float barSpace = 0.03f; // x4 DataSet
        float barWidth = 0.2f;

        List<IBarDataSet> barDataSets = new ArrayList<>();
        int index = 0;
        if (CollectionUtils.isNotEmpty(cities)) {
            scores = cities.get(0).getScoresOfCategories();
            for (City city : cities) {
                final List<Category> scoresOfCategories = city.getScoresOfCategories();
                List<BarEntry> entries = new ArrayList<>();
                if (CollectionUtils.isNotEmpty(scoresOfCategories)) {
                    int i = 0;
                    for (Category score : scoresOfCategories) {
                        double percentage = score.getScoreOutOf10();
                        entries.add(new BarEntry(i, ((float) percentage)));
                        i++;
                    }

                    BarDataSet dataSet;
                    BarData data = binding.barChart.getData();
                    if ((data != null) && (data.getDataSetCount() > index)) {
                        dataSet = (BarDataSet) data.getDataSetByIndex(index);
                        dataSet.setValues(entries);
                    } else {
                        dataSet = new BarDataSet(entries, city.getName());
                    }

                    dataSet.setColor(MATERIAL_COLORS[index]);
                    dataSet.notifyDataSetChanged();
                    barDataSets.add(dataSet);
                }
                index++;
            }
        }

        if (CollectionUtils.size(barDataSets) > 1) {
            BarData data = new BarData(barDataSets);
            data.setBarWidth(barWidth);
            data.setDrawValues(true);
            data.setValueTextSize(18f);
            data.setHighlightEnabled(true);

            data.notifyDataChanged();
            binding.barChart.setData(data);
            binding.barChart.notifyDataSetChanged();
            //binding.barChart.getXAxis().setAxisMinimum(0);
            //binding.barChart.getXAxis().setAxisMaximum(0 + data.getGroupWidth(groupSpace, barSpace) * 2);
            binding.barChart.groupBars(0, groupSpace, barSpace);
            //data.setValueFormatter(new LargeValueFormatter());


            binding.barChart.invalidate();
        }
    }
}