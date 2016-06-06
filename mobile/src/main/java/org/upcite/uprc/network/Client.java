package org.upcite.uprc.network;

import android.content.Context;
import android.os.AsyncTask;
import android.preference.PreferenceManager;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Created by emman on 9/27/2015.
 */
public class Client {

    private static String ip_address;
    private static int port;
    private static String type;

    private static DatagramSocket socket;
    private static DatagramPacket packet;

    private static Context context;

    public Client(Context context, String ip_address, int port) {
        Client.context = context;
        Client.ip_address = ip_address;
        Client.port = port;
    }

    public static void executeKeyboard(int command) {
        new Command().execute(command);
    }

    public static void executeMouse(int command, double cursor_x, double cursor_y) {
        new Command().execute(command, cursor_x, cursor_y);
    }

    private static class Command extends AsyncTask<Object, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ip_address = PreferenceManager.getDefaultSharedPreferences(context).getString("pref_ipAddress", "127.0.0.1");
            type = PreferenceManager.getDefaultSharedPreferences(context).getString("pref_hostSoftware", "0");
        }

        @Override
        protected Void doInBackground(Object... params) {
            String line;
            if (params.length == 1)
                line = type + "\n" + params[0];
            else
                line = type + "\n" + params[0] + "\n" +
                        Double.toString((Double) params[1]) + "\n" +
                        Double.toString((Double) params[2]);
            try {
                socket = new DatagramSocket();
                packet = new DatagramPacket(line.getBytes(), line.length(), InetAddress.getByName(ip_address), port);
                socket.send(packet);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (socket != null)
                    socket.close();
            }
            return null;
        }
    }

}
