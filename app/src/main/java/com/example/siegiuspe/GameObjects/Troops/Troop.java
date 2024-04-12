package com.example.siegiuspe.GameObjects.Troops;

import android.content.Context;
import android.graphics.Bitmap;

import com.example.siegiuspe.AnimationLibrary;
import com.example.siegiuspe.ContextParameters;
import com.example.siegiuspe.DebugLog;
import com.example.siegiuspe.Drawable.DrawableBitmap;
import com.example.siegiuspe.Drawable.DrawableObject;
import com.example.siegiuspe.Drawable.DrawableText;
import com.example.siegiuspe.GameObjects.BaseObject;
import com.example.siegiuspe.GameObjects.GameObject;
import com.example.siegiuspe.Drawable.HealthBar;
import com.example.siegiuspe.Managers.SoundManager;
import com.example.siegiuspe.Utils;

import java.util.ArrayList;

public abstract class Troop extends GameObject {
    protected int mSpeedPerSecond , mHealth,mMaxHealth;
    protected int mAttackDamage;
    protected int mAttackDelay;
    protected String name;

    protected boolean mPlayer;
    protected boolean isStateChanged;

    protected boolean notClear;
    protected boolean bloodPlay;
    protected boolean firePlay;
    protected boolean dmgPlay;
    protected int dmgTimer;

    protected Context context;
    protected SoundManager soundManager;
    protected HealthBar healthBar;

    protected int dmgTaken;

    protected int currentBloodFrame;
    protected int currentFireFrame;

    protected int walkFrameCount;
    protected int actFrameCount;
    protected int dieFrameCount;

    private static boolean showBlood;
    private static boolean showHealth;
    private static boolean showDmg;


    public Troop(boolean player, int x , int y)
    {
        this.context = BaseObject.sSystemRegistry.contextParameters.context;
        this.viewWidth = ContextParameters.viewWidth;
        this.viewHeight = ContextParameters.viewHeight;
        this.gameWidth = ContextParameters.gameWidth;
        this.gameHeight = ContextParameters.gameHeight;
        this.soundManager = BaseObject.sSystemRegistry.soundManager;
        this.notClear = false;
        this.mState = MOVE;


        mPlayer = player;
        mType = (mPlayer ? PLAYER : ENEMY);
        flippedImage = (!player);
        mCoordX = x;
        mCoordY = y;

        //bottom = mCoordY;
    }

    protected void die()
    {
        if(currentFrameTimer == 0)
        {
            currentFrame++;
            currentFrameTimer = 80;
            if(currentFrame == dieFrameCount)
            {
                currentFrame --;
                mMarkedToRemoved = true;
            }
        }
        else
        {
            currentFrameTimer--;
        }
    }

    protected void act()
    {
        if(currentFrameTimer == 0) {
            currentFrame++;
            if(currentFrame == actFrameCount)
            {
                currentFrameTimer = mAttackDelay;
                currentFrame = 0;
            }
        }
        else
        {
            currentFrameTimer--;
        }
    }

    protected void walk()
    {
        currentFrame++;
        if(currentFrame == walkFrameCount)
        {
            currentFrame = dieFrameCount;
            //currentFrame = 0;
        }
    }

    protected void playAttack()
    {
        soundManager.playCut();
    }

    protected boolean isAttack() {return currentFrame == actFrameCount - 1;}

    protected ArrayList<DrawableObject> addParticles(ArrayList<DrawableObject> queue)
    {
        if(showBlood && bloodPlay)
        {
            Bitmap blood = (flippedImage ? AnimationLibrary.bloodSmallFlippedAnimation[currentBloodFrame]
                    : AnimationLibrary.bloodSmallFlippedAnimation[currentBloodFrame] );
            queue.add(new DrawableBitmap(blood,mCenterX - AnimationLibrary.bloodFrameWidth/2,mCenterY - AnimationLibrary.bloodFrameHeight - frameHeight/2 ,AnimationLibrary.bloodFrameWidth,AnimationLibrary.bloodFrameHeight));
        }

        if(firePlay)
        {
            Bitmap fire = AnimationLibrary.fireAnimation[currentFireFrame];
            queue.add(new DrawableBitmap(fire,mCenterX - AnimationLibrary.fireFrameWidth/2,mCenterY - AnimationLibrary.fireFrameHeight - frameHeight/2,AnimationLibrary.fireFrameWidth,AnimationLibrary.fireFrameHeight));
        }

        if (showHealth && mHealth > 0 && mHealth < mMaxHealth) {
            queue.add(healthBar);
        }

        if (showDmg && dmgPlay) {
            queue.add(new DrawableText(String.valueOf(dmgTaken),mCenterX,mCenterY - frameHeight/2, Utils.dmgPaint));
        }

        return queue;
    }



