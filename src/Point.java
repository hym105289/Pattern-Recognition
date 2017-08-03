
/*************************************************************************
 * Name:Yunmeng Hu
 * Email:huyunmeng@163.com
 * 
 * Compilation:  javac Point.java
 * Execution:    java Point
 * Dependencies: StdDraw.java/In.java/StdOut.java
 *
 * Description: An immutable data type for points in the plane.
 *
 *************************************************************************/
import java.util.Arrays;
import java.util.Comparator;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class Point implements Comparable<Point> {
	private final int x;
	private final int y;

	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void draw() {
		StdDraw.point(x, y);
	}

	public void drawTo(Point that) {
		StdDraw.line(this.x, this.y, that.x, that.y);
	}

	/**
	 * Returns the slope between this point and the specified point. Formally,
	 * if the two points are (x0, y0) and (x1, y1), then the slope is (y1 - y0)
	 * / (x1 - x0). For completeness, the slope is defined to be +0.0 if the
	 * line segment connecting the two points is horizontal;
	 * Double.POSITIVE_INFINITY if the line segment is vertical; and
	 * Double.NEGATIVE_INFINITY if (x0, y0) and (x1, y1) are equal.
	 *
	 * @param that
	 *            the other point
	 * @return the slope between this point and the specified point
	 */
	public double slopeTo(Point that) {
		if (this.x == that.x && this.y == that.y) {
			return Double.NEGATIVE_INFINITY;
		} else if (this.x == that.x) {
			return Double.POSITIVE_INFINITY;
		} else if (this.y == that.y) {
			return +0.0;
		} else {
			return (that.y - this.y) / (double) (that.x - this.x);
		}
	}

	/**
	 * Compares two points by y-coordinate, breaking ties by x-coordinate.
	 * Formally, the invoking point (x0, y0) is less than the argument point
	 * (x1, y1) if and only if either y0 < y1 or if y0 = y1 and x0 < x1.
	 *
	 * @param that
	 *            the other point
	 * @return the value <tt>0</tt> if this point is equal to the argument point
	 *         (x0 = x1 and y0 = y1); a negative integer if this point is less
	 *         than the argument point; and a positive integer if this point is
	 *         greater than the argument point
	 */
	public int compareTo(Point that) {
		if (this.x == that.x && this.y == that.y) {
			return 0;
		} else if (this.y < that.y || (this.y == that.y && this.x < that.x)) {
			return -1;
		} else {
			return 1;
		}
	}

	public Comparator<Point> slopeOrder() {
		return new SlopeOrder();
	}

	private class SlopeOrder implements Comparator<Point> {
		public int compare(Point p1, Point p2) {
			double slope1 = slopeTo(p1);
			double slope2 = slopeTo(p2);
			if (slope1 < slope2) {
				return -1;
			} else if (slope1 > slope2) {
				return 1;
			} else {
				return 0;
			}
		}
	}

	public String toString() {
		return "(" + x + "," + y + ")";
	}

	public static void main(String[] args) {
		String filename = args[0];
		In in = new In(filename);
		int N = in.readInt();
		Point[] points = new Point[N];
		for (int i = 0; i < N; i++) {
			int x = in.readInt();
			int y = in.readInt();
			Point p = new Point(x, y);
			points[i] = p;
		}
		Arrays.sort(points, points[0].slopeOrder());
		for (Point p : points)
			StdOut.println(p);
	}
}
