package com.projects.melih.wonderandwander.ui.main;

import android.os.Bundle;

import com.google.android.gms.ads.MobileAds;
import com.projects.melih.wonderandwander.R;

/**
 * Created by Melih Gültekin on 11.08.2018
 */
public class MainActivity extends BaseMainActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MobileAds.initialize(this, getString(R.string.admob_app_id));
    }
}