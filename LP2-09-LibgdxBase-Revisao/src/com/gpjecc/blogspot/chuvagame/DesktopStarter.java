package com.gpjecc.blogspot.chuvagame;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;

public class DesktopStarter{

	public static void main (String[] args) {
        new LwjglApplication(new SimpleGame(), "ProvaLP2", 800, 600, false);
	}
}