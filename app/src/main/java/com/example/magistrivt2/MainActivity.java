package com.example.magistrivt2;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;


public class MainActivity extends Activity implements View.OnTouchListener {

    public static int selected = -1;
    Draw2D draw2D;
    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AssetManager assetManager = this.getAssets();
        int[][] map = new MapLoader().load(assetManager,"map.txt");

        draw2D = new Draw2D(this, map);
        draw2D.setOnTouchListener(this);

        this.setContentView(draw2D);


        // Создание кнопок выбора объектов
        //create a layout
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.HORIZONTAL);

        //params for Buttons
        ViewGroup.LayoutParams params =
                new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
        // controller
        ImageButton clickButton = new ImageButton(this);
        Resources res = this.getResources();
        Bitmap clickBitmap = BitmapFactory.decodeResource(res, R.drawable.clique);
        Bitmap clickScaled = Bitmap.createScaledBitmap(clickBitmap, 100, 100, false);
        clickButton.setImageBitmap(clickScaled);
        clickButton.setLayoutParams(params);
        clickButton.setId(0);
        clickButton.setOnTouchListener(this);
        layout.addView(clickButton);

        ImageButton xMark = new ImageButton(this);
        Bitmap xMarkBitmap = BitmapFactory.decodeResource(res, R.drawable.xmark);
        Bitmap xMarkScaled = Bitmap.createScaledBitmap(xMarkBitmap, 100, 100, false);
        xMark.setImageBitmap(xMarkScaled);
        xMark.setLayoutParams(params);
        xMark.setId(1);
        xMark.setOnTouchListener(this);
        layout.addView(xMark);
        //units
        for(int _i=0; _i<4; _i++) {
            ImageButton imageButton = new ImageButton(this);
            SpriteSheet spriteSheet = draw2D.getSpriteSheet(_i);
            Bitmap bitmap = spriteSheet.grabSprite(0, 4);
            Bitmap bitmapScaled = Bitmap.createScaledBitmap(bitmap, 100, 100, false);
            imageButton.setImageBitmap(bitmapScaled);
            imageButton.setLayoutParams(params);
            imageButton.setId(_i+2);
            imageButton.setOnTouchListener(this);
            layout.addView(imageButton);
        }
        //wall
        ImageButton imageButton = new ImageButton(this);
        Bitmap wallBitmap = BitmapFactory.decodeResource(res, R.drawable.wall);
        Bitmap wallScaled = Bitmap.createScaledBitmap(wallBitmap, 100, 100, false);
        imageButton.setImageBitmap(wallScaled);
        imageButton.setLayoutParams(params);
        imageButton.setId(6);
        imageButton.setOnTouchListener(this);
        layout.addView(imageButton);
        //create the layout param for the layout
        LinearLayout.LayoutParams layoutParam = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        this.addContentView(layout, layoutParam);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (v.getId()) {
            case 0:
                selected = 0;
                return false;
            case 1:
                selected = 1;
                return false;
            case 2:
                selected = 2;
                return false;
            case 3:
                selected = 3;
                return false;
            case 4:
                selected = 4;
                return false;
            case 5:
                selected = 5;
                return false;
            case 6:
                selected = 6;
                return false;
        }
        draw2D.onTouch(event);
        draw2D.postInvalidate();
        return false;
    }
}