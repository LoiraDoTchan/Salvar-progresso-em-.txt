package com.gpjecc.blogspot.chuvagame;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class SimpleGame extends ApplicationAdapter {

	private Music rainMusic;
	private SpriteBatch batch;
	private OrthographicCamera camera;

	private BitmapFont font;

	Bucket bucket;
	PingoManager pingoManager;
	Cloud cloud;
	BigornaManager bigorna;

	static public int vidas = 3;
	static public int pontos;
	private String string;

	@Override
	public void create() {
		Texture.setEnforcePotImages(false);

		bucket = new Bucket(null, null);
		pingoManager = new PingoManager(bucket);
		cloud = new Cloud();
		bigorna = new BigornaManager(bucket);

		pingoManager.carregaDrop();
		bucket.carregaBucket();
		cloud.carregaCloud();
		bigorna.carregaBigorna();

		pingoManager.carregaSom();
		rainMusic = Gdx.audio.newMusic(Gdx.files.internal("assets/rain.mp3"));

		font = new BitmapFont();
		font.setColor(Color.LIGHT_GRAY);

		// start the playback of the background music immediately
		rainMusic.setLooping(true);
		rainMusic.play();

		// create the camera and the SpriteBatch
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		batch = new SpriteBatch();

		// create a Rectangle to logically represent the bucket
		bucket.createRetangulo();
		cloud.createCloud();

		// create the raindrops array and spawn the first raindrop
		load();
	}

	@Override
	public void render() {
		// clear the screen with a dark blue color. The
		// arguments to glClearColor are the red, green
		// blue and alpha component in the range [0,1]
		// of the color to be used to clear the screen.
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// tell the camera to update its matrices.
		camera.update();

		// tell the SpriteBatch to render in the
		// coordinate system specified by the camera.
		batch.setProjectionMatrix(camera.combined);

		// begin a new batch and draw the bucket and
		// all drops
		batch.begin();
		cloud.move();

		if (bucket.isAlive)

			bucket.draw(batch);

		pingoManager.draw(batch);
		cloud.draw(batch);

		for (Rectangle rainbigorna : bigorna.arrayBigorna) {
			batch.draw(bigorna.dropImage, rainbigorna.x, rainbigorna.y);
		}

		font.draw(batch, "Vidas = " + SimpleGame.vidas, 680, 470);
		font.draw(batch, "Pontos = " + SimpleGame.pontos, 680, 450);

		batch.end();

		bucket.mover();
		// check if we need to create a new raindrop
		if (TimeUtils.nanoTime() - pingoManager.lastDropTime > 1000000000) {
			pingoManager.spawnRaindrop();
		}
		if (TimeUtils.nanoTime() - bigorna.lastDropTime > 1000000000) {
			bigorna.spawnBigorna();
		}
		// move the raindrops, remove any that are beneath the bottom edge of
		// the screen or that hit the bucket. In the later case we play back
		// a sound effect as well.

		// MovePingos
		pingoManager.mover();
		bigorna.mover();
		if (SimpleGame.vidas <= 0) {
			salve();
			Gdx.app.exit();
		}

	}

	public void salve() {
		if (SimpleGame.vidas == 0) {
			ManipuladorDeArquivos.escreverNoArquivo("save.txt", " ");
		} else {
			/*
			 * FORMATO: Balde - posicao X Pontos,vidas Posicao das gotas - X e Y cloud -
			 * posicao x bigorna - x e y
			 */

			StringBuilder saida = new StringBuilder();

			saida.append(bucket.toString());
			saida.append(System.lineSeparator());

			saida.append(Integer.toString(pontos) + "," + Integer.toString(vidas));
			saida.append(System.lineSeparator());

			for (Pingo p : pingoManager.raindrops) {
				saida.append(p.toString());
				saida.append(System.lineSeparator());
			}

			saida.append(cloud.toString());
			saida.append(System.lineSeparator());

			saida.append(bigorna);
			saida.append(System.lineSeparator());

			ManipuladorDeArquivos.escreverNoArquivo("save.txt", saida.toString());
		}
	}

	public void load() {
		List<String> lista = ManipuladorDeArquivos.lerDoArquivo("save.txt");
		if (lista.size() < 2) {
			return;
		} else {
			/*
			 * FORMATO: Balde - posicao X Pontos,vidas Posicao das gotas - X e Y cloud -
			 * posicao x
			 */

			bucket.rect.x = Float.parseFloat(lista.get(0));

			String[] campos = lista.get(1).split(",");
			pontos = Integer.parseInt(campos[0]);
			vidas = Integer.parseInt(campos[1]);

			int i = 2; // posicao 2 da lista
			while (i <= lista.size() - 1) {
				campos = lista.get(i).split(",");

				switch (campos[0]) {
				case "pingo":
					pingoManager.spawnRaindrop(Float.parseFloat(campos[1]), Float.parseFloat(campos[2]));
					break;
				case "cloud":
					cloud.rect.x = Float.parseFloat(campos[1]);
					break;
				case "bigorna":
					bigorna.spawnBigornas(Float.parseFloat(campos[1]), Float.parseFloat(campos[2]));
					break;
				default:
					System.out.println("deu erro");
				}
				i++;
			}

		}

	}

	@Override
	public void dispose() {
		// dispose of all the native resources
		salve();
		font.dispose();
		pingoManager.dropImage.dispose();
		bucket.bucketImage.dispose();
		pingoManager.dropSound.dispose();
		rainMusic.dispose();
		batch.dispose();
		System.out.println("Fim do Jogo!");
		// System.exit(-1);
	}
}