package org.upcite.uprc.utils;

/**
 * Created by Emman on 10/5/2015.
 */
public class WearService {

    public static final int COMPUTER    = 1;
    public static final int MOBILE      = 2;

    public static int IN_USE            = 0;

    public static void setInUse(int service) {
        IN_USE = service;
    }

    public static boolean isComputer() {
        return IN_USE == COMPUTER;
    }

    public static boolean isMobile() {
        return IN_USE == MOBILE;
    }

}
