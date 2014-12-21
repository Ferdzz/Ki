package com.ki.game;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class MainScreen implements Screen {

	private OrthographicCamera cam;
	private SpriteBatch batch;
	private Animation player;
	private Animation playerDeath;

	private ParticleEffect twinkle;
	private ParticleEffect twinkleFast;

	private Random rand;

	private ArrayList<String> depressList;
	private BitmapFont fontSpaceToMove;
	private BitmapFont fontToughts;

	private float time = 0;

	private ArrayList<Texture> foreGroundTrees;

	@Override
	public void show() {
		cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.setToOrtho(false);

		batch = new SpriteBatch();

		fontSpaceToMove = new BitmapFont(Gdx.files.internal("fonts/font.fnt"));
		fontSpaceToMove.setScale(0.9f);
		fontSpaceToMove.setColor(Color.WHITE);
		fontSpaceToMove.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		fontToughts = new BitmapFont(Gdx.files.internal("fonts/font.fnt"));
		fontToughts.setScale(0.85f);
		fontToughts.setColor(Color.WHITE);
		fontSpaceToMove.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);

		rand = new Random();

		twinkle = new ParticleEffect();
		twinkle.load(Gdx.files.internal("particle/Twinkle.p"), Gdx.files.internal("particle"));
		twinkle.setPosition(rand.nextFloat() * Gdx.graphics.getWidth(), rand.nextFloat() * Gdx.graphics.getHeight());
		twinkle.start();
		twinkleFast = new ParticleEffect();
		twinkleFast.load(Gdx.files.internal("particle/Twinkle.p"), Gdx.files.internal("particle"));
		twinkleFast.setPosition(rand.nextFloat() * Gdx.graphics.getWidth(), rand.nextFloat() * Gdx.graphics.getHeight());
		twinkleFast.start();

		player = new Animation(0.07f, new TextureRegion(new Texture("img/walk/walk0002.png")), new TextureRegion(new Texture("img/walk/walk0003.png")), new TextureRegion(new Texture("img/walk/walk0004.png")), new TextureRegion(new Texture("img/walk/walk0005.png")), new TextureRegion(new Texture("img/walk/walk0006.png")), new TextureRegion(new Texture("img/walk/walk0007.png")), new TextureRegion(new Texture("img/walk/walk0008.png")), new TextureRegion(new Texture("img/walk/walk0009.png")), new TextureRegion(new Texture("img/walk/walk0010.png")), new TextureRegion(new Texture("img/walk/walk0011.png")));
		player.setPlayMode(PlayMode.LOOP);
		// playerDeath = new Animation(0.07f, new TextureRegion(new
		// Texture("")));
		// playerDeath.setPlayMode(PlayMode.NORMAL);

		depressList = new ArrayList<String>();
		depressList.add("What's the point?");
		depressList.add("How can I keep up?");
		depressList.add("What's my role to play in all this?");
		depressList.add("Why do I keep walking?");
		depressList.add("There's no point in this at all.");
		depressList.add("Is it all really worth it?");
		depressList.add("Life only leads to death anyways.");
		depressList.add("Just taking to slow way to my inevitable end..."); // Too long.
		depressList.add("This world is one of greed.");
		depressList.add("I see no end.");
		depressList.add("Is this road endless?");
		// depressList.add("Life is a game, and this one is over"); //Yeee..?

		foreGroundTrees = new ArrayList<Texture>();
		foreGroundTrees.add(new Texture(Gdx.files.internal("img/ForeGroundTree1.png")));
		foreGroundTrees.add(new Texture(Gdx.files.internal("img/ForeGroundTree1.png")));
		foreGroundTrees.add(new Texture(Gdx.files.internal("img/ForeGroundTree1.png")));

		Gdx.input.setInputProcessor(new InputP());
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.5f, 0.5f, 0.4f, 0.5f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.setProjectionMatrix(cam.combined);
		batch.begin();

		drawBackground(delta);
		drawPlayer(delta);
		drawParticles(delta);
		drawForeground(delta);
		update(delta);

		batch.end();
	}

	public void drawBackground(float delta) {

	}

	public void drawForeground(float delta) {
		if (fontSpaceToMove.getColor().a != 0 && InputP.hasmoved) {
			fontSpaceToMove.setColor(1.0f, 1.0f, 1.0f, fontSpaceToMove.getColor().a - 0.008f);
		}
		fontSpaceToMove.draw(batch, "PRESS SPACE TO MOVE...", 150, 70);
	}

	public void drawPlayer(float delta) {
		if (InputP.isMoving()) {
			batch.draw(player.getKeyFrame(time += delta), cam.position.x - 100, 70);
		} else if (!InputP.hasmoved) {
			batch.draw(player.getKeyFrames()[0], cam.position.x - 100, 70);
		} else if (InputP.hasreleased) {
			time = 0;
			// batch.draw(player.getKeyFrame(time += delta), cam.position.x -
			// 100, 70);
		}

		if (isMessageOn) {
			delay += delta;
			fontToughts.draw(batch, depressList.get(messageToShow), cam.position.x, 220);
			if (delay > 2 || !InputP.isMoving()) {
				fontToughts.setColor(1.0f, 1.0f, 1.0f, fontToughts.getColor().a - 0.008f);
				if (fontToughts.getColor().a == 0) {
					fontToughts.setColor(Color.WHITE);
					timeSinceMessage = 0;
					delay = 0;
					isMessageOn = false;
				}
			}
		}
	}

	public void drawParticles(float delta) {
		twinkle.draw(batch);
		twinkleFast.draw(batch);
	}

	private float timeSinceMessage = 0;
	private float delay = 0;
	private boolean isMessageOn = false;
	private int messageToShow;
	private int previousMessage = -1;
	private float twinkleDelay = 0;

	public void update(float delta) {
		if (InputP.isMoving()) {
			cam.translate(delta, 0);
		}

		if (twinkle.isComplete()) {
			twinkleDelay += delta;
			if (twinkleDelay >= 1.5f) {
				twinkle.setPosition(rand.nextFloat() * Gdx.graphics.getWidth() + cam.position.x - 600, rand.nextFloat() * Gdx.graphics.getHeight());
				twinkle.start();
				twinkleDelay = 0;
			}
		}
		if (twinkleFast.isComplete()) {
			twinkleFast.setPosition(rand.nextFloat() * Gdx.graphics.getWidth() + cam.position.x - 600, rand.nextFloat() * Gdx.graphics.getHeight());
			twinkleFast.start();
		}
		twinkle.update(delta);
		twinkleFast.update(delta);

		if (isMessageOn == false && InputP.isMoving()) {
			timeSinceMessage += delta;
			if (timeSinceMessage >= 3) {
				do {
					messageToShow = rand.nextInt(depressList.size());
				} while (messageToShow == previousMessage);
				previousMessage = messageToShow;
				isMessageOn = true;
			}
		}
		cam.update();
	}

	@Override
	public void hide() {

	}

	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {
		twinkle.dispose();
		batch.dispose();
	//	tree.dispose();
		fontSpaceToMove.dispose();
	}
}
