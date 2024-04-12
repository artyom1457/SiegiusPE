package com.example.siegiuspe;

import android.graphics.Bitmap;

import com.example.siegiuspe.GameObjects.BaseObject;
import com.example.siegiuspe.GameObjects.GameObject;
import com.example.siegiuspe.Managers.ObjectManager;
import com.example.siegiuspe.Managers.SoundManager;

public abstract class GameThread extends Thread {
    protected long mLastTime;

    protected boolean drawPlaceable;

    protected boolean mFinished;
    protected boolean mPaused = false;
    protected ObjectManager mGameRoot;
    protected RenderSystem renderSystem;
    protected GameRenderer mRenderer;

    protected final Object mutexOM = new Object();
    protected final Object mutexBGX = new Object();

    protected int topEdge;
    protected int bottomEdge;

    protected Bitmap background;
    protected SoundManager soundManager;


    public GameThread()
    {
        renderSystem = BaseObject.sSystemRegistry.renderSystem;
        mRenderer = new GameRenderer(
                BaseObject.sSystemRegistry.contextParameters.context);
        mRenderer.resume();
        soundManager = new SoundManager();
    }

    public void stopGame() {
        mPaused = false;
        mFinished = true;
        soundManager.stopMusic();
    }

    public void pauseGame() {
        mPaused = true;
        soundManager.pauseMusic();
    }

    public void resumeGame() {
        mPaused = false;
        soundManager.resumeMusic();
    }

    public boolean getPaused() {
        return mPaused;
    }

    public void setGameRoot(ObjectManager gameRoot) {
        mGameRoot = gameRoot;
    }

    public GameRenderer getRenderer()
    {
        return mRenderer;
    }





    public void addObject(GameObject gameObject)
    {
        mGameRoot.add(gameObject);
    }

    public void moveRight(int x)
    {
        if(ContextParameters.bgX - x + ContextParameters.gameWidth < ContextParameters.viewWidth)
        {
            synchronized (mutexBGX) {
                int deltaX = ContextParameters.bgX;
                ContextParameters.bgX = ContextParameters.viewWidth - ContextParameters.gameWidth;

                deltaX -= ContextParameters.bgX;
                synchronized (mutexOM) {
                    mGameRoot.moveRight(deltaX);
                }
            }
        }
        else if(ContextParameters.bgX - x + ContextParameters.gameWidth >= ContextParameters.viewWidth)
        {
            synchronized (mutexBGX) {
                ContextParameters.bgX -= x;
                synchronized (mutexOM) {
                    mGameRoot.moveRight(x);
                }
            }
        }
    }

    public void moveLeft(int x)
    {
        if(ContextParameters.bgX + x > 0)
        {
            synchronized (mutexBGX) {
                int deltaX = -ContextParameters.bgX;
                ContextParameters.bgX = 0;

                synchronized (mutexOM) {
                    mGameRoot.moveLeft(deltaX);
                }
            }
        }
        else if(ContextParameters.bgX + x <= 0)
        {
            synchronized (mutexBGX) {
                ContextParameters.bgX += x;
                synchronized (mutexOM) {
                    mGameRoot.moveLeft(x);
                }
            }
        }
    }

    public void startDrawEdges()
    {
        drawPlaceable = true;
    }

    public void endDrawEdges()
    {
        drawPlaceable = false;
    }
}
