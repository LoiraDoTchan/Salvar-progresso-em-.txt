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



class Pingo extends Movel{
	protected Texture dropImage;
	Rectangle retangulo;
	public Sound dropSound;
	
	public void draw( SpriteBatch batch) {
		batch.draw(dropImage, retangulo.x, retangulo.y);		
	}
	
	public Pingo(Texture dropImage, Sound dropSound, Rectangle raindrop) {
		super(dropImage, raindrop);
		this.dropImage = dropImage;
		this.dropSound = dropSound;
		this.retangulo = raindrop;
	}
	
	public void mover() {
		
	}
	@Override
	public String toString() {
		return "pingo," + Float.toString(retangulo.x) + "," + Float.toString(retangulo.y);
	}
	
}
public class PingoManager {

	public Texture dropImage;
	public Sound dropSound;
	public Array<Pingo> raindrops = new Array<Pingo>();
	public long lastDropTime;
	
	private Bucket balde;

	
	PingoManager(Bucket balde){
		this.balde = balde;
	}
	public void carregaDrop() {
		dropImage = new Texture((Gdx.files.internal("assets/droplet.png")));
	}

	public void carregaSom() {
		dropSound = Gdx.audio.newSound(Gdx.files.internal("assets/drop.wav"));
	}

	public void spawnRaindrop() {
		Rectangle raindrop = new Rectangle();
		raindrop.x = MathUtils.random(0, 800 - 64);
		raindrop.y = 480;
		raindrop.width = 64;
		raindrop.height = 64;
		Pingo pingo = new Pingo(dropImage,dropSound,raindrop );
		raindrops.add(pingo);
		lastDropTime = TimeUtils.nanoTime();
	}
	
	public void spawnRaindrop(float posx, float posy) {
		Rectangle raindrop = new Rectangle();
		raindrop.x = posx;
		raindrop.y = posy;
		raindrop.width = 64;
		raindrop.height = 64;
		Pingo pingo = new Pingo(dropImage,dropSound,raindrop );
		raindrops.add(pingo);
		lastDropTime = TimeUtils.nanoTime();
	}

	public void draw(SpriteBatch batch) {
		for (Pingo pingo : raindrops) {
			pingo.draw(batch);
			
		}		
	}

	public void mover() {
		Iterator<Pingo> iter = raindrops.iterator();
		while (iter.hasNext()) {
			Pingo raindrop = iter.next();
			raindrop.retangulo.y -= 200 * Gdx.graphics.getDeltaTime();
			if (raindrop.retangulo.y + 64 < 0) {
				iter.remove();
				SimpleGame.vidas--;				
			}
			if (raindrop.retangulo.overlaps(balde.rect)) {
				dropSound.play();
				SimpleGame.pontos++;
				iter.remove();
			}
		}
		
	}
}
