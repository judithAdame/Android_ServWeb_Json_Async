package com.example.monappserviceweb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickMoyenne (View Main2Activity) {
        TextView tvLimite = (TextView) findViewById(R.id.editTextLimite);
        String limite = tvLimite.getText().toString();
        System.out.println("\n limite: " + limite);
        Intent monIntent = new Intent(this, Main2Activity.class);
        monIntent.putExtra("limite", Double.parseDouble(limite));
        startActivity(monIntent);
    }

}
