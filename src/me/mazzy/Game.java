package me.mazzy;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

import me.mazzy.gfx.image.BufferedImageLoader;
import me.mazzy.gfx.image.PixelImage;

public class Game extends Canvas implements Runnable {
	// True - Game is running     False - Game will stop after it complete iteration
	public boolean running = false;
	
	public int WIDTH, HEIGHT;
	
	private JFrame frame;
	public Dimension d;
	
	private BufferedImage image;
	private int[] pixels;
	private PixelImage testImg;
	
	public Game(String name, int WIDTH, int HEIGHT)
	{
		this.WIDTH = WIDTH;
		this.HEIGHT = HEIGHT;
		d = new Dimension(WIDTH, HEIGHT);
		
		testImg = new PixelImage("res/testimage.png");
		
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
		
		setMinimumSize(d);
		setMaximumSize(d);
		setPreferredSize(d);
		setVisible(true);
		
		frame = new JFrame(name);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		
		frame.add(this, BorderLayout.CENTER);
		frame.pack();
		
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	public synchronized void start() {
		running = true;
		new Thread(this).start();
	}	
	public synchronized void stop() {
		running = false;
		
	}
	
	@Override
	public void run() {
		long lastTime = System.nanoTime();
		double nsPerTick = 1000000000d/60d;
		
		int ticks = 0;
		int frames = 0;		
		
		long lastTimer = System.currentTimeMillis();
		double delta = 0;
		
		while(running)
		{
			boolean shouldRender = true;
			long now = System.nanoTime();
			delta += (now - lastTime)/nsPerTick;
			lastTime = now;
			
			while(delta >= 1)
			{
				ticks++;
				delta--;
				tick();
				shouldRender = true;
			}
			
			try {
				Thread.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			if(shouldRender)
			{
				frames++;
				render();
			}					
			
			if(System.currentTimeMillis() - lastTimer >= 1000)
			{
				System.out.println("Ticks: "+ticks +"   Frames: "+frames+"         DEBUG:PIXELS: "+testImg.getPixels()[0]);
				ticks = 0;
				frames = 0;
				testImg.mirror(true, true);
				lastTimer += 1000;
			}
		}
	}
	
	
	public void tick()
	{
		
	}
	
	public void render()
	{
		BufferStrategy bs = getBufferStrategy();
		if(bs==null) {
			createBufferStrategy(2);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		
		draw(g);
		
		g.dispose();
		bs.show();
		
	}
	
	public void draw(Graphics g)
	{
		g.fillRect(2, 2, 796, 636);
		testImg.draw(g, 50, 50);
	}
}
