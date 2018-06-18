package com.projects.melih.wonderandwander.common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.support.annotation.NonNull;
import android.view.View;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/**
 * Created by Melih Gültekin on 22.04.2018
 */
public final class Utils {
    private static DecimalFormat numberFormatter;

    private Utils() {
        // no-op
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public static boolean isNetworkConnected(@NonNull Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return ((connectivityManager != null) && (connectivityManager.getActiveNetworkInfo() != null));
    }

    public static void await(@NonNull final View view) {
        view.setEnabled(false);
        view.postDelayed(() -> view.setEnabled(true), view.getContext().getResources().getInteger(android.R.integer.config_mediumAnimTime));
    }

    @NonNull
    public static DecimalFormat getNumberFormatter() {
        if (numberFormatter == null) {
            DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols(Locale.getDefault());
            decimalFormatSymbols.setDecimalSeparator(',');
            decimalFormatSymbols.setGroupingSeparator('.');
            numberFormatter = new DecimalFormat();
            numberFormatter.setDecimalFormatSymbols(decimalFormatSymbols);
            numberFormatter.setGroupingSize(3);
            numberFormatter.setMaximumFractionDigits(3);
        }
        return numberFormatter;
    }
}