package com.example.siegiuspe.GameObjects;

import com.example.siegiuspe.ContextParameters;
import com.example.siegiuspe.Drawable.DrawableObject;

import java.util.ArrayList;

public abstract class GameObject extends BaseObject implements Comparable<GameObject>{

    public static final int PLAYER = 1,ENEMY = 2,PL_PROJECTILE = 3, EN_PROJECTILE = 4, PL_BASE = 5, EN_BASE = 6;
    public static final int ACT = 2,DIE = 3, MOVE = 1;
    public int mCoordX, mCoordY;
    public int mCenterX, mCenterY;
    public int mType;
    protected int currentFrameTimer;
    protected boolean mMarkedToRemoved;
    protected boolean flippedImage;

    protected int frameHeight;
    protected int frameWidth;

    protected int viewWidth;
    protected int viewHeight;

    protected int gameWidth;
    protected int gameHeight;

    protected int currentFrame;

    protected int mEffectRange, mEffectWidth;

    protected int mState;

    public boolean playSound;

    //protected int bottom;

    public abstract ArrayList<DrawableObject> getFrame(ArrayList<DrawableObject> queue);
    public abstract GameObject update(ArrayList<GameObject> gameObjects);
    public abstract void manageCurrentFrame();
    public abstract float inRange(GameObject gameObject);

    public void getAttacked (int damage) { } // not used in projectile



    @Override
    public void reset() {
        mCoordX = 0;
        mCoordY = 0;
        mType = 0;
        currentFrameTimer = 0;
    }

    @Override
    public int compareTo(GameObject gameObject) {

        return mCoordY - gameObject.mCoordY;
    }

    public boolean insideScreen()
    {
        return (mCoordX + frameWidth > 0 && mCoordX - frameWidth < ContextParameters.viewWidth);
    }
}
