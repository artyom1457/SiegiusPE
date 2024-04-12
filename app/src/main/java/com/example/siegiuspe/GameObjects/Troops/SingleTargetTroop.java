package com.example.siegiuspe.GameObjects.Troops;

import com.example.siegiuspe.ContextParameters;
import com.example.siegiuspe.GameObjects.GameObject;
import com.example.siegiuspe.GameObjects.Troops.Troop;

import java.util.ArrayList;

public abstract class SingleTargetTroop extends Troop {


    public SingleTargetTroop(boolean player, int x , int y) {
        super(player, x, y);
    }

    @Override
    public GameObject update(ArrayList<GameObject> gameObjects) {
        boolean playCut = false;

        if(mHealth > 0 || isAttack()) {
            boolean found = false;
            for (GameObject gameObject : gameObjects) {
                if (((gameObject.mType == GameObject.ENEMY || gameObject.mType == GameObject.EN_BASE) && this.mPlayer )||
                        ((gameObject.mType == GameObject.PLAYER|| gameObject.mType == GameObject.PL_BASE) && !this.mPlayer))
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
                changedState(ACT);

                if (currentFrameTimer == 0 && isAttack()) {
                    GameObject closest = null;
                    float minX = viewWidth;
                    for (GameObject gameObject : gameObjects) {
                        if (((gameObject.mType == GameObject.ENEMY || gameObject.mType == GameObject.EN_BASE) && this.mPlayer )||
                                ((gameObject.mType == GameObject.PLAYER|| gameObject.mType == GameObject.PL_BASE) && !this.mPlayer))
                        {
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
                        if(playSound) {
                            playAttack();
                        }
                    }
                }
            }
            else if(mHealth > 0)
            {
                changedState(MOVE);

                currentFrameTimer = 0;
                mCoordX = mCenterX = (int) (mCoordX + mSpeedPerSecond );

                if ((mCoordX > gameWidth && mSpeedPerSecond > 0) || (mCoordX < ContextParameters.bgX && mSpeedPerSecond < 0)) {
                    mMarkedToRemoved = true;
                }
            }

            healthBar.updateHp(mCoordX,mCoordY,mHealth);
        }
        else{
            if(mState != DIE) {
                changedState(DIE);
                currentFrameTimer = 5;
            }
        }
        return null;
    }
}
