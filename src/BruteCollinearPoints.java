import java.util.Arrays;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;

public class BruteCollinearPoints {
	private int lineNumber;
	private ListNode last;

	private class ListNode {
		private LineSegment val;
		private ListNode pre;
	}

	public BruteCollinearPoints(Point[] points) {
		if (points == null) {
			throw new IllegalArgumentException();
		}
		int num = points.length;
		Point[] copy = new Point[num];
		if (num < 4)
			return;
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
		for (int i = 0; i < num; i++) {
			for (int j = i + 1; j < num; j++) {
				for (int k = j + 1; k < num; k++) {
					for (int l = k + 1; l < num; l++) {
						double slope1 = copy[i].slopeTo(copy[j]);
						double slope2 = copy[j].slopeTo(copy[k]);
						double slope3 = copy[k].slopeTo(copy[l]);
						if (Double.compare(slope1, slope2) == 0 && Double.compare(slope2, slope3) == 0) {
							addLine(copy[i], copy[l]);
							lineNumber++;
						}
					}
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

	public int numberOfSegments() {
		return lineNumber;
	}

	public LineSegment[] segments() {
		LineSegment[] lines = new LineSegment[lineNumber];
		ListNode pNode = last;
		for (int i = 0; i < lineNumber; i++) {
			lines[i] = pNode.val;
			pNode = pNode.pre;
		}
		return lines;
	}

	public static void main(String[] args) {
		StdDraw.enableDoubleBuffering();
		StdDraw.setXscale(0, 32768);
		StdDraw.setYscale(0, 32768);
		StdDraw.setPenRadius(0.01);
		String filename = args[0];
		In in = new In(filename);
		int n = in.readInt();
		Point[] points = new Point[n];
		for (int i = 0; i < n; i++) {
			int x = in.readInt();
			int y = in.readInt();
			Point p = new Point(x, y);
			points[i] = p;
			p.draw();
		}
		StdDraw.show();
		BruteCollinearPoints collinear = new BruteCollinearPoints(points);
		System.out.println(collinear.lineNumber);
		for (LineSegment segment : collinear.segments()) {
			System.out.println(segment);
			segment.draw();
		}
		StdDraw.show();
	}
}