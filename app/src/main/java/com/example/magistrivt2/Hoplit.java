package com.example.magistrivt2;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Hoplit extends Unit {
	private final int INTER_QUANTITY = 10;
	private SpriteSheet ss;
	private Bitmap frame;
	private HoplitAnimation pAnimation = new HoplitAnimation();
		
	private int x;
	private int y;
	private int state = 0;
	private ArrayList<Integer> trends = new ArrayList<Integer>();
	
	private ArrayList<Point> path = new ArrayList<Point>();
	private boolean selected = false;
	private Point target = null;
	
	private HashMap<Integer, Integer> trendMap = new HashMap<Integer, Integer>();
	private HashMap<Integer, Integer> turnMap = new HashMap<Integer, Integer>();
	
	private int renderPhase = 0;
	private int renderCount = 0;
	private int deltaY;
	private int deltaX;
	private boolean isInterMediate = false;
	
	public Hoplit(int x, int y, Draw2D game) {
		this.x = x;
		this.y = y;
		Field.map0[y][x]=-2;
		
		ss = game.getSpriteSheet(4);
		
		for(int _i=0; _i<8; _i++) {
			trends.add(_i);
		}
		
		trendMap.put(-10, 0);
		trendMap.put( -9, 1);
		trendMap.put(  1, 2);
		trendMap.put( 11, 3);
		trendMap.put( 10, 4);
		trendMap.put(  9, 5);
		trendMap.put( -1, 6);
		trendMap.put(-11, 7);
		
		turnMap.put(1, -1);//направо полоборота
		turnMap.put(2, -2);//направо 
		turnMap.put(3, -2);//направо + в полоборота
		turnMap.put(4, 2);//кругом
		turnMap.put(5, 2);//налево + в полоборота
		turnMap.put(6, 2);//налево
		turnMap.put(7, 1);//налево полоборота
	}

	public void paint(Canvas g) {
		int px = Field.left+x*Field.xSize;
		int py = Field.top+y*Field.ySize;
		ss.render(g, frame, px+deltaX, py+deltaY, selected);

	}

	public void tick() {
		if(target!=null && ! isInterMediate) {
			path = new WaveAlg().findDiagonPath(Field.map0, x, y, target.x, target.y);
			target = null;
		}
		
	if(! isInterMediate) {
		if(path.size()>1) {
			Point p = path.get(1);
			int newTrend = trendMap.get((p.x-x)+10*(p.y-y));
			if(newTrend==trends.get(0)) {
				if(Field.map0[p.y][p.x]==-1) {
					//двигается вперед
					isInterMediate = true;
				}else {
					state = 0;
				}
			}else {
				//делаем поворот
				//int offSet = Math.abs(newTrend-trends.get(0));
				int offSet = 0;
				for(int _i=0; _i<trends.size(); _i++) {
					if(trends.get(_i)==newTrend) {
						offSet = _i;
						break;
					}
				}
				Collections.rotate(trends, turnMap.get(offSet));
				isInterMediate = false;
			}
		}else {
			state=0;
		}
	}
	if(isInterMediate  == true) {
		state = 1;
		if(renderPhase==1) {
			if(path.size()>1) {
				Point p = path.get(1);
				if(Field.map0[p.y][p.x]==-1) {
					path.remove(1);
					Field.map0[y][x]=-1;
					x = p.x; y= p.y;
					Field.map0[y][x]=-2;
					renderPhase = 2;
					MapLoader.print(Field.map0);
				}else {
					state = 0;
					renderPhase = 3;
				}
			}
		}
		if(renderPhase==0) {
			//рисование первой половины пр кадров
			renderCount++;
			setOffSet();
			if(renderCount>=INTER_QUANTITY/2) {
				renderPhase = 1;
			}
		}else if(renderPhase==2) {
			//рисование второй половины пр кадров
			renderCount--;
			setOffSet2();
			if(renderCount<=0) {
				renderPhase = 0;
				isInterMediate = false;
			}
		}else if(renderPhase==3) {
			//рисование второй половины пр кадров
			renderCount--;
			setOffSet();
			if(renderCount<=0) {
				renderPhase = 0;
				state = 0;
				isInterMediate = false;
			}
		}
	}

		frame = ss.grabSprite(trends.get(0),pAnimation.getRow(state));
	}

	private void setOffSet2() {
		setOffSet();
		deltaX = - deltaX;
		deltaY = - deltaY; 
	}

	private void setOffSet() {
		switch(trends.get(0)) {
		case 0:
			deltaY = -renderCount*Field.ySize/INTER_QUANTITY ;
			break;
		case 1:
			deltaX = +renderCount*Field.xSize/INTER_QUANTITY;
			deltaY = -renderCount*Field.ySize/INTER_QUANTITY;
			break;
		case 2:
			deltaX = +renderCount*Field.xSize/INTER_QUANTITY;
			break;
		case 3:
			deltaX = +renderCount*Field.xSize/INTER_QUANTITY;
			deltaY = +renderCount*Field.ySize/INTER_QUANTITY;
			break;
		case 4:
			deltaY = +renderCount*Field.ySize/INTER_QUANTITY;
			break;
		case 5:
			deltaX = -renderCount*Field.xSize/INTER_QUANTITY;
			deltaY = +renderCount*Field.ySize/INTER_QUANTITY;
			break;
		case 6:
			deltaX = -renderCount*Field.xSize/INTER_QUANTITY;
			break;
		case 7:
			deltaX = -renderCount*Field.xSize/INTER_QUANTITY;
			deltaY = -renderCount*Field.ySize/INTER_QUANTITY;
			break;
		}		
	}



	public void setTrend(int trend) {
		Collections.rotate(trends, -trend);
	}

	public void setState(int state) {
		this.state = state;
	}
	public void setTarget(int tx, int ty) {
		if(selected) {
			//WaveAlg alg = new WaveAlg();
			//path = new WaveAlg().findDiagonPath(Field.map0, x, y, tx, ty);
			target = new Point(tx, ty);
			//selected = false;//времянка
		}
	}
	public boolean setSelectedState (int qx, int qy) {
		if(x==qx && y==qy) {
			selected = !selected;
			return true;
		}
		return false;
	}
}
