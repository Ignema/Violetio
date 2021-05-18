package com.TETOSOFT.tilegame.objects;

import java.awt.Image;
import com.TETOSOFT.tilegame.Box;

public class Player {
	//position
	public float x,y;
	// velocity
	public float dx = 0, dy = 0;
	public boolean jumping = false;
	public final float max_dx = 0.5f, max_dy = -1f;
	public static class PlayerAnimation
	{
		//NOTE(Mouad): everything is static because we only gonna have one player
		static public Image[] idleLeftFrames;
		static public Image[] idleRightFrames;
		static public Image[] movingLeftFrames;
		static public Image[] movingRightFrames;
		static public Image[] jumpingLeftFrames;
		static public Image[] jumpingRightFrames;
		static public Image[] dyingLeftFrames;
		static public Image[] dyingRightFrames;
		static public Image[] currentFrames;
		static public long frameDuration = 250;
		static public long currentFrameDuration;
		static public int currentFrameIndex = 0;
		static public long remainingDieTime;
		static public long DIE_TIME = 1000;
	}

	public void jump(){
		if (!jumping) {
			jumping = true;
			ForceJump();
		}
	}
	public void ForceJump(){
		jumping = true;
		dy = max_dy;
		if (PlayerAnimation.currentFrames == PlayerAnimation.movingRightFrames ||
			PlayerAnimation.currentFrames == PlayerAnimation.idleRightFrames
		   )
		{
			PlayerAnimation.currentFrames = PlayerAnimation.jumpingRightFrames;
		}
		else{
			PlayerAnimation.currentFrames = PlayerAnimation.jumpingLeftFrames;
		}
	}

	public void updateAnimation(long elapsedTime){
		PlayerAnimation.currentFrameDuration -= elapsedTime;
		int max_frames = PlayerAnimation.currentFrames.length;
		while (PlayerAnimation.currentFrameDuration <= 0){
			PlayerAnimation.currentFrameDuration += PlayerAnimation.frameDuration;
			PlayerAnimation.currentFrameIndex++;
			if (PlayerAnimation.currentFrameIndex == max_frames){
				PlayerAnimation.currentFrameIndex = 0;
			}
		}
	}

	public Image getImage(){
		return PlayerAnimation.currentFrames[PlayerAnimation.currentFrameIndex];
	}

	public Player(){
		PlayerAnimation.currentFrames = PlayerAnimation.idleRightFrames;
	}
	public void moveLeft(){
		PlayerAnimation.currentFrames = PlayerAnimation.movingLeftFrames;
		PlayerAnimation.currentFrameIndex = 0;
		PlayerAnimation.currentFrameDuration = PlayerAnimation.frameDuration;
	}
	public void moveRight(){
		PlayerAnimation.currentFrames = PlayerAnimation.movingRightFrames;
		PlayerAnimation.currentFrameIndex = 0;
		PlayerAnimation.currentFrameDuration = PlayerAnimation.frameDuration;
	}
	public int getWidth(){
		return getImage().getWidth(null);
	}
	public int getHeight(){
		return getImage().getHeight(null);
	}
	public Box getBox(){
		Box b = new Box();
		b.x = x;
		b.y = y;
		b.width = getWidth();
		b.height = getHeight();
		return b;
	}

	public void die(){
		PlayerAnimation.remainingDieTime = PlayerAnimation.DIE_TIME;
		if (PlayerAnimation.currentFrames == PlayerAnimation.movingRightFrames ||
			PlayerAnimation.currentFrames == PlayerAnimation.idleRightFrames ||
			PlayerAnimation.currentFrames == PlayerAnimation.jumpingRightFrames
		   )
		{
			PlayerAnimation.currentFrames = PlayerAnimation.dyingRightFrames;
		}
		else{
			PlayerAnimation.currentFrames = PlayerAnimation.dyingLeftFrames;
		}
	}
}
