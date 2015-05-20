package com.CulLight.FlappyBird.maths;

public class Vector3f {
	// FlappyBird is 2d game, but z is render order.
	//So if bird has higher render order than background,
	//bird will be on top of background
	public float x, y, z;
	
	public Vector3f() {
		x = 0.0f;
		y = 0.0f;
		z = 0.0f;
	}
	
	public Vector3f(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
}
