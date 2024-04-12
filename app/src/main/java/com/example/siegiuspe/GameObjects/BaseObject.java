package com.example.siegiuspe.GameObjects;

import com.example.siegiuspe.ObjectRegistry;

public abstract class BaseObject {
    public static ObjectRegistry sSystemRegistry = new ObjectRegistry();

    public BaseObject() {
        super();
    }
    
    public abstract void reset();

}
