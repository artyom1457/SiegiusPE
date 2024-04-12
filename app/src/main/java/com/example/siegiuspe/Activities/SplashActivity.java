package com.example.siegiuspe.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.ProgressBar;

import com.example.siegiuspe.AnimationLibrary;
import com.example.siegiuspe.ContextParameters;
import com.example.siegiuspe.GameObjects.BaseObject;
import com.example.siegiuspe.R;
import com.example.siegiuspe.RenderSystem;
import com.example.siegiuspe.SettingsParameters;
import com.example.siegiuspe.Managers.SoundManager;
import com.example.siegiuspe.Managers.SystemUiManager;
import com.example.siegiuspe.Utils;

import java.util.Objects;


public class SplashActivity extends AppCompatActivity {

    SystemUiManager mSysManager;
    ProgressBar progressBar;
    int counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        progressBar = (ProgressBar) findViewById(R.id.progress_horizontal);
        mSysManager = new SystemUiManager(getWindow().getDecorView());
//        mSysManager.showSystemUI();

        Objects.requireNonNull(getSupportActionBar()).setTitle("");
        ContextParameters params = new ContextParameters();
        params.context = this;
        BaseObject.sSystemRegistry.contextParameters = params;
        BaseObject.sSystemRegistry.systemUiManager = mSysManager;
        loadSettings();

        Thread bootstrap = new Thread() {
            public void run() {
                try {
                    BaseObject.sSystemRegistry.soundManager = new SoundManager();
                    counter += 4;
                    BaseObject.sSystemRegistry.renderSystem = new RenderSystem();
                    counter += 4;
                    BaseObject.sSystemRegistry.animationLibrary = new AnimationLibrary();
                    counter += 4;
                    BaseObject.sSystemRegistry.animationLibrary.loadLegionaryAnimation();
                    counter += 4;
                    BaseObject.sSystemRegistry.animationLibrary.loadDefenderAnimation();
                    counter += 4;
                    BaseObject.sSystemRegistry.animationLibrary.loadRomanCavalryAnimation();
                    counter += 4;
                    BaseObject.sSystemRegistry.animationLibrary.loadRomanArcherAnimation();
                    counter += 4;
                    BaseObject.sSystemRegistry.animationLibrary.loadRomanFireArcherAnimation();
                    counter += 4;
                    BaseObject.sSystemRegistry.animationLibrary.loadCenturionAnimation();
                    counter += 4;
                    BaseObject.sSystemRegistry.animationLibrary.loadCaesarAnimation();
                    counter += 4;
                    BaseObject.sSystemRegistry.animationLibrary.loadRomanBaseAnimation();
                    counter += 5;
                    BaseObject.sSystemRegistry.animationLibrary.loadRaiderAnimation();
                    counter += 5;
                    BaseObject.sSystemRegistry.animationLibrary.loadBerserkerAnimation();
                    counter += 5;
                    BaseObject.sSystemRegistry.animationLibrary.loadBehemothAnimation();
                    counter += 5;
                    BaseObject.sSystemRegistry.animationLibrary.loadBoarRiderAnimation();
                    counter += 5;
                    BaseObject.sSystemRegistry.animationLibrary.loadMaceRiderAnimation();
                    counter += 5;
                    BaseObject.sSystemRegistry.animationLibrary.loadGaulArcherAnimation();
                    counter += 5;
                    BaseObject.sSystemRegistry.animationLibrary.loadVercingAnimation();
                    counter += 5;
                    BaseObject.sSystemRegistry.animationLibrary.loadBloodSmallAnimation();
                    counter += 5;
                    BaseObject.sSystemRegistry.animationLibrary.loadFireAnimation();
                    counter += 5;
                    BaseObject.sSystemRegistry.animationLibrary.loadGaulBaseAnimation();
                    counter += 5;
                    BaseObject.sSystemRegistry.animationLibrary.loadProjectiles();
                    counter += 5;


                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                }
            }
        };

        final Thread progress = new Thread()
        {
            public void run() {
                while(counter < 100)
                {
                    progressBar.setProgress(counter);
                }
            }
        };

        progress.start();
        bootstrap.start();
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        mSysManager.delayedHideALL(SystemUiManager.INITIAL_HIDE_DELAY);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus)
    {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            mSysManager.delayedHideALL(SystemUiManager.INITIAL_HIDE_DELAY);
        } else {
            mSysManager.removeMessages(0);
        }
    }

    private void loadSettings() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(BaseObject.sSystemRegistry.contextParameters.context);

        SettingsParameters.play_music = sharedPreferences.getBoolean(SettingsParameters.PREF_MUSIC, true);
        SettingsParameters.play_SFX  = sharedPreferences.getBoolean(SettingsParameters.PREF_SFX, true);
        SettingsParameters.show_blood = sharedPreferences.getBoolean(SettingsParameters.PREF_BLOOD, true);
        SettingsParameters.show_hp =  sharedPreferences.getBoolean(SettingsParameters.PREF_HP, true);
        SettingsParameters.show_dmg = sharedPreferences.getBoolean(SettingsParameters.PREF_DMG, true);
        Utils.MAX_FPS = Integer.parseInt(sharedPreferences.getString(SettingsParameters.PREF_FPS, "10"));

    }

}