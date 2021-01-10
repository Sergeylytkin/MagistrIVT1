package com.example.magistrivt2;

import android.graphics.Canvas;
import android.view.MotionEvent;

import java.util.ArrayList;

public class UnitCont {
    ArrayList<Unit> units = new ArrayList<Unit>();
    Draw2D game;

    public UnitCont(Draw2D game) {
        this.game = game;


    }
    public void paint(Canvas g) {
        for(Unit u: units) {
            if (u == null)
                continue;
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
            if (u == null)
                continue;
            u.tick();
        }
    }

    public void onTouch(MotionEvent e) {
        int qx = (int)((e.getX())/Field.cellSize);
        int qy = (int)((e.getY()-Field.top)/Field.cellSize);
        if (Field.fieldComplexity <= qx || qx<0 || Field.fieldComplexity <= qy || qy<0)
            return;
        //remove
        if (MainActivity.selected == 1) {
            if (Field.map0[qy][qx] == -4)
                Field.map0[qy][qx] = -1;
            for(Unit u: units) {
                if (u.getUnit(qx,qy) != null) {
                    units.remove(u);
                    break;
                }
            }
            return;
        }
        if (Field.map0[qy][qx] != -1 && MainActivity.selected != 0)
            return;
        //create
        switch (MainActivity.selected) {
            case 2:
                Unit hoplit = new Hoplit(qx, qy, game);
                units.add(hoplit);
                return;
            case 3:
                Unit archer = new Archer(qx, qy, game);
                units.add(archer);
                return;
            case 4:
                Unit eagle = new Eagle(qx, qy, game);
                units.add(eagle);
                return;
            case 5:
                Unit ballista = new Ballista(qx, qy, game);
                units.add(ballista);
                return;
            case 6:
                Field.map0[qy][qx] = -4;
                return;
        }

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
