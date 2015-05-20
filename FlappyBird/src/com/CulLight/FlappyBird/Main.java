package com.CulLight.FlappyBird;

//import every static method
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

import java.nio.ByteBuffer;

import org.lwjgl.glfw.GLFWvidmode;


public class Main implements Runnable{
	//need to implement Runnable because a thread needs a run function and
	//class Main only knows it has a run function if we implement Runnable
	
	//FIELDS
	
	private int width = 1280;
	private int height = 720;
	
	private Thread thread;
	private boolean bRunning = false;
	
	//C does not have instances of classes so one stores the ID of sth (memoryplace)
	// -> need for long
	private long window;
	
	//METHODS
	
	public void start() {
		bRunning = true;
		thread = new Thread(this,"Game");
		thread.start();
	}
	
	public void run() {
		init();
		while (bRunning) {
			update();
			render();
			
			if (glfwWindowShouldClose(window) == GL_TRUE) {
				bRunning = false;
			}
		}
	}
	
	private void init() {
		//following method is static. so import package static allows to neglect class name
		//GLFW.glfwInit();
		if (glfwInit() == GL_FALSE) {
			//TODO: handle it
			return;
		}
		
		glfwWindowHint(GLFW_RESIZABLE, GL_TRUE);
		window = glfwCreateWindow(width, height, "Flappy Bird", NULL, NULL);
		if (window == NULL) {
			//TODO handle error
			return;
		}
		ByteBuffer vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		glfwSetWindowPos(window, (GLFWvidmode.width(vidmode) - width) /2 , (GLFWvidmode.height(vidmode) - height) /2);
		glfwMakeContextCurrent(window);
		glfwShowWindow(window);
		
	}
	
	private void update() {
		glfwPollEvents();		
	}
	
	private void render() {
		glfwSwapBuffers(window);	
	}
	
	public static void main(String[] args) {
		new Main().start();
	}

}
