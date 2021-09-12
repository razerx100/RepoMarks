package com.example.snotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.snotes.databinding.ActivityEditorBinding;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONObject;

public class EditorActivity extends AppCompatActivity {
    private ActivityEditorBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditorBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        Toolbar toolbar = binding.toolbarEditor;
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        try {
            ab.setDisplayHomeAsUpEnabled(true);
        }
        catch (NullPointerException e) {}
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.editor_toolbar_buttons, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.done_action){
            HideKeyboard();

            String title = FormatData(binding.titleEditor.getText());
            String content = FormatData(binding.textEditor.getText());

            if(title.isEmpty() || content.isEmpty()){
                Snackbar snackbar = Snackbar.make(binding.getRoot(), "Please provide an Owner's and a repo name", BaseTransientBottomBar.LENGTH_SHORT);
                snackbar.show();
            }
            else
                CheckRepoAndOwner();

            return true;
        }
        else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void go_back_to_main(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private String FormatData(Editable editable) {
        return editable.toString().trim();
    }

    private void CheckRepoAndOwner() {
        String url = "https://api.github.com/repos/"
                + FormatData(binding.titleEditor.getText()) + "/"
                + FormatData(binding.textEditor.getText());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        AsyncTask.execute(new Runnable() {
                            @Override
                            public void run() {
                                NameDBSingleton.GetDB().getRepoDao().InsertEntity(
                                        FormatData(binding.titleEditor.getText()),
                                        FormatData(binding.textEditor.getText())
                                );
                            }
                        });

                        go_back_to_main();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Snackbar snackbar = Snackbar.make(binding.getRoot(),
                                "Owner or Repository doesn't exist.", BaseTransientBottomBar.LENGTH_SHORT);
                        snackbar.show();
                    }
                }
        );

        NetworkManagerSingleton.Get().AddToRequestQueue(jsonObjectRequest);
    }

    private void HideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        try {
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        catch (NullPointerException e) {}
    }
}