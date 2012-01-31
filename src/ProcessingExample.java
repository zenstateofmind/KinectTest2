import king.kinect.NativeKinect;
import processing.core.PApplet;
import processing.core.PImage;
import java.io.*;


public class ProcessingExample extends PApplet {

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
//		System.out.println(img.pixels.length);
//		background(0);
		fill(255, 0, 0);
	}
}
