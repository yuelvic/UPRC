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
import android.widget.Toast;

import org.upcite.uprc.remote.PresentActivity;
import org.upcite.uprc.remote.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by emman on 10/1/15.
 */
public class PresentFragment extends Fragment {

    private final int ACTIVITY_CHOOSE_FILE = 1;

    private String extension = "pptx";
    private String path;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_present, container, false);
        ViewDisplay.setViewOnDisplay(ViewDisplay.PRESENT);

        Title.set(getActivity(), R.string.nav_present);
        ButterKnife.bind(this, view);

        getFragmentManager().beginTransaction()
                .add(R.id.content_present, new FloatingMenu())
                .commit();

        return view;
    }

    @OnClick(R.id.btn_open)
    public void open() {
        Intent fileChooser;
        Intent intent;
        fileChooser = new Intent(Intent.ACTION_GET_CONTENT);
        fileChooser.setType("file/*");
        intent = Intent.createChooser(fileChooser, "Choose a file");
        startActivityForResult(intent, ACTIVITY_CHOOSE_FILE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case ACTIVITY_CHOOSE_FILE:
                if (resultCode == Activity.RESULT_OK) {
                    Uri uri = data.getData();
                    path = uri.getPath();
                    int i = path.lastIndexOf('.');
                    if (i > 0) {
                        if (path.substring(i+1).toLowerCase().equals("pptx")) {
                            Intent intent = new Intent(getActivity(), PresentActivity.class);
                            intent.putExtra("EXTRA_PATH", path);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getActivity(), "Invalid file", Toast.LENGTH_LONG).show();
                        }
                    }
                }
        }
    }
}
