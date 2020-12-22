package com.example.magistrivt2;

import android.graphics.Canvas;
import android.view.MotionEvent;

public class UnitCont {
	Unit[] units = new Unit[5];

	public UnitCont(Game game) {
		units[0]= new Hoplit(2,2, game);
		units[1]= new Hoplit(5,7, game);
		units[2]= new Archer(8,3, game);
		units[3]= new Archer(6,2, game);
		units[4]= new Hoplit(10,1, game);
		units[0].setTrend(2);
		units[0].setState(0);
		units[2].setTrend(2);
		units[2].setState(0);
		
	}
	public void paint(Canvas g) {
		for(Unit u: units) {
			u.paint(g);
		}
	}
//	public void mouseClicked(MouseEvent e) {
//		int qx = (e.getX()-field.left)/field.xSize;
//		int qy = (e.getY()-field.top)/field.ySize;
//		if(e.getButton()==MouseEvent.BUTTON1) {
//			for(Unit u: units) {
//				u.setSelectedState(qx, qy);
//			}
//		}else if(e.getButton()==MouseEvent.BUTTON3){
//			for(Unit u: units) {
//				u.setTarget(qx, qy);
//			}
//		}
//	}
	public void refresh() {
		for(Unit u: units) {
			u.tick();
		}
	}

	public void onTouch(MotionEvent e) {
		int qx = (int)((e.getX()-Field.left)/Field.xSize);
		int qy = (int)((e.getY()-Field.top)/Field.ySize);
		boolean isBuzy = false;
		if(e.getAction()==MotionEvent.ACTION_DOWN) {
			for(Unit u: units) {
				if(u.setSelectedState(qx, qy)) {
					isBuzy = true;
				}
			}
		}
		if(isBuzy == false){
			for(Unit u: units) {
				u.setTarget(qx, qy);
			}
		}
	}
}
