package com.CulLight.FlappyBird;

//import every static method
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

import java.nio.ByteBuffer;

import org.lwjgl.glfw.GLFWvidmode;
import org.lwjgl.opengl.GLContext;

import com.CulLight.FlappyBird.input.Input;


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
		
		//usually one would call a callback here. however in java one has to create new class
		//to pass a function (new Input())
		glfwSetKeyCallback(window, new Input());
		  
		glfwMakeContextCurrent(window);
		glfwShowWindow(window);
		GLContext.createFromCurrent();
		
		glClearColor(1.0f, 0f, 1.0f, 1.0f);
		glEnable(GL_DEPTH_TEST);
		System.out.println("OpenGL: " + glGetString(GL_VERSION));
		
	}
	
	private void update() {
		glfwPollEvents();	
		if (Input.keys[GLFW_KEY_SPACE]) {
			System.out.println("FLAP!");
		}
	}
	
	private void render() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		glfwSwapBuffers(window);	
	}
	
	public static void main(String[] args) {
		new Main().start();
	}

}
