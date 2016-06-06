package org.upcite.uprc.remote;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Wearable;

import org.upcite.uprc.network.Client;
import org.upcite.uprc.network.Connection;
import org.upcite.uprc.process.Timer;
import org.upcite.uprc.process.TimerState;
import org.upcite.uprc.utils.Presentation;
import org.upcite.uprc.utils.WearService;
import org.upcite.uprc.views.SettingsFragment;
import org.upcite.uprc.views.SlideShowFragment;
import org.upcite.uprc.views.Title;
import org.upcite.uprc.views.ViewDisplay;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;


public class UPRCActivity extends AppCompatActivity implements MessageApi.MessageListener {

    private final int COMPUTER_IN_USE = 1;
    private GoogleApiClient googleApiClient;

    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uprc);

        getFragmentManager().beginTransaction()
                .replace(R.id.content_frame, new SlideShowFragment())
                .commit();

        initComponents();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        WearService.setInUse(COMPUTER_IN_USE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_uprc, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            if (ViewDisplay.getViewOnDisplay() != ViewDisplay.SETTINGS) {
                getFragmentManager().beginTransaction()
                        .setCustomAnimations(
                                android.R.animator.fade_in, android.R.animator.fade_out,
                                android.R.animator.fade_in, android.R.animator.fade_out
                        )
                        .replace(R.id.content_frame, new SettingsFragment())
                        .addToBackStack("settings")
                        .commit();
            }
        } else if (id == R.id.action_help) {
            startActivity(new Intent(this, HelpActivity.class));
        } else if (id == R.id.action_connect) {
            new Connection(
                    this,
                    PreferenceManager.getDefaultSharedPreferences(this).getString("pref_ipAddress", "127.0.0.1"),
                    4444
            ).execute();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && ViewDisplay.getViewOnDisplay() != ViewDisplay.SETTINGS) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.action_exit)
                    .setMessage(R.string.dialog_exit)
                    .setPositiveButton(R.string.action_positive, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Client.executeKeyboard(Presentation.Control.Slide.POWER);
                            finish();
                        }
                    })
                    .setNegativeButton(R.string.action_negative, null)
                    .create()
                    .show();
        }
        return true;
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (PreferenceManager.getDefaultSharedPreferences(this).getBoolean("pref_useHardware", false) &&
                ViewDisplay.getViewOnDisplay() == ViewDisplay.SLIDESHOW) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_VOLUME_UP:
                    Client.executeKeyboard(Presentation.Control.Slide.NEXT);
                    break;
                case KeyEvent.KEYCODE_VOLUME_DOWN:
                    Client.executeKeyboard(Presentation.Control.Slide.PREVIOUS);
                    break;
            }
        }

        if (ViewDisplay.getViewOnDisplay() == ViewDisplay.MEDIA) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_VOLUME_UP:
                    Client.executeKeyboard(Presentation.Control.Media.VOLUME_UP);
                    break;
                case KeyEvent.KEYCODE_VOLUME_DOWN:
                    Client.executeKeyboard(Presentation.Control.Media.VOLUME_DOWN);
                    break;
            }
        }

        if (keyCode == KeyEvent.KEYCODE_BACK && ViewDisplay.getViewOnDisplay() == ViewDisplay.SETTINGS)
            getFragmentManager().popBackStack();

        return true;
    }

    private void initComponents() {
        Title.set(this, R.string.app_name);
        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Wearable.API)
                .build();
        googleApiClient.connect();
        Wearable.MessageApi.addListener(googleApiClient, this);
    }

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        if (PreferenceManager.getDefaultSharedPreferences(this).getBoolean("pref_useWearable", false) && WearService.isComputer()) {
            Client.executeKeyboard(Integer.parseInt(messageEvent.getPath()));
        }
    }

    public void startTimer(Button button) {
        timer = new Timer(this, button);
    }

    public void updateTime(Button button) {
        if (TimerState.isAlive()) {
            button.setTextColor(Color.WHITE);
            timer.getUpdate(button);
        } else {
            button.setTextColor(Color.RED);
            timer.setPaused(button);
        }
    }

}
