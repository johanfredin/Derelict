package se.jaygames.derelict.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import se.jaygames.derelict.Derelict;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.x = 0;
		config.y = 0;
		config.width = 600;
		config.height = 400;
		new LwjglApplication(new Derelict(), config);
	}
}
