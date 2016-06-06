package org.upcite.uprc.remote;

import android.app.Activity;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

import org.upcite.uprc.utils.Presentation;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class UPRCActivity extends Activity {

    private static final long CONNECTION_TIMEOUT = 100;

    private GoogleApiClient googleApiClient;
    private String node_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uprc);
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                ButterKnife.bind(UPRCActivity.this, stub);
                initComponents();
            }
        });
    }

    private void initComponents() {
        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Wearable.API)
                .build();

        getPairedDeviceNode();
    }

    private void getPairedDeviceNode() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                googleApiClient.blockingConnect(CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS);
                NodeApi.GetConnectedNodesResult result =
                        Wearable.NodeApi.getConnectedNodes(googleApiClient).await();
                List<Node> nodes = result.getNodes();
                if (nodes.size() > 0)
                    node_id = nodes.get(0).getId();
                googleApiClient.disconnect();
            }
        }).start();
    }

    private void sendCommand(final int command) {
        if (node_id != null) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    googleApiClient.blockingConnect(CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS);
                    Wearable.MessageApi.sendMessage(googleApiClient, node_id, Integer.toString(command), null);
                    googleApiClient.disconnect();
                }
            }).start();
        }
    }

    @OnClick({R.id.btn_start, R.id.btn_stop, R.id.btn_pause, R.id.btn_left, R.id.btn_right})
    public void execute(ImageButton button) {
        switch (button.getId()) {
            case R.id.btn_start:                sendCommand(Presentation.Control.Slide.START);              break;
            case R.id.btn_stop:                 sendCommand(Presentation.Control.Slide.STOP);               break;
            case R.id.btn_pause:                sendCommand(Presentation.Control.Slide.PAUSE);              break;
            case R.id.btn_right:                sendCommand(Presentation.Control.Slide.NEXT);               break;
            case R.id.btn_left:                 sendCommand(Presentation.Control.Slide.PREVIOUS);           break;
        }
    }
}
