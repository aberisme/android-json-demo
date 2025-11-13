package com.example.demojson;

import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        fetchJSONData();

    }

    private void fetchJSONData() {
        String urlString = "https://jsonplaceholder.typicode.com/posts/1";

        // establish connection to URL
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder jsonBuilder = new StringBuilder();
            String line;

            while((line = reader.readLine()) != null) {
                jsonBuilder.append(line);
            }

            reader.close();

            // extract JSON data
            JSONObject json = new JSONObject(jsonBuilder.toString());
            int id = json.getInt("id");
            String title = json.getString("title");
            String body = json.getString("body");

            String result = "ID: " + id + "\nTitle: " + title + "\nBody: " + body;
            Log.d("JSON", result);

        } catch(Exception e) {
            Log.wtf("JSON Error", e.getMessage());
        }
    }
}