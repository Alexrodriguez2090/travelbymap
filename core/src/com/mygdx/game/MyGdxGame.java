package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Input.Keys;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	Texture imgCloser;
	OrthographicCamera cam;
	int WIDTH, HEIGHT;
	int imgWidth, imgHeight, imgCenterX, imgCenterY;

	int imgX, imgY;
	int imgAngle;

	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("map1.jpg");
		imgWidth = img.getWidth();
		imgHeight = img.getHeight();
		imgCenterX = imgWidth / 2;
		imgCenterY = imgHeight / 2;

		imgCloser = new Texture("map2.jpg");

		imgX = 0;
		imgY = 0;
		imgAngle = 0;

		WIDTH = Gdx.graphics.getWidth();
		HEIGHT = Gdx.graphics.getHeight();
		cam = new OrthographicCamera(WIDTH,HEIGHT);
		cam.translate(WIDTH/2, HEIGHT/2);
		cam.update();
		batch.setProjectionMatrix(cam.combined);
	}

	public void handleInput() {
		boolean updateCam = false;
		boolean shiftHeld = false;

		if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)) {
			shiftHeld = true;
		} else if (Gdx.input.isKeyPressed(Keys.W)) {
			cam.position.y += 5;
			updateCam = true;
		} else if (Gdx.input.isKeyPressed(Keys.S)) {
			cam.position.y -= 5;
			updateCam = true;
		} else if (Gdx.input.isKeyPressed(Keys.A)) {
			cam.position.x -= 5;
			updateCam = true;
		} else if (Gdx.input.isKeyPressed(Keys.D)) {
			cam.position.x += 5;
			updateCam = true;
		}

		if (shiftHeld) {
			if (Gdx.input.isKeyPressed(Keys.W)) {
				cam.zoom -= .01;
				updateCam = true;
			} else if (Gdx.input.isKeyPressed(Keys.S)) {
				cam.zoom += .01;
				updateCam = true;
			} else if (Gdx.input.isKeyPressed(Keys.A)) {
				cam.rotate(1);
				updateCam = true;
			} else if (Gdx.input.isKeyPressed(Keys.D)) {
				cam.rotate(-1);
				updateCam = true;
			}
		}

		if (updateCam) {
			cam.update();
			batch.setProjectionMatrix(cam.combined);

			//Shows camera position and zoom level
			//System.out.printf("%.2f, %.2f, %.2f\n", cam.position.x, cam.position.y, cam.zoom);
		}
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		handleInput();
		batch.begin();
		if (cam.zoom > .6) {
			batch.draw(img, imgX, imgY, imgWidth, imgHeight);
		} else {
			batch.draw(imgCloser, imgX, imgY, imgWidth, imgHeight);
		}
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
