package com.example.magistrivt2;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Eagle extends Unit{
    private SpriteSheet ss;
    private Bitmap frame;
    EagleAnimation animation = new EagleAnimation();

    private ArrayList<Integer> trends= new ArrayList<>();
    private int state = 1;

    ArrayList<Point> path = new ArrayList<Point>();
    private Point target = null;
    private boolean selected = false;

    private HashMap<Integer, Integer> trendMap = new HashMap<>();
    private HashMap<Integer, Integer> turnMap = new HashMap<>();
    private static final int INTER_QUANTITY = 10;
    private boolean isInterMediate = false;
    private int renderPhase = 0;
    private int renderCount = 0;
    private float deltaY;
    private float deltaX;

    public Eagle(int x, int y, Draw2D game) {
        this.x = x;
        this.y = y;
        Field.map0[y][x]=-2;
        ss = game.getSpriteSheet(2);

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
        float px = Field.cellSize*x;
        float py = Field.cellSize*y+Field.top;
        ss.render(g, frame, px+deltaX, py+deltaY, selected);
    }

    public void tick() {
        //battle, building, destroy
        renderTick();//рисование
    }
    public void renderTick() {
        if(target!=null && ! isInterMediate) {
            WaveAlg alg = new WaveAlg();
            path = alg.findDiagonPath(Field.map0, x, y, target.x, target.y);
        }
        if(! isInterMediate) {
            if(path.size()>1) {//если есть следующая клетка для передвижения
                state=1;
                Point p = path.get(1);
                int newTrend  = trendMap.get((p.x-x)+10*(p.y-y));
                if(newTrend==trends.get(0)) {//если текущее направление юнита совпадает с направлением следующей клетки
                    if(Field.map0[p.y][p.x]==-1) {//если клетка свободна
                        isInterMediate = true;
                    }else {
                        state = 0;
                    }
                }else{
                    int offSet = 0;//на сколько надо повернуть
                    for(int _i=0; _i<trends.size(); _i++) {
                        if(trends.get(_i)==newTrend) {
                            offSet = _i;
                            break;
                        }
                    }
                    Collections.rotate(trends, turnMap.get(offSet));//поворачиваем
                    isInterMediate = false;
                }
            }else {
                state=0;
            }
        }

        if(isInterMediate) {
            if(renderPhase==5) {
                if(path.size()>1) {
                    Point p = path.get(1);
                    if(Field.map0[p.y][p.x]==-1) {
                        Field.map0[y][x]=-1;
                        x = p.x;
                        y = p.y;
                        Field.map0[y][x]=-2;
                        path.remove(1);
                        renderPhase=1;
                        //MapLoader.print(Field.map0);
                    }else {
                        renderPhase = 2;
                    }
                }
            }
            if(renderPhase==0) {
                renderCount++;
                setOffSet();
                if(renderCount>=INTER_QUANTITY/2) {
                    renderPhase=5;
                }
            }else if(renderPhase==1) {
                renderCount--;
                setOffSet2();
                if(renderCount<=0) {
                    renderPhase=0;
                    isInterMediate = false;
                }
            }else if(renderPhase==2) {
                renderCount--;
                setOffSet();
                if(renderCount<=0) {
                    renderPhase=0;
                    state = 0;
                    isInterMediate = false;
                }
            }
        }
        frame = ss.grabSprite(trends.get(0),animation.getRow(state));
    }

    private void setOffSet2() {
        setOffSet();
        deltaX = - deltaX;
        deltaY = - deltaY;
    }
    private void setOffSet() {
        switch(trends.get(0)) {
            case 0:
                deltaY = -renderCount*Field.cellSize/INTER_QUANTITY;
                break;
            case 1:
                deltaX = +renderCount*Field.cellSize/INTER_QUANTITY;
                deltaY = -renderCount*Field.cellSize/INTER_QUANTITY;
                break;
            case 2:
                deltaX = +renderCount*Field.cellSize/INTER_QUANTITY;
                break;
            case 3:
                deltaX = +renderCount*Field.cellSize/INTER_QUANTITY;
                deltaY = +renderCount*Field.cellSize/INTER_QUANTITY;
                break;
            case 4:
                deltaY = +renderCount*Field.cellSize/INTER_QUANTITY;
                break;
            case 5:
                deltaX = -renderCount*Field.cellSize/INTER_QUANTITY;
                deltaY = +renderCount*Field.cellSize/INTER_QUANTITY;
                break;
            case 6:
                deltaX = -renderCount*Field.cellSize/INTER_QUANTITY;
                break;
            case 7:
                deltaX = -renderCount*Field.cellSize/INTER_QUANTITY;
                deltaY = -renderCount*Field.cellSize/INTER_QUANTITY;
                break;
        }
    }
    public void setTarget(int cx, int cy) {
        if(selected){

            target = new Point(cx, cy);
            selected = !selected;
        }
    }

    public boolean setSelectedState(int cx, int cy) {
        if(x==cx && y==cy) {
            selected = !selected;
            return true;
        }
        return false;
    }

    public void setTrend(int trend) {
        Collections.rotate(trends, trend);
    }

    public void setState(int state) {
        this.state = state;
    }
}
