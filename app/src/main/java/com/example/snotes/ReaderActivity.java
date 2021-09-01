package com.example.snotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

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
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        accept_incoming_data();
    }

    public void accept_incoming_data(){
        Intent intent = getIntent();
        if(intent.hasExtra(notesFragment.TAG)) {
            String[] title_and_content = intent.getStringArrayExtra(notesFragment.TAG);
            TextView content_editor = binding.textEditor;
            TextView title_editor = binding.titleEditor;
            content_editor.setText(title_and_content[1]);
            title_editor.setText(title_and_content[0]);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.reader_toolbar_buttons, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}