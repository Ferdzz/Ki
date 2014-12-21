package com.ki.game;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

public class InputP implements InputProcessor{
	
	static public boolean hasmoved = false;
	static public boolean hasreleased = false;
	
	static public boolean isMoving() {
		return (hasmoved && !hasreleased);
	}

	@Override
	public boolean keyDown(int keycode) {
		if(keycode == Input.Keys.SPACE) {
			hasmoved = true;
		}
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		if(keycode == Input.Keys.SPACE) {
			hasreleased = true;
		}
		return true;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
