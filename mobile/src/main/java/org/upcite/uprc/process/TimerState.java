package org.upcite.uprc.process;

/**
 * Created by emman on 9/29/2015.
 */
public class TimerState {

    private static final int DEAD    = 1;
    private static final int ALIVE   = 2;
    private static final int PAUSE   = 3;

    private static int state = 1;

    public static boolean isDead() {
        return state == DEAD;
    }

    public static boolean isAlive() {
        return state == ALIVE;
    }

    public static boolean isPaused() {
        return state == PAUSE;
    }

    public static void die() {
        state = DEAD;
    }

    public static void rise() {
        state = ALIVE;
    }

    public static void pause() {
        state = PAUSE;
    }

}
