package interfaces;

public interface Music {

	public void play();
	public void pause();
	public void stop();
	public void dispose();
	public boolean isLooping();
	public void setLooping(boolean loop);
	public void  setVolume(float volume);
	public boolean isStopped();
	public boolean isPlaying();
}
