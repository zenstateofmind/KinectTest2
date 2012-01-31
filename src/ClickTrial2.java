import king.kinect.NativeKinect;
import processing.core.PApplet;
import processing.core.PImage;
import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.*;

public class ClickTrial2 extends PApplet {

	PImage img;
	PImage retImg;
	PImage meDraw;
	PImage img2;

	public void setup() {

		retImg = createImage(100, 100, RGB);
		for (int i = 0; i < 10000; i++) {
			retImg.pixels[i] = color(0, 0, 0);
		}
		// test
		img = createImage(3, 3, RGB);
		img.pixels[0] = color(255, 255, 255);
		img.pixels[1] = color(0, 0, 0);
		img.pixels[2] = color(0, 0, 0);
		img.pixels[4] = color(0, 0, 0);
		img.pixels[7] = color(0, 0, 0);
		img.pixels[3] = color(255, 255, 255);
		img.pixels[6] = color(255, 255, 255);
		img.pixels[5] = color(255, 255, 255);
		img.pixels[8] = color(255, 255, 255);

		img2 = createImage(100, 100, RGB);
		for (int i = 0; i < 10000; i++) {
			img2.pixels[i] = color(0, 0, 0);
		}
		for (int j = 49; j < 60; j++) {
			for (int i = j; i < 10000; i = i + 100) {
				img2.pixels[i] = color(255, 255, 255);
			}
		}
		for (int i = 4; i < 10000; i = i + 100) {
//			img2.pixels[i] = color(255, 255, 255);
		}
		// for(int i=)

	}

	public void draw() {
		// image(img, 0, 0, 3, 3);
		// FirstPass(img);
		image(img2, 0, 0, 100, 100);

	}

	public void mousePressed() {
		OnePass(img2);
		image(retImg, 100, 100, 100, 100);

	}

	// ArrayList<HashSet> linked = new ArrayList<HashSet>();
	// HashSet<Integer>[] linked = new HashSet<>[9];

	int[] labels = new int[10000];

	public int[] OnePass(PImage img) {

		Set<Integer>[] linked = new HashSet[10000];
		for (int i = 0; i < linked.length; i++)
			linked[i] = new HashSet<Integer>();

		// tester variables//
		int counter = 0;
		// ////
		int position = 0;
		int rows = 100;
		int cols = 100;
		int nextLabel = 1;
		for (int i = 0; i < 100; i++) {
			for (int j = 0; j < 100; j++) {
				position = j + i * 100;
				// if you are at a background pixel, then make the label 0
				if (img.pixels[position] == color(0, 0, 0)) {
					System.out.println("it comes here");
					System.out.println("position is " + position);
					labels[position] = 0;
				}

				// if you are at the first pixel, then make a new label if it
				// isnt background
				else if (i == 0 && j == 0) {
					System.out.println(" loop is 2 position is " + position);
					labels[position] = nextLabel;
					nextLabel++;
				}

				// if i is 0 (first row), check if the left is not a background,
				// and if it isnt, put that label in the current position
				else if (i == 0 && j != 0) {
					if (img.pixels[position] != color(0, 0, 0)
							&& img.pixels[position - 1] != color(0, 0, 0)) {
						labels[position] = labels[position - 1];
					} else if (img.pixels[position] != color(0, 0, 0)
							&& img.pixels[position - 1] == color(0, 0, 0)) {
						labels[position] = nextLabel;
						nextLabel++;
					}

					System.out.println(" loop is 3 position is " + position);

				}

				// if j = 0, then you are in the first column, then just do the
				// same for the north
				else if (i != 0 && j == 0) {
					System.out.println(" loop is 4 position is " + position);

					if (img.pixels[position] != color(0, 0, 0)
							&& img.pixels[position - cols] != color(0, 0, 0)) {
						labels[position] = labels[position - cols];
					} else if (img.pixels[position] != color(0, 0, 0)
							&& img.pixels[position - cols] == color(0, 0, 0)) {
						labels[position] = nextLabel;
						nextLabel++;
					}
				}
				// now if i or j is not 0, then check if the north and west
				// arent background, then just union and put the smallest label
				// in the current position
				else if (i != 0 && j != 0
						&& img.pixels[position - 1] != color(0, 0, 0)
						&& img.pixels[position - cols] != color(0, 0, 0)) {

					System.out.println(" loop is 5 position is " + position);

					labels[position] = Math.min(labels[position - 1],
							labels[position - cols]);
					System.out.println(labels[position]);

					linked[position].add(labels[position]);
					linked[position].add(labels[position - 1]);
					linked[position].add(labels[position - cols]);

					linked[position - 1].add(labels[position]);
					linked[position - 1].add(labels[position - cols]);
					linked[position - 1].add(labels[position - 1]);

					linked[position - cols].add(labels[position]);
					linked[position - cols].add(labels[position - 1]);
					linked[position - cols].add(labels[position - cols]);

				}

				// same condition as above, if the north is background, and west
				// isnt, then put wests label
				else if (i != 0 && j != 0
						&& img.pixels[position - cols] != color(0, 0, 0)
						&& img.pixels[position - 1] == color(0, 0, 0)) {

					System.out.println(" loop is 6 position is " + position);

					labels[position] = labels[position - cols];
				}

				// if west is in the background, and north isnt, same condition
				// as above, put the north label

				else if (i != 0 && j != 0
						&& img.pixels[position - 1] != color(0, 0, 0)
						&& img.pixels[position - cols] == color(0, 0, 0)) {
					System.out.println(" loop is 7 position is " + position);

					labels[position] = labels[position - 1];
				}
				// if both are background, make a new label
				else if (i != 0 && j != 0
						&& img.pixels[position - 1] == color(0, 0, 0)
						&& img.pixels[position - cols] == color(0, 0, 0)) {
					System.out.println(" loop is 8 position is " + position);

					labels[position] = nextLabel;
					nextLabel++;
				}
			}
		}
		for (int i = 0; i < 10000; i++) {
			System.out.println("labels[" + i + "] = " + labels[i]);
		}
		for (int i = 0; i < 10000; i++) {
			if (labels[i] == 0) {
				retImg.pixels[i] = color(0, 0, 0);
			} else if (labels[i] == 1) {
				retImg.pixels[i] = color(255, 255, 255);

			} else if (labels[i] == 2) {
				retImg.pixels[i] = color(123, 234, 123);
			}
		}
		return labels;

	}

