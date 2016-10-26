/*************************************************************************
 * Name:
 * Email:
 *
 * Compilation:  javac Point.java
 * Execution:
 * Dependencies: StdDraw.java
 *
 * Description: An immutable data type for points in the plane.
 *
 *************************************************************************/

import java.util.Comparator;

public class Point implements Comparable<Point> {

    // compare points by slope
    public final Comparator<Point> SLOPE_ORDER = new Comparator<Point>(){
        public int compare(Point p, Point q){
            double slopeP = slopeTo(p);
            double slopeQ = slopeTo(q);
            if (slopeP > slopeQ) return 1;
            if (slopeP < slopeQ) return -1;
            if (slopeP == slopeQ) return 0;
            return 0;
        }
    };

    private final int x;                              // x coordinate
    private final int y;                              // y coordinate

    // create the point (x, y)
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    // plot this point to standard drawing
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    // draw line between this point and that point to standard drawing
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // slope between this point and that point
    public double slopeTo(Point that) {
        /* YOUR CODE HERE */
        if (that.x == this.x && that.y == this.y) return Double.NEGATIVE_INFINITY;
        if (that.x-this.x == 0) return Double.POSITIVE_INFINITY;
        return (double)(that.y - this.y)/(double)(that.x - this.x);
    }

    // is this point lexicographically smaller than that one?
    // comparing y-coordinates and breaking ties by x-coordinates
    public int compareTo(Point that) {
        /* YOUR CODE HERE */
        if (this.y > that.y) return 1;
        if (this.y < that.y) return -1;
        if (this.x > that.x) return 1;
        if (this.x < that.x) return -1;
        return 0;
    }

    // return string representation of this point
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    // unit test
    public static void main(String[] args) {
        /* YOUR CODE HERE */
        Point first = new Point(1200, 9100);
        Point second = new Point(2000, 6000);
        Point third = new Point(6,3);

        Long num = 9100L-6000L;
        Long den = 1200L-2000L;
        Long result = num/den;


        System.out.printf("%.2f", 1.2434234);
        System.out.println();
        System.out.println(second.slopeTo(first));
    }
}