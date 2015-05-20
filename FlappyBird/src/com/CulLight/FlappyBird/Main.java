package com.CulLight.FlappyBird;

public class Main implements Runnable{
	//need to implement Runnable because a thread needs a run function and
	//class Main only knows it has a run function if we implement Runnable
	
	//FIELDS
	
	private int width = 1280;
	private int height = 720;
	
	private Thread thread;
	private boolean bRunning = false;
	
	
	//METHODS
	
	public void start() {
		bRunning = false;
		thread = new Thread(this,"Game");
		thread.start();
	}
	
	public void run() {
		
		while (bRunning) {
			update();
			render();
		}
	}
	
	private void update() {
				
	}
	
	private void render() {
		
	}
	
	public static void main(String[] args) {
		new Main().start();
	}

}
