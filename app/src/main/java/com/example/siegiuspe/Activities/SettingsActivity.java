package com.example.siegiuspe.Activities;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.siegiuspe.GameObjects.BaseObject;
import com.example.siegiuspe.R;
import com.example.siegiuspe.SettingsFragment;
import com.example.siegiuspe.Managers.SystemUiManager;

public class SettingsActivity extends AppCompatActivity {

    ImageButton back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);

        BaseObject.sSystemRegistry.systemUiManager.changeDecorView(getWindow().getDecorView());
        BaseObject.sSystemRegistry.systemUiManager.hideSystemUIALL();

        back = (ImageButton)findViewById(R.id.settings_back_button);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.settings, new SettingsFragment())
                    .commit();
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        setTheme(R.style.SettingsFragmentStyle);

    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        BaseObject.sSystemRegistry.systemUiManager.delayedHideALL(SystemUiManager.INITIAL_HIDE_DELAY);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus)
    {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            BaseObject.sSystemRegistry.systemUiManager.delayedHideALL(SystemUiManager.INITIAL_HIDE_DELAY);
        } else {
            BaseObject.sSystemRegistry.systemUiManager.removeMessages(0);
        }
    }
}