	public void PrintColor(int c) {
		int alpha = (c >> 24) & 0xFF;
		int red = (c >> 16) & 0xFF;
		int green = (c >> 8) & 0xFF;
		int blue = c & 0xFF;
		println(" red: " + red + " green: " + green + " blue: " + blue);
	}

	public HashSet<Integer> union(HashSet<Integer> a, int label) {
		HashSet<Integer> ret = a;
		ret.add(label);
		return ret;

	}

	public int minLabel(ArrayList<Integer> nLabels) {
		int smallestLabel = nLabels.get(0);
		for (int i = 1; i < nLabels.size(); i++) {
			if (smallestLabel > nLabels.get(i)) {
				smallestLabel = nLabels.get(i);
			}
		}
		return smallestLabel;
	}

	public class Coordinates {
		int c;
		int r;

		public Coordinates(int row, int col) {
			r = row;
			c = col;
		}

		public int getRow() {
			return r;
		}

		public int getCol() {
			return c;
		}
	}

	public class UnionFind<T> {
		int[] p;
		ArrayList<T> elements;
		boolean mod;

		public UnionFind() {
			mod = true;
			p = new int[10];
			elements = new ArrayList<T>();

		}

		/**
		 * Unions the sets containing elements a and b together. If a and b are
		 * already in the same set, this method will do nothing.
		 * 
		 * @throws NullPointerException
		 *             if a or b is null
		 */
		public void union(T a, T b) {
			mod = true;
			// checks if a is null or b is null
			if (a == null || b == null) {
				throw new NullPointerException();
			}
			// if a is not present in the arrayqueue, we add it
			if (elements.contains(a) == false) {
				elements.add(a);
				if (elements.indexOf(a) == p.length - 1) {
					resize();
				}
				p[elements.indexOf(a)] = -1;
			}
			// if b is not present in the arrayqueue, we add it

			if (elements.contains(b) == false) {
				elements.add(b);
				if (elements.indexOf(b) == p.length - 1) {
					resize();
				}
				p[elements.indexOf(b)] = -1;
			}

			// finds the root of a
			T rootElementA = find(a);
			int rootA = elements.indexOf(rootElementA);

			// finds the root of b
			T rootElementB = find(b);
			int rootB = elements.indexOf(rootElementB);

			// if the roots of a and b are the same, then nothing is union-ed
			if (rootA == rootB) {
				mod = false;
				return;
			}
			// UNION TAKES PLACE in the next two loops
			if (p[rootA] < p[rootB]) {
				p[rootB] = rootA;

				return;
			}
			if (p[rootA] == p[rootB]) {
				p[rootA]--;
			}
			p[rootB] = rootA;

		}

		// returns true if two elements are actually union-ed
		public boolean getMod() {
			return mod;
		}

		// resizes if int array is full
		private void resize() {
			int[] doubleP = new int[p.length * 2];
			for (int i = 0; i < p.length; i++) {
				doubleP[i] = p[i];
			}
			p = doubleP;
		}

		/**
		 * Returns the canonical element representing the set a is in.
		 * 
		 * @param a
		 *            the element to be queried
		 * @return the canonical element representing the set a is in
		 * @throws NullPointerException
		 *             if a is null
		 */

		public T find(T a) {
			// exceptions
			if (a == null) {
				throw new NullPointerException();
			}
			// if element is not in the arraylist, it adds it
			if (elements.contains(a) == false) {
				elements.add(a);
				if (elements.indexOf(a) == p.length - 1) {
					resize();
				}
				p[elements.indexOf(a)] = -1;
			}

			// go into the findP method in which we find the roots based on the
			// int
			// array
			int rootIndex = findP(elements.indexOf(a));
			T parent = elements.get(rootIndex);
			return parent;
		}

		// implement find with path compression
		public int findP(int a) {
			if (p[a] < 0) {
				return a;
			}
			return (p[a] = findP(p[a]));
		}

		/**
		 * Resets this Union-Find so that each element is its own canonical
		 * element (ie. each element is in its own set).
		 */
		public void clear() {
			for (int i = 0; i < elements.size(); i++) {
				p[i] = -1;
			}
		}

	}

}
