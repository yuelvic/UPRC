package org.upcite.uprc.views;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.upcite.uprc.network.Client;
import org.upcite.uprc.remote.R;
import org.upcite.uprc.remote.UPRCActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Emman on 10/21/2015.
 */
public class HomeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        ButterKnife.bind(this, view);

        return view;
    }

    @OnClick(R.id.btn_present)
    public void present() {
        final int port = 4444;

        new Client(
                getActivity(),
                PreferenceManager.getDefaultSharedPreferences(getActivity()).getString("pref_ipAddress", "127.0.0.1"),
                port
        );

        startActivity(new Intent(getActivity(), UPRCActivity.class));
        getActivity().finish();
    }
}
