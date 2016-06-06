package org.upcite.uprc.network;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.preference.PreferenceManager;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by Emman on 10/17/2015.
 */
public class Transfer extends AsyncTask<Void, Void, Void> {

    private Context context;
    private String path;

    private Socket socket;
    private File file;
    private OutputStream outputStream;
    private FileInputStream fileStream;
    private BufferedInputStream bufferedStream;

    private ProgressDialog dialog;
    private String ip_address;
    private final int PORT = 4546;
    private byte[] bytes;

    public Transfer(Context context, String path) {
        this.context = context;
        this.path = path;

        file = new File(path);
        bytes = new byte[4096];
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog = new ProgressDialog(context);
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.setMessage("Sending file");
        dialog.show();

        ip_address = PreferenceManager.getDefaultSharedPreferences(context).getString("pref_ipAddress", "127.0.0.1");
    }

    @Override
    protected Void doInBackground(Void... params) {
        int count;
        try {
            socket = new Socket(ip_address, PORT);
            outputStream = socket.getOutputStream();
            fileStream = new FileInputStream(file);
            bufferedStream = new BufferedInputStream(fileStream);
            while ((count = bufferedStream.read(bytes)) > 0) {
                outputStream.write(bytes, 0, count);
            }
            outputStream.flush();
            bufferedStream.close();
            fileStream.close();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (socket != null)
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if (dialog.isShowing())
            dialog.dismiss();
    }
}
