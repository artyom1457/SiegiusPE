package com.example.siegiuspe.Managers;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.media.SoundPool;

import com.example.siegiuspe.GameObjects.BaseObject;
import com.example.siegiuspe.R;
import com.example.siegiuspe.Utils;

import java.util.ArrayList;

public class SoundManager {

    private Context context;
    private MediaPlayer mediaPlayer;
    private SoundPool sp;

    private static boolean playSFX;
    private static boolean playMusic;

    private int [] cut;
    private int [] die;
    private int [] whop;
    private int [] blunt;
    private int [] whip;


    private int [] boarRider;
    private int horseID;
    private int lengthMenuMusic;

    final ArrayList<Integer> songs = new ArrayList<>();

    public static final int INTRO = 0 , SELECTION_MENU = 1 , ARMY = 2, ARMY2 = 3, CHAMP = 4, CHAOTIC = 5
    , EGYPT = 6, GATE = 7, GATE2 = 8, LOSE = 9;


    public SoundManager()
    {
        init();
    }

    public void init()
    {
        context = BaseObject.sSystemRegistry.contextParameters.context;

        AudioAttributes aa = new AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .setUsage(AudioAttributes.USAGE_GAME)
                .build();
        sp = new SoundPool.Builder().
                setMaxStreams(10).setAudioAttributes(aa).build();

        cut = new int[4];
        die = new int[9];
        whop = new int[3];
        blunt = new int[2];
        boarRider = new int[4];
        whip = new int[4];

        for(int i = 0; i < 4 ; i++)
        {
            cut[i] = sp.load(context, Utils.getResourceIDByString(context,"cut"+(i+1),"raw"),1);
        }
        for(int i = 0; i < 9 ; i++)
        {
            die[i] = sp.load(context,Utils.getResourceIDByString(context,"die"+(i+1),"raw"),1);
        }
        for(int i = 0; i < 3 ; i++)
        {
            whop[i] = sp.load(context,Utils.getResourceIDByString(context,"whop"+(i+1),"raw"),1);
        }
        for(int i = 0; i < 3 ; i++)
        {
            whip[i] = sp.load(context,Utils.getResourceIDByString(context,"whip"+(i+1),"raw"),1);
        }

        blunt[0] = sp.load(context,Utils.getResourceIDByString(context,"blunt"+1,"raw"),1);
        blunt[1] = sp.load(context,Utils.getResourceIDByString(context,"blunt"+2,"raw"),1);

        boarRider[0] = cut[0];
        boarRider[1] = cut[1];
        boarRider[2] = blunt[0];
        boarRider[3] = blunt[1];

        horseID = sp.load(context,Utils.getResourceIDByString(context,"horse1","raw"),1);

        songs.add(0, R.raw.music_intro);
        songs.add(1,R.raw.music_selection_menu);
        songs.add(2,R.raw.music_army);
        songs.add(3,R.raw.music_army2);
        songs.add(4,R.raw.music_champ);
        songs.add(5,R.raw.music_chaotic);
        songs.add(6,R.raw.music_egypt);
        songs.add(7,R.raw.music_gate);
        songs.add(8,R.raw.music_gate2);
        songs.add(9,R.raw.music_lose);
    }

    public void loadMusic(int songIndex)
    {
        mediaPlayer = MediaPlayer.create(context,songs.get(songIndex));
        mediaPlayer.setLooping(true);
    }


    public void playMusic()
    {
        if(playMusic && mediaPlayer != null) mediaPlayer.start();
    }

    public void stopMusic()
    {
        if(playMusic && mediaPlayer != null) mediaPlayer.stop();
    }
    public void pauseMusic()
    {
        if(playMusic && mediaPlayer != null) {
            mediaPlayer.pause();
            lengthMenuMusic = mediaPlayer.getCurrentPosition();
        }
    }
    public void resumeMusic()
    {
        if(playMusic && mediaPlayer != null) {
            mediaPlayer.seekTo(lengthMenuMusic);
            mediaPlayer.start();
        }
    }

    public void playCut()
    {
        if(playSFX) sp.play(Utils.getRandomFromArray(cut),1,1,0,0,1);
    }

    public void playWhip()
    {
        if(playSFX) sp.play(Utils.getRandomFromArray(whip),1,1,0,0,1);
    }

    public void playDie()
    {
        if(playSFX) sp.play(Utils.getRandomFromArray(die),1,1,0,0,1);
    }

    public void playHorse() {if(playSFX) sp.play(horseID,1,1,0,0,1);}

    public void playWhop() {if(playSFX) sp.play(Utils.getRandomFromArray(whop),2,2,0,0,1);}

    public void playBlunt() {if(playSFX) sp.play(Utils.getRandomFromArray(blunt),1,1,0,0,1);}
    public void playBoarRider() {if(playSFX) sp.play(Utils.getRandomFromArray(boarRider),1,1,0,0,1);}

    public static void setPlaySFX(boolean SFX)
    {
        playSFX = SFX;
    }
    public static void setPlayMusic(boolean music)
    {
        playMusic = music;
    }






}
