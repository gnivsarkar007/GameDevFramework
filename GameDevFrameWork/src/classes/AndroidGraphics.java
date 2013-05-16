package classes;

import java.io.IOException;
import java.io.InputStream;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import interfaces.Graphics;
import interfaces.Pixmap;

public class AndroidGraphics implements Graphics {

	AssetManager assetsMgr;
	Bitmap frameBuffer;
	Canvas canvas;
	Rect sourceRect= new Rect();
	Rect destinationRect=new Rect();
	Paint paint=new Paint();
	
	public AndroidGraphics(AssetManager asset,Bitmap buffer,Paint _paint){
		assetsMgr=asset;
		frameBuffer=buffer;
		canvas= new Canvas(frameBuffer);
		paint=_paint;
	}
	
	@Override
	public Pixmap newPixmap(String fileName, PixmapFormat format) {
		Config config;
		if(format==PixmapFormat.ARGB4444)
			config=Config.ARGB_4444;
		else if(format==PixmapFormat.RGB565)
			config=Config.RGB_565;
		else config=Config.ARGB_8888;
		Options option= new Options();
		option.inPreferredConfig=config;
		InputStream in=null;
		Bitmap bitmap=null;
		try{
			in=assetsMgr.open(fileName);
			bitmap=BitmapFactory.decodeStream(in);
			if(bitmap==null){throw new RuntimeException("Error decoding the bitmap.");}
		}catch(IOException e){
			throw new RuntimeException("Error opening the file "+fileName);
		}
		finally{
			if(in!=null){
				try{
					in.close();
				}catch(Exception e){
					
				}
			}
		}
		if (bitmap.getConfig() == Config.RGB_565)
			format = PixmapFormat.RGB565;
			else if (bitmap.getConfig() == Config.ARGB_4444)
			format = PixmapFormat.ARGB4444;
			else
			format = PixmapFormat.ARGB8888;
			return new AndroidPixmap(bitmap, format);
	}

	@Override
	public void clear(int color) {
		canvas.drawRGB((color & 0xff0000) >> 16, (color & 0xff00) >> 8,
				(color & 0xff));
	}

	@Override
	public void drawPixel(int x, int y, int color) {
		paint.setColor(color);
		canvas.drawPoint(x, y, paint);
		}

	@Override
	public void drawLine(int x, int y, int x2, int y2, int color) {
		paint.setColor(color);
		canvas.drawLine(x, y, x2, y2, paint);
		}

	@Override
	public void drawRect(int x, int y, int width, int height, int color) {
		paint.setColor(color);
		paint.setStyle(Style.FILL);
		canvas.drawRect(x, y, x + width - 1, y + width - 1, paint);
	}

	@Override
	public void drawPixmap(Pixmap pixmap, int x, int y, int srcX, int srcY,
			int srcWidth, int srcHeight) {
		sourceRect.left = srcX;
		sourceRect.top = srcY;
		sourceRect.right = srcX + srcWidth - 1;
		sourceRect.bottom = srcY + srcHeight - 1;
		destinationRect.left = x;
		destinationRect.top = y;
		destinationRect.right = x + srcWidth - 1;
		destinationRect.bottom = y + srcHeight - 1;
		canvas.drawBitmap(((AndroidPixmap) pixmap).bitmap, sourceRect, destinationRect,
		null);
		}

	@Override
	public void drawPixmap(Pixmap pixmap, int x, int y) {
		canvas.drawBitmap(((AndroidPixmap)pixmap).bitmap, x, y, null);
	}

	@Override
	public int getWidth() {
		
		return frameBuffer.getWidth();
	}

	@Override
	public int getHeight() {
		
		return frameBuffer.getHeight();
	}

}
