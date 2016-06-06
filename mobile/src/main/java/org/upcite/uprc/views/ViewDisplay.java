package org.upcite.uprc.views;

/**
 * Created by emman on 7/28/15.
 */
public class ViewDisplay {

    public static final int SLIDESHOW           = 0x00;
    public static final int MOUSE               = 0x01;
    public static final int MEDIA               = 0x02;
    public static final int TIMER               = 0x03;
    public static final int PRESENT             = 0x04;
    public static final int TRANSFER            = 0x05;
    public static final int SETTINGS            = 0x06;

    private static int current;

    public static void setViewOnDisplay(int display) {
        current = display;
    }

    public static int getViewOnDisplay() {
        return current;
    }

}
