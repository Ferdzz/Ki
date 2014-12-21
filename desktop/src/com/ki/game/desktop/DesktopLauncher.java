package com.ki.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.ki.game.KiMain;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.height = 700;
		config.width = 1200;
		config.title = "Ki's Game";
		config.resizable = false;
		new LwjglApplication(new KiMain(), config);
	}
}
