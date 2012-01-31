import king.kinect.NativeKinect;
import processing.core.PApplet;
import processing.core.PImage;
import java.io.*;

public class ClickTrial extends PApplet {

	PImage img, depth;
	PImage back;
	PImage dup;

	public void setup() {
		size(640 * 2, 480 * 2);
		frameRate(50);

		NativeKinect.init();
		NativeKinect.start();

		img = createImage(640, 480, RGB);
		depth = createImage(640, 480, RGB);
		back = createImage(640, 480, RGB);
		dup = createImage(640, 480, RGB);
	}

	public void draw() {
		scale(.5f, .5f);
		// System.out.println(img.pixels.length);
		// background(0);
		fill(255, 0, 0);
		rect(mouseX, mouseY, 50, 50);

		// img.pixels;
		image(depth, 0, 0, 640, 480);

		depth.pixels = NativeKinect.getDepthMap();
		depth.updatePixels();
		image(back, 640, 0, 640, 480);
		for (int i = 0; i < depth.pixels.length; i++) {
			int diff = (int) abs(red(depth.pixels[i]) - red(back.pixels[i]));

			diff = constrain(diff * 5, 0, 255);
			dup.pixels[i] = color(diff);

		}
		dup.updatePixels();
		dup.filter(ERODE);
		dup.filter(ERODE);
		dup.filter(ERODE);
		dup.filter(ERODE);
		dup.filter(ERODE);

		image(dup, 0, img.height);

	}

	public void mousePressed() {
		System.out.println("come here");
		for (int i = 0; i < depth.pixels.length; i++) {
			int store = depth.pixels[i];
			back.pixels[i] = store;
		}
		// back.pixels = depth.pixels;
		back.updatePixels();

//		back.filter(ERODE);
//		back.filter(ERODE);
//		back.filter(ERODE);
//		back.filter(ERODE);
		try {
			// Create file
			FileWriter fstream = new FileWriter("out.txt");
			BufferedWriter out = new BufferedWriter(fstream);
			for (int i = 0; i < back.pixels.length; i++) {

				int c = back.pixels[i];
				int alpha = (c >> 24) & 0xFF;
				int red = (c >> 16) & 0xFF;
				int green = (c >> 8) & 0xFF;
				int blue = c & 0xFF;
//				out.wrote(" red: " + red + "green: " + green + "blue: " + blue);
				out.write("i : " + i + " back[" + i + "] : " + back.pixels[i]
						+ " red: " + red + " green: " + green + " blue: " + blue+"\n");
			}
			// out.write("Hello Java");
			// Close the output stream
			out.close();
		} catch (Exception e) {// Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
	}
}
