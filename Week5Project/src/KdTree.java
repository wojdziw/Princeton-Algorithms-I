import java.util.ArrayList;
import java.util.List;

public class KdTree {

    private int size;
    private Node root;

    private class Node {
        private Point2D point;
        private RectHV rect;

        private Node left;
        private Node right;

        public Node(Point2D point, RectHV rect) {
            this.point = point;
            this.rect = rect;
        }
    }

    private Node put(RectHV rect, Node node, Point2D point, Boolean goVertical) {

        if (node == null) {
            size++;
            return new Node(point, rect);
        }

        if (node.point.equals(point)) {
            return node;
        }

        if (goVertical) {
            RectHV rightRect = new RectHV(node.point.x(), rect.ymin(), rect.xmax(), rect.ymax());
            RectHV leftRect = new RectHV(rect.xmin(), rect.ymin(), node.point.x(), rect.ymax());

            if (node.point.x() <= point.x()) node.right = put(rightRect, node.right, point, false);
            if (node.point.x() > point.x()) node.left = put(leftRect, node.left, point, false);
        } else {
            RectHV topRect = new RectHV(rect.xmin(), node.point.y(), rect.xmax(), rect.ymax());
            RectHV bottomRect = new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), node.point.y());

            if (node.point.y() <= point.y()) node.right = put(topRect, node.right, point, true);
            if (node.point.y() > point.y()) node.left = put(bottomRect, node.left, point, true);
        }
        return node;
    }

    private Boolean find(Node node, Point2D point, Boolean goVertical) {
        if (node == null) return false;
        if (node.point.equals(point)) return true;
        if (goVertical) {
            if (node.point.x() <= point.x()) return find(node.right, point, false);
            if (node.point.x() > point.x()) return find(node.left, point, false);
        } else {
            if (node.point.y() <= point.y()) return find(node.right, point, true);
            if (node.point.y() > point.y()) return find(node.left, point, true);
        }
        return false;
    }

    private List<Point2D> traverse(Node node) {
        List<Point2D> toReturn = new ArrayList<>();

        if (node.left == null)
            if (node.right == null) {
                toReturn.add(node.point);
                return toReturn;
            }

        toReturn.add(node.point);

        if (node.left != null) {
            toReturn.addAll(traverse(node.left));
        }
        if (node.right != null) {
            toReturn.addAll(traverse(node.right));
        }

        return toReturn;
    }

    private List<Point2D> rectangle(RectHV rect, Node node, Boolean goVertical) {
        if (node == null) return null;

        List<Point2D> toReturn = new ArrayList<>();
        if (isItIn(node.point, rect)) toReturn.add(node.point);

        if (goVertical) {
            if (rect.xmin() <= node.point.x()) {
                if (node.left != null) toReturn.addAll(rectangle(rect, node.left, false));
            }
            if (rect.xmax() >= node.point.x()) {
                if (node.right != null) toReturn.addAll(rectangle(rect, node.right, false));
            }
        } else {
            if (rect.ymin() <= node.point.y()) {
                if (node.left != null) toReturn.addAll(rectangle(rect, node.left, true));
            }
            if (rect.ymax() >= node.point.y()) {
                if (node.right != null) toReturn.addAll(rectangle(rect, node.right, true));
            }
        }
        return toReturn;
    }

    private Point2D neighbor(Point2D winner, double minDist, Point2D target, Node node, Boolean goVertical) {

        if (node.point.equals(target)) return node.point;

        double xDif = node.point.x() - target.x();
        double yDif = node.point.y() - target.y();

        double newDist = (xDif)*(xDif)+(yDif)*(yDif);
        if (newDist < minDist) {
            winner = node.point;
            minDist = newDist;
        }

        if (goVertical) {
            double toSplittingLine = xDif*xDif;

            if (Math.abs(toSplittingLine) <= minDist) {
                if (toSplittingLine < 0) {
                    if (node.left != null) winner = neighbor(winner, minDist, target, node.left, false);
                }
                if (toSplittingLine > 0) {
                    if (node.right != null) winner = neighbor(winner, minDist, target, node.right, false);
                }
            } else {
                if (node.left != null) winner = neighbor(winner, minDist, target, node.left, false);
                if (node.right != null) winner = neighbor(winner, minDist, target, node.right, false);
            }

        } else {
            double toSplittingLine = yDif*yDif;

            if (Math.abs(toSplittingLine) <= minDist) {
                if (toSplittingLine < 0) {
                    if (node.left != null) winner = neighbor(winner, minDist, target, node.left, true);
                }
                if (toSplittingLine > 0) {
                    if (node.right != null) winner = neighbor(winner, minDist, target, node.right, true);
                }
            } else {
                if (node.left != null) winner = neighbor(winner, minDist, target, node.left, true);
                if (node.right != null) winner = neighbor(winner, minDist, target, node.right, true);
            }
        }

        return winner;
    }

    private Boolean isItIn (Point2D point2D, RectHV rect) {
        if (point2D.x() >= rect.xmin()
                && point2D.x() <= rect.xmax()
                && point2D.y() >= rect.ymin()
                && point2D.y() <= rect.ymax()) {
            return true;
        }
        return false;
    }

    public KdTree()                               // construct an empty set of points
    {
        root = null;
    }
    public boolean isEmpty()                      // is the set empty?
    {
        return root == null;
    }
    public int size()                         // number of points in the set
    {
        return size;
    }
    public void insert(Point2D p)              // add the point to the set (if it is not already in the set)
    {
        root = put(new RectHV(0,0,1,1), root, p, true);
    }
    public boolean contains(Point2D p)            // does the set contain point p?
    {
        return find(root, p, true);
    }
    public void draw()                         // draw all points to standard draw
    {
        List<Point2D> pointSet = traverse(root);
        for (Point2D point2D : pointSet) {
            StdDraw.point(point2D.x(), point2D.y());
        }
    }

    public Iterable<Point2D> range(RectHV rect)             // all points that are inside the rectangle
    {
        List<Point2D> pointsWithin = new ArrayList<>();
        pointsWithin.addAll(rectangle(rect, root, true));

        return pointsWithin;
    }
    public Point2D nearest(Point2D p)             // a nearest neighbor in the set to point p; null if the set is empty
    {
        Point2D far = new Point2D(10,10);
        double maxDist = 10;
        Point2D nearestPoint = neighbor(far, maxDist, p, root, true);

        return nearestPoint;
    }

    public static void main(String[] args)                  // unit testing of the methods (optional)
    {
        KdTree kdTree = new KdTree();

        kdTree.insert(new Point2D(0.7, 0.2));
        kdTree.insert(new Point2D(0.5, 0.4));
        kdTree.insert(new Point2D(0.2, 0.3));
        kdTree.insert(new Point2D(0.4, 0.7));
        kdTree.insert(new Point2D(0.9, 0.6));
        kdTree.insert(new Point2D(0.1, 0.1));
        kdTree.insert(new Point2D(1, 1));

        System.out.println(kdTree.contains(new Point2D(1, 0)));

        kdTree.insert(new Point2D(0.2, 0.3));

        System.out.println(kdTree.size());


//        for (Point2D point2D : kdTree.range(new RectHV(0,0,1.1, 1.1))) {
//            System.out.println(point2D);
//        }
//
//        System.out.println("nearest");
//
//        System.out.println(kdTree.nearest(new Point2D(1, 1)));




    }
}