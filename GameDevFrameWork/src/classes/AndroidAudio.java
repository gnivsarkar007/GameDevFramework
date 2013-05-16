package classes;

import java.io.IOException;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;
import interfaces.Audio;
import interfaces.Music;
import interfaces.Sound;

public class AndroidAudio implements Audio {

	AssetManager assets;
	SoundPool pool;
	
	public AndroidAudio(Activity activity){
		activity.setVolumeControlStream(AudioManager.STREAM_MUSIC);
		assets=activity.getAssets();
		this.pool=new SoundPool(20, AudioManager.STREAM_MUSIC,0);
		
	}
	@Override
	public Music newMusic(String fileName) {
		try {
			AssetFileDescriptor assetDescriptor=assets.openFd(fileName);
			return new AndroidMusic(assetDescriptor);
		} catch (IOException e) {
			throw new RuntimeException("Couldn't load music '" + fileName + "'");
		}
	
	}

	@Override
	public Sound newSound(String fileName) {
		try {
		AssetFileDescriptor assetDescriptor = assets.openFd(fileName);
		int soundId = pool.load(assetDescriptor, 0);
		return new AndroidSound(pool, soundId);
		} catch (IOException e) {
		throw new RuntimeException("Couldn't load sound '" + fileName + "'");
		}
	}

}
