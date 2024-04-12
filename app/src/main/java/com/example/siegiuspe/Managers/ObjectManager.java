package com.example.siegiuspe.Managers;

import com.example.siegiuspe.GameObjects.GameObject;
import com.example.siegiuspe.Utils;

import java.util.ArrayList;
import java.util.Collections;

/**
 * ObjectManagers are "group nodes" in the game graph.  They contain child objects, and updating
 * an object manager invokes update on its children.  ObjectManagers themselves are derived from
 * BaseObject, so they may be strung together into a hierarchy of objects.  ObjectManager may 
 * be specialized to implement special types of traversals (e.g. PhasedObjectManager sorts its
 * children).
 */
public class ObjectManager {
    protected static final int DEFAULT_ARRAY_SIZE = 64;

    private ArrayList<GameObject> mObjects;
    private ArrayList<GameObject> mPendingAdditions;
    private ArrayList<GameObject> mPendingRemovals;

    protected int mFrameLengthInMillisecond;
    protected long mLastFrameChangeTime;

    public ObjectManager() {
        super();
        mObjects = new ArrayList<GameObject>(DEFAULT_ARRAY_SIZE);
        mPendingAdditions = new ArrayList<GameObject>(DEFAULT_ARRAY_SIZE);
        mPendingRemovals = new ArrayList<GameObject>(DEFAULT_ARRAY_SIZE);



    }
    
    public ObjectManager(int arraySize) {
        super();
        mObjects = new ArrayList<GameObject>(arraySize);
        mPendingAdditions = new ArrayList<GameObject>(arraySize);
        mPendingRemovals = new ArrayList<GameObject>(arraySize);
    }

    public void reset() {
        commitUpdates();
        for(GameObject object: mObjects)
        {
            object.reset();
        }
    }

    public void commitUpdates() {
        final int additionCount = mPendingAdditions.size();
        if (additionCount > 0) {
            mObjects.addAll(mPendingAdditions);
            mPendingAdditions.clear();
        }

        final int removalCount = mPendingRemovals.size();
        if (removalCount > 0) {
            mObjects.removeAll(mPendingRemovals);
            mPendingRemovals.clear();
        }

        if(mObjects.size() > 0) {
            Collections.sort(mObjects);
        }
    }
    
    public void update() {
        commitUpdates();
        final int count = mObjects.size();
        if (count > 0) {
            long time = System.currentTimeMillis();
            mFrameLengthInMillisecond = 1000/ Utils.FPS;
            if (time > mLastFrameChangeTime + mFrameLengthInMillisecond) {
                mLastFrameChangeTime = time;

                for (int i = 0; i < count; i++) {
                    GameObject gameObject = mObjects.get(i);
                    gameObject.manageCurrentFrame();
                    GameObject projectile = gameObject.update(mObjects);
                    if(projectile != null)
                    {
                        add(projectile);
                    }
                }
            }
        }
    }

    public final GameObject[] getObjects() {return mObjects.toArray(new GameObject[0]);}
    
    public final int getCount() {
        return mObjects.size();
    }
    
    /** Returns the count after the next commitUpdates() is called. */
    public final int getConcreteCount() {
        return mObjects.size() + mPendingAdditions.size() - mPendingRemovals.size();
    }
    
    public final GameObject get(int index) {
        return mObjects.get(index);
    }

    public void add(GameObject object) {
        mPendingAdditions.add(object);
    }

    public void remove(GameObject object) {
        mPendingRemovals.add(object);
    }

    public void moveRight(int x)
    {
        commitUpdates();
        final int count = mObjects.size();
        if (count > 0) {
            for (int i = 0; i < count; i++) {
                GameObject gameObject = mObjects.get(i);
                gameObject.mCoordX -= x;
                gameObject.mCenterX -= x;
                gameObject.gameWidth -= x;
            }
        }
    }

    public void moveLeft(int x)
    {
        commitUpdates();
        final int count = mObjects.size();
        if (count > 0) {
            for (int i = 0; i < count; i++) {
                GameObject gameObject = mObjects.get(i);
                gameObject.mCoordX += x;
                gameObject.mCenterX += x;
                gameObject.gameWidth += x;
            }
        }
    }


    public void removeMarked()
    {
        for(GameObject i : mObjects)
        {
            if(i.mMarkedToRemoved) {
                remove(i);
            }
        }

    }

    
    public void removeAll() {
        mPendingRemovals.addAll(mObjects);
        mPendingAdditions.clear();
    }

    
    protected ArrayList<GameObject> getPendingObjects() {
        return mPendingAdditions;
    }

}
