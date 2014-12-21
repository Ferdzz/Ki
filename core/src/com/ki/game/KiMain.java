package com.ki.game;

import com.badlogic.gdx.Game;

public class KiMain extends Game {
	
	@Override
	public void create () {
		this.setScreen(new MainScreen());
	}
}
