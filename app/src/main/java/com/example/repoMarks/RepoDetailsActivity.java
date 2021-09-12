package com.example.repoMarks;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.repoMarks.databinding.ActivityRepoDetailsBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    }

    public void ProcessReceivedData(){
        Intent intent = getIntent();
        if(intent.hasExtra(notesFragment.TAG)) {
            String[] receivedData = intent.getStringArrayExtra(notesFragment.TAG);

            repoFragment fragment = (repoFragment)
                    getSupportFragmentManager().findFragmentById(R.id.repo_fragment);

            binding.contentLayoutRepo.toolbarContent.setTitle(receivedData[0] + "/" + receivedData[1]);

            String url = getString(R.string.api_url)
                    + receivedData[0] + "/contents/" + receivedData[1];

            JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(
                    Request.Method.GET, url, null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            List<RepoContent.RepoItem> repoData = new ArrayList<>();

                            for (int i = 0; i < response.length(); ++i) {
                                try {
                                    JSONObject obj = response.getJSONObject(i);
                                    String fileName = obj.getString("path");

                                    repoData.add(RepoContent.createNoteItem(
                                            fileName,
                                            obj.getString("type"),
                                            receivedData[0],
                                            obj.getString("download_url")
                                    ));
                                } catch (JSONException e) {}
                            }

                            fragment.ChangeItemList(repoData);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {}
                    }
            );

            NetworkManagerSingleton.Get().AddToRequestQueue(jsonObjectRequest);
        }
    }
}