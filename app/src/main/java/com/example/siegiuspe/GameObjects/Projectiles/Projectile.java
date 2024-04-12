package com.example.siegiuspe.GameObjects.Projectiles;

import com.example.siegiuspe.ContextParameters;
import com.example.siegiuspe.GameObjects.GameObject;

import java.util.ArrayList;

public abstract class Projectile extends GameObject {

    protected GameObject mTarget;
    protected int speed;
    protected int damage;

    public Projectile(boolean player, int x, int y, GameObject target) {
        this.viewWidth = ContextParameters.viewWidth;
        this.viewHeight = ContextParameters.viewHeight;
        this.gameWidth = ContextParameters.gameWidth;
        this.gameHeight = ContextParameters.gameHeight;
        this.mState = MOVE;

        mType = (player ? PL_PROJECTILE : EN_PROJECTILE);
        flippedImage = (!player);
        mCoordX = x;
        mCoordY = y;
        mTarget = target;
    }

    @Override
    public GameObject update(ArrayList<GameObject> gameObjects) {
        int distanceX = mTarget.mCoordX - mCoordX;
        int distanceY = mTarget.mCoordY - mCoordY;



//        if (mType == EN_PROJECTILE) {
//            distanceX = -distanceX;
//        }

        float time = (float) distanceX / speed;
        float speedY = 0;

        if(mTarget.mType != EN_BASE && mTarget.mType != PL_BASE)
        {
            speedY = (float) distanceY / time;
        }

        mCoordX +=speed;
        mCoordY +=(int) speedY;

        if (mType == EN_PROJECTILE) {
            if (mCoordX <= mTarget.mCoordX) {
                mTarget.getAttacked(damage);
                this.mMarkedToRemoved = true;
            }
        } else {
            if (mCoordX >= mTarget.mCoordX) {
                mTarget.getAttacked(damage);
                this.mMarkedToRemoved = true;
            }
        }
        return null;
    }

    public void manageCurrentFrame()
    {}

    public float inRange(GameObject gameObject)
    {
        return -1;
    }

}
