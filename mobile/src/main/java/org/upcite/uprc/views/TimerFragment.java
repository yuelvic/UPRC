package org.upcite.uprc.views;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.upcite.uprc.process.TimerState;
import org.upcite.uprc.remote.R;
import org.upcite.uprc.remote.UPRCActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by emman on 9/28/2015.
 */
public class TimerFragment extends Fragment {

    @Bind(R.id.btn_time)
    Button btn_time;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_timer, container, false);
        ViewDisplay.setViewOnDisplay(ViewDisplay.TIMER);

        Title.set(getActivity(), R.string.nav_timer);
        ButterKnife.bind(this, view);

        getFragmentManager().beginTransaction()
                .add(R.id.content_timer, new FloatingMenu())
                .commit();

        if (!TimerState.isDead())
            ((UPRCActivity)getActivity()).updateTime(btn_time);

        return view;
    }

    @OnClick(R.id.btn_time)
    public void start() {
        if (TimerState.isDead()) {
            TimerState.rise();
            ((UPRCActivity)getActivity()).startTimer(btn_time);
        } else if (TimerState.isAlive()) {
            TimerState.pause();
            btn_time.setTextColor(Color.RED);
        } else if (TimerState.isPaused()) {
            TimerState.rise();
            btn_time.setTextColor(Color.WHITE);
        }
    }

    @OnClick(R.id.tv_reset)
    public void reset() {
        if (!TimerState.isDead()) {
            TimerState.die();
            btn_time.setText("00:00:00");
            btn_time.setTextColor(Color.WHITE);
        }
    }

}
