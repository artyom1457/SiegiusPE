package com.example.siegiuspe.GameObjects.Fortress;

import android.graphics.Bitmap;

import com.example.siegiuspe.Drawable.BaseHealthBar;
import com.example.siegiuspe.ContextParameters;
import com.example.siegiuspe.Drawable.DrawableBitmap;
import com.example.siegiuspe.Drawable.DrawableObject;
import com.example.siegiuspe.GameObjects.GameObject;

import java.util.ArrayList;

public abstract class Fortress extends GameObject {

    protected int mMaxHealth;
    protected int mAttackDamage;
    protected int mAttackDelay;
    public int mHealth;

    protected boolean mPlayer;
    protected boolean notClear;

    protected int actFrameCount;

    protected Bitmap extension;

    protected BaseHealthBar healthBar;
    public Fortress(boolean player)
    {
        mMaxHealth = 5000;
        mHealth = mMaxHealth;
        mAttackDelay = 10;
        currentFrame = 0;
        mAttackDamage = 20;

        mEffectRange = 1500;
        actFrameCount = 5;

        mPlayer = player;
        mType = (mPlayer ? PL_BASE : EN_BASE);
        flippedImage = (!player);

        healthBar = new BaseHealthBar(player);

        viewWidth = ContextParameters.viewWidth;
        viewHeight = ContextParameters.viewHeight;
    }


    @Override
    public GameObject update(ArrayList<GameObject> gameObjects) {

        if(mHealth > 0) {
            boolean found = false;


            for (GameObject gameObject : gameObjects) {
                if ((gameObject.mType == GameObject.ENEMY && this.mPlayer )|| (gameObject.mType == GameObject.PLAYER && !this.mPlayer))
                {
                    float distance = inRange(gameObject);
                    if (distance != -1) {
                        found = true;
                        break;
                    }
                }
            }
            notClear = found;
            if(notClear) {
                mState = ACT;

                if (currentFrameTimer == 0 && currentFrame == 3) {
                    GameObject closest = null;
                    float minX = viewWidth;
                    for (GameObject gameObject : gameObjects) {
                        if ((gameObject.mType == GameObject.ENEMY && this.mPlayer )|| (gameObject.mType == GameObject.PLAYER && !this.mPlayer)){
                            float distance = inRange(gameObject);
                            if (distance != -1){
                                if(distance < minX) {
                                    closest = gameObject;
                                    minX = distance;
                                }
                            }
                        }
                    }
                    if(closest != null) {
                        closest.getAttacked(mAttackDamage);
//                        DebugLog.d("attack","name:" + this.name + " hp:"+ this.mHealth +  " frame:" + currentFrame);
                    }
                }
            }
            else if(mHealth > 0)
            {
                mState = MOVE;
                currentFrameTimer = 0;
            }
        }
        return null;
    }

    @Override
    public void manageCurrentFrame() {
        switch (mState)
        {
            case 1:
                currentFrame = 0;
                break;
            case 2:
                act();
                break;
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

    @Override
    public float inRange(GameObject gameObject) {

        if(gameObject.mState != DIE) {
            if (this.mPlayer && gameObject.mType == GameObject.ENEMY) {
                if (gameObject.mCoordX <= this.mCoordX + mEffectRange) {
                    return gameObject.mCoordX - this.mCoordX;
                }
            } else if (gameObject.mType == GameObject.PLAYER) {
                if (gameObject.mCoordX >= this.mCoordX - mEffectRange) {
                    return this.mCoordX - gameObject.mCoordX;
                }
            }
        }
        return -1;
    }

    protected ArrayList<DrawableObject> addHealthBar(ArrayList<DrawableObject> queue)
    {
        queue.add(healthBar);
        return queue;
    }

    public DrawableBitmap getExtension()
    {
        int height = extension.getHeight();
        int width = extension.getWidth();

        if(mPlayer)
        {
            return new DrawableBitmap(extension,mCoordX - frameWidth/2,mCoordY-height,width,height);
        }
        else
        {
            return new DrawableBitmap(extension,mCoordX + frameWidth/2 - width,mCoordY-height,width,height);
        }
    }

    public void getAttacked (int damage)
    {
        this.mHealth -= damage;
        healthBar.updateHp(mHealth);
    }


}
