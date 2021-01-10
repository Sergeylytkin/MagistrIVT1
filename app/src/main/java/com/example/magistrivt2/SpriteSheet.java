package com.example.magistrivt2;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.List;

public class SpriteSheet {
    private ArrayList<Bitmap> frames = new ArrayList<Bitmap>();
    private int MAX_WIDTH;
    private int MAX_HEIGHT;
    int sprNumber =0;
    public SpriteSheet(Bitmap source, List<Integer> numbers) {
        sprNumber = numbers.size()/6;
        for(int _i=0; _i<sprNumber; _i++) {
            int halfWidth = numbers.get(_i*6+4) - numbers.get(_i*6);
            if((numbers.get(_i*6) - numbers.get(_i*6+2))>halfWidth) {
                halfWidth = numbers.get(_i*6) - numbers.get(_i*6+2);
            }
            int halfHeight = numbers.get(_i*6+5) - numbers.get(_i*6+1);
            if((numbers.get(_i*6+1) - numbers.get(_i*6+3))>halfHeight) {
                halfHeight = numbers.get(_i*6+1) - numbers.get(_i*6+3);
            }
            if(2*halfWidth>MAX_WIDTH) {
                MAX_WIDTH = 2*halfWidth;
            }
            if(2*halfHeight>MAX_HEIGHT) {
                MAX_HEIGHT = 2*halfHeight;
            }
        }
        for(int _i=0; _i<sprNumber; _i++) {
            //Bitmap newImage = new BufferedImage(MAX_WIDTH, MAX_HEIGHT, BufferedImage.TYPE_INT_ARGB);
            Bitmap newImage  = Bitmap.createBitmap(MAX_WIDTH,MAX_HEIGHT, Bitmap.Config.ARGB_8888);
            Bitmap cropped  = Bitmap.createBitmap(source, numbers.get(_i*6+2), numbers.get(_i*6+3), numbers.get(_i*6+4), numbers.get(_i*6+5));
            //BufferedImage bi = image.getSubimage(numbers.get(_i*6+2), numbers.get(_i*6+3), numbers.get(_i*6+4), numbers.get(_i*6+5));
            Canvas canvas = new Canvas(newImage);
            canvas.drawBitmap(cropped,  0, 0, null );
            //g.drawImage(bi, MAX_WIDTH/2 - numbers.get(_i*6), MAX_HEIGHT/2 - numbers.get(_i*6+1), null);
            frames.add(newImage);
        }
        for(int _k=3; _k>0; _k--) {
            for(int _i=sprNumber*_k/5; _i<sprNumber*(_k+1)/5; _i++) {
                Matrix matrix = new Matrix();
                matrix.preScale(-1.0f,1.0f);
                Bitmap mirrored = Bitmap.createBitmap(frames.get(_i),0,0, MAX_WIDTH, MAX_HEIGHT, matrix, false);
                frames.add(mirrored);
            }
        }
    }

    public Bitmap grabSprite(int col, int row) {
        int sprRow = sprNumber/5;
        int _n = col*sprRow+row;
        return frames.get(_n);
    }

    public void render(Canvas g, Bitmap frame, float x, float y, boolean selected) {
//		int px = Field.left+x*Field.xSize;
//		int py = Field.top+y*Field.ySize;
        Paint mPaint = new Paint();
        if(selected) {
            mPaint.setColor(Color.RED);
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setStrokeWidth(2);
            //mPaint.setStyle(Paint.Style.);
            g.drawCircle(x+Field.cellSize/2, y+Field.cellSize/2, (Field.cellSize+Field.cellSize-4)/4,mPaint);
        }
        if (frame==null)
            return;
        g.drawBitmap(frame, x-MAX_WIDTH/2+Field.cellSize/2, y-MAX_HEIGHT/2+Field.cellSize/2, mPaint);
        //g.drawImage(frame, px-(MAX_WIDTH-Field.xSize)/2, py-MAX_HEIGHT/2+Field.ySize/2, null);
    }
}