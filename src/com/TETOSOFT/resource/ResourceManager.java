package com.TETOSOFT.resource;

import com.TETOSOFT.tilegame.TileMap;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.io.*;
import javax.swing.ImageIcon;
import java.util.ArrayList;
import com.TETOSOFT.tilegame.objects.*;

//this class will be responsible to load sprites and levels
public class ResourceManager {
	public static int currentMap = 1;
	public static int MAP_COUNT = 4;
	public static GraphicsConfiguration gc;
	public static Image[] tiles;
	public static int tileImagesCount = 9;

	public static Image LoadImage(String name){
		String filename = "images/" + name;
		return new ImageIcon(filename).getImage();
	}

	public static Image getMirrorImage(Image image) {
		return getScaledImage(image, -1, 1);
	}

	public static Image getFlippedImage(Image image) {
		return getScaledImage(image, 1, -1);
	}

	private static Image getScaledImage(Image image, float x, float y) {
		// set up the transform
		AffineTransform transform = new AffineTransform();
		transform.scale(x, y);
		transform.translate((x - 1) * image.getWidth(null) / 2, (y - 1) * image.getHeight(null) / 2);
		// create a transparent (not translucent) image
		Image newImage = gc.createCompatibleImage(image.getWidth(null),
												  image.getHeight(null),
												  Transparency.BITMASK);
		// draw the transformed image
		Graphics2D g = (Graphics2D) newImage.getGraphics();
		g.drawImage(image, transform, null);
		g.dispose();
		return newImage;
	}

	public static void InitImages(){
		//load player images
		//TODO: add more animation
		Player.PlayerAnimation.idleLeftFrames = new Image[1];
		Player.PlayerAnimation.idleRightFrames = new Image[1];
		Player.PlayerAnimation.dyingLeftFrames = new Image[1];
		Player.PlayerAnimation.dyingRightFrames = new Image[1];
		Image playerIdleLeft = LoadImage("player.png");
		Image playerIdleRight = getMirrorImage(playerIdleLeft);
		Player.PlayerAnimation.idleLeftFrames[0] = playerIdleLeft;
		Player.PlayerAnimation.idleRightFrames[0] = getMirrorImage(playerIdleLeft);
		Player.PlayerAnimation.dyingLeftFrames[0] = getFlippedImage(playerIdleLeft);
		Player.PlayerAnimation.dyingRightFrames[0] = getFlippedImage(playerIdleRight);
		Player.PlayerAnimation.jumpingLeftFrames = Player.PlayerAnimation.idleLeftFrames;
		Player.PlayerAnimation.jumpingRightFrames = Player.PlayerAnimation.idleRightFrames;
		Player.PlayerAnimation.movingLeftFrames = Player.PlayerAnimation.idleLeftFrames;
		Player.PlayerAnimation.movingRightFrames = Player.PlayerAnimation.idleRightFrames;
		//load grub images
		Enemy.EnemyAnimation.grubMovingLeftFrames = new Image[] {
			LoadImage("grub1.png"),
			LoadImage("grub2.png")
		};
		int grubAnimSize = Enemy.EnemyAnimation.grubMovingLeftFrames.length;
		Enemy.EnemyAnimation.grubMovingRightFrames = new Image[grubAnimSize];
		Enemy.EnemyAnimation.grubDyingLeftFrames = new Image[grubAnimSize];
		Enemy.EnemyAnimation.grubDyingRightFrames = new Image[grubAnimSize];
		for (int i = 0; i < grubAnimSize; ++i){
			Enemy.EnemyAnimation.grubMovingRightFrames[i] =
				getMirrorImage(Enemy.EnemyAnimation.grubMovingLeftFrames[i]);
			Enemy.EnemyAnimation.grubDyingLeftFrames[i] =
				getFlippedImage(Enemy.EnemyAnimation.grubMovingLeftFrames[i]);
			Enemy.EnemyAnimation.grubDyingRightFrames[i] =
				getFlippedImage(Enemy.EnemyAnimation.grubMovingRightFrames[i]);
		}
		//load fly images
		Enemy.EnemyAnimation.flyMovingLeftFrames = new Image[] {
			LoadImage("fly1.png"),
			LoadImage("fly2.png")
		};
		int flyAnimSize = Enemy.EnemyAnimation.flyMovingLeftFrames.length;
		Enemy.EnemyAnimation.flyMovingRightFrames = new Image[flyAnimSize];
		Enemy.EnemyAnimation.flyDyingLeftFrames = new Image[flyAnimSize];
		Enemy.EnemyAnimation.flyDyingRightFrames = new Image[flyAnimSize];
		for (int i = 0; i < flyAnimSize; ++i){
			Enemy.EnemyAnimation.flyMovingRightFrames[i] =
				getMirrorImage(Enemy.EnemyAnimation.flyMovingLeftFrames[i]);
			Enemy.EnemyAnimation.flyDyingLeftFrames[i] =
				getFlippedImage(Enemy.EnemyAnimation.flyMovingLeftFrames[i]);
			Enemy.EnemyAnimation.flyDyingRightFrames[i] =
				getFlippedImage(Enemy.EnemyAnimation.flyMovingRightFrames[i]);
		}
		//load coin images
		PowerUp.PowerUpAnimation.coinFrames = new Image[] {
			LoadImage("coin1.png"),
			LoadImage("coin2.png"),
			LoadImage("coin3.png"),
			LoadImage("coin4.png"),
			LoadImage("coin5.png"),
		};
		//load home image
		PowerUp.PowerUpAnimation.homeFrames = new Image[] {
			LoadImage("heart.png"),
		};
		//load tiles images
		int tileSize ='I' - 'A' + 1;
		tiles = new Image[tileSize];
		for (char ch = 'A'; ch <= 'I'; ++ch){
			String name = ch + ".png";
			tiles[ch - 'A'] = LoadImage(name);
		}
	}


