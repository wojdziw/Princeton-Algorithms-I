import java.util.Arrays;

public class Brute {
    public static void main(String[] args)
    {
        In input = new In(args[0]);
        int noPoints = input.readInt();
        Point[] points = new Point[noPoints];

        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);

        for (int i = 0; i < noPoints; i++){
            int x = input.readInt();
            int y = input.readInt();
            points[i] = new Point(x, y);
            points[i].draw();
        }

        for (int i = 0; i < noPoints; i++){
            for (int j = i+1; j < noPoints; j++){
                double slopeJ = points[i].slopeTo(points[j]);
                for (int k = j+1; k < noPoints; k++){
                    double slopeK = points[i].slopeTo(points[k]);
                    for (int l=k+1; l < noPoints; l++){
                        double slopeL = points[i].slopeTo(points[l]);
                        if (slopeJ == slopeK && slopeJ == slopeL && slopeK == slopeL){
                            StdOut.print(points[i]);
                            StdOut.print(" -> ");
                            StdOut.print(points[j]);
                            StdOut.print(" -> ");
                            StdOut.print(points[k]);
                            StdOut.print(" -> ");
                            StdOut.println(points[l]);

                            Point[] line = new Point[4];
                            line[0] = points[i];
                            line[1] = points[j];
                            line[2] = points[k];
                            line[3] = points[l];

                            Arrays.sort(line);

                            line[0].drawTo(line[3]);
                        }
                    }
                }
            }
        }
    }
}
