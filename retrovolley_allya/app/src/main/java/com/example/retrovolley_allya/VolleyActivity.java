package com.example.retrovolley_allya;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import adapter.UserAdapter;

public class VolleyActivity extends AppCompatActivity {

    private final String TAG= getClass().getSimpleName();
    private ListView lvUserVolley;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volley);

        lvUserVolley = findViewById(R.id.lv_user_volley);
        setTitle(getString(R.string.volley));
        getUserFromApi();
    }

    private void getUserFromApi() {

    }

    public void actionRefresh(View view) {
        finish();
    }

    public void actionClose(View view) {
        getUserFromApi();
    }
}