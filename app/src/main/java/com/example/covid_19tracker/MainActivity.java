package com.example.covid_19tracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.covid_19tracker.adapter.Adapter;
import com.example.covid_19tracker.models.Model;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    ArrayList<Model> list;
    Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);

        list = new ArrayList<>();

        adapter = new Adapter(list, this);
        recyclerView.setAdapter(adapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        getData();
    }

    private void getData() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        try {
            String apiLink = "https://data.covid19india.org/state_district_wise.json";
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, apiLink, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Iterator<String> iterator = response.keys();
                    iterator.next();
                    while (iterator.hasNext()) {
                        String state_name = iterator.next();
                        try {
                            JSONObject states = response.getJSONObject(state_name);
                            JSONObject district_data = states.getJSONObject("districtData");
                            Iterator<String> districts = district_data.keys();
                            while (districts.hasNext()) {
                                String district_name = districts.next();
                                JSONObject district = district_data.getJSONObject(district_name);
                                int nos_active = district.getInt("active");
                                int nos_recovered = district.getInt("recovered");
                                int nos_confirmed = district.getInt("confirmed");
                                int nos_deceased = district.getInt("deceased");

                                Log.d("response", "onResponse: " + state_name + " " + district_name);
                                list.add(new Model(state_name, district_name, nos_active, nos_confirmed, nos_deceased, nos_recovered));
                                Log.d("response", "onResponse: added");
                                adapter.notifyDataSetChanged();
                                Log.d("response", "onResponse: displayed");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                }
            });
            requestQueue.add(jsonObjectRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

