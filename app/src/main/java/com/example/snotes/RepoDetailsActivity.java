package com.example.snotes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.snotes.databinding.ActivityRepoDetailsBinding;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class RepoDetailsActivity extends AppCompatActivity {

    private ActivityRepoDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRepoDetailsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        ProcessReceivedData();

        Toolbar toolbar = binding.contentLayoutRepo.toolbarContent;
        setSupportActionBar(toolbar);

        ActionBar ab = getSupportActionBar();
        try {
            ab.setDisplayHomeAsUpEnabled(true);
        }
        catch (NullPointerException e) {}
    }

    public void ProcessReceivedData(){
        Intent intent = getIntent();
        if(intent.hasExtra(notesFragment.TAG)) {
            String[] ownerAndRepo = intent.getStringArrayExtra(notesFragment.TAG);

            repoFragment fragment = (repoFragment)
                    getSupportFragmentManager().findFragmentById(R.id.repo_fragment);

            binding.contentLayoutRepo.toolbarContent.setTitle(ownerAndRepo[1]);

            String url = "https://api.github.com/repos/"
                    + ownerAndRepo[0] + "/"
                    + ownerAndRepo[1] + "/contents/";

            JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(
                    Request.Method.GET, url, null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            List<String> fileNames = new ArrayList<>();

                            for (int i = 0; i < response.length(); ++i) {
                                try {
                                    fileNames.add(response.getJSONObject(i).getString("path"));
                                } catch (JSONException e) {
                                }
                            }

                            fragment.ChangeItemList(fileNames);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) { }
                    }
            );

            NetworkManagerSingleton.Get().AddToRequestQueue(jsonObjectRequest);
        }
    }
}