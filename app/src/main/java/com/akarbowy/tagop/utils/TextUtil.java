package com.akarbowy.tagop.utils;

import android.widget.TextView;

public class TextUtil {
    public static boolean empty(TextView text) {
        return text.getText().toString().isEmpty();
    }

    public static String getTrimmed(TextView text) {
        return text.getText().toString().trim();
    }
}
