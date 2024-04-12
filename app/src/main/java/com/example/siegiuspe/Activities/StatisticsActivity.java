package com.example.siegiuspe.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.siegiuspe.GameObjects.BaseObject;
import com.example.siegiuspe.Managers.SystemUiManager;
import com.example.siegiuspe.R;

public class StatisticsActivity extends AppCompatActivity {

    ImageButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        BaseObject.sSystemRegistry.systemUiManager.changeDecorView(getWindow().getDecorView());
        BaseObject.sSystemRegistry.systemUiManager.hideSystemUIALL();

        back = (ImageButton)findViewById(R.id.statistics_back_button);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

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