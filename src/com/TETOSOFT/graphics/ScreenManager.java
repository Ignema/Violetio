package com.TETOSOFT.graphics;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.lang.reflect.InvocationTargetException;
import javax.swing.*;

public class ScreenManager {
	private final GraphicsDevice device;
	private BufferStrategy strategy;
	private int width;
	private int height;
	Window fullScreenWindow;
	public ScreenManager() {
		GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
		device = environment.getDefaultScreenDevice();

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
				device.setDisplayMode(displayMode);
			} catch (IllegalArgumentException ignored) {
			}

			frame.setSize(displayMode.getWidth(), displayMode.getHeight());
			width = displayMode.getWidth();
			height = displayMode.getHeight();
		}

		try {
			EventQueue.invokeAndWait(() -> frame.createBufferStrategy(2));
		} catch (InterruptedException | InvocationTargetException ignored) {}
		strategy = frame.getBufferStrategy();
		fullScreenWindow = frame;
	}

	public Graphics2D getGraphics() {
		return (strategy != null) ? (Graphics2D) strategy.getDrawGraphics() : null;
	}

	public void update() {
		if (!strategy.contentsLost()) {
			strategy.show();
		}
		Toolkit.getDefaultToolkit().sync();
	}

	public JFrame getFullScreenWindow() {
		return (JFrame) fullScreenWindow;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void restoreScreen() {
		if (fullScreenWindow != null) {
			fullScreenWindow.dispose();
		}
		device.setFullScreenWindow(null);
	}
}
