package com.example.magistrivt2;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;


public class Field {
	public static int fieldComplexity; //Размерность поля nxn, где n - fieldComplexity
	public static float cellSize;
	public static float top;
	public static int[][] map0;
	private Bitmap wall;
	private UnitCont unitCont;

	public Field(int[][] map, Bitmap wall, UnitCont unitCont) {
		this.fieldComplexity = map.length;
		map0 = new int[fieldComplexity][fieldComplexity];
		for(int _i=0; _i<map0.length;_i++) {
			for(int _j=0; _j<map0[_i].length; _j++) {
				map0[_i][_j]=-1;
			}
		}
		for(int _i=0; _i<map.length;_i++) {
			for(int _j=0; _j<map[_i].length; _j++) {
				if(map[_i][_j]==1) {
					map0[_i][_j]=-4;
				}
			}
		}
		this.wall = wall;
		this.unitCont = unitCont;
	}

	public void paint(Canvas canvas) {
		cellSize = canvas.getWidth() / fieldComplexity;
		top = (canvas.getHeight() - cellSize *fieldComplexity) / 2;

		Paint mPaint = new Paint();
		// Vertical Lines
		mPaint.setColor(Color.RED);
		for(int _i=0; _i<fieldComplexity+1; _i++) {
			canvas.drawLine(_i* cellSize, top, _i* cellSize, top+ cellSize *fieldComplexity, mPaint);
		}
		// Horizontal Lines
		mPaint.setColor(Color.BLUE);
		for(int _i=0; _i<fieldComplexity+1; _i++) {
			canvas.drawLine(0, top + _i * cellSize, cellSize * fieldComplexity, top + _i * cellSize, mPaint);
		}
		//Wall
		Bitmap wallScaled = Bitmap.createScaledBitmap(wall, Math.round(Field.cellSize), Math.round(Field.cellSize), false);
		for(int _i=0; _i<map0.length; _i++) {
			for(int _j=0; _j<map0.length; _j++) {
				if(map0[_j][_i]==-4)
					canvas.drawBitmap(wallScaled, _i*cellSize, top+_j*cellSize, mPaint);
			}
		}
		unitCont.paint(canvas);
	}
}
