package com.example.magistrivt2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {
    Draw2D draw2D;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AssetManager assetManager = this.getAssets();
        int[][] map = new MapLoader().load(assetManager,"map.txt");
        //MapLoader.print(map);

        draw2D = new Draw2D(this, map);
        draw2D.setOnTouchListener(this);
        this.setContentView(draw2D);

        //setContentView(R.layout.activity_main);


    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        draw2D.onTouch(event);
        draw2D.postInvalidate();
        return false;
    }
}