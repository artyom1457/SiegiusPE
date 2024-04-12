package com.example.siegiuspe;

import android.graphics.BitmapFactory;
import android.os.SystemClock;

import com.example.siegiuspe.Drawable.DrawableBitmap;
import com.example.siegiuspe.Drawable.DrawableObject;
import com.example.siegiuspe.GameObjects.BaseObject;
import com.example.siegiuspe.GameObjects.GameObject;
import com.example.siegiuspe.GameObjects.Troops.Troop;
import com.example.siegiuspe.GameObjects.Troops.Gaul.Behemoth;
import com.example.siegiuspe.GameObjects.Troops.Gaul.Berserker;
import com.example.siegiuspe.GameObjects.Troops.Gaul.BoarRider;
import com.example.siegiuspe.GameObjects.Troops.Roman.Centurion;
import com.example.siegiuspe.GameObjects.Troops.Roman.Defender;
import com.example.siegiuspe.GameObjects.Troops.Gaul.GaulArcher;
import com.example.siegiuspe.GameObjects.Troops.Roman.Legionary;
import com.example.siegiuspe.GameObjects.Troops.Gaul.MaceRider;
import com.example.siegiuspe.GameObjects.Troops.Gaul.Raider;
import com.example.siegiuspe.GameObjects.Troops.Roman.RomanArcher;
import com.example.siegiuspe.GameObjects.Troops.Roman.RomanCavalry;
import com.example.siegiuspe.GameObjects.Troops.Roman.RomanFireArcher;
import com.example.siegiuspe.Managers.SoundManager;

import java.util.ArrayList;

public class IntroDemoThread extends GameThread{

    private int height;
    private long added;

    boolean moveRight;


    public IntroDemoThread()
    {
        super();
        moveRight = true;
        SoundManager.setPlaySFX(false);
        SoundManager.setPlayMusic(SettingsParameters.play_music);
        Troop.setShowDmg(false);
        Troop.setShowBlood(true);
        Troop.setShowHealth(true);

        topEdge = 400;
        soundManager.loadMusic(SoundManager.INTRO);
        soundManager.playMusic();
        background = BitmapFactory.decodeResource(BaseObject.sSystemRegistry.contextParameters.context.getResources(),R.drawable.intro_bg);

    }


    @Override
    public void run() {
        super.run();

        mLastTime = SystemClock.uptimeMillis();
        mFinished = false;
        while (!mFinished ) {
            if (mGameRoot != null && !mPaused && ContextParameters.viewWidth != 0) {

                final long time = SystemClock.uptimeMillis();
                final long timeDelta = time - mLastTime;
                if (timeDelta > 12) {
                    mLastTime = time;

                    synchronized (mutexOM) {
                        mGameRoot.update();
                    }
                    synchronized (mutexOM) {
                        mGameRoot.removeMarked();
                    }

                    GameObject[] objects = mGameRoot.getObjects();
                    ArrayList<DrawableObject> drawableObjects = new ArrayList<DrawableObject>();
                    drawableObjects.add(new DrawableBitmap(background, ContextParameters.bgX, ContextParameters.bgY, ContextParameters.gameWidth, ContextParameters.gameHeight)) ;
                    if(objects.length > 0) {
                        for (GameObject object : objects) {
                            try {
                                if(object.insideScreen())
                                {
                                    drawableObjects = object.getFrame(drawableObjects);
                                }
                                else
                                {
//                                    DebugLog.d("not drawn", "x: " + object.mCoordX + " y: " + object.mCoordY + " bx:" + ContextParameters.bgX + " bgy:" + ContextParameters.bgY);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
//                                DebugLog.d("getFrame", "state: " + object.mState + " frame: " + object.currentFrameTimer);
                            }
                        }


                    }
                    renderSystem.scheduleForDraw(drawableObjects);
                    renderSystem.swap(mRenderer, 0, 0);

                    if(moveRight) {
                        moveRight(1);
                        if(ContextParameters.bgX + ContextParameters.gameWidth == ContextParameters.viewWidth) moveRight = false;
                    }
                    else
                    {
                        moveLeft(1);
                        if(ContextParameters.bgX == 0) moveRight = true;
                    }

                    if(ContextParameters.gameHeight != 0) {
                        height = ContextParameters.gameHeight - topEdge;
                        addRandomTroop();
                    }
                }

            }

        }
    }

    public void addRandomTroop()
    {
        if (added + 500 < SystemClock.uptimeMillis() && mGameRoot.getCount() <= 40)
        {
            try
            {
                added = SystemClock.uptimeMillis();
                int selector = 1 + (int)(Math.random()*6);
                switch (selector)
                {
                    case 1:
                        mGameRoot.add(new Legionary(true, ContextParameters.bgX, topEdge + (int)(Math.random()* height)));
                        break;
                    case 2:
                        mGameRoot.add(new RomanCavalry(true, ContextParameters.bgX, topEdge + (int)(Math.random()* height)));
                        break;
                    case 3:
                        mGameRoot.add(new Defender(true, ContextParameters.bgX, topEdge + (int)(Math.random()* height)));
                        break;
                    case 4:
                        mGameRoot.add(new RomanArcher(true, ContextParameters.bgX, topEdge + (int)(Math.random()* height)));
                        break;
                    case 5:
                        mGameRoot.add(new RomanFireArcher(true, ContextParameters.bgX, topEdge + (int)(Math.random()* height)));
                        break;
                    case 6:
                        mGameRoot.add(new Centurion(true, ContextParameters.bgX, topEdge + (int)(Math.random()* height)));
                        break;
                }

                selector = 1 + (int)(Math.random()*6);
                switch (selector)
                {
                    case 1:
                        mGameRoot.add(new Raider(false, ContextParameters.bgX + ContextParameters.gameWidth, topEdge + (int)(Math.random()* height)));
                        break;
                    case 2:
                        mGameRoot.add(new Berserker(false, ContextParameters.bgX + ContextParameters.gameWidth, topEdge + (int)(Math.random()* height)));
                        break;
                    case 3:
                        mGameRoot.add(new Behemoth(false, ContextParameters.bgX + ContextParameters.gameWidth, topEdge + (int)(Math.random()* height)));
                        break;
                    case 4:
                        mGameRoot.add(new BoarRider(false, ContextParameters.bgX + ContextParameters.gameWidth, topEdge + (int)(Math.random()* height)));
                        break;
                    case 5:
                        mGameRoot.add(new MaceRider(false, ContextParameters.bgX + ContextParameters.gameWidth, topEdge + (int)(Math.random()* height)));
                        break;
                    case 6:
                        mGameRoot.add(new GaulArcher(false, ContextParameters.bgX + ContextParameters.gameWidth, topEdge + (int)(Math.random()* height)));
                        break;

                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
