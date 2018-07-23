package com.projects.melih.wonderandwander.ui.main;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;

import com.projects.melih.wonderandwander.R;
import com.projects.melih.wonderandwander.databinding.ActivityMainBinding;
import com.projects.melih.wonderandwander.ui.base.BaseActivity;
import com.projects.melih.wonderandwander.ui.base.BaseFragment;
import com.projects.melih.wonderandwander.ui.base.BaseTabFragment;

import static com.projects.melih.wonderandwander.ui.main.BottomNavigationPagerAdapter.TAB_COUNT;

/**
 * Created by Melih Gültekin on 15.06.2018
 */
public class MainActivity extends BaseActivity {
    private ActivityMainBinding binding;
    private ViewPager.OnPageChangeListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.viewPager.setAdapter(new BottomNavigationPagerAdapter(getSupportFragmentManager()));
        binding.viewPager.setOffscreenPageLimit(TAB_COUNT - 1);
        binding.viewPager.setCanScroll(false);
        listener = new ViewPager.OnPageChangeListener() {
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
    }

    @Override
    protected void onDestroy() {
        binding.viewPager.removeOnPageChangeListener(listener);
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(currentFragmentId);
        if (binding.viewPager.getCurrentItem() == BottomNavigationPagerAdapter.SEARCH) {
            super.onBackPressed();
        } else if ((fragmentManager.getBackStackEntryCount() == 0) && !(fragment instanceof BaseTabFragment)) {
            binding.viewPager.setCurrentItem(BottomNavigationPagerAdapter.SEARCH);
        } else if (fragment instanceof BaseTabFragment) {
            final FragmentManager childFragmentManager = fragment.getChildFragmentManager();
            if (childFragmentManager.getBackStackEntryCount() > 0) {
                childFragmentManager.popBackStack();
            } else {
                binding.viewPager.setCurrentItem(BottomNavigationPagerAdapter.SEARCH);
            }
        } else {
            super.onBackPressed();
        }
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