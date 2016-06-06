package org.upcite.uprc.views;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import org.upcite.uprc.network.Client;
import org.upcite.uprc.remote.R;
import org.upcite.uprc.utils.Presentation;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by emman on 7/24/15.
 */
public class MediaFragment extends Fragment {

    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_media, container, false);
        ViewDisplay.setViewOnDisplay(ViewDisplay.MEDIA);

        Title.set(getActivity(), R.string.nav_media);

        ButterKnife.bind(this, view);

        getFragmentManager().beginTransaction()
                .add(R.id.content_media, new FloatingMenu())
                .commit();

        return view;
    }

    @OnClick({
            R.id.btn_media_play, R.id.btn_media_stop, R.id.btn_media_pause,
            R.id.btn_media_backward, R.id.btn_media_forward, R.id.btn_media_fullscreen
    })
    public void execute(ImageButton button) {
        switch (button.getId()) {
            case R.id.btn_media_play:           Client.executeKeyboard(Presentation.Control.Media.PLAY);            break;
            case R.id.btn_media_pause:          Client.executeKeyboard(Presentation.Control.Media.PAUSE);           break;
            case R.id.btn_media_stop:           Client.executeKeyboard(Presentation.Control.Media.STOP);            break;
            case R.id.btn_media_backward:       Client.executeKeyboard(Presentation.Control.Media.BACKWARD);        break;
            case R.id.btn_media_forward:        Client.executeKeyboard(Presentation.Control.Media.FORWARD);         break;
            case R.id.btn_media_fullscreen:     Client.executeKeyboard(Presentation.Control.Media.MUTE);            break;
        }
    }
}