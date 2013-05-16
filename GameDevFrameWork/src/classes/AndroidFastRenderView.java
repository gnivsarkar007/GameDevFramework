package classes;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class AndroidFastRenderView extends SurfaceView implements Runnable {

	AndroidGame game;
	Bitmap frameBuffer;
	Thread renderThread=null;
	SurfaceHolder holder;
	volatile boolean isRunning=false;
	
	public AndroidFastRenderView(Bitmap _buffer,AndroidGame context){
		super(context);
		this.game=context;
		this.frameBuffer=_buffer;
		this.holder=getHolder();
	}
	public void resume(){
		isRunning=true;
		renderThread=new Thread(this);
		renderThread.start();

	}
	@Override
	public void run() {
		Rect dstRect = new Rect();
		long startTime = System.nanoTime();
		while (isRunning) {
			if (!holder.getSurface().isValid())
				continue;
			float deltaTime = (System.nanoTime() - startTime) / 1000000000.0f;
			startTime = System.nanoTime();
			 game.getCurrentScreen().update(deltaTime);
			 game.getCurrentScreen().present(deltaTime);
			Canvas canvas = holder.lockCanvas();
			canvas.getClipBounds(dstRect);
			canvas.drawBitmap(frameBuffer, null, dstRect, null);
			holder.unlockCanvasAndPost(canvas);
		}
	}

	public void pause() {
		isRunning = false;
		while (true) {
			try {
				renderThread.join();
				break;
			} catch (InterruptedException e) {
				// retry
			}
		}
	}

}
