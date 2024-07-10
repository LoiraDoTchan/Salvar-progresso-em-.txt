package com.gpjecc.blogspot.chuvagame;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class BigornaManager {
	public Texture dropImage;
	public Array<Rectangle> arrayBigorna = new Array<Rectangle>();
	public long lastDropTime;
	
	private Bucket balde;

	BigornaManager(Bucket balde){
		this.balde = balde;
	}

	public void carregaBigorna() {
		dropImage = new Texture((Gdx.files.internal("assets/bigorna.png")));
	}

	public void spawnBigorna() {
		Rectangle bigorna = new Rectangle();
		bigorna.x = MathUtils.random(0, 800 - 70);
		bigorna.y = 490;
		bigorna.width = 70;
		bigorna.height = 70;
		arrayBigorna.add(bigorna);
		lastDropTime = TimeUtils.nanoTime();
	}

	public void spawnBigornas(float x, float y) {
		Rectangle bigorna = new Rectangle();
		bigorna.x = x;
		bigorna.y = y;
		bigorna.width = 70;
		bigorna.height = 70;
		arrayBigorna.add(bigorna);
		lastDropTime = TimeUtils.nanoTime();
	}
	
	public void mover() {
		Iterator<Rectangle> iter = arrayBigorna.iterator();
		while (iter.hasNext()) {
			Rectangle raindrop = iter.next();
			raindrop.y -= 200 * Gdx.graphics.getDeltaTime();
			if (raindrop.y + 64 < 0) {
				iter.remove();
			}
			if (raindrop.overlaps(balde.rect)) {
				SimpleGame.vidas--;
				iter.remove();
			}
		}

	}
	@Override
	public String toString() {
		StringBuilder saida = new StringBuilder();
		for (Rectangle raindrop : arrayBigorna) {			
			saida.append("bigorna," + Float.toString(raindrop.x) + "," + Float.toString(raindrop.y));
			saida.append(System.lineSeparator());
		}
		saida.append(System.lineSeparator());
		return saida.toString();
	}
}
