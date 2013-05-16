package inputhandlers;

import java.util.ArrayList;
import java.util.List;

import android.view.MotionEvent;
import android.view.View;
import inputhandlers.Pool.PoolObjectFactory;
import interfaces.Input;
import interfaces.Input.TouchEvent;
import interfaces.TouchHandler;

public class SingleTouchHandler implements TouchHandler{

	boolean isTouched;
	int touchX,touchY;
	float scaleX,scaleY;
	Pool<interfaces.Input.TouchEvent> touchEventPool;
	List<interfaces.Input.TouchEvent> touchevents= new ArrayList<interfaces.Input.TouchEvent>();
	List<interfaces.Input.TouchEvent> toucheventsBufer= new ArrayList<interfaces.Input.TouchEvent>();

	public SingleTouchHandler(View _view,float _scaleX,float _scaleY){
		PoolObjectFactory<Input.TouchEvent> factory= new PoolObjectFactory<Input.TouchEvent>() {

			@Override
			public TouchEvent createObject() {
				// TODO Auto-generated method stub
				return new Input.TouchEvent();
			}
		};
		touchEventPool= new Pool<Input.TouchEvent>(factory, 100);
		_view.setOnTouchListener(this);
		_view.setFocusableInTouchMode(true);
		this.scaleX=_scaleX;
		this.scaleY=_scaleY;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		synchronized (this) {
			Input.TouchEvent touch= touchEventPool.newObject();

			switch(event.getAction()){
			case MotionEvent.ACTION_DOWN:
				touch.type=Input.TouchEvent.TOUCH_DOWN;
				isTouched=true;
				break;
			case MotionEvent.ACTION_MOVE:
				touch.type=Input.TouchEvent.TOUCH_DRAG;
				isTouched=true;
				break;
			case MotionEvent.ACTION_CANCEL:
			case MotionEvent.ACTION_UP:
				touch.type=Input.TouchEvent.TOUCH_UP;
				isTouched=false;
				break;
			}
			touch.x=touchX=(int)(event.getX()*scaleX);
			touch.y=touchY=(int)(event.getY()*scaleY);

			return true;
		}


	}

	@Override
	public boolean isTouchDown(int pointer) {
		synchronized (this) {
			if(pointer==0) return isTouched;
			else return false;
		}
	}

	@Override
	public int getTouchX(int pointer) {
		synchronized (this) {
			return touchX;
		}
	}

	@Override
	public int getTouchY(int pointer) {
		synchronized (this) {
			return touchY;
		}
	}

	@Override
	public List<TouchEvent> getTouchEvents() {
		synchronized(this){
			int len=touchevents.size();
			for(int i=0;i<len;i++){
				touchEventPool.free(touchevents.get(i));
				}
			touchevents.clear();
			touchevents.addAll(toucheventsBufer);
			toucheventsBufer.clear();
			return touchevents;
		}
	}

}
