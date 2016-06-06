package org.upcite.uprc.views;

import android.app.Fragment;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.upcite.uprc.network.Client;
import org.upcite.uprc.remote.R;
import org.upcite.uprc.utils.Presentation;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;

/**
 * Created by emman on 7/24/15.
 */
public class MouseFragment extends Fragment {

    private final int ARROW     = 1;
    private final int PEN       = 2;
    private final int ERASER    = 3;
    private final int HIDE      = 4;
    private final int LASER     = 5;
    private int active          = 0;

    private boolean LB_HOLD = false;

    private float coord_x;
    private float coord_y;
    private float cursor_x;
    private float cursor_y;

    private View view;

    @Bind({R.id.btn_arrow, R.id.btn_pen, R.id.btn_eraser, R.id.btn_hide, R.id.btn_laser})
    List<Button> buttons;

    @Bind(R.id.btn_hold)
    Button btn_hold;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_mouse, container, false);
        ViewDisplay.setViewOnDisplay(ViewDisplay.MOUSE);

        Title.set(getActivity(), R.string.nav_mouse);

        ButterKnife.bind(this, view);

        getFragmentManager().beginTransaction()
                .add(R.id.content_mouse, new FloatingMenu())
                .commit();

        buttons.get(0).setBackgroundResource(R.drawable.button_focus);
        setActive(ARROW);

        String type = PreferenceManager.getDefaultSharedPreferences(getActivity()).getString("pref_hostSoftware", "0");
        if (Integer.parseInt(type)!= 0) {
            for (Button button : buttons)
                button.setEnabled(false);
            btn_hold.setEnabled(false);
        }

        return view;
    }

    @OnClick({R.id.btn_arrow, R.id.btn_pen, R.id.btn_laser, R.id.btn_eraser, R.id.btn_hide})
    public void executeExtras(Button button) {
//        if (LB_HOLD && (getActive() != PEN || getActive() != LASER)) {
//            btn_hold.setBackgroundResource(R.drawable.button_circle);
//            Client.executeKeyboard(Presentation.Mouse.LEFT_CLICK_UP);
//        }

        for (Button b : buttons)
            b.setBackgroundResource(R.drawable.button_circle);
        button.setBackgroundResource(R.drawable.button_focus);
        switch (button.getId()) {
            case R.id.btn_arrow:
                Client.executeKeyboard(Presentation.Control.Slide.ARROW);
                setActive(ARROW);
                break;
            case R.id.btn_pen:
                Client.executeKeyboard(Presentation.Control.Slide.PEN);
                setActive(PEN);
                break;
            case R.id.btn_eraser:
                Client.executeKeyboard(Presentation.Control.Slide.ERASE);
                setActive(ERASER);
                break;
            case R.id.btn_hide:
                Client.executeKeyboard(Presentation.Control.Slide.HIDE);
                setActive(HIDE);
                break;
            case R.id.btn_laser:
                Client.executeKeyboard(Presentation.Control.Slide.LASER);
                setActive(LASER);
                break;
        }
    }

    @OnClick(R.id.btn_hold)
    public void hold(Button button) {
            if (getActive() == PEN || getActive() == LASER) {
                if (!LB_HOLD) {
                    LB_HOLD = true;
                    Client.executeKeyboard(Presentation.Mouse.LEFT_CLICK_DOWN);
                    button.setBackgroundResource(R.drawable.button_focus);
                } else {
                    LB_HOLD = false;
                    Client.executeKeyboard(Presentation.Mouse.LEFT_CLICK_UP);
                    button.setBackgroundResource(R.drawable.button_circle);
                }
            }
    }

    @OnTouch({R.id.touch_pad, R.id.btn_touch_left, R.id.btn_touch_right})
    public boolean executeTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                switch (v.getId()) {
                    case R.id.btn_touch_left:   Client.executeKeyboard(Presentation.Mouse.LEFT_CLICK_DOWN);     break;
                    case R.id.btn_touch_right:  Client.executeKeyboard(Presentation.Mouse.RIGHT_CLICK_DOWN);    break;
                    case R.id.touch_pad:
                        coord_x = event.getX();
                        coord_y = event.getY();
                        break;
                }
                break;
            case MotionEvent.ACTION_UP:
                switch (v.getId()) {
                    case R.id.btn_touch_left:   Client.executeKeyboard(Presentation.Mouse.LEFT_CLICK_UP);     break;
                    case R.id.btn_touch_right:  Client.executeKeyboard(Presentation.Mouse.RIGHT_CLICK_UP);    break;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (v.getId() == R.id.touch_pad) {
                    if (event.getX() > coord_x + 4)
                        cursor_x = 1;
                    else if (event.getX() < coord_x - 4)
                        cursor_x = -1;
                    else
                        cursor_x = 0;

                    if (event.getY() > coord_y + 3)
                        cursor_y = 1;
                    else if (event.getY() < coord_y - 3)
                        cursor_y = -1;
                    else
                        cursor_y = 0;

                    Client.executeMouse(
                            Presentation.Mouse.MOUSE_MOVE, cursor_x, cursor_y
                    );
                    coord_x = event.getX();
                    coord_y = event.getY();
                }
        }

        return true;
    }

    private void setActive(int active) {
        this.active = active;
    }

    private int getActive() {
        return active;
    }
}