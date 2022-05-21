package com.example.grabyoutubevideoinformation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    EditText youtube_link;
    OkHttpClient client = new OkHttpClient();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        youtube_link=(EditText)findViewById(R.id.youtube_url);
    }
    public void get_Info(View view)
    {
        //String link=youtube_link.getText().toString();
        String link="ggkj9k_MXt8";
        String youTube_link="https://www.youtube.com/watch?";
        Toast.makeText(this, youTube_link, Toast.LENGTH_SHORT).show();
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try  {
                    //Your code goes here
                    Request request = new Request.Builder()
                            .url(youTube_link)
                            .get()
                            .addHeader("v",link)
                            //.addHeader("ab_channel","QuesoMonstar")
                            //.addHeader("Content-Type","application/json")
                            .build();
                    try {
                        Response response = client.newCall(request).execute();

                        client.newCall(request).enqueue(new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                call.cancel();
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {

                                final String myResponse = response.body().string();

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Pattern title_pattern = Pattern.compile("(\\btitle\\b)(.*?)(\\btitle\\b)");
                                        Matcher title_matcher = title_pattern.matcher(myResponse);
                                        List<String> matches = new ArrayList<String>();
                                        while (title_matcher.find()) {
                                            matches.add(title_matcher.group(2));
                                        }
                                        for(int i=0;i<matches.size();i++)
                                            Log.i("title",matches.get(i));
                                    }
                                });
                            }
                        });

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
    }
}