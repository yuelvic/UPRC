//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.upcite.uprc.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.olivephone.office.TempFileManager;
import com.olivephone.office.powerpoint.DocumentSession;
import com.olivephone.office.powerpoint.DocumentSessionBuilder;
import com.olivephone.office.powerpoint.DocumentSessionStatusListener;
import com.olivephone.office.powerpoint.android.AndroidMessageProvider;
import com.olivephone.office.powerpoint.android.AndroidSystemColorProvider;
import com.olivephone.office.powerpoint.android.AndroidTempFileStorageProvider;
import com.olivephone.office.powerpoint.view.PersentationView;
import com.olivephone.office.powerpoint.view.SlideShowNavigator;
import com.olivephone.office.powerpoint.view.SlideView;
import java.io.File;

public class PPTViewer extends RelativeLayout implements DocumentSessionStatusListener {
    ProgressBar pb;
    TextView tv;
    LayoutParams params;
    private DocumentSession session;
    PersentationView slide;
    String path;
    Context ctx;
    Activity act;
    private SlideShowNavigator navitator;
    private int currentSlideNumber;
    private float zoomlevel = 20.0F;
    SimpleOnGestureListener simpleOnGestureListener = new SimpleOnGestureListener() {
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if(PPTViewer.this.zoomlevel > 25.0F) {
                return false;
            } else {
                float sensitvity = (float)PPTViewer.this.dpToPx(100);
                if(e1.getX() - e2.getX() > sensitvity) {
                    PPTViewer.this.next();
                } else if(e2.getX() - e1.getX() > sensitvity) {
                    PPTViewer.this.prev();
                }

                return true;
            }
        }
    };
    GestureDetector gestureDetector;

    public int getTotalSlides() {
        return this.session != null && this.session.getPPTContext() != null?this.navitator.getSlideCount():-1;
    }

    void log(Object log) {
        Log.d("rex", log.toString());
    }

    void toast(Object msg) {
        Toast.makeText(this.act, msg.toString(), Toast.LENGTH_LONG).show();
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void loadPPT(Activity act, String path) {
        this.setPath(path);
        this.loadPPT(act);
    }

    public void loadPPT(Activity act) {
        this.act = act;

        try {
            AndroidMessageProvider e = new AndroidMessageProvider(this.ctx);
            TempFileManager tmpFileManager = new TempFileManager(new AndroidTempFileStorageProvider(this.ctx));
            AndroidSystemColorProvider sysColorProvider = new AndroidSystemColorProvider();
            this.session = (new DocumentSessionBuilder(new File(this.path))).setMessageProvider(e).setTempFileManager(tmpFileManager).setSystemColorProvider(sysColorProvider).setSessionStatusListener(this).build();
            this.session.startSession();
        } catch (Exception var5) {
            var5.printStackTrace();
        }

    }

    int dpToPx(int dp) {
        return (int)((float)dp * Resources.getSystem().getDisplayMetrics().density);
    }

    int pxToDp(int px) {
        return (int)((float)px / Resources.getSystem().getDisplayMetrics().density);
    }

    public PPTViewer(Context ctx, AttributeSet attr) {
        super(ctx, attr);
        this.gestureDetector = new GestureDetector(this.ctx, this.simpleOnGestureListener);
        this.ctx = ctx;
        this.pb = new ProgressBar(ctx);
        this.tv = new TextView(ctx);
        this.slide = new PersentationView(ctx, attr);
        this.slide.setVisibility(INVISIBLE);
        //this.slide.setId(1233);
        this.slide.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                PPTViewer.this.gestureDetector.onTouchEvent(event);
                return false;
            }
        });
        this.params = new LayoutParams(-2, -2);
        this.params.addRule(13);
        this.addView(this.slide, this.params);
        this.params = new LayoutParams(this.dpToPx(50), this.dpToPx(50));
        this.params.addRule(10);
        this.params.addRule(11);
        this.params = new LayoutParams(this.dpToPx(80), this.dpToPx(80));
        this.params.addRule(15);
        this.params.addRule(11);
        this.params = new LayoutParams(this.dpToPx(80), this.dpToPx(80));
        this.params.addRule(15);
        this.params.addRule(9);
        this.params = new LayoutParams(-2, -2);
        this.params.addRule(13);
        this.pb.setBackgroundColor(Color.parseColor("#80000000"));
        this.addView(this.pb, this.params);
    }

    public void onDocumentException(Exception arg0) {
    }

    public void onDocumentReady() {
        this.act.runOnUiThread(new Runnable() {
            public void run() {
                PPTViewer.this.navitator = new SlideShowNavigator(PPTViewer.this.session.getPPTContext());
                PPTViewer.this.currentSlideNumber = PPTViewer.this.navitator.getFirstSlideNumber() - 1;
                PPTViewer.this.next();
                PPTViewer.this.pb.setVisibility(ProgressBar.INVISIBLE);
                PPTViewer.this.slide.setVisibility(ProgressBar.VISIBLE);
            }
        });
    }

    public void onSessionEnded() {
    }

    public void onSessionStarted() {
    }

    private void navigateTo(int slideNumber) {
        this.log(Integer.valueOf(slideNumber));
        this.tv.setText(slideNumber + " / " + this.getTotalSlides());
        SlideView slideShow = this.navitator.navigateToSlide(this.slide.getGraphicsContext(), slideNumber);
        this.slide.setContentView(slideShow);
    }

    public void next() {
        if(this.navitator != null) {
            if(this.navitator.getFirstSlideNumber() + this.navitator.getSlideCount() - 1 > this.currentSlideNumber) {
                this.navigateTo(++this.currentSlideNumber);
            } else {
                //this.toast("IT\'S THE LAST SLIDE");
            }
        }

    }

    public void prev() {
        if(this.navitator != null) {
            if(this.navitator.getFirstSlideNumber() < this.currentSlideNumber) {
                this.navigateTo(--this.currentSlideNumber);
            } else {
                //this.toast("IT\'S THE FIRST SLIDE");
            }
        }

    }

    public void pause() {
        if(this.navitator != null && PPTViewer.this.slide.getVisibility() == VISIBLE) {
            PPTViewer.this.slide.setVisibility(ProgressBar.INVISIBLE);
        }
    }

    public void resume() {
        if(this.navitator != null && PPTViewer.this.slide.getVisibility() == INVISIBLE) {
            PPTViewer.this.slide.setVisibility(ProgressBar.VISIBLE);
        }
    }

}
