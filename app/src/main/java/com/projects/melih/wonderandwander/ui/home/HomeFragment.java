package com.projects.melih.wonderandwander.ui.home;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.projects.melih.wonderandwander.R;
import com.projects.melih.wonderandwander.databinding.FragmentHomeBinding;
import com.projects.melih.wonderandwander.ui.base.BaseFragment;

import static com.projects.melih.wonderandwander.ui.home.BottomNavigationPagerAdapter.TAB_COUNT;

/**
 * Created by Melih Gültekin on 30.06.2018
 */
public class HomeFragment extends BaseFragment {

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentHomeBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);

        binding.viewPager.setAdapter(new BottomNavigationPagerAdapter(getChildFragmentManager()));
        binding.viewPager.setOffscreenPageLimit(TAB_COUNT - 1);
        ViewPager.OnPageChangeListener listener = new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //no-op
            }

            @Override
            public void onPageSelected(int position) {
                int selectedItemId;
                switch (position) {
                    case BottomNavigationPagerAdapter.PROFILE:
                        selectedItemId = R.id.action_profile;
                        break;
                    case BottomNavigationPagerAdapter.SEARCH:
                    default:
                        selectedItemId = R.id.action_search;
                        break;
                }
                binding.bottomNavigation.setSelectedItemId(selectedItemId);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //no-op
            }
        };
        binding.viewPager.addOnPageChangeListener(listener);
        binding.bottomNavigation.setOnNavigationItemSelectedListener(item -> {
            int viewPagerCurrentItem;
            switch (item.getItemId()) {
                case R.id.action_profile:
                    viewPagerCurrentItem = BottomNavigationPagerAdapter.PROFILE;
                    break;
                case R.id.action_search:
                default:
                    viewPagerCurrentItem = BottomNavigationPagerAdapter.SEARCH;
                    break;
            }
            binding.viewPager.setCurrentItem(viewPagerCurrentItem);
            return true;
        });

        return binding.getRoot();
    }
}