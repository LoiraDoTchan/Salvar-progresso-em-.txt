package com.gpjecc.blogspot.chuvagame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;


public class Movel {
	protected Texture texture;
	protected Rectangle rect;
	
	private int count;
	
	public Movel(Texture texture, Rectangle rect) {
		super();
		this.texture = texture;
		this.rect = rect;
	}
	public void draw(SpriteBatch batch) {
		batch.draw(texture, rect.x, rect.y);
	}
	
	public Texture getTexture() {
		return texture;
	}
	public Rectangle getRect() {
		return rect;
	}
	public void setRect(Rectangle rect) {
		this.rect = rect;
	}
	
	public void mover() {
		
	}
}
