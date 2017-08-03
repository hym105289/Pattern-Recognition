import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class FastCollinearPoints {
	private int lineNumber;
	private ListNode last;

	private class ListNode {
		private LineSegment val;
		private ListNode pre;
	}

	public FastCollinearPoints(Point[] points) // finds all line segments
												// containing 4 or more points
	{
		if (points == null) {
			throw new IllegalArgumentException();
		}
		int num = points.length;
		Point[] copy = new Point[num];
		for (int i = 0; i < num; i++) {
			if (points[i] == null) {
				throw new IllegalArgumentException();
			}
			for (int j = i + 1; j < num; j++) {
				if (points[i].compareTo(points[j]) == 0) {
					throw new IllegalArgumentException();
				}
			}
			copy[i] = points[i];
		}
		Arrays.sort(copy, 0, num);// be careful
		if (num < 4) {
			return;
		}
		for (int i = 0; i < num - 1; i++) {
			Point origPoint = copy[i];
			int otherPointsN = 0;
			Point[] otherPoints = new Point[num - 1];

			for (int j = 0; j < num; j++) {
				if (i != j)
					otherPoints[otherPointsN++] = copy[j];
			}
			Arrays.sort(otherPoints, copy[i].slopeOrder());
			int count = 0;
			Point min = null;
			Point max = null;
			for (int j = 0; j < otherPointsN - 1; j++) {
				if (Double.compare(origPoint.slopeTo(otherPoints[j]), origPoint.slopeTo(otherPoints[j + 1])) == 0) {
					count++;
					if (min == null && max == null) {
						if (origPoint.compareTo(otherPoints[j]) > 0) {
							max = origPoint;
							min = otherPoints[j];
						} else {
							max = otherPoints[j];
							min = origPoint;
						}
					}
					if (otherPoints[j + 1].compareTo(min) < 0) {
						min = otherPoints[j + 1];
					}
					if (otherPoints[j + 1].compareTo(max) > 0) {
						max = otherPoints[j + 1];
					}
					if (j == otherPointsN - 2 && count >= 2 && origPoint.compareTo(min) == 0) {
						addLine(min, max);
						lineNumber++;
					}
				} else {
					if (count >= 2 && origPoint.compareTo(min) == 0) {
						addLine(min, max);
						lineNumber++;
					}
					count = 0;
					max = null;
					min = null;
				}
			}
		}
	}

	private void addLine(Point a, Point b) {
		if (last != null) {
			ListNode node = new ListNode();
			node.pre = last;
			node.val = new LineSegment(a, b);
			last = node;
		} else {
			last = new ListNode();
			last.val = new LineSegment(a, b);
		}
	}

	public int numberOfSegments() // the Nber of line segments
	{
		return lineNumber;
	}

	public LineSegment[] segments() // the line segments
	{
		LineSegment[] lines = new LineSegment[lineNumber];
		ListNode current = last;

		for (int i = 0; i < lineNumber; i++) {
			lines[i] = current.val;
			current = current.pre;
		}

		return lines;
	}

	public static void main(String[] args) {
		// read the n points from a file
		In in = new In(args[0]);
		int n = in.readInt();
		Point[] points = new Point[n];

		for (int i = 0; i < n; i++) {
			int x = in.readInt();
			int y = in.readInt();
			points[i] = new Point(x, y);
		}

		// draw the points
		StdDraw.enableDoubleBuffering();
		StdDraw.setXscale(0, 32768);
		StdDraw.setYscale(0, 32768);
		StdDraw.setPenRadius(0.01);
		for (Point p : points) {
			p.draw();
		}

		StdDraw.show();

		// print and draw the line segments
		FastCollinearPoints collinear = new FastCollinearPoints(points);
		System.out.println(collinear.lineNumber);
		for (LineSegment segment : collinear.segments()) {
			StdOut.println(segment);
			segment.draw();
		}
		StdDraw.show();
	}
}
