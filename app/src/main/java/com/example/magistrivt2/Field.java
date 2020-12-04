package com.example.magistrivt2;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Field {
	int count;//=0
	final static int xNum = 14;
	final static int yNum = 14;
	final static int xSize = 50;
	final static int ySize = 50;
	final static int left = 100;
	final static int top = 100;
	final static int[][] map0 = new int[yNum][xNum];
	final int[][] map1;
	

	public Field(int[][] map) {
		this.map1 = map;
		for(int _i=0; _i<map0.length;_i++) {
			for(int _j=0; _j<map0[_i].length; _j++) {
				map0[_i][_j]=-1;
			}
		}
		for(int _i=0; _i<map1.length;_i++) {
			for(int _j=0; _j<map1[_i].length; _j++) {
				if(map1[_i][_j]==1) {
					map0[_i][_j]=-4;
				}
			}
		}
	}


	public void paint(Canvas g) {
		Paint mPaint = new Paint();
		mPaint.setColor(Color.RED);
		for(int _i=0; _i<yNum+1; _i++) {
			g.drawLine(left+_i*xSize, top, left+_i*xSize, top+ySize*xNum, mPaint);
		}
		mPaint.setColor(Color.BLUE);
		for(int _i=0; _i<xNum+1; _i++) {
			g.drawLine(left, top+_i*ySize, left+xSize*yNum, top+_i*ySize, mPaint);
		}
		for(int _i=0; _i<map1.length; _i++) {
			for(int _j=0; _j<map1[_i].length; _j++) {
				if(map1[_j][_i]==1)
					g.drawRect(left+_i*xSize, top+_j*ySize, left+_i*xSize+xSize, top+_j*ySize+ySize, mPaint);
			}
		}
		
		
//		g.setFont(new Font("Arial",1,20));
//		count++;//count=count+1
//		g.drawString(""+count, 300, 300);
		//g.drawString(String.valueOf(count), 300, 300);
	}
		
}
