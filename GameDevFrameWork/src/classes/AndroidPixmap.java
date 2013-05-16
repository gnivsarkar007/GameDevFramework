package classes;

import android.graphics.Bitmap;
import interfaces.Graphics.PixmapFormat;
import interfaces.Pixmap;

public class AndroidPixmap implements Pixmap{

	Bitmap bitmap;
	PixmapFormat format;
	
	public AndroidPixmap(Bitmap _bmp,PixmapFormat _frm){
		bitmap=_bmp;
		format=_frm;
	}
	@Override
	public int getWidth() {
		
		return bitmap.getWidth();
	}

	@Override
	public int getHeight() {
		
		return bitmap.getHeight();
	}

	@Override
	public PixmapFormat getFormat() {
		
		return format;
	}

	@Override
	public void dispose() {
		bitmap.recycle();
	}

}
