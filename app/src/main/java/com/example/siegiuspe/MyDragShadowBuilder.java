package com.example.siegiuspe;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.appcompat.content.res.AppCompatResources;

import com.example.siegiuspe.GameObjects.BaseObject;

public class MyDragShadowBuilder extends View.DragShadowBuilder {

    private Context context;
    private Drawable shadow;
    private Bitmap img;
    private float offset_x,offset_y;

    public MyDragShadowBuilder(View v,int img,float offset_x , float offset_y)
    {
        super(v);
        context = BaseObject.sSystemRegistry.contextParameters.context;
        this.img = BitmapFactory.decodeResource(context.getResources(), img);;
        shadow = AppCompatResources.getDrawable(context, img);
        this.offset_x = offset_x;
        this.offset_y = offset_y;
    }

    public MyDragShadowBuilder(View v,Bitmap img,float offset_x , float offset_y)
    {
        super(v);
        context = BaseObject.sSystemRegistry.contextParameters.context;
        this.img = img;
        shadow = new BitmapDrawable(context.getResources(), img);
        this.offset_x = offset_x;
        this.offset_y = offset_y;
    }



    @Override
    public void onProvideShadowMetrics(Point outShadowSize, Point outShadowTouchPoint) {
        int width , height;

        width = img.getWidth()/2;
        height = img.getHeight()/2;

        shadow.setBounds(0,0,width,height);
        outShadowSize.set(width,height);
        outShadowTouchPoint.set((int)(width*offset_x),(int)(height*offset_y));
    }

    @Override
    public void onDrawShadow(Canvas canvas) {
        int width , height;
        RectF rect;
        Paint paint = new Paint();
        paint.setARGB(150,0,0,200);

        width = img.getWidth()/2;
        height = img.getHeight()/2;

        rect = new RectF((int)(width*offset_x) - 80,(int)(height*offset_y) - 40,(int)(width*offset_x) + 80,(int)(height*offset_y) + 40);
        canvas.drawOval(rect,paint);
        shadow.draw(canvas);
    }
}
