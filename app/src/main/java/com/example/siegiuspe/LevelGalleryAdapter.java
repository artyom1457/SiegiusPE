package com.example.siegiuspe;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class LevelGalleryAdapter extends ArrayAdapter<Integer> {

    private Context context;
    private Integer[] resources;


    public LevelGalleryAdapter(Context context,int resource, Integer[] objects)
    {
        super(context,resource,objects);
        resources = objects;
        this.context = context;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if(convertView == null)
        {
            convertView = new ImageView(getContext());
        }
        ((ImageView)convertView).setImageResource(getItem(position));
        ((ImageView) convertView).setScaleType(ImageView.ScaleType.FIT_XY);
        convertView.setLayoutParams(new Gallery.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));


        return convertView;
    }
}
