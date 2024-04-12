package com.example.siegiuspe;

import android.graphics.Bitmap;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;

import com.example.siegiuspe.Drawable.DrawableBitmap;
import com.example.siegiuspe.Drawable.DrawableObject;
import com.example.siegiuspe.Drawable.DrawableRect;
import com.example.siegiuspe.GameObjects.Fortress.Fortress;
import com.example.siegiuspe.GameObjects.GameObject;
import com.example.siegiuspe.GameObjects.Fortress.GaulBase;
import com.example.siegiuspe.GameObjects.Fortress.RomanBase;
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

public class RealGameThread extends GameThread{

    private Fortress playerBase;
    private Fortress enemyBase;
    private Handler handler;
    private int commander;
    private int height;
    private long added;

    public RealGameThread(Bitmap background, int topEdge, int bottomEdge, Handler handler, int commander,int music)
    {
        super();
        SoundManager.setPlaySFX(SettingsParameters.play_SFX);
        SoundManager.setPlayMusic(SettingsParameters.play_music);
        Troop.setShowDmg(SettingsParameters.show_dmg);
        Troop.setShowBlood(SettingsParameters.show_blood);
        Troop.setShowHealth(SettingsParameters.show_hp);

        this.background = background;
        this.topEdge = topEdge;
        this.bottomEdge = bottomEdge;
        drawPlaceable = false;
        this.handler = handler;
        this.commander = commander;

        soundManager.loadMusic(music);
        soundManager.playMusic();
    }

    @Override
    public void run() {
        super.run();

        mLastTime = SystemClock.uptimeMillis();
        mFinished = false;

        if(commander == Utils.CAESAR)
        {
            playerBase = new RomanBase(true);
            enemyBase = new GaulBase(false);
        }
        else
        {
            playerBase = new GaulBase(true);
            enemyBase = new RomanBase(false);
        }

        mGameRoot.add(playerBase);
        mGameRoot.add(enemyBase);

        while (!mFinished ) {
            if (mGameRoot != null && !mPaused && ContextParameters.viewWidth != 0 && playerBase.mHealth > 0 && enemyBase.mHealth > 0) {

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

                    drawableObjects.add(playerBase.getExtension());
                    drawableObjects.add(enemyBase.getExtension());

                    if(drawPlaceable)
                    {
                        drawableObjects.add(drawTopEdge());
                        drawableObjects.add(drawBottomEdge());
                    }

                    if(objects.length > 0) {
                        for (GameObject object : objects) {
                            try {
                                if(object.insideScreen())
                                {
                                    object.playSound = true;
                                    drawableObjects = object.getFrame(drawableObjects);
                                }
                                else
                                {
                                    object.playSound = false;
//                                    DebugLog.d("not drawn", "x: " + object.mCoordX + " y: " + object.mCoordY + " bx:" + ContextParameters.bgX + " bgy:" + ContextParameters.bgY);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
//                                DebugLog.d("getFrame", "state: " + object.mState + " frame: " + object.currentFrameTimer);
                            }
                        }
                    }

                    height = bottomEdge - topEdge;
                    addRandomTroop();

                    renderSystem.scheduleForDraw(drawableObjects);
                    renderSystem.swap(mRenderer, 0, 0);
                }

            }
            if(!(playerBase.mHealth > 0 && enemyBase.mHealth > 0))
            {
                mFinished = true;
            }
        }

        if(!(playerBase.mHealth > 0 && enemyBase.mHealth > 0))
        {
            Message msg = new Message();
            msg.arg1 = 1;
            msg.arg2 = enemyBase.mHealth;
            handler.sendMessage(msg);
        }
    }

    public void addTroop(Troop troop)
    {
        mGameRoot.add(troop);
    }

    public void moveRightRG()
    {
        moveRight(10);
    }

    public void moveLeftRG()
    {
        moveLeft(10);
    }

    public DrawableRect drawTopEdge()
    {
        Paint paint = new Paint();
        paint.setARGB(50,255,0,0);
        return new DrawableRect(ContextParameters.bgX,0,ContextParameters.gameWidth,topEdge,paint);
    }

    public DrawableRect drawBottomEdge()
    {
        Paint paint = new Paint();
        paint.setARGB(50,255,0,0);
        return new DrawableRect(ContextParameters.bgX,bottomEdge,ContextParameters.gameWidth,ContextParameters.gameHeight-bottomEdge,paint);
    }


    public void addRandomTroop()
    {
        if (added + 7000 < SystemClock.uptimeMillis())
        {
            try
            {
                added = SystemClock.uptimeMillis();
                int selector = 1 + (int)(Math.random()*6);

                if(commander == Utils.CAESAR)
                {
                    switch (selector) {
                        case 1:
                            mGameRoot.add(new Raider(false, ContextParameters.bgX + ContextParameters.gameWidth, topEdge + (int) (Math.random() * height)));
                            break;
                        case 2:
                            mGameRoot.add(new Berserker(false, ContextParameters.bgX + ContextParameters.gameWidth, topEdge + (int) (Math.random() * height)));
                            break;
                        case 3:
                            mGameRoot.add(new Behemoth(false, ContextParameters.bgX + ContextParameters.gameWidth, topEdge + (int) (Math.random() * height)));
                            break;
                        case 4:
                            mGameRoot.add(new BoarRider(false, ContextParameters.bgX + ContextParameters.gameWidth, topEdge + (int) (Math.random() * height)));
                            break;
                        case 5:
                            mGameRoot.add(new MaceRider(false, ContextParameters.bgX + ContextParameters.gameWidth, topEdge + (int) (Math.random() * height)));
                            break;
                        case 6:
                            mGameRoot.add(new GaulArcher(false, ContextParameters.bgX+ ContextParameters.gameWidth, topEdge + (int) (Math.random() * height)));
                            break;
                    }
                }
                else
                {
                    switch (selector)
                    {
                        case 1:
                            mGameRoot.add(new Legionary(false, ContextParameters.bgX+ ContextParameters.gameWidth, topEdge + (int)(Math.random()* height)));
                            break;
                        case 2:
                            mGameRoot.add(new RomanCavalry(false, ContextParameters.bgX+ ContextParameters.gameWidth, topEdge + (int)(Math.random()* height)));
                            break;
                        case 3:
                            mGameRoot.add(new Defender(false, ContextParameters.bgX+ ContextParameters.gameWidth, topEdge + (int)(Math.random()* height)));
                            break;
                        case 4:
                            mGameRoot.add(new RomanArcher(false, ContextParameters.bgX+ ContextParameters.gameWidth, topEdge + (int)(Math.random()* height)));
                            break;
                        case 5:
                            mGameRoot.add(new RomanFireArcher(false, ContextParameters.bgX+ ContextParameters.gameWidth, topEdge + (int)(Math.random()* height)));
                            break;
                        case 6:
                            mGameRoot.add(new Centurion(false, ContextParameters.bgX+ ContextParameters.gameWidth, topEdge + (int)(Math.random()* height)));
                            break;
                    }
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
