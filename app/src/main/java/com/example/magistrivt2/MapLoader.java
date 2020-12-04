package com.example.magistrivt2;

import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class MapLoader {

	public int[][] load(AssetManager assetManager, String string) {

		BufferedReader reader = null;
//		InputStream input = null;
//		try {
//			input = assetManager.open("map.txt");
//			int size = input.available();
//			System.out.println("размер "+size);
//			byte[] buffer = new byte[size];
//			input.read(buffer);
//			input.close();
//			// byte buffer into a string
//			//String text = new String(buffer);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		try {
			reader = new BufferedReader(new InputStreamReader(assetManager.open("map.txt")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		int[][] arr = new int[8][];
		Scanner sc;
		try {
			sc = new Scanner(reader);
			int count = 0;
			while(sc.hasNextLine()) {
				String str = sc.nextLine();
				String[] strArr = str.split(" ");
				int[] iArr = new int[strArr.length];
				for(int _i=0; _i<strArr.length; _i++) {
					iArr[_i] = Integer.parseInt(strArr[_i]);
				}
				arr[count] = iArr;
				count++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			
		}
		//print(arr);
		return arr;
	}

	public static void print(int[][] arr) {
		for(int _i=0; _i<arr.length; _i++) {
			int[] tArr = arr[_i];
			for(int _j=0; _j<tArr.length; _j++) {
				System.out.print(tArr[_j]+",");
			}
			System.out.println();
		}
	}
}
