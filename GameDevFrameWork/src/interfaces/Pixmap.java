package interfaces;

import interfaces.Graphics.PixmapFormat;

public interface Pixmap {
public int getWidth();
public int getHeight();
public PixmapFormat getFormat();
public void dispose();
}