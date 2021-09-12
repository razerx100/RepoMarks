package com.example.snotes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.snotes.databinding.ActivityReaderBinding;

public class ReaderActivity extends AppCompatActivity {
    private ActivityReaderBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityReaderBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        Toolbar toolbar = binding.toolbarEditor;
        setSupportActionBar(toolbar);

        accept_incoming_data();
    }

    public void accept_incoming_data(){
        Intent intent = getIntent();
        if(intent.hasExtra(notesFragment.TAG)) {
            String[] receivedData = intent.getStringArrayExtra(notesFragment.TAG);

            TextView title_editor = binding.titleEditor;
            title_editor.setText(receivedData[1]);

            SetBodyTextAsync(intent.getStringExtra(repoFragment.TAG));
        }
    }

    private void SetBodyTextAsync(String url) {
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        TextView textBody = binding.textEditor;
                        textBody.setText(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {}
                }
        );

        NetworkManagerSingleton.Get().AddToRequestQueue(stringRequest);
    }
}