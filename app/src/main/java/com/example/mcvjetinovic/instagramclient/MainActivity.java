package com.example.mcvjetinovic.instagramclient;

import android.content.Context;
import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.mcvjetinovic.instagramclient.client.InstagramClient;
import com.example.mcvjetinovic.instagramclient.model.InstagramModel;
import com.example.mcvjetinovic.instagramclient.model.InstagramViewAdapter;
import com.loopj.android.http.JsonHttpResponseHandler;


import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String MAIN_ACTIVITY = "INSTAGRAM_MAIN";
    private ArrayList<InstagramModel> instagramList;
    private InstagramViewAdapter listAdapter;


//    public static String AssetJSONFile (String filename, Context context) throws IOException {
//        AssetManager manager = context.getAssets();
//        InputStream file = manager.open(filename);
//        byte[] formArray = new byte[file.available()];
//        file.read(formArray);
//        file.close();
//
//        return new String(formArray);
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        instagramList = new ArrayList<>();
        listAdapter = new InstagramViewAdapter(this, instagramList);

        final ListView imageList = (ListView) findViewById(R.id.listView);
        imageList.setAdapter(listAdapter);

        InstagramClient.get(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                Log.d(MAIN_ACTIVITY, "statusCode:" + statusCode);

//                for (Header h: headers) {
//                    Log.d(MAIN_ACTIVITY, h.getName() + ":" + h.getValue());
//                }


                if(response != null) {
                    Log.d(MAIN_ACTIVITY, response.toString());
                }

                try {

                    JSONArray data = response.getJSONArray("data");
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject post = (JSONObject) data.get(i);
                        String type = post.getString("type");

                        if( type == null || !type.equals("image")) continue;
                        Log.d(MAIN_ACTIVITY, "post:" + post);

                        InstagramModel model = new InstagramModel(post);
                        instagramList.add(model);
                        Log.d(MAIN_ACTIVITY, model.toString());
                    }
//                    Log.d(MAIN_ACTIVITY ,"URL:" + url);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                listAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(int statusCode, org.apache.http.Header[] headers, Throwable throwable, JSONArray errorResponse) {
                Log.d(MAIN_ACTIVITY, "statusCode:" + statusCode, throwable);
                if(errorResponse != null) {
                    Log.d(MAIN_ACTIVITY, errorResponse.toString());
                }
                instagramList = new ArrayList<>();
                listAdapter.notifyDataSetChanged();
            }

        },getResources(), false);

    }
}
