import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Fast {
    public static void main(String[] args)
    {
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);

        In input = new In(args[0]);
        int noPoints = input.readInt();
        Point[] points = new Point[noPoints];
        Point[] sorted = new Point[noPoints];

        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);

        for (int i = 0; i < noPoints; i++){
            int x = input.readInt();
            int y = input.readInt();
            points[i] = new Point(x, y);
            sorted[i] = new Point(x, y);
            points[i].draw();
        }

        List<Double> slopes = new ArrayList<>();

        for (int i = 0; i < noPoints; i++){
            Point ref = points[i];
            Arrays.sort(sorted, i+1, noPoints, ref.SLOPE_ORDER);

            int chainLen = 1;
            for (int j = 1; j < noPoints; j++){
                if (points[i].slopeTo(sorted[j]) == points[i].slopeTo(sorted[j-1]) && !slopes.contains(points[i].slopeTo(sorted[j]))){
                    chainLen++;
                    if (chainLen == 3){
                        StdOut.print(points[i]);
                        StdOut.print(" -> ");
                        StdOut.print(sorted[j]);
                        StdOut.print(" -> ");
                        StdOut.print(sorted[j - 1]);
                        StdOut.print(" -> ");
                        StdOut.print(sorted[j - 2]);
                    }
                    if (chainLen >= 3){
                        StdOut.print(" -> ");
                        StdOut.print(sorted[j]);
                    }
                } else {
                    if (chainLen >= 3) {
                        slopes.add(points[i].slopeTo(sorted[j-1]));
                        StdOut.println("");
                    }
                    chainLen = 1;
                }
            }
        }
    }
}