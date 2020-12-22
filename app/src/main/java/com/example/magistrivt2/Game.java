package com.example.magistrivt2;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;

import com.example.magistr.R;

public class Game extends View implements Runnable {

    private Paint mPaint = new Paint();
    private Rect mRect = new Rect();
    private Bitmap mBitmap;

    Field field = null;
    private Bitmap uBitmap;
    private SpriteSheetProvider spriteSheetProvider = new SpriteSheetProvider(this);
    private UnitCont unitCont;

    public Game(Context context, int[][] map) {
        super(context);
        // Выводим значок из ресурсов
        Resources res = this.getResources();
        mBitmap = BitmapFactory.decodeResource(res, R.drawable.cat_bottom);
        field = new Field(map);
        uBitmap = BitmapFactory.decodeResource(res, R.drawable.p0);
        unitCont = new UnitCont(this);
        Thread t = new Thread(this);
        t.start();
    }


    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);

        int width = canvas.getWidth();
        int height = canvas.getHeight();

        // стиль Заливка
        mPaint.setStyle(Paint.Style.FILL);

        // закрашиваем холст белым цветом
        mPaint.setColor(Color.WHITE);
        canvas.drawPaint(mPaint);

        // Рисуем желтый круг
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.YELLOW);
        // canvas.drawCircle(950, 30, 25, mPaint);
        canvas.drawCircle(width - 30, 30, 25, mPaint);

        // Рисуем зеленый прямоугольник
        mPaint.setColor(Color.GREEN);
        //  canvas.drawRect(20, 650, 950, 680, mPaint);
        canvas.drawRect(0, canvas.getHeight() - 30, width, height, mPaint);

        // Рисуем текст
        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(32);
        //  canvas.drawText("Лужайка только для котов", 30, 648, mPaint);
        canvas.drawText("Лужайка только для котов", 30, height - 32, mPaint);

        // Текст под углом
        // int x = 810;
        int x = width - 170;
        int y = 190;

        mPaint.setColor(Color.GRAY);
        mPaint.setTextSize(27);
        String beam = "Лучик солнца!";

        canvas.save();
        // Создаем ограничивающий прямоугольник для наклонного текста
        // поворачиваем холст по центру текста
        //canvas.rotate(-45, x + mRect.exactCenterX(), y + mRect.exactCenterY());

        // Рисуем текст
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawText(beam, x, y, mPaint);
        field.paint(canvas);
       //canvas.drawBitmap(uBitmap, 0, 0 , mPaint);
        //Bitmap newImage  = Bitmap.createBitmap(200,200, Bitmap.Config.ALPHA_8);
//        Bitmap newImage  = Bitmap.createBitmap(uBitmap, 0,0, 200,180);
//        canvas.drawBitmap(newImage, 200,200, mPaint);
//
//        Matrix matrix = new Matrix();
//        matrix.preScale(-1.0f, 1.0f);
//        Bitmap mirroredBitmap = Bitmap.createBitmap(newImage, 0, 0, newImage.getWidth(), newImage.getHeight(), matrix, false);
//        canvas.drawBitmap(mirroredBitmap, 200,300, mPaint);
        // восстанавливаем холст
        //canvas.restore();

        // Выводим изображение
        // canvas.drawBitmap(mBitmap, 450, 530, mPaint);

        unitCont.paint(canvas);

        canvas.drawBitmap(mBitmap, width - mBitmap.getWidth(), height - mBitmap.getHeight() - 10, mPaint);
    }
    public SpriteSheet getSpriteSheet(int id) {
        // TODO Auto-generated method stub
        return spriteSheetProvider.getSpriteSheet(id);
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
    public void onTouch(MotionEvent event) {
        unitCont.onTouch(event);
    }
}
