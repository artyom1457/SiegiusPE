package com.example.siegiuspe;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import androidx.preference.ListPreference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreferenceCompat;

import com.example.siegiuspe.GameObjects.BaseObject;

public class SettingsFragment extends PreferenceFragmentCompat {

    private static SharedPreferences sharedPreferences;
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
    }


    private void loadSettings() {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(BaseObject.sSystemRegistry.contextParameters.context);

        musicPref(sharedPreferences);
        SFXPref(sharedPreferences);
        bloodPref(sharedPreferences);
        HpPref(sharedPreferences);
        DMGPref(sharedPreferences);
        fpsPref(sharedPreferences);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadSettings();
    }

    private void musicPref(SharedPreferences sharedPreferences) {
        //        SoundManager.setPlayMusic(sw);
        SettingsParameters.play_music = sharedPreferences.getBoolean(SettingsParameters.PREF_MUSIC, true);

        SwitchPreferenceCompat swPref = findPreference(SettingsParameters.PREF_MUSIC);
        assert swPref != null;
        swPref.setOnPreferenceChangeListener((preference, newValue) -> {
            SettingsParameters.play_music = (boolean) newValue;
//                SoundManager.setPlayMusic(yes);
            return true;
        });
    }

    private void SFXPref(SharedPreferences sharedPreferences) {
        SettingsParameters.play_SFX  = sharedPreferences.getBoolean(SettingsParameters.PREF_SFX, true);

        SwitchPreferenceCompat swPref = findPreference(SettingsParameters.PREF_SFX);
        assert swPref != null;

        swPref.setOnPreferenceChangeListener((preference, newValue) -> {
            SettingsParameters.play_SFX = (Boolean) newValue;
            return true;
        });
    }

    private void bloodPref(SharedPreferences sharedPreferences) {
        SettingsParameters.show_blood = sharedPreferences.getBoolean(SettingsParameters.PREF_BLOOD, true);

        SwitchPreferenceCompat swPref = findPreference(SettingsParameters.PREF_BLOOD);
        assert swPref != null;

        swPref.setOnPreferenceChangeListener((preference, newValue) -> {
            SettingsParameters.show_blood = (Boolean) newValue;
            return true;
        });
    }

    private void HpPref(SharedPreferences sharedPreferences) {
        SettingsParameters.show_hp =  sharedPreferences.getBoolean(SettingsParameters.PREF_HP, true);

        SwitchPreferenceCompat swPref = findPreference(SettingsParameters.PREF_HP);
        assert swPref != null;

        swPref.setOnPreferenceChangeListener((preference, newValue) -> {
            SettingsParameters.show_hp = (Boolean) newValue;
            return true;
        });
    }

    private void DMGPref(SharedPreferences sharedPreferences) {
        SettingsParameters.show_dmg = sharedPreferences.getBoolean(SettingsParameters.PREF_DMG, true);

        SwitchPreferenceCompat swPref = findPreference(SettingsParameters.PREF_DMG);
        assert swPref != null;

        swPref.setOnPreferenceChangeListener((preference, newValue) -> {
            SettingsParameters.show_dmg = (Boolean) newValue;
            return true;
        });
    }

    private void fpsPref(SharedPreferences sharedPreferences) {
        final ListPreference listPreference = findPreference(SettingsParameters.PREF_FPS);
        String fps = sharedPreferences.getString(SettingsParameters.PREF_FPS, "30");
        assert listPreference != null;
        listPreference.setSummary(listPreference.getEntries()[listPreference.findIndexOfValue(fps)] + " fps");
        switch (fps) {
            case "10":
                Utils.MAX_FPS = 10;
                break;
            case "20":
                Utils.MAX_FPS = 20;
                break;
            case "30":
                Utils.MAX_FPS = 30;
                break;
        }
        
        listPreference.setOnPreferenceChangeListener((preference, newValue) -> {

            String string = (String) newValue;
            if (preference.getKey().equals(SettingsParameters.PREF_FPS)) {
                switch (string) {
                    case "10":
                        Utils.MAX_FPS = 10;
                        break;
                    case "20":
                        Utils.MAX_FPS = 20;
                        break;
                    case "30":
                        Utils.MAX_FPS = 30;
                        break;
                }

                ListPreference lpp = (ListPreference) preference;
                lpp.setSummary(lpp.getEntries()[lpp.findIndexOfValue(string)]+ " fps");
            }
            return true;
        });
    }

    public static void setPerformanceDrop()
    {
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putBoolean(SettingsParameters.PREF_BLOOD,false);
        editor.putBoolean(SettingsParameters.PREF_DMG,false);
        editor.putBoolean(SettingsParameters.PREF_HP,false);
        editor.putString(SettingsParameters.PREF_FPS,"10");
        editor.apply();

        SettingsParameters.show_blood = false;
        SettingsParameters.show_dmg = false;
        SettingsParameters.show_hp = false;
        Utils.MAX_FPS = 10;
    }

}
