package org.upcite.uprc.views;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import org.upcite.uprc.network.Client;
import org.upcite.uprc.remote.R;
import org.upcite.uprc.utils.Presentation;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;

/**
 * Created by emman on 7/23/15.
 */
public class SlideShowFragment extends Fragment  {

    private View view;

    private SharedPreferences sharedPreferences;

    private static boolean isRunning = false;

    @Bind(R.id.btn_start)
    ImageButton btn_start;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_slideshow, container, false);
        ViewDisplay.setViewOnDisplay(ViewDisplay.SLIDESHOW);

        Title.set(getActivity(), R.string.nav_slide);

        ButterKnife.bind(this, view);

        getFragmentManager().beginTransaction()
                .add(R.id.content_slide, new FloatingMenu())
                .commit();

        int bId = (!isRunning) ? R.drawable.play : R.drawable.pause;
        btn_start.setImageResource(bId);

        return view;
    }

    @OnClick({
            R.id.btn_start, R.id.btn_stop, R.id.btn_select,
            R.id.btn_up, R.id.btn_down, R.id.btn_left, R.id.btn_right,
            R.id.btn_zoom_in, R.id.btn_zoom_out, R.id.btn_power
    })
    public void execute(ImageButton button) {
        switch (button.getId()) {
            case R.id.btn_start:
                if (!isRunning) {
                    isRunning = true;
                    button.setImageResource(R.drawable.pause);
                    Client.executeKeyboard(Presentation.Control.Slide.START);
                } else {
                    isRunning = false;
                    button.setImageResource(R.drawable.play);
                    Client.executeKeyboard(Presentation.Control.Slide.PAUSE);
                }
                break;
            case R.id.btn_stop:
                isRunning = false;
                btn_start.setImageResource(R.drawable.play);
                Client.executeKeyboard(Presentation.Control.Slide.STOP);
                break;
            case R.id.btn_select:           Client.executeKeyboard(Presentation.Control.Slide.SELECT);             break;
            case R.id.btn_up:               Client.executeKeyboard(Presentation.Control.Slide.UP);                 break;
            case R.id.btn_down:             Client.executeKeyboard(Presentation.Control.Slide.DOWN);               break;
            case R.id.btn_left:             Client.executeKeyboard(Presentation.Control.Slide.PREVIOUS);           break;
            case R.id.btn_right:            Client.executeKeyboard(Presentation.Control.Slide.NEXT);               break;
            case R.id.btn_zoom_in:          Client.executeKeyboard(Presentation.Control.Slide.ZOOM_IN);            break;
            case R.id.btn_zoom_out:         Client.executeKeyboard(Presentation.Control.Slide.ZOOM_OUT);           break;
        }
    }

    @OnClick(R.id.btn_power)
    public void exit() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.action_exit)
                .setMessage(R.string.dialog_exit)
                .setPositiveButton(R.string.action_positive, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Client.executeKeyboard(Presentation.Control.Slide.POWER);
                        getActivity().finish();
                    }
                })
                .setNegativeButton(R.string.action_negative, null)
                .create()
                .show();
    }

    @OnLongClick(R.id.btn_start)
    public boolean skip(ImageButton button) {
        if (!isRunning) {
            isRunning = true;
            button.setImageResource(R.drawable.pause);
            Client.executeKeyboard(Presentation.Control.Slide.SKIP);
        }
        return true;
    }

}
