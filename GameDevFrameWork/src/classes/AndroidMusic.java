package classes;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import interfaces.Music;
public class AndroidMusic implements Music,OnCompletionListener,OnErrorListener {

	MediaPlayer mMediaPlayer=null;
	boolean isPrepared;
	public AndroidMusic(AssetFileDescriptor assetDescriptor) {
		if(mMediaPlayer==null)
			mMediaPlayer= new MediaPlayer();
		mMediaPlayer.setOnCompletionListener(this);
		mMediaPlayer.setOnErrorListener(this);
		try{
			mMediaPlayer.setDataSource(assetDescriptor.getFileDescriptor(),assetDescriptor.getStartOffset(),assetDescriptor.getLength());
			mMediaPlayer.prepare();
			isPrepared=true;
		}catch (Exception e){
			throw new RuntimeException("This is a Music error.");
		}
	}

	@Override
	public void play() {
		// TODO Auto-generated method stub
		mMediaPlayer.start();
	}

	@Override
	public void pause() {
		if(mMediaPlayer.isPlaying())
			mMediaPlayer.pause();
	}

	@Override
	public void stop() {
		mMediaPlayer.stop();
		synchronized(this){
			isPrepared=false;
		}
	}

	@Override
	public void dispose() {
		if(mMediaPlayer.isPlaying())
			mMediaPlayer.stop();
		mMediaPlayer.release();
		mMediaPlayer=null;
	}

	@Override
	public boolean isLooping() {
		
		return mMediaPlayer.isLooping();
	}

	@Override
	public void setLooping(boolean loop) {
		
		mMediaPlayer.setLooping(loop);
	}

	@Override
	public void setVolume(float volume) {
		mMediaPlayer.setVolume(volume, volume);
	}

	@Override
	public boolean isStopped() {
		// TODO Auto-generated method stub
		return!isPrepared;
	}

	@Override
	public boolean isPlaying() {
		// TODO Auto-generated method stub
		return mMediaPlayer.isPlaying();
	}

	@Override
	public boolean onError(MediaPlayer mp, int what, int extra) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public void onCompletion(MediaPlayer mp) {
		synchronized(this){
			isPrepared=false;
		}
		
	}

}
