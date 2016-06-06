package org.upcite.uprc.views;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.view.KeyEvent;
import android.view.View;

import org.upcite.uprc.remote.R;

/**
 * Created by emman on 7/26/15.
 */
public class SettingsFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        Title.set(getActivity(), R.string.nav_settings);

        ViewDisplay.setViewOnDisplay(ViewDisplay.SETTINGS);
    }

}
