package com.gpjecc.blogspot.chuvagame;

import java.util.concurrent.ConcurrentHashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class Cloud {
	public Texture cloudImage;
	public Rectangle rect;
	public long lastDropTime;
	Vector2 velocidade =  new Vector2(2, 0);
	int count = 0;
	
	public String toString() {
		return "cloud," + Float.toString(rect.x);
	}
	
	public void carregaCloud() {
		cloudImage = new Texture((Gdx.files.internal("assets/cloud.png")));
	}

	public void createCloud() {
		rect = new Rectangle();
		rect.x = 800 / 2 - 64 / 2; // center the bucket horizontally
		rect.y = Gdx.graphics.getHeight() - 300 ; // bottom left corner of the bucket is 20 pixels above the bottom screen edge
		rect.width = 64;
		rect.height = 64;

	}
	
	void move() {
		
		if (rect.x < 0) {
			rect.x = 0;
			velocidade.x *=-1;
		}
		if (rect.x > 800 - 64) {
			rect.x = 800 - 64;
			velocidade.x *=-1;
		}
		
		
		if (rect.y < 0) {
			rect.y = 0;
			velocidade.y *=-1;
		}
		if (rect.y > Gdx.graphics.getHeight() - 64) {
			rect.y = Gdx.graphics.getHeight() - 64;
			
			velocidade.y *=-1;
		}
		
		count++;
		
		if(count < 20) {
			velocidade.y = 1;
		}else if (count > 60 ) {
			count = 0;
		}else if (count > 40 ){
			velocidade.y = 0;		
		}else if (count > 20 ){
			velocidade.y = -1;		
		
		}
		
		rect.x +=velocidade.x;
		rect.y +=velocidade.y;
	}

	public void draw(SpriteBatch batch) {
		 if (count < 40 ){					
			 batch.draw(cloudImage, rect.x, rect.y, cloudImage.getWidth()/5, cloudImage.getHeight()/5);
		 }
	}
}
