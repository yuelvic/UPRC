package org.upcite.uprc.network;

import android.content.Context;
import android.os.AsyncTask;
import android.preference.PreferenceManager;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Created by Emman on 10/21/2015.
 */
public class Filename {

    private DatagramSocket socket;
    private DatagramPacket packet;

    private static String ip_address;
    private final int port = 4547;

    private Context context;
    private String name;

    public Filename(Context context, String name) {
        this.context = context;
        this.name = name;

        ip_address = PreferenceManager.getDefaultSharedPreferences(context).getString("pref_ipAddress", "127.0.0.1");
        new TransferName().execute();
    }

    private class TransferName extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                socket = new DatagramSocket();
                packet = new DatagramPacket(name.getBytes(), name.length(), InetAddress.getByName(ip_address), port);
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
