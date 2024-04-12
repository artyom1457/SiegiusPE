package com.example.siegiuspe.GameObjects.Troops;

import com.example.siegiuspe.ContextParameters;
import com.example.siegiuspe.GameObjects.GameObject;
import com.example.siegiuspe.GameObjects.Projectiles.Projectile;
import com.example.siegiuspe.GameObjects.Projectiles.RomanArrow;
import com.example.siegiuspe.GameObjects.Projectiles.RomanFireArrow;
import com.example.siegiuspe.GameObjects.Projectiles.Spear;
import com.example.siegiuspe.GameObjects.Troops.Gaul.GaulArrow;

import java.util.ArrayList;

public abstract class RangedTroop extends Troop {
    int mtype;
    public RangedTroop(boolean player, int x, int y,int type) {
        super(player, x, y);
        mtype = type;
    }

    @Override
    public GameObject update(ArrayList<GameObject> gameObjects) {
        boolean playCut = false;
        GameObject projectile = null;

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
                        projectile = makeProjectile(mPlayer,mCenterX,mCenterY,closest,mtype);
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
        return projectile;
    }

    private Projectile makeProjectile(boolean player, int x, int y, GameObject target, int type)
    {
        Projectile projectile;
        switch (type)
        {
            case 1:
                projectile = new RomanArrow(player,x,y,target);
                break;
            case 2:
                projectile = new RomanFireArrow(player,x,y,target);
                break;
            case 3:
                projectile = new GaulArrow(player,x,y,target);
                break;
            case 4:
                projectile = new Spear(player,x,y,target);
                break;
            default:
                projectile = new RomanArrow(player,x,y,target);
                break;
        }
        return projectile;
    }

    protected void playAttack()
    {
        soundManager.playWhip();
    }
}
