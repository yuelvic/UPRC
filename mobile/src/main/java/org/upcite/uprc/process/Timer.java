package org.upcite.uprc.process;

import android.app.Activity;
import android.content.Context;
import android.widget.Button;


/**
 * Created by emman on 9/28/2015.
 */
public class Timer {

    private Thread thread;

    private Context context;
    private Button btn_time;

    private int sec;
    private int min;
    private int hr;

    public Timer(Context context, Button btn_time) {
        this.context = context;
        this.btn_time = btn_time;
        sec = min = hr = 0;
        startTimer();
    }

    private void startTimer() {
        thread = new Thread(new TimerTask());
        thread.start();
    }

    private void updateTime() {
        ((Activity) context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (TimerState.isAlive()) {
                    sec++;

                    if (sec == 60) {
                        min++;
                        sec = 0;
                    }

                    if (min == 60) {
                        hr++;
                        min = 0;
                    }

                    String time = String.format("%02d:%02d:%02d", hr, min, sec);
                    btn_time.setText(time);
                }
            }
        });
    }

    public void getUpdate(Button button) {
        btn_time = button;
    }

    public void setPaused(Button button) {
        btn_time = button;
        String time = String.format("%02d:%02d:%02d", hr, min, sec);
        btn_time.setText(time);
    }

    private class TimerTask implements Runnable {

        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted() && !TimerState.isDead()) {
                try {
                    Thread.sleep(1000);
                    updateTime();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
