package me.mazzy.sprites;

import java.awt.image.BufferedImage;

import me.mazzy.gfx.image.BufferedImageLoader;

public class SpriteSheet {
	private BufferedImage image;
	
	public void setImage(String pathRelativeToThis)
	{
		image = BufferedImageLoader.loadImage(pathRelativeToThis);
	}
	
	public BufferedImage grabSprite(BufferedImage image, int x, int y, int width, int height)
	{
		return image.getSubimage(x, y, width, height);
	}
	
	public BufferedImage grabSprite(int x, int y, int width, int height)
	{
		return grabSprite(image, x,y,width,height);
	}
}
