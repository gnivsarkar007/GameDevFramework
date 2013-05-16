package inputhandlers;

import inputhandlers.Pool.PoolObjectFactory;

import java.util.ArrayList;
import java.util.List;
import interfaces.Input;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;

public class KeyBoardHandler implements OnKeyListener {

	boolean[] pressedKeys = new boolean[128];
	Pool<interfaces.Input.KeyEvent> keyPoolEvents;
	List<interfaces.Input.KeyEvent> keyEventsBuffer= new ArrayList<interfaces.Input.KeyEvent>();
	List<interfaces.Input.KeyEvent> keyEvents = new ArrayList<interfaces.Input.KeyEvent>();
	
	
	public KeyBoardHandler(View _view){
		PoolObjectFactory<interfaces.Input.KeyEvent> factory= new PoolObjectFactory<interfaces.Input.KeyEvent>() {

			@Override
			public interfaces.Input.KeyEvent createObject() {
				// TODO Auto-generated method stub
				return new interfaces.Input.KeyEvent();
			}
		};
		keyPoolEvents=new Pool<interfaces.Input.KeyEvent>(factory,100);
		_view.setOnKeyListener(this);
		_view.setFocusableInTouchMode(true);
		_view.requestFocus();
	}
	
	public boolean isKeyPressed(int keyCode){
		if(keyCode<0 || keyCode>127)
			return false;
		return pressedKeys[keyCode];
	}
	
	@Override
	public boolean onKey(View v, int keyCode, android.view.KeyEvent event) {
		if(event.getAction()==android.view.KeyEvent.ACTION_MULTIPLE)
		return false;
		
		synchronized(this){
			interfaces.Input.KeyEvent key= keyPoolEvents.newObject();
			key.keyCode=keyCode;
			key.keyChar=(char)event.getUnicodeChar();
			if(event.getAction()==android.view.KeyEvent.ACTION_DOWN){
				key.type=interfaces.Input.KeyEvent.KEY_DOWN;
				if(keyCode>=0 && keyCode<=127) pressedKeys[keyCode]=true;
			}
			if(event.getAction()==android.view.KeyEvent.ACTION_UP){
				key.type=interfaces.Input.KeyEvent.KEY_UP;
				if(keyCode>=0 && keyCode<=127) pressedKeys[keyCode]=false;
			}
			keyEventsBuffer.add(key);
		}
			return false;
	}
	
	public List<interfaces.Input.KeyEvent> getKeyEvents(){
		synchronized(this){
			int len=keyEvents.size();
			for(int i=0;i<len;i++){
				keyPoolEvents.free(keyEvents.get(i));
				}
			keyEvents.clear();
			keyEvents.addAll(keyEventsBuffer);
			keyEventsBuffer.clear();
			return keyEvents;
		}
	}

}
