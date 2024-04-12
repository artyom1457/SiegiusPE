package com.example.siegiuspe.Game;

import android.content.Context;
import android.graphics.Bitmap;

import com.example.siegiuspe.ContextParameters;
import com.example.siegiuspe.GameObjects.GameObject;
import com.example.siegiuspe.GameRenderer;
import com.example.siegiuspe.GameThread;
import com.example.siegiuspe.Managers.ObjectManager;

public abstract class Game{
    protected GameThread mGameThread;
    protected ObjectManager mGameRoot;

    protected GameRenderer mRenderer;
    protected boolean mRunning;
    protected boolean mBootstrapComplete;

    protected ContextParameters mContextParameters;
    protected Bitmap background;


    public Game() {
        super();
        mRunning = false;
        mBootstrapComplete = false;
        mContextParameters = new ContextParameters();
    }

    public abstract void bootstrap(Context context,int gameWidth,int gameHeight ,int viewWidth, int viewHeight);

    public GameRenderer getRenderer()
    {
        return mRenderer;
    }

    public GameThread getGameThread()
    {
        return mGameThread;
    }

    public void stopGame() {
        mGameThread.stopGame();
        mRenderer.isGameRunning = false;
        while(true)
        {
            try {
                mGameThread.join();
            } catch (InterruptedException e) {
                //TODO add logging
                e.printStackTrace();
                continue;
            }
            break;
        }
    }

    public void pauseGame() {
        mGameThread.pauseGame();
        mRenderer.toDraw = false;
    }

    public void resumeGame() {
        if(mGameThread!=null) {
            mGameThread.resumeGame();
        }
        if(mRenderer != null)
        {
            mRenderer.toDraw = true;
        }
    }

    public void moveLeft()
    {
        mGameThread.moveLeft(5);
    }

    public void moveRight()
    {
        mGameThread.moveRight(5);
    }

    protected void addToGame(GameObject gameObject)
    {
        mGameThread.addObject(gameObject);
    }

    public void startDrawEdges()
    {
        mGameThread.startDrawEdges();
    }

    public void endDrawEdges()
    {
        mGameThread.endDrawEdges();
    }

}
