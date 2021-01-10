package com.example.magistrivt2;

import java.util.ArrayList;

public class WaveAlg {
    public static ArrayList<Point> findHorVerPath(int[][] map0, int x, int y, int tx, int ty) {
        return findPath(map0, x, y, tx, ty, 4);
    }
    public static ArrayList<Point> findDiagonPath(int[][] map0, int x, int y, int tx, int ty) {
        return findPath(map0, x, y, tx, ty, 8);
    }

    private static ArrayList<Point> findPath(int[][] map0, int x, int y, int tx, int ty, int neighboorNum) {
        MapLoader.print(map0);
        ArrayList<Point> wave = new ArrayList<Point>();
        if(map0[y][x]==-4 && map0[ty][tx]==-4) {
            System.out.println("юнит стоит на препятствии или направлен на препятствие");
            return wave;
        }
        //распространяем волну
        int[][] cloneMap = clone(map0);
        cloneMap[y][x]=-1;
        ArrayList<Point> neighbourWave = new ArrayList<Point>();
        neighbourWave.add(new Point(tx, ty));
        int step = 0;
        cloneMap[ty][tx] = step;
//		int[] dx = {0,1,0,-1};
//		int[] dy = {-1,0,1,0};
        int[] dx = {0,1,0,-1,-1,-1,1,1};
        int[] dy = {-1,0,1,0,-1,1,-1,1};
        //int neighboorNum = 4;
        while(neighbourWave.size()>0) {
            step++;
            wave.clear();
            for(Point p:neighbourWave) {
                for(int _d=0; _d<neighboorNum; _d++) {
                    tx = p.x + dx[_d];
                    ty = p.y + dy[_d];
                    if(tx>=0 && tx<cloneMap[0].length && ty>=0 && ty<cloneMap.length) {
                        if(cloneMap[ty][tx]==-1) {
                            wave.add(new Point(tx,ty));
                            cloneMap[ty][tx]=step;
                        }
                    }
                }
            }
            neighbourWave = new ArrayList<>(wave);
        }
        //поиск пути
        wave.clear();
        wave.add(new Point(x, y));
        boolean flag = true;
        while(cloneMap[y][x]!=0) {
            flag = true;
            for(int _d=0; _d<dx.length; _d++) {
                tx = x + dx[_d];
                ty = y + dy[_d];
                if(tx>=0 && tx<cloneMap[0].length && ty>=0 && ty<cloneMap.length) {
                    if(cloneMap[y][x]-1 == cloneMap[ty][tx]) {
                        x = tx;
                        y = ty;
                        wave.add(new Point(x, y));
                        flag = false;
                        break;
                    }
                }
            }
            if(flag) {
                System.out.println("Что-то не в порядке!!!");
                break;
            }
        }
        return wave;
    }
    private static int[][] clone(int[][] map) {
        int[][] map2 = new int[map.length][map[0].length];
        for(int _i=0; _i<map2.length; _i++) {
            for(int _j=0; _j<map2[0].length; _j++) {
                map2[_i][_j] = map[_i][_j];
            }
        }
        return map2;
    }
}
