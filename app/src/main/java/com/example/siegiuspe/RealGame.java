package com.example.siegiuspe;


import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;

import com.example.siegiuspe.Game.Game;
import com.example.siegiuspe.Managers.ObjectManager;

public class RealGame extends Game {

    private int topEdge;
    private int bottomEdge;
    private Handler handler;
    private int commander;
    private int songId;
    private int money;
    private int economy;

    public RealGame(Bitmap map, int topEdge, int bottomEdge, Handler handler, int commander,int music)
    {
        background = map;
        this.topEdge = topEdge;
        this.bottomEdge = bottomEdge;
        this.handler = handler;
        this.commander = commander;
        songId = music;
    }


    @Override
    public void bootstrap(Context context, int gameWidth,int gameHeight , int viewWidth, int viewHeight) {
        if (!mBootstrapComplete) {

            ContextParameters.gameWidth = gameWidth;
            ContextParameters.gameHeight = gameHeight;
            ContextParameters.viewWidth = viewWidth;
            ContextParameters.viewHeight = viewHeight;

            ContextParameters.bgX = 0;
            ContextParameters.bgY = 0;


            mGameRoot = new ObjectManager();
            mGameThread = new RealGameThread(background,topEdge,bottomEdge,handler,commander,songId);
            mGameThread.setGameRoot(mGameRoot);
            mRenderer = mGameThread.getRenderer();

            mGameThread.start();
        }
    }
}
