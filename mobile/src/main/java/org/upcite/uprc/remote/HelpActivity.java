package org.upcite.uprc.remote;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.upcite.uprc.views.Title;

import java.net.URI;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class HelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        ButterKnife.bind(this);

        Title.set(this, R.string.nav_help);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @OnClick(R.id.tv_link)
    public void redirect() {
        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("https://theuprc.wordpress.com/"));
        startActivity(intent);
    }
}
