package org.upcite.uprc.views;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.upcite.uprc.network.Filename;
import org.upcite.uprc.network.Transfer;
import org.upcite.uprc.remote.R;

import java.io.File;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Emman on 10/17/2015.
 */
public class TransferFragment extends Fragment {

    private final int ACTIVITY_SEND_FILE = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_transfer, container, false);
        ViewDisplay.setViewOnDisplay(ViewDisplay.TRANSFER);

        Title.set(getActivity(), R.string.nav_transfer);
        ButterKnife.bind(this, view);

        getFragmentManager().beginTransaction()
                .add(R.id.content_transfer, new FloatingMenu())
                .commit();

        return view;
    }

    @OnClick(R.id.btn_send)
    public void send() {
        Intent fileChooser = new Intent(Intent.ACTION_GET_CONTENT);
        fileChooser.setType("file/*");
        Intent intent = Intent.createChooser(fileChooser, "Choose a file");
        startActivityForResult(intent, ACTIVITY_SEND_FILE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case ACTIVITY_SEND_FILE:
                if (resultCode == Activity.RESULT_OK) {
                    Uri uri = data.getData();
                    String path = uri.getPath();
                    String filename = uri.getLastPathSegment();
                    new Filename(getActivity(), filename);
                    new Transfer(getActivity(), path).execute();
                }
                break;
        }
    }
}
