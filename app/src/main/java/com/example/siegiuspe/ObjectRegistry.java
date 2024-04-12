package com.example.siegiuspe;

import com.example.siegiuspe.GameObjects.BaseObject;
import com.example.siegiuspe.Managers.SoundManager;
import com.example.siegiuspe.Managers.SystemUiManager;

import java.util.ArrayList;

/**
 * The object registry manages a collection of global singleton objects.  However, it differs from
 * the standard singleton pattern in a few important ways:
 * - The objects managed by the registry have an undefined lifetime.  They may become invalid at 
 *   any time, and they may not be valid at the beginning of the program.
 * - The only object that is always guaranteed to be valid is the ObjectRegistry itself.
 * - There may be more than one ObjectRegistry, and there may be more than one instance of any of
 *   the systems managed by ObjectRegistry allocated at once.  For example, separate threads may
 *   maintain their own separate ObjectRegistry instances.
 */
public class ObjectRegistry extends BaseObject {

    public ContextParameters contextParameters;
    public AnimationLibrary animationLibrary;
    public SoundManager soundManager;
    public RenderSystem renderSystem;
//    public SystemUiManager systemUiManager;

	
    private ArrayList<BaseObject> mItemsNeedingReset = new ArrayList<BaseObject>();
    
    public ObjectRegistry() {
        super();
    }
    
    public void registerForReset(BaseObject object) {
    	final boolean contained = mItemsNeedingReset.contains(object);
    	assert !contained;
    	if (!contained) {
    		mItemsNeedingReset.add(object);
    	}
    }
    
    @Override
    public void reset() {
    	final int count = mItemsNeedingReset.size();
    	for (int x = 0; x < count; x++) {
    		mItemsNeedingReset.get(x).reset();
    	}
    }
    
}
