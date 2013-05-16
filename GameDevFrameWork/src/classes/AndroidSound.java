package classes;

import android.media.SoundPool;
import interfaces.Sound;

public class AndroidSound implements Sound {

	int id;
	SoundPool pool;
	
	public AndroidSound(SoundPool _pool,int _id){
		pool=_pool;
		id=_id;
	}
	@Override
	public void play(float volume) {
		
		pool.play(id	, volume, volume, 0, 0, 1);
		
	}

	@Override
	public void dispose() {
		pool.unload(id);
		
	}

}
