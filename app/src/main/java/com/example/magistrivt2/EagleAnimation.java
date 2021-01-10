package com.example.magistrivt2;

import java.util.ArrayList;
import java.util.Collections;

public class EagleAnimation {
    private ArrayList<Integer> stoping = new ArrayList<Integer>();
    private ArrayList<Integer> walking = new ArrayList<Integer>();
    private ArrayList<Integer> battlng = new ArrayList<Integer>();
    private ArrayList<Integer> destroy = new ArrayList<Integer>();
    private ArrayList<ArrayList<Integer>> animation = new ArrayList<ArrayList<Integer>>();

    public EagleAnimation() {


        stoping.add(0);

        walking.add(0);
        walking.add(1);
        walking.add(2);
        walking.add(4);

        battlng.add(5);
        battlng.add(6);
        battlng.add(7);

        destroy.add(7);

        animation.add(stoping);
        animation.add(walking);
        animation.add(battlng);
        animation.add(destroy);
    }
    public int getRow(int state) {
        int row = animation.get(state).get(0);
        Collections.rotate(walking, 1);
        Collections.rotate(battlng, 1);
        return row;
    }
}
