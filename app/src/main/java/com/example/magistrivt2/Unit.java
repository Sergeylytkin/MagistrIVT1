package com.example.magistrivt2;

import android.graphics.Canvas;

public class Unit {

    protected int x;
    protected int y;

    public void paint(Canvas g) {
        // TODO Auto-generated method stub

    }

    public boolean setSelectedState(int qx, int qy) {
        // TODO Auto-generated method stub
        return false;
    }

    public void setTarget(int qx, int qy) {
        // TODO Auto-generated method stub

    }

    public void tick() {
        // TODO Auto-generated method stub

    }

    public void setTrend(int i) {
        // TODO Auto-generated method stub

    }

    public void setState(int i) {
        // TODO Auto-generated method stub

    }

    public Unit getUnit(int x, int y) {
        // TODO Auto-generated method stub
        if (this.x == x && this.y == y)
            return this;
        return null;
    }
}
