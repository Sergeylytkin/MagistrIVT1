package com.example.magistrivt2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.AssetManager;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AssetManager assetManager = this.getAssets();
        int[][] map = new MapLoader().load(assetManager,"map.txt");
        MapLoader.print(map);
        Draw2D draw2D = new Draw2D(this, map);
        this.setContentView(draw2D);

        //setContentView(R.layout.activity_main);


    }
}