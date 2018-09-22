package com.projects.melih.wonderandwander.ui.user;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.projects.melih.wonderandwander.R;
import com.projects.melih.wonderandwander.common.CollectionUtils;
import com.projects.melih.wonderandwander.common.Utils;
import com.projects.melih.wonderandwander.databinding.FragmentCompareBinding;
import com.projects.melih.wonderandwander.model.Category;
import com.projects.melih.wonderandwander.model.City;
import com.projects.melih.wonderandwander.ui.base.BaseFragment;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

/**
 * Created by Melih Gültekin on 02.07.2018
 */
public class CompareFragment extends BaseFragment implements View.OnClickListener {
    @Inject
    public ViewModelProvider.Factory viewModelFactory;
    private FragmentCompareBinding binding;
    private CompareListAdapter compareListAdapter;
    private CompareViewModel compareViewModel;

    public static CompareFragment newInstance() {
        return new CompareFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_compare, container, false);
        compareViewModel = ViewModelProviders.of(this, viewModelFactory).get(CompareViewModel.class);
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

        compareListAdapter = new CompareListAdapter();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayout.VERTICAL, false));
        binding.recyclerView.setHasFixedSize(false);
        binding.recyclerView.setAdapter(compareListAdapter);

        binding.delete.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Utils.await(v);
        switch (v.getId()) {
            case R.id.delete:
                compareViewModel.clearCompareList();
                break;
        }
    }

    private void updateUI(@NonNull List<City> cities) {
        if (cities.size() > 1) {
            showCompareViewHideEmptyView();
            binding.firstCity.setText(cities.get(0).getName());
            binding.secondCity.setText(cities.get(1).getName());
            Map<String, List<String>> categoriesMap = new HashMap<>();
            int i = 0;
            for (City city : cities) {
                final List<Category> scoresOfCategories = city.getScoresOfCategories();
                scoresOfCategories.add(0, new Category(context.getString(R.string.total_score), city.getTeleportCityScore() / 10));
                if (CollectionUtils.isNotEmpty(scoresOfCategories)) {
                    for (Category score : scoresOfCategories) {
                        final String key = score.getName();
                        if (categoriesMap.containsKey(key)) {
                            List<String> scoreOfEachCity = categoriesMap.get(key);
                            scoreOfEachCity.add(new DecimalFormat("#.##").format(score.getScoreOutOf10()));
                            categoriesMap.put(key, scoreOfEachCity);
                        } else {
                            List<String> scoreOfEachCity = new ArrayList<>();
                            for (int j = 0; j < i; j++) {
                                scoreOfEachCity.add("");
                            }
                            scoreOfEachCity.add(new DecimalFormat("#.##").format(score.getScoreOutOf10()));
                            categoriesMap.put(key, scoreOfEachCity);
                        }
                    }
                }
                i++;
            }
            compareListAdapter.submitCategoryPairList(categoriesMap);
        } else {
            showEmptyViewHideCompareView();
        }
    }

    private void showCompareViewHideEmptyView() {
        binding.compareView.setVisibility(View.VISIBLE);
        binding.emptyView.setVisibility(View.GONE);
    }

    private void showEmptyViewHideCompareView() {
        binding.emptyView.setVisibility(View.VISIBLE);
        binding.compareView.setVisibility(View.GONE);
    }
}