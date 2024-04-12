package com.example.siegiuspe;

import android.content.Context;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.siegiuspe.Drawable.DrawableObject;

import java.util.ArrayList;


public class GameRenderer extends SurfaceView implements Runnable {

    private Context mContext;
    private ArrayList<DrawableObject> mDrawQueueTroops;
   // private boolean mDrawQueueTroopsChanged;

    private final Object mDrawLock;

    private Thread thread = null;
    private final SurfaceHolder holder;
    private boolean isGameRunning = true;

    boolean toDraw = true;

    private long mTimeThisFrame;
    //HUD hud;


    public GameRenderer(Context context) {
        super(context);

        mContext = context;


//        mDrawQueueTroopsChanged = false;

        holder = getHolder();
        mDrawLock = new Object();

    }

    @Override
    public void run() {
        while (isGameRunning) {
//            if (toDraw) {
            if (!holder.getSurface().isValid())
                continue;

            Canvas canvas = null;
            try {
                canvas = this.getHolder().lockCanvas();

                long startFrameTime = System.currentTimeMillis();
//                synchronized(mDrawLock) {
//                    if (!mDrawQueueTroopsChanged) {
//                        while (!mDrawQueueTroopsChanged) {
//                            try {
//                                mDrawLock.wait();
//                            } catch (InterruptedException e) {
//                                // No big deal if this wait is interrupted.
//                            }
//                        }
//                    }
//                    mDrawQueueTroopsChanged = false;
//                }


                if (mDrawQueueTroops != null) {
                    for (int i = 0; i < mDrawQueueTroops.size(); i++) {
                        DrawableObject drawableObject = mDrawQueueTroops.get(i);
                        if (drawableObject != null) {
                            drawableObject.drawObject(canvas);
                        } else {
                            DebugLog.d("drawableObject null", "number in array:" + i + "from " + mDrawQueueTroops.size());
                        }
                    }
                }


                mTimeThisFrame = System.currentTimeMillis() - startFrameTime;
                if (mTimeThisFrame >= 1) {
                    Utils.FPS = (int) (1000 / mTimeThisFrame);
                }
                if (Utils.FPS > Utils.MAX_FPS || Utils.FPS == 0) Utils.FPS = Utils.MAX_FPS;

            } catch (final Exception e) {
                //TODO add logging
                e.printStackTrace();
            } finally {
                if (canvas != null) {
                    this.getHolder().unlockCanvasAndPost(canvas);
                }
            }

        }
//        }
    }

    public synchronized void setDrawQueue(ArrayList<DrawableObject> queue, float cameraX, float cameraY) {

        //if(queue != null) {
            mDrawQueueTroops = queue;
            synchronized (mDrawLock) {
//                mDrawQueueTroopsChanged = true;
                mDrawLock.notify();
            }
        //}
    }


    public void pause()
    {
        isGameRunning =  false;
        while (true) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
                DebugLog.d("pause thread",e.getMessage());
                continue;
            }
            break;
        }
        thread = null;
    }

    public void resume()
    {
        isGameRunning =  true;
        thread = new Thread(this);
        thread.start();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //int width = (int)(MeasureSpec.getSize(widthMeasureSpec) * 1.5);
        int width = (int)(MeasureSpec.getSize(widthMeasureSpec));
        int height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(width, height);
    }


    /**
     * return whether or not the Renderer is active
     * @return true or false for if the game is running
     */
    public boolean isActive(){
     return this.isGameRunning;
    }

    /**
     * activates the Renderer
     */
    public void activate(){
        this.isGameRunning = true;
    }

    /**
     * deactivates the renderer
     */
    public void deactivate(){
        this.isGameRunning = false;
    }


//    class HUD {
//
//        Rect left;
//        Rect right;
//        Rect thrust;
//        Rect shoot;
//        Rect pause;
//
//        //create an array of buttons for the draw method
//        public ArrayList<Rect> currentButtonList = new ArrayList<>();
//
//        HUD(int screenWidth, int screenHeight) {
//
//            //Configure the player buttons
//            int buttonWidth = screenWidth / 8;
//            int buttonHeight = screenHeight / 7;
//            int buttonPadding = screenWidth / 80;
//
//            left = new Rect(buttonPadding,
//                    screenHeight - buttonHeight - buttonPadding,
//                    buttonWidth,
//                    screenHeight - buttonPadding);
//
//            right = new Rect(buttonWidth + buttonPadding,
//                    screenHeight - buttonHeight - buttonPadding,
//                    buttonWidth + buttonPadding + buttonWidth,
//                    screenHeight - buttonPadding);
//
//            thrust = new Rect(screenWidth - buttonWidth - buttonPadding,
//                    screenHeight - buttonHeight - buttonPadding - buttonHeight - buttonPadding,
//                    screenWidth - buttonPadding,
//                    screenHeight - buttonPadding - buttonHeight - buttonPadding);
//
//            shoot = new Rect(screenWidth - buttonWidth - buttonPadding,
//                    screenHeight - buttonHeight - buttonPadding,
//                    screenWidth - buttonPadding,
//                    screenHeight - buttonPadding);
//
//            pause = new Rect(screenWidth - buttonPadding - buttonWidth,
//                    buttonPadding,
//                    screenWidth - buttonPadding,
//                    buttonPadding + buttonHeight);
//
//            // Add the rect objects in the same order as the static final values
//            currentButtonList.add(left);
//            currentButtonList.add(right);
//            currentButtonList.add(thrust);
//            currentButtonList.add(shoot);
//            currentButtonList.add(pause);
//        }
//
//        public void handleInput(MotionEvent motionEvent) {
//
//            int x = (int) motionEvent.getX(0);
//            int y = (int) motionEvent.getY(0);
//
//            switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
//
//                case MotionEvent.ACTION_DOWN:
//                    if (right.contains(x, y)) {
//                        player.setMovementState(player.RIGHT);
//                    } else if (left.contains(x, y)) {
//                        player.setMovementState(player.LEFT);
//                    } else if (thrust.contains(x, y)) {
//                        player.setMovementState(player.THRUSTING);
//                    } else if (shoot.contains(x, y)) {
//                        playerBullets[nextPlayerBullet].shoot(
//                                player.getA().x,player.getA().y, player.getFacingAngle());
//                        nextPlayerBullet++;
//                        if (nextPlayerBullet == maxPlayerBullets) {
//                            nextPlayerBullet = 0;
//                        }
//                        soundPool.play(shootID, 1, 1, 0, 0, 1);
//
//                    }else if(pause.contains(x, y)) {
//                        paused = !paused;
//                    }
//                    break;
//
//                case MotionEvent.ACTION_UP:
//
//                    player.setMovementState(player.STOPPING);
//
//            }
//
//        }
//    }// End of HUD
}
