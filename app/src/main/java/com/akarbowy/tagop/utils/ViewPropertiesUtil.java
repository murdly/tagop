package com.akarbowy.tagop.utils;

import android.support.annotation.NonNull;
import android.text.method.LinkMovementMethod;
import android.text.method.MovementMethod;
import android.view.View;
import android.widget.TextView;

import butterknife.ButterKnife;

public class ViewPropertiesUtil {

    public static ButterKnife.Setter<View, Boolean> VISIBILITY = new ButterKnife.Setter<View, Boolean>() {
        @Override public void set(@NonNull View view, Boolean visible, int index) {
            view.setVisibility(visible ? View.VISIBLE : View.GONE);
        }
    };

    public static void setLinkMovementMethod(TextView view) {
        MovementMethod m = view.getMovementMethod();
        if ((m == null) || !(m instanceof LinkMovementMethod)) {
            if (view.getLinksClickable()) {
                view.setMovementMethod(LinkMovementMethod.getInstance());
            }
        }
    }
}