	/**
	* load the currentMap
	* @return
	 */
	public static TileMap LoadMap()
	{
		if (currentMap > MAP_COUNT){
			// all levels cleared!!
			// TODO : WIN condition satisfied, do something about it
			return null;
		}
		ArrayList<String> lines = new ArrayList<String>();
		int width = 0;
		int height = 0;
		int coinCount = 0;
		int musicCount = 0;
		int grubCount = 0;
		int fliesCount = 0;
		String filename = "maps/map" + currentMap + ".txt";
		// read every line in the text file into the list
		String line;
		try{
			BufferedReader reader = new BufferedReader(new FileReader(filename));
			while ((line = reader.readLine()) != null) {
				for (int i = 0; i < line.length(); ++i){
					switch(line.charAt(i)){
						case 'o': coinCount++; break;
						case '!': musicCount++; break;
						case '1': grubCount++; break;
						case '2': fliesCount++; break;
					}
				}
				lines.add(line);
				width = Math.max(width, line.length());
			}
			reader.close();
		}
		catch(IOException e){
			e.printStackTrace();
		}
		// parse the lines to create a TileEngine
		height = lines.size();
		TileMap newMap = new TileMap(width, height, grubCount, fliesCount, coinCount);
		for (int y = 0; y < height; y++) {
			line = (String) lines.get(y);
			for (int x = 0; x < line.length(); ++x) {
				char ch = line.charAt(x);
				// check if the char represents tile A, B, C etc.
				int tile = ch - 'A';
				if (tile >= 0 && tile < tileImagesCount) {
					newMap.setTile(x, y, tiles[tile]);
				}
				else {
					switch(ch){
						case 'o': newMap.AddCoin(x,y); break;
						case '!': //TODO: add music
								  ;break;
						case '*': newMap.AddHome(x,y); break;
						case '1': newMap.AddGrub(x,y); break;
						case '2': newMap.AddFly(x,y); break;
					}
				}
			}
		}
		return newMap;
	}
}
