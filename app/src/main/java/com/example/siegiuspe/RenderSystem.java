package com.example.siegiuspe;


import com.example.siegiuspe.Drawable.DrawableObject;
import com.example.siegiuspe.GameObjects.BaseObject;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Manages a double-buffered queue of renderable objects.  The game thread submits drawable objects
 * to the the active render queue while the render thread consumes drawables from the alternate
 * queue.  When both threads complete a frame the queues are swapped.  Note that this class can
 * manage any number (>=2) of render queues, but increasing the number over two means that the game
 * logic will be running significantly ahead of the rendering thread, which may make the user feel
 * that the controls are "loose."
 */
public class RenderSystem extends BaseObject {
    private Queue<ArrayList<DrawableObject>> mRenderQueues;
    private int mQueueIndex;

    private final static int DRAW_QUEUE_COUNT = 2;
    private final static int MAX_RENDER_OBJECTS_PER_FRAME = 384;
    private final static int MAX_RENDER_OBJECTS = MAX_RENDER_OBJECTS_PER_FRAME * DRAW_QUEUE_COUNT;

    public boolean doneDrawing = true;

    public RenderSystem() {
        super();
        mRenderQueues = new LinkedList<ArrayList<DrawableObject>>();
        mQueueIndex = 0;
    }

    @Override
    public void reset() {

    }

    public void scheduleForDraw(ArrayList<DrawableObject> drawQueue) {
        if(mRenderQueues.size() < 2 && drawQueue != null) mRenderQueues.add(drawQueue);
    }

    public void swap(GameRenderer renderer, float cameraX, float cameraY) {

            // This code will block if the previous queue is still being executed.


            renderer.setDrawQueue(mRenderQueues.poll(), cameraX, cameraY);

//            final int lastQueue = (mQueueIndex == 0) ? DRAW_QUEUE_COUNT - 1 : mQueueIndex - 1;
//
//            // Clear the old queue.
//            mRenderQueues.set(lastQueue,null);
//
//            mQueueIndex = (mQueueIndex + 1) % DRAW_QUEUE_COUNT;

    }

    /* Empties all draw queues and disconnects the game thread from the renderer. */
    public void emptyQueues(GameRenderer renderer) {
        renderer.setDrawQueue(null, 0.0f, 0.0f);
        mRenderQueues = null;
    }
}