    public float inRange(GameObject gameObject)
    {
        if(gameObject.mState != DIE) {

            if(this.mPlayer) {
                if (gameObject.mType == GameObject.ENEMY) {
                    DebugLog.d("cord vs center", " " + mCoordX + " " + mCenterX + " " + mCoordY  + " " + mCenterY);
                    if((gameObject.mCenterX <= this.mCenterX + mEffectRange && gameObject.mCenterX >= this.mCenterX)&& (gameObject.mCenterY >= this.mCenterY - mEffectWidth / 2 && gameObject.mCenterY <= this.mCenterY + mEffectWidth / 2))
                    {
                        return (float)Math.sqrt((mCenterX - gameObject.mCenterX) * (mCenterX -  gameObject.mCenterX) + (mCenterY - gameObject.mCenterY) * (mCenterY - gameObject.mCenterY));
                    }
                }
                else if(gameObject.mType == GameObject.EN_BASE)
                {
                    if(gameObject.mCoordX <= this.mCenterX + mEffectRange)
                    {
                        return gameObject.mCenterX -this.mCenterX;
                    }
                }
            }
            else
            {
                if(gameObject.mType == GameObject.PLAYER){
                    DebugLog.d("cord vs center", " " + mCoordX + " " + mCenterX + " " + mCoordY  + " " + mCenterY);
                    if ((gameObject.mCenterX >= this.mCenterX - mEffectRange && gameObject.mCenterX <= this.mCenterX)&& (gameObject.mCenterY >= this.mCenterY - mEffectWidth / 2 && gameObject.mCenterY <= this.mCenterY + mEffectWidth / 2)) {
                        return (float) Math.sqrt((mCenterX - gameObject.mCenterX) * (mCenterX - gameObject.mCenterX) + (mCenterY - gameObject.mCenterY) * (mCenterY - gameObject.mCenterY));
                    }
                }
                else if(gameObject.mType == GameObject.PL_BASE)
                {
                    if(gameObject.mCoordX >= this.mCenterX - mEffectRange)
                    {
                        return this.mCenterX - gameObject.mCenterX;
                    }
                }
            }
        }
        return -1;
    }

    public void manageCurrentFrame() {
        switch (mState)
        {
            case 1:
                walk();
                break;
            case 2:
                act();
                break;
            case 3:
                die();
                break;
        }

        if(bloodPlay)
        {
            currentBloodFrame++;
            if(currentBloodFrame == 11)
            {
                currentBloodFrame = 0;
                bloodPlay = false;
            }
        }

        dmgTimer--;
        if(dmgTimer == 0) dmgPlay = false;
    }

    public void getAttacked (int damage)
    {
        this.mHealth -= damage;
        dmgTaken = damage;
        dmgPlay = true;
        dmgTimer = 3;
        bloodPlay = true;
        if(!(this.mHealth > 0)) {
            this.mHealth = 0;
            if(playSound)soundManager.playDie();
        }
    }

    protected void changedState(int state)
    {
        if(!isStateChanged)
        {
            isStateChanged = mState != state;
            if(isStateChanged)
            {
                mState = state;
                switch (state)
                {
                    case MOVE:
                        currentFrame = dieFrameCount;
                        break;
                    case ACT:
                        currentFrame = 0;
                        break;
                    case DIE:
                        currentFrame = actFrameCount;
                }
                isStateChanged = false;
            }
        }
    }

    public static void setShowBlood(boolean showBlood) {
        Troop.showBlood = showBlood;
    }

    public static void setShowHealth(boolean showHealth) {
        Troop.showHealth = showHealth;
    }

    public static void setShowDmg(boolean showDmg) {
        Troop.showDmg = showDmg;
    }

}
