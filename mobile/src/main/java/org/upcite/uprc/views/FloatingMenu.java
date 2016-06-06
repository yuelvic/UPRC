package org.upcite.uprc.views;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.upcite.uprc.navigation.FloatingActionButton;
import org.upcite.uprc.remote.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by emman on 7/24/15.
 */
public class FloatingMenu extends Fragment {

    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.floating_menu, container, false);

        ButterKnife.bind(this, view);

        return view;
    }

    @OnClick({R.id.btn_slide, R.id.btn_mouse, R.id.btn_media, R.id.btn_timer, R.id.btn_present, R.id.btn_transfer})
    public void navigate(FloatingActionButton button) {
        Fragment fragment = null;
        switch (button.getId()) {
            case R.id.btn_slide:        fragment = new SlideShowFragment();     break;
            case R.id.btn_mouse:        fragment = new MouseFragment();         break;
            case R.id.btn_media:        fragment = new MediaFragment();         break;
            case R.id.btn_timer:        fragment = new TimerFragment();         break;
            case R.id.btn_present:      fragment = new PresentFragment();       break;
            case R.id.btn_transfer:     fragment = new TransferFragment();      break;
        }
        getFragmentManager().beginTransaction()
                .setCustomAnimations(
                        android.R.animator.fade_in, android.R.animator.fade_out,
                        android.R.animator.fade_in, android.R.animator.fade_out
                )
                .replace(R.id.content_frame, fragment)
                .commit();
    }

}
