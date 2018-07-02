package com.projects.melih.wonderandwander.ui.user;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.projects.melih.wonderandwander.R;
import com.projects.melih.wonderandwander.databinding.FragmentCompareBinding;
import com.projects.melih.wonderandwander.ui.base.BaseFragment;

/**
 * Created by Melih Gültekin on 02.07.2018
 */
public class CompareFragment extends BaseFragment {
    private FragmentCompareBinding binding;

    public static CompareFragment newInstance() {
        return new CompareFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_compare, container, false);
        return binding.getRoot();
    }

}