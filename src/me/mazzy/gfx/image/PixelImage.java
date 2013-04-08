package me.mazzy.gfx.image;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class PixelImage {
	private BufferedImage image;
	private int[] pixels;
	public PixelImage(String path)
	{
		BufferedImage img = null;
		
		try {
			img = ImageIO.read(new File(path));
			image = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
		} catch (IOException e) {
			e.printStackTrace();
		}
		pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
		
		byte[] bytePixels = ((DataBufferByte)img.getRaster().getDataBuffer()).getData();
		for(int x = 0; x < bytePixels.length; x+=4)
		{
			int alpha = (bytePixels[x] < 0) ? (bytePixels[x]+256) : bytePixels[x];
			int blue = (bytePixels[x+1] < 0) ? (bytePixels[x+1]+256) : bytePixels[x+1];
			int green = (bytePixels[x+2] < 0) ? (bytePixels[x+2]+256) : bytePixels[x+2];
			int red = (bytePixels[x+3] < 0) ? (bytePixels[x+3]+256) : bytePixels[x+3];
			
			System.out.println("ALPHA: "+alpha+"   Blue: "+blue+"  Green: "+green+"  Red: "+red);
			
			pixels[x/4]= alpha*16777216 + red *65536 + green*256 + blue;
					
			//sSystem.out.println("X: "+pixels[x/4]);
		}
	}
	
	public BufferedImage getImage()
	{
		return this.image;

	}
	
	
	public int[] getPixels()
	{
		return pixels;
	}
	
	public void mirror(boolean mirrorX, boolean mirrorY)
	{
		int[] pixels_ = new int[pixels.length];
		for(int i =0; i<pixels.length ;i++)
		{
			pixels_[i] = pixels[i];
		}
		for(int x = 0; x < image.getWidth(); x++)
		{
			int xx = x;
			if(mirrorX)
			{
				xx = image.getWidth() - x -1;
			}
			
			for(int y = 0; y < image.getHeight(); y++)
			{
				int yy = y;
				if(mirrorY)
				{
					yy = image.getHeight() - y -1;
				}
				//System.out.println("XX: "+xx+"      YY: "+yy);
				pixels[xx + yy*image.getHeight()] = 
						pixels_[x + y*image.getHeight()];
			}
		}
	}
	
	public void draw(Graphics g, int x, int y)
	{
		g.drawImage(this.image, x, y, null);
	}
}
