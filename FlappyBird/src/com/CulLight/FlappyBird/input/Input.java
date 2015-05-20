package com.CulLight.FlappyBird.input;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallback;

public class Input extends GLFWKeyCallback{

	//FIELDS
	public static boolean[] keys = new boolean[65536]; //65536 highes unsigned character
	
	//METHODS
	public void invoke(long window, int key, int scancode, int action, int mods) {
		//key correspond to see GLFW.class
		//usually we would use 
		// action == GLFW.GLFW_PRESSED
		//but in GLFW there is also method
		//reapeat which counts how long one presses the key, so single press
		// keys[key] is true if I push the key key
		keys[key] = action != GLFW.GLFW_RELEASE;
//		keys[key] = action == GLFW.GLFW_PRESS;
	}

}
