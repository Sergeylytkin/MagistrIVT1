package com.example.magistrivt2;

import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class SpriteSheetProvider {
	ArrayList<SpriteSheet> spriteSheets = new ArrayList<SpriteSheet>();
	Game game ;

	public SpriteSheetProvider(Game game) {
		String[] seq = new String[] {"p0.png","p1.png","p2.png","p3.png"};
		this.game = game;
		add(seq);
		addOffSet(seq);

	}
	
	private void addOffSet(String[] seq) {
		for(String filePath : seq) {
			try {
				Resources res = game.getResources();
				AssetManager assetManager = res.getAssets();
				InputStream is = assetManager.open("spr_"+filePath+".txt");
				BufferedReader reader = new BufferedReader(new InputStreamReader(is));
				List<Integer> numbers = new ArrayList<>();
				String line;
				while ((line = reader.readLine()) != null) {
					System.out.println(line + "\n");
					String[] strs = line.split(" ");
					for(int _i=0; _i<strs.length; _i++){
						numbers.add(Integer.parseInt(strs[_i]));
					}
				}
				//Bitmap bi = BitmapFactory.decodeResource(res, R.drawable.p0);
				InputStream is2 = assetManager.open(filePath);
				Bitmap bi = BitmapFactory.decodeStream(is2);
				ColorProvider.setOffSetColor(bi);
				SpriteSheet spriteSheet = new SpriteSheet(bi,numbers);
				spriteSheets.add(spriteSheet);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}

	private void add(String[] seq) {
		for(String filePath : seq) {
			try {
				Resources res = game.getResources();
				AssetManager assetManager = res.getAssets();
				InputStream is = assetManager.open("spr_"+filePath+".txt");
				BufferedReader reader = new BufferedReader(new InputStreamReader(is));
				List<Integer> numbers = new ArrayList<>();
				String line;
				while ((line = reader.readLine()) != null) {
					System.out.println(line + "\n");
					String[] strs = line.split(" ");
					for(int _i=0; _i<strs.length; _i++){
						numbers.add(Integer.parseInt(strs[_i]));
					}
				}
				//Bitmap bi = BitmapFactory.decodeResource(res, R.drawable.p0);
				InputStream is2 = assetManager.open(filePath);
				Bitmap bi = BitmapFactory.decodeStream(is2);

				SpriteSheet spriteSheet = new SpriteSheet(bi,numbers);
				spriteSheets.add(spriteSheet);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}


	public SpriteSheet getSpriteSheet(int id) {
		// TODO Auto-generated method stub
		return spriteSheets.get(id);
	}

}
