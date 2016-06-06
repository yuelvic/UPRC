package org.upcite.uprc.utils;

import android.content.Context;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.apache.commons.validator.routines.InetAddressValidator;

/**
 * Created by emman on 8/1/15.
 */
public class CEditTextPreference extends EditTextPreference implements Preference.OnPreferenceChangeListener {

    private Context context;

    public CEditTextPreference(Context context) {
        super(context);
        this.context = context;
    }

    public CEditTextPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public CEditTextPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    @Override
    protected View onCreateView(ViewGroup parent) {
        setOnPreferenceChangeListener(this);
        setSummary(getText());
        return super.onCreateView(parent);
    }

    @Override
    protected void onDialogClosed(boolean positiveResult) {
        super.onDialogClosed(positiveResult);
        setSummary(getText());
    }


    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        InetAddressValidator validator = new InetAddressValidator();
        if (validator.isValidInet4Address(newValue.toString())) {
            return true;
        }
        else {
            Toast.makeText(context, "Invalid IP address", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}
