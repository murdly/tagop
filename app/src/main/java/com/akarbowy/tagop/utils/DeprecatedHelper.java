package com.akarbowy.tagop.utils;

import android.text.Html;
import android.text.Spanned;

public class DeprecatedHelper {

    public static Spanned fromHtml(String source) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            return Html.fromHtml(source, Html.FROM_HTML_MODE_LEGACY);
        } else {
            return Html.fromHtml(source);
        }
    }
}
