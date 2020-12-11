package com.example.magistrivt2;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
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
			Bitmap newImage  = Bitmap.createBitmap(MAX_WIDTH,MAX_HEIGHT, Bitmap.Config.ALPHA_8);
			Bitmap cropped  = Bitmap.createBitmap(source, numbers.get(_i*6+2), numbers.get(_i*6+3), numbers.get(_i*6+4), numbers.get(_i*6+5));
			//BufferedImage bi = image.getSubimage(numbers.get(_i*6+2), numbers.get(_i*6+3), numbers.get(_i*6+4), numbers.get(_i*6+5));
			Canvas canvas = new Canvas(newImage);
			canvas.drawBitmap(cropped,  0, 0, null );
			//g.drawImage(bi, MAX_WIDTH/2 - numbers.get(_i*6), MAX_HEIGHT/2 - numbers.get(_i*6+1), null);
			frames.add(newImage);
		}
		for(int _k=3; _k>0; _k--) {
			for(int _i=sprNumber*_k/5; _i<sprNumber*(_k+1)/5; _i++) {
				Bitmap newImage  = Bitmap.createBitmap(MAX_WIDTH,MAX_HEIGHT, Bitmap.Config.ALPHA_8);
				Bitmap cropped  = Bitmap.createBitmap(source, numbers.get(_i*6+2), numbers.get(_i*6+3), numbers.get(_i*6+4), numbers.get(_i*6+5));
				//BufferedImage bi = image.getSubimage(numbers.get(_i*6+2), numbers.get(_i*6+3), numbers.get(_i*6+4), numbers.get(_i*6+5));
				Canvas canvas = new Canvas(newImage);
				canvas.drawBitmap(cropped,  MAX_WIDTH/2 - numbers.get(_i*6), MAX_HEIGHT/2 - numbers.get(_i*6+1), null );
//				BufferedImage newImage = new BufferedImage(MAX_WIDTH, MAX_HEIGHT, BufferedImage.TYPE_INT_ARGB);
//				g.drawImage(frames.get(_i), 0+frames.get(_i).getWidth(), 0,-frames.get(_i).getWidth(), frames.get(_i).getHeight(), null);
//				frames.add(newImage);
			}
		}
	}

	public Bitmap grabSprite(int col, int row) {
		int sprRow = sprNumber/5;
		int _n = col*sprRow+row;
		return frames.get(_n);
	}

	public void render(Canvas g, Bitmap frame, int x, int y, boolean selected) {
//		int px = Field.left+x*Field.xSize;
//		int py = Field.top+y*Field.ySize;
		Paint mPaint = new Paint();
		if(selected) {
//			g2.setColor(Color.orange);
//			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//			Stroke newStroke = new BasicStroke(2f);
//			g2.setStroke(newStroke );
//			g2.drawOval(px+4, py+4, Field.xSize-8, Field.ySize-8);
			mPaint.setColor(Color.RED);
			g.drawOval(x+4, y+4, x+4+Field.xSize-8, y+4+Field.ySize-8, mPaint);
		}

		g.drawBitmap(frame, x-MAX_WIDTH/2+Field.xSize/2, y-MAX_HEIGHT/2+Field.xSize/2, mPaint);
		//g.drawImage(frame, px-(MAX_WIDTH-Field.xSize)/2, py-MAX_HEIGHT/2+Field.ySize/2, null);
	}
}