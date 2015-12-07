package com.example.mcvjetinovic.instagramclient.client;

import android.content.res.Resources;
import android.util.Log;

import com.example.mcvjetinovic.instagramclient.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by mcvjetinovic on 12/2/15.
 */
public class InstagramClient {
    private static final String INSTAGRAM_CLIENT = "INSTAGRAM_CLIENT";
    private static final String BASE_URL = "https://api.instagram.com/v1/media/popular?client_id=e05c462ebd86446ea48a5af73769b602";
    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void get(AsyncHttpResponseHandler callback,Resources resources, boolean mock){
        if(mock){
            Log.d(INSTAGRAM_CLIENT, "MOCK MODE:" + mock);
            try {

                callback.onSuccess(200, null, readResource(resources.openRawResource(R.raw.simple)) );
            } catch (Exception e) {
                Log.e(INSTAGRAM_CLIENT,"Error getting resource:" + e.getMessage());
                callback.onFailure(404, null, null, e );
            }
        } else {
            client.get(BASE_URL, callback);
        }
    }






    private static byte[] readResource(InputStream inputStream) {
        Log.d(INSTAGRAM_CLIENT,"READING RESOURCE");

        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        try {
            int nRead;
            byte[] data = new byte[16384];

            while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }

            buffer.flush();

            return buffer.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static byte[] readFile(String filePath) {

        File f = new File(filePath);
        FileInputStream fin = null;
        FileChannel ch = null;


        try {
            fin = new FileInputStream(f);
            ch = fin.getChannel();
            int size = (int) ch.size();
            MappedByteBuffer buf = ch.map(FileChannel.MapMode.READ_ONLY, 0, size);
            byte[] bytes = new byte[size];
            buf.get(bytes);
            return buf.array();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                if (fin != null) {
                    fin.close();
                }
                if (ch != null) {
                    ch.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
