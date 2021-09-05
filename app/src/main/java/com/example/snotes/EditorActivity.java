package com.example.snotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.snotes.databinding.ActivityEditorBinding;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

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
        ab.setDisplayHomeAsUpEnabled(true);
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
            String title = binding.titleEditor.getText().toString();
            String content = binding.textEditor.getText().toString();
            if(title.isEmpty()){
                Snackbar snackbar = Snackbar.make(binding.getRoot(), "Please provide an Owner's name", BaseTransientBottomBar.LENGTH_SHORT);
                snackbar.show();
            }
            else {
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        NameDBSingelton.GetDB().getRepoDao().InsertEntity(title, content);
                    }
                });
                go_back_to_main();
            }
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
}