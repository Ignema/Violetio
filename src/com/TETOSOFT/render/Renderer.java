package com.TETOSOFT.render;

import java.awt.*;

import com.TETOSOFT.resource.*;
import com.TETOSOFT.tilegame.TileMap;
import com.TETOSOFT.tilegame.objects.*;
import java.awt.Image;

public class Renderer {
	public static Image background;

	public static final int TILE_SIZE = 64;
	// the size in bits of the tile
	// Math.pow(2, TILE_SIZE_BITS) == TILE_SIZE
	private static final int TILE_SIZE_BITS = 6;

	/**
	 * Converts a pixel position to a tile position.
	 */
	public static int pixelsToTiles(float pixels) {
		return pixelsToTiles(Math.round(pixels));
	}

	/**
	 * Converts a pixel position to a tile position.
	 */
	public static int pixelsToTiles(int pixels) {
		// use shifting to get correct values for negative pixels
		return pixels >> TILE_SIZE_BITS;
	}

	/**
	 * Converts a tile position to a pixel position.
	 */
	public static int tilesToPixels(int numTiles) {
		// no real reason to use shifting here.
		// it's slighty faster, but doesn't add up to much
		// on modern processors.
		return numTiles << TILE_SIZE_BITS;
		// use this if the tile size isn't a power of 2:
		// return numTiles * TILE_SIZE;
	}

	public static void renderMenu(){
		//TODO: render the menu here
	}

	public static void renderGameOverMenu(Graphics2D g, int screenWidth, int screenHeight){
		int alfa = 200;
		Color customColor = new Color(100,100,100, alfa);
		g.setColor(customColor);
		g.fillRect(0,0, screenWidth, screenHeight);
		g.setColor(Color.RED);
		g.drawString("GameOver",screenWidth / 2 - 50, screenHeight / 2 -10);
		//TODO : rendrer some buttons or idk
	}

	static void renderEnemy(Graphics2D g, Enemy enemy, int screenWolrdPositionX, int screenWolrdPositionY, int screenWidth, int screenHeight, float newSpeed){
		int enemyScreenX = Math.round(enemy.x) - screenWolrdPositionX;
		int enemyScreenY = Math.round(enemy.y) - screenWolrdPositionY;
		g.drawImage(enemy.getImage(), enemyScreenX, enemyScreenY,null);
		g.setColor(Color.BLUE);
		g.drawRect(enemyScreenX, enemyScreenY, enemy.getWidth(), enemy.getHeight());
		if (enemy.dx == 0 && enemyScreenX >= 0 && enemyScreenX <= screenWidth
				&& enemyScreenY >= 0 && enemyScreenY <= screenHeight){
			//wake up
			enemy.dx = newSpeed;
		}
	}

	public static void renderMap(Graphics2D g,TileMap map,int screenWidth, int screenHeight){
		//draw the background if exist
		if (background != null){
			int backgroundWidth = background.getWidth(null);
			int backgroundHeight = background.getHeight(null);
			int backgroundX = (screenWidth - backgroundWidth) / 2;
			int backgroundY = (screenHeight - backgroundHeight) / 2;
			g.drawImage(background, backgroundX, backgroundY, null);
		}
		else {
			g.setColor(Color.black);
			g.fillRect(0, 0, screenWidth, screenHeight);
		}
		Player player = map.player;
		int mapWidth = map.getWidth();
		int mapHeight = map.getHeight();
		int mapWorldWidth = tilesToPixels(mapWidth);
		int mapWorldHeight = tilesToPixels(mapHeight);
		//NOTE(Mouad): player should be always in the middle of the screen if there is a room for it
		int screenWolrdPositionX = Math.round(player.x) - (screenWidth / 2);
		int screenWolrdPositionY = Math.round(player.y) - (screenHeight / 2);
		screenWolrdPositionX = Math.max(0, screenWolrdPositionX);
		screenWolrdPositionX = Math.min(screenWolrdPositionX, mapWorldWidth - screenWidth);
		screenWolrdPositionY = Math.max(0, screenWolrdPositionY);
		screenWolrdPositionY = Math.min(screenWolrdPositionY, mapWorldHeight - screenHeight);
		//draw tiles
		for (int i = 0; i < mapHeight; ++i){
			int tileWorldY = tilesToPixels(i);
			for (int j = 0; j < mapWidth; ++j){
				int tileWorldX = tilesToPixels(j);
				int tileScreenX = tileWorldX - screenWolrdPositionX;
				int tileScreenY = tileWorldY - screenWolrdPositionY;
				g.drawImage(map.tiles[i][j], tileScreenX, tileScreenY, TILE_SIZE, TILE_SIZE, null);
			}
		}
		//draw coins
		for (int i = 0; i < map.remainingCoins; ++i){
			PowerUp coin = map.coins[i];
			int coinScreenX = Math.round(coin.x) - screenWolrdPositionX;
			int coinScreenY = Math.round(coin.y) - screenWolrdPositionY;
			g.drawImage(coin.getImage(), coinScreenX, coinScreenY, null);
		}
		//draw alive and dying grubs
		for (int i = 0; i <map.aliveGrubs + map.dyingGrubs; ++i){
			renderEnemy(g, map.grubs[i], screenWolrdPositionX,screenWolrdPositionY,screenWidth,screenHeight, Enemy.max_grub_dx);
		}
		//draw alive and dying fly
		for (int i = 0; i <map.aliveFlies + map.dyingFlies; ++i){
			renderEnemy(g, map.flies[i], screenWolrdPositionX,screenWolrdPositionY,screenWidth,screenHeight, Enemy.max_fly_dx);
		}
		//draw home
		PowerUp home = map.home;
		int homeScreenX = Math.round(home.x) - screenWolrdPositionX;
		int homeScreenY = Math.round(home.y) - screenWolrdPositionY;
		g.drawImage(home.getImage(), homeScreenX, homeScreenY,null);
		// draw player
		int playerScreenX = Math.round(player.x) - screenWolrdPositionX;
		int playerScreenY = Math.round(player.y) - screenWolrdPositionY;
		g.drawImage(player.getImage(), playerScreenX, playerScreenY, null);
		// TODO(Mouad): for debug only, remove them when done
		g.setColor(Color.RED);
		g.drawRect(playerScreenX, playerScreenY, player.getWidth(), player.weakSpotHeight);
		g.setColor(Color.GREEN);
		g.drawRect(playerScreenX , playerScreenY + player.weakSpotHeight, player.getWidth(), player.getHeight() - player.weakSpotHeight);
	}

	public static void renderHUD(Graphics2D g, int collectedStars, int numLives, int frameCount){
		g.setColor(Color.WHITE);
		g.drawString("Press ESC for EXIT.", 10.0f, 20.0f);
		g.setColor(Color.GREEN);
		g.drawString("Coins: " + collectedStars, 300.0f, 20.0f);
		g.setColor(Color.YELLOW);
		g.drawString("Lives: " + (numLives), 500.0f, 20.0f);
		g.setColor(Color.WHITE);
		g.drawString("Home: " + ResourceManager.currentMap, 700.0f, 20.0f);
		g.drawString("frames: " + frameCount,500.f, 40.f);
	}
}
