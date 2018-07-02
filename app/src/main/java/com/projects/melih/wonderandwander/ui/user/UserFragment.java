package com.projects.melih.wonderandwander.ui.user;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.projects.melih.wonderandwander.R;
import com.projects.melih.wonderandwander.databinding.FragmentUserBinding;
import com.projects.melih.wonderandwander.ui.base.BaseFragment;

/**
 * Created by Melih Gültekin on 02.07.2018
 */
public class UserFragment extends BaseFragment {
    private FragmentUserBinding binding;

    public static UserFragment newInstance() {
        return new UserFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final UserTabPagerAdapter userTabPagerAdapter = new UserTabPagerAdapter(context, getChildFragmentManager());
        binding.viewPager.setAdapter(userTabPagerAdapter);
        binding.tabLayout.setupWithViewPager(binding.viewPager);
    }

    private static class UserTabPagerAdapter extends FragmentStatePagerAdapter {
        private static final int TAB_COUNT = 2;
        private static final int TAB_PROFILE = 0;
        private static final int TAB_COMPARE = TAB_PROFILE + 1;
        private final Context context;

        UserTabPagerAdapter(@NonNull Context context, FragmentManager fm) {
            super(fm);
            this.context = context.getApplicationContext();
        }

        @Override
        public BaseFragment getItem(int position) {
            switch (position) {
                case TAB_PROFILE:
                    return ProfileFragment.newInstance();
                case TAB_COMPARE:
                    return CompareFragment.newInstance();
                default:
                    return null;
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case TAB_PROFILE:
                    return context.getString(R.string.profile_u);
                case TAB_COMPARE:
                    return context.getString(R.string.compare_u);
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return TAB_COUNT;
        }

        @Override
        public int getItemPosition(@NonNull Object object) {
            return POSITION_NONE;
        }
    }
}