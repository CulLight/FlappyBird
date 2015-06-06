package com.CulLight.FlappyBird.graphics;

import static org.lwjgl.opengl.GL20.*;

import java.util.HashMap;
import java.util.Map;

import com.CulLight.FlappyBird.maths.Matrix4f;
import com.CulLight.FlappyBird.maths.Vector3f;
import com.CulLight.FlappyBird.utils.ShaderUtils;

public class Shader {

	public static final int VERTEX_ATTRIB = 0;
	//TextureCoordinates
	public static final int TCOORD_ATTRIB = 1;
	
	private final int ID;
	private Map<String, Integer> locationCache = new HashMap<String, Integer>();
	
	public Shader(String vertex, String fragment) {
		ID = ShaderUtils.load(vertex, fragment);
	}
	
	public int getUniform(String name) {
		//location of shader does not change during life of program.
		//To avoid communication between graphics card and CPU each tick
		//ask for location only at beginning of program
		if (locationCache.containsKey(name))
			return locationCache.get(name);
	
		int result =  glGetUniformLocation(ID, name);
		// -1 -> could not find location of shader
		if (result == -1) 
			System.err.println("Could not find uniform variable " + name);
		else
			locationCache.put(name, result);
		return result;
	}
	
	public void setUniform1i(String name, int value) {
		glUniform1i(getUniform(name), value);
	}

	public void setUniform1f(String name, float value) {
		glUniform1f(getUniform(name), value);
	}
	
	public void setUniform2f(String name, float x, float y) {
		glUniform2f(getUniform(name), x, y);
	}
	
	
	public void setUniform3f(String name, Vector3f vector) {
		glUniform3f(getUniform(name), vector.x, vector.y, vector.z);
	}
	
	public void setUniformMat4f(String name, Matrix4f matrix) {
		glUniformMatrix4fv(getUniform(name), false, matrix.toFloatBuffer());
	}
	
	
	
	public void enable() {
		glUseProgram(ID);
	}
	
	public void disable() {
		glUseProgram(0);
	}
}
