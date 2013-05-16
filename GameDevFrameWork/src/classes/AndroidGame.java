package classes;

import interfaces.Audio;
import interfaces.FileIO;
import interfaces.Game;
import interfaces.Graphics;
import interfaces.Input;
import interfaces.Screen;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.hardware.display.DisplayManager;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;

public class AndroidGame extends Activity implements Game {

	AndroidFastRenderView renderView;
	Audio audio;
	Graphics graphics;
	FileIO fileIO;
	Input input;
	Screen screen;
	WakeLock mWakeLock;
	final DisplayMetrics screenMetrics=new DisplayMetrics();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		boolean isLandscape = getResources().getConfiguration().orientation ==
				Configuration.ORIENTATION_LANDSCAPE;
		int frameBufferWidth = isLandscape ? 480 : 320;
		int frameBufferHeight = isLandscape ? 320 : 480;
		Bitmap frameBuffer = Bitmap.createBitmap(frameBufferWidth,
				frameBufferHeight, Config.RGB_565);
		getWindowManager().getDefaultDisplay().getMetrics(screenMetrics);
		float scaleX = (float) frameBufferWidth
				/ screenMetrics.widthPixels;
		float scaleY = (float) frameBufferHeight
				/ screenMetrics.heightPixels;
		renderView = new AndroidFastRenderView(frameBuffer,this);
		graphics = new AndroidGraphics(getAssets(), frameBuffer, null);
		fileIO = new AndroidFileIO(this);
		audio = new AndroidAudio(this);
		input = new AndroidInput(this, renderView, scaleX, scaleY);
		screen = getStartScreen();
		setContentView(renderView);
		PowerManager powerManager = (PowerManager)
				getSystemService(Context.POWER_SERVICE);
		mWakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK|PowerManager.ACQUIRE_CAUSES_WAKEUP, "GLGame");
		mWakeLock.acquire();
	}


	@Override
	protected void onPause() {
		super.onPause();
		mWakeLock.release();
		renderView.pause();
		screen.pause();
		
	}


	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		mWakeLock.acquire();
		screen.resume();
		renderView.resume();
		if(isFinishing()) screen.dispose();
	}

	@Override
	public Input getInput() {
		// TODO Auto-generated method stub
		return input;
	}

	@Override
	public FileIO getFileIO() {
		// TODO Auto-generated method stub
		return fileIO;
	}

	@Override
	public Graphics getGraphics() {
		// TODO Auto-generated method stub
		return graphics;
	}

	@Override
	public Audio getAudio() {
		// TODO Auto-generated method stub
		return audio;
	}

	@Override
	public void setScreen(Screen screen) {
		if(screen==null)
			throw new RuntimeException("Could not instantiate the game screen.");
		this.screen.pause();
		this.screen.dispose();
		screen.resume();
		screen.update(0);
		this.screen=screen;
	}

	@Override
	public Screen getCurrentScreen() {
		// TODO Auto-generated method stub
		return screen;
	}

	@Override
	public Screen getStartScreen() {
		// TODO Auto-generated method stub
		return null;
	}

}
