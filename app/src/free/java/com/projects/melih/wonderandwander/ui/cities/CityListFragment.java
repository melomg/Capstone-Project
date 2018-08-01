package com.projects.melih.wonderandwander.ui.cities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.projects.melih.wonderandwander.R;

/**
 * Created by Melih Gültekin on 01.08.2018
 */
public class CityListFragment extends BaseCityListFragment {
    public static CityListFragment newInstance() {
        return new CityListFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = super.onCreateView(inflater, container, savedInstanceState);

        //noinspection ConstantConditions
        AdView mAdView = root.findViewById(R.id.adView);
        // Creates an ad request. Check logcat output for the hashed device ID to
        // get test ads on a physical device
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);
        return root;
    }
}