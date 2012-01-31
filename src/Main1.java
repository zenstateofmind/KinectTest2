import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import king.kinect.NativeKinect;
import processing.core.PApplet;
import processing.core.PImage;

public class Main1 extends PApplet {

	PImage img;
	PImage retImg;
	PImage meDraw;
	PImage img2;
	PImage img3;

	public void setup() {
		size(1280, 1200);
		NativeKinect.init();
		NativeKinect.start();
		
		img2 = createImage(640, 480, RGB);
		
		
//		img2 = createImage(100, 100, RGB);
//		for (int i = 0; i < 10000; i++) {
//			img2.pixels[i] = color(0, 0, 0);
//		}
//		for (int j = 49; j < 60; j++) {
//			for (int i = j; i < 10000; i = i + 100) {
//				img2.pixels[i] = color(255, 255, 255);
//			}
//		}
//		for (int i = 4; i < 10000; i = i + 100) {
//			img2.pixels[i] = color(255, 255, 255);
//		}
		
		//test one
		/**img2 = createImage(3, 3, RGB);
		img2.pixels[0] = color(255, 255, 255); // white
		img2.pixels[1] = color(0, 0, 128); //navy
		img2.pixels[2] = color(0, 0, 128); //navy
		img2.pixels[3] = color(255, 255, 255); // white
		img2.pixels[4] = color(0, 0, 0); // black
		img2.pixels[5] = color(0, 0, 128); //navy
		img2.pixels[6] = color(0, 0, 0); // black
		img2.pixels[7] = color(0, 0, 0); // black
		img2.pixels[8] = color(0, 0, 128); // navy*/
		
//		int col =  color(255, 255, 255);
//		
//		System.out.println("color is : "+col);
//		int r=(col&0x00FF0000)>>16; // red part
//		int g=(col&0x0000FF00)>>8; // green part
//		int b=(col&0x000000FF); // blue part
//		System.out.println("("+r+", "+g+", "+b+")");
		
//		img2 = createImage(5, 5, RGB);
//		
//		img2.pixels[0] = color(0, 0, 0); // black
//		img2.pixels[1] = color(255, 255, 255); // white
//		img2.pixels[2] = color(255, 255, 255); // white
//		img2.pixels[3] = color(255, 255, 255); // white
//		img2.pixels[4] = color(255, 255, 255); // white
//
//		img2.pixels[5] = color(0, 0, 0); // black
//		img2.pixels[6] = color(255, 255, 255); // white
//		img2.pixels[7] = color(0, 0, 128); //navy
//		img2.pixels[8] = color(255, 255, 255); // white
//		img2.pixels[9] = color(255, 255, 255); // white
//
//		img2.pixels[10] = color(255, 255, 255); // white
//		img2.pixels[11] = color(0, 0, 128); //navy
//		img2.pixels[12] = color(0, 0, 128); //navy
//		img2.pixels[13] = color(0, 0, 128); //navy
//		img2.pixels[14] = color(0, 0, 128); //navy
//
//		img2.pixels[15] = color(255, 255, 255); // white
//		img2.pixels[16] = color(0, 0, 128); //navy
//		img2.pixels[17] = color(0, 0, 128); //navy
//		img2.pixels[18] = color(0, 0, 128); //navy
//		img2.pixels[19] = color(0, 0, 128); //navy
//
//		img2.pixels[20] = color(255, 255, 255); // white
//		img2.pixels[21] = color(255, 255, 255); // white
//		img2.pixels[22] = color(0, 0, 128); //navy
//		img2.pixels[23] = color(255, 255, 255); // white
//		img2.pixels[24] = color(255, 255, 255); // white



//		size(300, 300);
//		
//		img2 = loadImage("nadal.jpg");
//		System.out.println);
//		 int c = img2.pixels[5];
//		int r=(c&0x00FF0000)>>16; // red part
//		int g=(c&0x0000FF00)>>8; // green part
//		int b=(c&0x000000FF); // blue part
//		System.out.println("("+r+", "+g+", "+b+")");
	}

	public void draw() {
		img2.pixels = NativeKinect.getVideo();
		img2.updatePixels();

		image(img2, 0, 0, 300, 300);
//		image(img3, 0, 0, 5, 100);
		OnePass(img2, 640, 480);
		image(retImg, 300, 300, 300, 300);

	}

	public void mousePressed() {
		OnePass(img2, 640, 480);
		// image(retImg, 100, 100, 100, 100);
		image(retImg, 150, 150, 200, 200);
	}

