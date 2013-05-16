package classes;

import java.util.List;

import android.content.Context;
import android.os.Build.VERSION;
import android.view.View;

import inputhandlers.AccelerometerHandler;
import inputhandlers.KeyBoardHandler;
import inputhandlers.MultiTouchHandler;
import inputhandlers.SingleTouchHandler;
import interfaces.Input;
import interfaces.TouchHandler;

public class AndroidInput implements Input {

	AccelerometerHandler accelHandler;
	KeyBoardHandler keyHandler;
	MultiTouchHandler touchHandler;

	public AndroidInput(Context _context, View _view, float _scaleX,
			float _scaleY) {
		accelHandler = new AccelerometerHandler(_context);
		keyHandler = new KeyBoardHandler(_view);
		//if ((VERSION.SDK_INT) < 5)
		//else	touchHandler = new SingleTouchHandler(_view, _scaleX, _scaleY);
		
			touchHandler = new MultiTouchHandler(_view, _scaleX, _scaleY);

	}

	@Override
	public boolean isKeyPressed(int keyCode) {
		return keyHandler.isKeyPressed(keyCode);
	}

	@Override
	public boolean isTouchDown(int pointer) {
		return touchHandler.isTouchDown(pointer);
	}

	@Override
	public int getTouchX(int pointer) {
		return touchHandler.getTouchX(pointer);
	}

	@Override
	public int getTouchY(int pointer) {
		return touchHandler.getTouchY(pointer);
	}

	@Override
	public float getAccelX() {
		return accelHandler.getAccelX();
	}

	@Override
	public float getAccelY() {
		return accelHandler.getAccelY();
	}

	@Override
	public float getAccelZ() {
		return accelHandler.getAccelZ();
	}

	@Override
	public List<TouchEvent> getTouchEvents() {
		return touchHandler.getTouchEvents();
	}

	@Override
	public List<KeyEvent> getKeyEvents() {
		return keyHandler.getKeyEvents();
	}

}
