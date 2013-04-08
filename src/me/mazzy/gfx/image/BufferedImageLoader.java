package me.mazzy.gfx.image;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

public class BufferedImageLoader {
	public static BufferedImage loadImage(String pathRelativeToThis)
	{
		try {
			return ImageIO.read(BufferedImageLoader.class.getResourceAsStream(pathRelativeToThis));
		} catch (Exception e) {
			System.out.println("Error when loading BufferedImage from "+pathRelativeToThis);
			e.printStackTrace();
		}
		return null;
	}
}
