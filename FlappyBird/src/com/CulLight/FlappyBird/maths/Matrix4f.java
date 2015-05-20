package com.CulLight.FlappyBird.maths;

import java.nio.FloatBuffer;

import com.CulLight.FlappyBird.utils.BufferUtils;

public class Matrix4f {
	
	//Matrix are sorted Column Major meaning
	// [1  5   9  13
	//  2  6  10  14
	//  3  7  11  15
	//  4  8  12  16]

	
	public static final int SIZE = 4 * 4;
	public float[] elements = new float[4 * 4];
	
	private Matrix4f() {
	}
	
	public static Matrix4f identity() {
		//identity matrix
		Matrix4f result = new Matrix4f();
		for (int i = 0; i < SIZE; i++) {
			result.elements[i] = 0.0f;
		}
		result.elements[0 + 0 * 4] = 1.0f;
		result.elements[1 + 1 * 4] = 1.0f;
		result.elements[2 + 2 * 4] = 1.0f;
		result.elements[3 + 3 * 4] = 1.0f;
		
		return result;
	}
	
	public static Matrix4f orthographic(float left, float right, float bottom, float top, float near, float far) {
		//return orthographic matrix: projection matrix, defined by 6 planes, used for 2d rendering
		//not used in 3d because things further away (larger z) do not decrease in size
		// top, left, bottom, right are window margins, anything outside that is not rendered
		// also only objects between near and far get rendered
		Matrix4f result = identity();
		
		result.elements[0 + 0 * 4] = 2.0f / (right - left);
		
		result.elements[1 + 1 * 4] = 2.0f / (top - bottom);
		
		result.elements[2 + 2 * 4] = 2.0f / (near - far);
		
		result.elements[0 + 3 * 4] = (left + right) / (left - right) ;
		result.elements[1 + 3 * 4] = (bottom + top) / (bottom - top) ;
		result.elements[2 + 3 * 4] = (far + near) / (far - near) ;
		
		return result;
	}
	
	public static Matrix4f translate(Vector3f vector) {
		// [1  0  0  x
		//  0  1  0  y
		//  0  0  1  z
		//  0  0  0  1]
		
		Matrix4f result = identity();
		
		result.elements[0 + 3 * 4] = vector.x;
		result.elements[1 + 3 * 4] = vector.y;
		result.elements[2 + 3 * 4] = vector.z;
		
		return result;
	}
	
	public static Matrix4f rotate(float angle) {
		// rotation only around z axis
		// [ cos   sin   0   0
		//  -sin   cos   0   0
		//     0     0   1   0
		//     0     0   0   1]
		
		Matrix4f result = identity();
		
		float r = (float) Math.toRadians(angle);
		float cos = (float) Math.cos(r);
		float sin = (float) Math.sin(r);
		
		result.elements[0 + 0 * 4] = cos;
		result.elements[1 + 0 * 4] = sin;
		
		result.elements[0 + 1 * 4] = -sin;
		result.elements[1 + 1 * 4] = cos;	
		
		return result;
	}
	
	public Matrix4f multiply(Matrix4f matrix) {
		Matrix4f result = new Matrix4f();

		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 4; x++) {
				float sum = 0.0f;
				for (int e = 0; e < 4; e++) {
					sum += this.elements[x + e * 4] * matrix.elements[e + y * 4];
				}
				result.elements[x + y * 4] = sum;
			}
		}
		return result;
	}
	
	public FloatBuffer toFloatBuffer() {
		return BufferUtils.createFloatBuffer(elements);
	}
}