	public void OnePass(PImage img, int height, int width) {
		
		HashMap<Integer,Integer > labelColorMap = new HashMap<Integer, Integer>();
		HashMap<Integer, TreeSet<Integer>> unionFind = new HashMap<Integer, TreeSet<Integer>>();
		
		System.out.println("height is "+height);
		System.out.println("width is "+width);
		
		
		int label = 0;
		int position = 0;
		int[] labelArray = new int[height*width];
		for (int i = 0; i < width; i++) 
		{
			for (int j = 0; j < height; j++) 
			{
				int westPosition = (j - 1) + (i * height);
				int northPosition = j + ((i - 1) * height);

				position = j + (i * height);
//				System.out.println("i is "+i);
//				System.out.println("j is "+j);
//				System.out.println("loop "+position);
				
				if (i != 0 && j != 0) 
				{
//					System.out.println("gets here");
//					System.out.println("North position "+northPosition);
//					System.out.println("West Position "+westPosition);
//					System.out.println("North Pixel is "+img.pixels[northPosition]);
//					System.out.println("West Pixel is "+img.pixels[westPosition]);
//					System.out.println("Current Pixel si "+img.pixels[position]);
					if (img.pixels[westPosition] == img.pixels[position] && img.pixels[northPosition] == img.pixels[position])
					{
						if (labelArray[westPosition] == labelArray[northPosition]) 
						{
							labelArray[position] = labelArray[westPosition];

						} 
						
						else 
						{
						
							labelArray[position] = Math.min( labelArray[westPosition], labelArray[northPosition]);
							// do Union Find
							
							//add the north label to the set of the western shit
							TreeSet<Integer> left = new TreeSet<Integer>();
							left = unionFind.get(labelArray[westPosition]);
							left.add(labelArray[northPosition]);
							unionFind.put(labelArray[westPosition], left);
							
							//add the west label to the set of the northern shit
							TreeSet<Integer> north = new TreeSet<Integer>();
							north = unionFind.get(labelArray[northPosition]);
							north.add(labelArray[westPosition]);
							unionFind.put(labelArray[northPosition], north);
							
						}
					}
					else if(img.pixels[westPosition] == img.pixels[position] && img.pixels[northPosition] != img.pixels[position])
					{
						labelArray[position] = labelArray[westPosition];

					}
					else if(img.pixels[westPosition] != img.pixels[position] && img.pixels[northPosition] == img.pixels[position])
					{
						labelArray[position] = labelArray[northPosition];
//						System.out.println("it should come into this loop");
					}
					else
					{
						labelArray[position] = label;
						labelColorMap.put(label, img.pixels[position]);
						label++;
						if(unionFind.get(key) == null)
						{
							TreeSet<Integer> set = new TreeSet<Integer>();
							set.add(labelArray[position]);
							unionFind.put(labelArray[position], set);
							
							
						}
					}
				}
				else if(j!=0 && i == 0)
				{
					if(img.pixels[westPosition] == img.pixels[position])
					{
						labelArray[position] = labelArray[westPosition];
					}
					else
					{
						labelArray[position] = label;
						labelColorMap.put(label, img.pixels[position]);

						label++;
						if(unionFind.get(key) == null)
						{
							TreeSet<Integer> set = new TreeSet<Integer>();
							set.add(labelArray[position]);
							unionFind.put(labelArray[position], set);
						}
					}
				}
				else if(j== 0 && i != 0 )
				{
					if(img.pixels[northPosition] == img.pixels[position])
					{
						labelArray[position] = labelArray[northPosition];
					}
					else
					{
						labelArray[position] = label;
						labelColorMap.put(label, img.pixels[position]);

						label++;
						if(unionFind.get(key) == null)
						{
							TreeSet<Integer> set = new TreeSet<Integer>();
							set.add(labelArray[position]);
							unionFind.put(labelArray[position], set);
						}
					}
				}
				else
				{
					labelArray[position] = label;
					labelColorMap.put(label, img.pixels[position]);

					label++;
					if(unionFind.get(key) == null)
					{
						TreeSet<Integer> set = new TreeSet<Integer>();
						set.add(labelArray[position]);
						unionFind.put(labelArray[position], set);
					}
				}
//				System.out.println("_________________");

			}
		}

		
		Set<Integer> kSet = unionFind.keySet();
		List<Integer> kSetList = new ArrayList<Integer>(kSet);
	
		for(int i=0; i < kSetList.size(); i++)
		{
//			System.out.println(kSetList.get(i)+ " lala "+unionFind.get(kSetList.get(i)).toString());
		}
		System.out.println();
		for(int i =0; i < labelArray.length; i++)
		{
//			System.out.print(" "+labelArray[i]+" ");
		}
		
		//second pass
		int[] secondPass = new int[height*width];
		System.out.println();
		for(int i = 0; i < height*width; i++)
		{
			int current = labelArray[i];
			TreeSet<Integer> values = unionFind.get(kSetList.get(current));
			secondPass[i] = values.first();
//			System.out.print(" "+secondPass[i]+" ");
		}
		
		Set<Integer> lcMap = labelColorMap.keySet();
		List<Integer> lcMapList = new ArrayList<Integer>(kSet);
		
		for(int i = 0; i < lcMap.size(); i++)
		{
			int color = labelColorMap.get(lcMapList.get(i));
			int r=(color&0x00FF0000)>>16; // red part
			int g=(color&0x0000FF00)>>8; // green part
			int b=(color&0x000000FF); // blue part
//			System.out.println();
//			System.out.println(lcMapList.get(i) + " "+ "color("+r+", "+g+", "+b+")");
		}
		retImg = createImage(640, 480, RGB);
		for(int i = 0; i < secondPass.length; i++)
		{
			int spLabel = labelArray[i];
			int color = labelColorMap.get(spLabel);
			int r=(color&0x00FF0000)>>16; // red part
			int g=(color&0x0000FF00)>>8; // green part
			int b=(color&0x000000FF); // blue part
			retImg.pixels[i] = color(r, g, b);
		}
	}
}