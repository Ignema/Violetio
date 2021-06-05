package com.TETOSOFT.tilegame.objects;

import java.awt.Image;
import com.TETOSOFT.render.*;

public class Enemy {
	//position
	public float x,y;
	// velocity
	public float dx,dy = 0;
	public static float max_grub_dx = -0.05f;
	public static float max_fly_dx = -0.1f;
	//animation
	public EnemyAnimation animation;

	public static class EnemyAnimation
	{
		static public Image[] flyMovingRightFrames;
		static public Image[] flyMovingLeftFrames;
		static public Image[] flyDyingRightFrames;
		static public Image[] flyDyingLeftFrames;
		static public Image[] grubMovingRightFrames;
		static public Image[] grubMovingLeftFrames;
		static public Image[] grubDyingRightFrames;
		static public Image[] grubDyingLeftFrames;
		// NOTE(Mouad): will always point to the right frame to draw from
		public Image[] currentFrames;
		static public long DIE_TIME = 1000;
		public long frameDuration;
		public long remainingDieTime;
		public long currentFrameDuration = 0;
		public int currentFrameIndex = 0;
	}

	public void update(long elapsedTime){
		animation.currentFrameDuration -= elapsedTime;
		int max_frame = animation.currentFrames.length;
		while (animation.currentFrameDuration <= 0){
			animation.currentFrameDuration += animation.frameDuration;
			animation.currentFrameIndex++;
			if(animation.currentFrameIndex == max_frame){
				animation.currentFrameIndex = 0;
			}
		}
	}

	public Image getImage(){
		return animation.currentFrames[animation.currentFrameIndex];
	}

	public static Enemy getGrub(int x, int y){
		Enemy grub = createEnemy(x, y + 1);
		grub.animation.frameDuration = 250;
		grub.animation.currentFrames = EnemyAnimation.grubMovingLeftFrames;
		grub.x = grub.x - grub.getWidth() / 2;
		grub.y -= grub.getHeight();
		return grub;
	}

	public static Enemy getFly(int x, int y){
		Enemy fly = createEnemy(x , y + 1);
		fly.animation.frameDuration = 50;
		fly.animation.currentFrames = EnemyAnimation.flyMovingLeftFrames;
		fly.x = fly.x  - fly.getWidth() / 2;
		fly.y -= fly.getHeight();
		return fly;
	}

	public static Enemy createEnemy(int x, int y){
		Enemy enemy = new Enemy();
		enemy.x = Renderer.tilesToPixels(x) + Renderer.TILE_SIZE;
		enemy.y = Renderer.tilesToPixels(y);
		return enemy;
	}

	public void resetAnimation(){
		animation.currentFrameIndex = 0;
		animation.currentFrameDuration = animation.frameDuration;
	}

	Enemy(){
		animation = new EnemyAnimation();
	}
	public int getWidth(){
		return getImage().getWidth(null);
	}
	public int getHeight(){
		return getImage().getHeight(null);
	}
}
