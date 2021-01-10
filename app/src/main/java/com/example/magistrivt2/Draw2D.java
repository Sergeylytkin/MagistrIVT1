package com.example.magistrivt2;

import android.app.ActionBar;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

public class Draw2D extends View implements Runnable {

    private Paint mPaint = new Paint();
    private Bitmap wallBitmap;
    private Field field;
    private UnitCont unitCont;

    private SpriteSheetProvider spriteSheetProvider = new SpriteSheetProvider(this);

    public Draw2D(Context context, int[][] map) {
        super(context);
        // Выводим значок из ресурсов
        Resources res = this.getResources();
        wallBitmap = BitmapFactory.decodeResource(res, R.drawable.wall);
        unitCont = new UnitCont(this);
        field = new Field(map, wallBitmap, unitCont);
        Thread t = new Thread(this);
        t.start();
    }

    public SpriteSheet getSpriteSheet(int id) {
        // TODO Auto-generated method stub
        return spriteSheetProvider.getSpriteSheet(id);
    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        //Background Color
        mPaint.setColor(Color.WHITE);
        //Поле
        field.paint(canvas);
    }

    public void onTouch(MotionEvent event) {
        unitCont.onTouch(event);
    }

    @Override
    public void run() {
        while(true){
            unitCont.refresh();
            this.postInvalidate();
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
