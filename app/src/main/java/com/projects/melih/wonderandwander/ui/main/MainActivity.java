package com.projects.melih.wonderandwander.ui.main;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.projects.melih.wonderandwander.R;
import com.projects.melih.wonderandwander.ui.base.BaseActivity;
import com.projects.melih.wonderandwander.ui.home.HomeFragment;

/**
 * Created by Melih Gültekin on 15.06.2018
 */
public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataBindingUtil.setContentView(this, R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, HomeFragment.newInstance())
                    .addToBackStack("")
                    .commit();
        }
    }
}