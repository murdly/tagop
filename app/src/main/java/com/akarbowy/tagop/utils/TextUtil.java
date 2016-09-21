package com.akarbowy.tagop.utils;

import android.widget.TextView;

public class TextUtil {
    public static boolean empty(TextView text) {
        return text.getText().toString().isEmpty();
    }

    public static String getTrimmed(TextView text) {
        return text.getText().toString().trim();
    }

    public static CharSequence alphaNumericOrEmpty(CharSequence src, int start, int end) {
        for (int i = start; i < end; i++) {
            if ( !Character.isLetterOrDigit(src.charAt(i))) {
                return "";
            }
        }

        return src;
    }
}
