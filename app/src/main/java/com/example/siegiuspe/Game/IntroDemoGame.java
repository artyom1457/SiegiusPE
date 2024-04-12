package com.example.siegiuspe.Game;

import android.content.Context;

import com.example.siegiuspe.ContextParameters;
import com.example.siegiuspe.Game.Game;
import com.example.siegiuspe.IntroDemoThread;
import com.example.siegiuspe.Managers.ObjectManager;

public class IntroDemoGame extends Game {

    public IntroDemoGame()
    {
        super();
    }


    @Override
    public void bootstrap(Context context,int gameWidth,int gameHeight , int viewWidth, int viewHeight) {
        if (!mBootstrapComplete) {

            ContextParameters.gameWidth = gameWidth;
            ContextParameters.gameHeight = gameHeight;
            ContextParameters.viewWidth = viewWidth;
            ContextParameters.viewHeight = viewHeight;

            mGameRoot = new ObjectManager();
            mGameThread = new IntroDemoThread();
            mGameThread.setGameRoot(mGameRoot);
            mRenderer = mGameThread.getRenderer();

            mGameThread.start();
        }
    }
}
