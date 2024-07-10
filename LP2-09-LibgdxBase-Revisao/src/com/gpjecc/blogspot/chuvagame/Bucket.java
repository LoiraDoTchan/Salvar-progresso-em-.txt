package com.gpjecc.blogspot.chuvagame;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class Bucket extends Movel{

	public Texture bucketImage;
	public Rectangle rect;
	public boolean isAlive = true;

	public Bucket(Texture texture, Rectangle rect) {
		super(texture, rect);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return Float.toString(rect.x);
	}

	public void carregaBucket() {
		bucketImage = new Texture(Gdx.files.internal("assets/bucket.png"));
	}
	
	public void createRetangulo() {
		rect = new Rectangle();
		rect.x = 800 / 2 - 64 / 2; // center the bucket horizontally
		rect.y = 20; // bottom left corner of the bucket is 20 pixels above the bottom screen edge
		rect.width = 64;
		rect.height = 64;
	}
	
	public void draw( SpriteBatch batch) {
		batch.draw(bucketImage, rect.x, rect.y);
	}
	
	public void mover() {
		// process user input
		
		if (Gdx.input.isKeyPressed(Keys.LEFT))
			rect.x -= 200 * Gdx.graphics.getDeltaTime();
		if (Gdx.input.isKeyPressed(Keys.RIGHT))
			rect.x  += 200 * Gdx.graphics.getDeltaTime();

		// make sure the bucket stays within the screen bounds
		if (rect.x < 0)
			rect.x  = 0;
		if (rect.x  > 800 - 64)
			rect.x  = 800 - 64;
	}
	
}
