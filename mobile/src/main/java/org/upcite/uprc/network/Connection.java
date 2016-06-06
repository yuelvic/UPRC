package org.upcite.uprc.network;

import android.content.Context;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.widget.Toast;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Created by Emman on 10/8/2015.
 */
public class Connection extends AsyncTask<Void, Void, Void> {

    private Context context;
    private String ipAddress;
    private int port;

    private Socket socket;
    private boolean isConnected = false;

    public Connection(Context context, String ipAddress, int port) {
        this.context = context;
        this.ipAddress = ipAddress;
        this.port = port;
    }

    @Override
    protected Void doInBackground(Void... params) {
        socket = new Socket();
        try {
            socket.connect(new InetSocketAddress(
                    PreferenceManager.getDefaultSharedPreferences(context).getString("pref_ipAddress", "127.0.0.1"),
                    4545
            ), 5000);
            isConnected = true;
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
            isConnected = false;
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if (isConnected)
            Toast.makeText(context, "Connection successful", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(context, "Connection timeout", Toast.LENGTH_LONG).show();
    }
}
