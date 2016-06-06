package org.upcite.uprc.utils;

/**
 * Created by emman on 7/24/15.
 */
public class Presentation {

    public static final class Type {

        public static final int POWERPOINT      = 0x00;
        public static final int IMPRESS         = 0x01;
        public static final int KEYNOTE         = 0x02;
        public static final int PREZI           = 0x03;

    }

    public static final class Control {

        public static final class Slide {

            public static final int START           = 0x00;
            public static final int STOP            = 0x01;
            public static final int PAUSE           = 0x02;
            public static final int SELECT          = 0x03;
            public static final int UP              = 0x04;
            public static final int DOWN            = 0x05;
            public static final int PREVIOUS        = 0x06;
            public static final int NEXT            = 0x07;
            public static final int HOME            = 0x08;
            public static final int END             = 0x09;
            public static final int ZOOM_IN         = 0x0A;
            public static final int ZOOM_OUT        = 0x0B;
            public static final int ARROW           = 0x0C;
            public static final int PEN             = 0x0D;
            public static final int ERASE           = 0x0E;
            public static final int LASER           = 0x0F;
            public static final int HIDE            = 0x10;

            public static final int POWER           = 0x30;
            public static final int SKIP            = 0x31;

        }

        public static final class Media {

            public static final int PLAY            = 0x11;
            public static final int STOP            = 0x12;
            public static final int PAUSE           = 0x13;
            public static final int VOLUME_UP       = 0x14;
            public static final int VOLUME_DOWN     = 0x15;
            public static final int MUTE            = 0x16;
            public static final int FORWARD         = 0x17;
            public static final int BACKWARD        = 0x18;

        }

    }

    public static final class Mouse {

        public static final int LEFT_CLICK_DOWN             = 0x19;
        public static final int LEFT_CLICK_UP               = 0x1A;
        public static final int RIGHT_CLICK_DOWN            = 0x1B;
        public static final int RIGHT_CLICK_UP              = 0x1C;
        public static final int MOUSE_MOVE                  = 0x1D;

    }

    public static final class Transfer {

        public static final int FILE            = 60;
        public static final int NAME            = 61;

    }

}
