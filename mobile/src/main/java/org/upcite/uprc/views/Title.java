package org.upcite.uprc.views;

import android.app.Activity;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.widget.Button;
import android.widget.TextView;

import org.upcite.uprc.utils.TypefaceSpan;

/**
 * Created by emman on 8/2/15.
 */
public class Title {

    public static void set(Activity activity, int resId) {
        String title = activity.getString(resId);
        SpannableString s = new SpannableString(title);
        s.setSpan(new TypefaceSpan(activity, "bariol.otf"), 0, s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        activity.setTitle(s);
    }

    public static void set(Activity activity, Button button) {
        Typeface typeface = Typeface.createFromAsset(activity.getAssets(), "aller.ttf");
        button.setTypeface(typeface);
    }

    public static void set(Activity activity, TextView textView) {
        Typeface typeface = Typeface.createFromAsset(activity.getAssets(), "aller.ttf");
        textView.setTypeface(typeface);
    }

}
