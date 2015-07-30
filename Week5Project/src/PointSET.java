import java.util.*;

public class PointSET {

    private Set<Point2D> pointSet;

    public PointSET()                               // construct an empty set of points
    {
        pointSet = new TreeSet<>();
    }
    public boolean isEmpty()                      // is the set empty?
    {
        return pointSet.isEmpty();
    }
    public int size()                         // number of points in the set
    {
        return pointSet.size();
    }
    public void insert(Point2D p)              // add the point to the set (if it is not already in the set)
    {
        pointSet.add(p);
    }
    public boolean contains(Point2D p)            // does the set contain point p?
    {
        return pointSet.contains(p);
    }
    public void draw()                         // draw all points to standard draw
    {
        for (Point2D point2D : pointSet) {
            StdDraw.point(point2D.x(), point2D.y());
        }
    }
    public Iterable<Point2D> range(RectHV rect)             // all points that are inside the rectangle
    {
        List<Point2D> pointsWithin = new ArrayList<>();

        for (Point2D point2D : pointSet) {
            if (point2D.x() >= rect.xmin()
                    && point2D.x() <= rect.xmax()
                    && point2D.y() >= rect.ymin()
                    && point2D.y() <= rect.ymax()) {
                pointsWithin.add(point2D);
            }
        }

        return pointsWithin;
    }
    public Point2D nearest(Point2D p)             // a nearest neighbor in the set to point p; null if the set is empty
    {
        double minDist = Double.POSITIVE_INFINITY;
        double pointDist;
        Point2D nearestPoint = null;

        for (Point2D point2D : pointSet) {
            pointDist = (p.x()-point2D.x())*(p.x()-point2D.x())+(p.y()-point2D.y())*(p.y()-point2D.y());
            if (pointDist < minDist) {
                minDist = pointDist;
                nearestPoint = point2D;
            }
        }

        return nearestPoint;
    }

    public static void main(String[] args)                  // unit testing of the methods (optional)
    {
        PointSET pointSET = new PointSET();

        pointSET.insert(new Point2D(0.5, 0.5));
        pointSET.insert(new Point2D(0.1, 0.7));
        pointSET.insert(new Point2D(0.6, 0.8));
        pointSET.insert(new Point2D(0.9, 0.3));
        pointSET.insert(new Point2D(0.3, 0.3));
        pointSET.insert(new Point2D(0.1, 0.1));

        RectHV rectHV = new RectHV(0, 0, 0.5, 0.5);

        for (Point2D point2D : pointSET.range(rectHV)) {
            System.out.println(point2D);
        }

        System.out.println(pointSET.nearest(new Point2D(0, 0)));

    }
}