package com.CulLight.FlappyBird.graphics;

import static org.lwjgl.opengl.GL11.*;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.CulLight.FlappyBird.utils.BufferUtils;

public class Texture {
	
	private int width, height;
	private int texture; // this is the ID
	
	public Texture(String path) {
		texture = load(path);
	}
	
	private int load(String path) {
		int[] pixels = null;
		try{
			BufferedImage image = ImageIO.read(new FileInputStream(path));
			width = image.getWidth();
			height = image.getHeight();
			pixels = new int[width * height];
			image.getRGB(0, 0, width, height, pixels, 0, width);
		} catch (IOException e) {
			e.printStackTrace();
		}


		// pixels is argb
		// but OpenGL needs abgr
		int data[] = new int[width * height];
		for (int i = 0; i < width * height; i++) {
			// color C8E0601B
			// CE = alpha
			// E0 = red
			// 06 = green
			// 1B = blue
			
			// alpha channel
			// C8E0601B
			// pixels[i] & 0xff000000 -> C8000000
			// >> 24 (shift by 6*4 bits) -> 000000C8 = C8 only aplha channel
			int a = (pixels[i] & 0xff000000) >> 24; //0xff000000 end operator
			int r = (pixels[i] & 0xff0000) >> 16;
			int g = (pixels[i] & 0xff00) >> 8;
			int b = (pixels[i] & 0xff);
			
			data[i] = a << 24 | b << 16 | g << 8 | r;
		}
		
		int result = glGenTextures();
		// like in PhotoShop when one wants to modify a layer
		// one needs to select ("bind") it first
		glBindTexture(GL_TEXTURE_2D, texture);
		// no antialiasing, keep textures sharp
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, BufferUtils.createIntBuffer(data));
		// deselect it
		glBindTexture(GL_TEXTURE_2D, 0);
		return result;
	}
	
	public void bind() {
		// like in PhotoShop when one wants to modify a layer
		// one needs to select ("bind") it first
		glBindTexture(GL_TEXTURE_2D, texture);
	}
	
	public void unbind() {
		// like in PhotoShop when one wants to modify a layer
		// one needs to select ("bind") it first
		glBindTexture(GL_TEXTURE_2D, 0);
	}
}
