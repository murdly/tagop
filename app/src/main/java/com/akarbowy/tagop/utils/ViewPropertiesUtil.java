package com.akarbowy.tagop.utils;

import android.support.annotation.NonNull;
import android.view.View;

import butterknife.ButterKnife;

public class ViewPropertiesUtil {

    public static ButterKnife.Setter<View, Boolean> VISIBILITY = new ButterKnife.Setter<View, Boolean>() {
        @Override public void set(@NonNull View view, Boolean visible, int index) {
            view.setVisibility(visible ? View.VISIBLE : View.GONE);
        }
    };
}
