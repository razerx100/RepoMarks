package com.example.snotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.snotes.databinding.ActivityMainBinding;
import com.google.android.material.appbar.AppBarLayout;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NameDBSingelton.InitDB(getApplicationContext());
        NetworkManagerSingleton.Init(getApplicationContext());
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        Toolbar toolbar = binding.contentLayout.toolbarContent;
        setSupportActionBar(toolbar);

        maintain_title();
    }

    public void launch_editor_activity(View view){
        Intent intent = new Intent(this, EditorActivity.class);
        startActivity(intent);
    }

    private void maintain_title(){
        AppBarLayout appBarLayout = binding.appbar;
        Toolbar extended_toolbar = binding.toolbarHome;
        final Toolbar collapsed_toolbar = binding.contentLayout.toolbarContent;
        final String app_name = getResources().getString(R.string.app_name);
        extended_toolbar.setTitle(app_name);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if(Math.abs(verticalOffset) - appBarLayout.getTotalScrollRange() == 0){
                    collapsed_toolbar.setTitle(app_name);
                }
                else{
                    collapsed_toolbar.setTitle("");
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_option, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int item_id = item.getItemId();
        notesFragment fragment = (notesFragment) getSupportFragmentManager().findFragmentById(R.id.notes_fragment);
        if(item_id == R.id.sort_action_alpha){
            fragment.update_list(0);
            return true;
        }
        else if(item_id == R.id.sort_action_date){
            fragment.update_list(1);
            return true;
        }
        else {
            return super.onOptionsItemSelected(item);
        }
    }
}