package com.example.siegiuspe;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.siegiuspe.GameObjects.BaseObject;

public class OptionsFragment extends Fragment {

    Button btnCampaign;


    public OptionsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_options, container, false);
        btnCampaign = view.findViewById(R.id.btn_campaign);
        btnCampaign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BaseObject.sSystemRegistry.contextParameters.context, SelectionRoom.class);
                startActivityForResult(intent,1);

//                try {
//                    Intent intent = new Intent(BaseObject.sSystemRegistry.contextParameters.context, GameBoard.class);
//                    intent.putExtra("BitmapImage", BitmapFactory.decodeResource(getResources(), R.drawable.map_killing_fields));
//                    startActivityForResult(intent,1);
//                }
//                catch (Exception exception)
//                {
//                    exception.printStackTrace();
//                    DebugLog.d("game",exception.toString());
//                }

            }
        });
        return view;
    }

    public static OptionsFragment newInstance()
    {
        OptionsFragment fragment = new OptionsFragment();
        return fragment;
    }

}