package com.TETOSOFT.graphics;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.lang.reflect.InvocationTargetException;
import javax.swing.*;

public class ScreenManager {
	private final GraphicsDevice device;
	private BufferStrategy strategy;
	private int screenWidth;
	private int screenHeight;
	private int targetWidth;
	private int targetHeight;
	private float scaleWidth;
	private float scaleHeight;

	Window fullScreenWindow;
	BufferedImage buffer;
	public ScreenManager() {
		GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
		device = environment.getDefaultScreenDevice();
		screenWidth = device.getDisplayMode().getWidth();
		screenHeight = device.getDisplayMode().getHeight();
	}

	public DisplayMode findFirstCompatibleMode(DisplayMode[] modes) {
		DisplayMode[] goodModes = device.getDisplayModes();
		for (DisplayMode mode : modes) {
			for (DisplayMode goodMode : goodModes) {
				if (displayModesMatch(mode, goodMode)) {
					return mode;
				}
			}
		}
		return null;
	}

	public boolean displayModesMatch(DisplayMode mode1, DisplayMode mode2) {
		if (mode1.getWidth() != mode2.getWidth() || mode1.getHeight() != mode2.getHeight()) {
			return false;
		}

		if (mode1.getBitDepth() != DisplayMode.BIT_DEPTH_MULTI && mode2.getBitDepth() != DisplayMode.BIT_DEPTH_MULTI
				&& mode1.getBitDepth() != mode2.getBitDepth()) {
			return false;
		}

		if (mode1.getRefreshRate() != DisplayMode.REFRESH_RATE_UNKNOWN
				&& mode2.getRefreshRate() != DisplayMode.REFRESH_RATE_UNKNOWN
				&& mode1.getRefreshRate() != mode2.getRefreshRate()) {
			return false;
		}

		return true;
	}

	public void setFullScreen(DisplayMode displayMode) {
		final JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setUndecorated(true);
		frame.setIgnoreRepaint(true);
		frame.setResizable(false);

		device.setFullScreenWindow(frame);

		if (displayMode != null && device.isDisplayChangeSupported()) {
			try {
				//device.setDisplayMode(displayMode);
			} catch (IllegalArgumentException ignored) {
			}

			frame.setSize(screenWidth, screenHeight);
			targetWidth = displayMode.getWidth();
			targetHeight = displayMode.getHeight();
			scaleHeight = (float) screenHeight / targetHeight;
			scaleWidth = (float) screenWidth / targetWidth;
			if (scaleHeight < scaleWidth) scaleWidth = scaleHeight;
			else scaleHeight = scaleWidth;

			buffer = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB);
		}

		try {
			EventQueue.invokeAndWait(() -> frame.createBufferStrategy(2));
		} catch (InterruptedException | InvocationTargetException ignored) {}
		strategy = frame.getBufferStrategy();
		fullScreenWindow = frame;
	}

	public Graphics2D getGraphics() {
		if (buffer != null) {
			Graphics2D g = (Graphics2D) buffer.getGraphics();
			g.clearRect(0, 0, targetWidth, targetHeight);
			return g;
		}
		return null;
		//return (strategy != null) ? (Graphics2D) strategy.getDrawGraphics() : null;
	}

	public void update() {
		Graphics2D g = (Graphics2D) strategy.getDrawGraphics();
		int finalWidth = (int)(targetWidth * scaleWidth);
		int finalHeight = (int)(targetHeight * scaleHeight);
		g.clearRect(0,0,screenWidth,screenHeight);
		g.drawImage(buffer,(screenWidth - finalWidth)/2, (screenHeight - finalHeight) / 2, finalWidth, finalHeight, null);
		g.dispose();
		if (!strategy.contentsLost()) {
			strategy.show();
		}
		Toolkit.getDefaultToolkit().sync();
	}

	public JFrame getFullScreenWindow() {
		return (JFrame) fullScreenWindow;
	}

	public int getWidth() {
		return targetWidth;
	}

	public int getHeight() {
		return targetHeight;
	}

	public void restoreScreen() {
		if (fullScreenWindow != null) {
			fullScreenWindow.dispose();
		}
		device.setFullScreenWindow(null);
	}
}
