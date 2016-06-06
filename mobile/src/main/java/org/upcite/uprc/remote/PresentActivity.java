package org.upcite.uprc.remote;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.test.ActivityInstrumentationTestCase2;
import android.test.InstrumentationTestCase;
import android.view.View;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Wearable;


import org.upcite.uprc.utils.PPTViewer;
import org.upcite.uprc.utils.WearService;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PresentActivity extends AppCompatActivity implements MessageApi.MessageListener {

    private final int MOBILE_IN_USE = 2;

    private final int NEXT = 7;
    private final int PREV = 6;
    private final int BACK = 1;
    private final int RESUME = 0;
    private final int PAUSE = 2;

    private InstrumentationTestCase testCase;
    private GoogleApiClient googleApiClient;

    private ActivityInstrumentationTestCase2<PresentActivity> testCase2;

    private View view;

    @Bind(R.id.viewer)
    PPTViewer viewer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_present);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        String path = intent.getStringExtra("EXTRA_PATH");

        viewer.setPath(path);
        viewer.loadPPT(this);

        initComponents();
    }

    private void initComponents() {
        WearService.setInUse(MOBILE_IN_USE);
        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Wearable.API)
                .build();
        googleApiClient.connect();
        Wearable.MessageApi.addListener(googleApiClient, this);
    }

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        switch (Integer.parseInt(messageEvent.getPath())) {
            case NEXT:      viewer.next();       break;
            case PREV:      viewer.prev();       break;
            case RESUME:    viewer.resume();     break;
            case PAUSE:     viewer.pause();      break;
            case BACK:      finish();            break;
        }
    }

}
