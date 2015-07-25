import java.util.ArrayList;
import java.util.List;

public class Board {

    private int[][] blocks;
    private int N;

    public Board(int[][] blocks)           // construct a board from an N-by-N array of blocks
    // (where blocks[i][j] = block in row i, column j)
    {
        this.blocks = blocks;
        this.N = blocks[0].length;
    }
    public int dimension()                 // board dimension N
    {
        return N;
    }
    public int hamming()                   // number of blocks out of place
    {
        int outOfPlace = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                int position = i*N + j + 1;
                if (blocks[i][j] != position && blocks[i][j] != 0){
                    outOfPlace++;
                }
            }
        }
        return outOfPlace;
    }
    public int manhattan()                 // sum of Manhattan distances between blocks and goal
    {
        int totalDistance = 0;
        for (int i = 0; i< N; i++) {
            for (int j = 0; j < N; j++) {
                if (blocks[i][j] != 0) {
                    int homeI = (blocks[i][j]-1)/N;
                    int homeJ = blocks[i][j] % N - 1;
                    if (homeJ == -1) homeJ = N-1;
                    int distance = Math.abs(homeI-i) + Math.abs(homeJ-j);

                    totalDistance += distance;
                }
            }
        }
        return totalDistance;
    }
    public boolean isGoal()                // is this board the goal board?
    {
        return (this.hamming() == 0);
    }
    public Board twin()                    // a board that is obtained by exchanging two adjacent blocks in the same row
    {
        int[][] twinInt = new int[N][N];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                int swap = blocks[i][j];
                twinInt[i][j] = swap;
            }
        }

        Board twin = new Board(twinInt);

        int N = this.dimension();
        boolean mirrored = false;
        for (int i = 0; i < N; i++) {
            for (int j = 1; j < N; j++) {
                if (twin.blocks[i][j] != 0 && twin.blocks[i][j-1] != 0 && !mirrored) {
                    int helper = twin.blocks[i][j];
                    twin.blocks[i][j] = twin.blocks[i][j-1];
                    twin.blocks[i][j-1] = helper;
                    mirrored = true;
                }
            }
        }

        return twin;
    }
    public boolean equals(Object y)        // does this board equal y?
    {
        if (y == this) return true;

        if (y == null) return false;

        if (y.getClass() != this.getClass())
            return false;

        Board that = (Board) y;

        int N = this.dimension();
        if (N != that.dimension()) return false;

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (this.blocks[i][j] != that.blocks[i][j]) return false;
            }
        }

        return true;
    }
    public Iterable<Board> neighbors()     // all neighboring boards
    {
        List<Board> neighbors = new ArrayList<>();

        int zeroI = 0;
        int zeroJ = 0;

        int[][] left = new int[N][N];
        int[][] right = new int[N][N];
        int[][] top = new int[N][N];
        int[][] bottom = new int[N][N];


        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (blocks[i][j] == 0) {
                    zeroI = i;
                    zeroJ = j;
                }
                int swap = blocks[i][j];
                left[i][j] = swap;
                right[i][j] = swap;
                top[i][j] = swap;
                bottom[i][j] = swap;
            }
        }

        // move the tile on the left to the right
        if (zeroJ != 0) {
            left[zeroI][zeroJ] = left[zeroI][zeroJ-1];
            left[zeroI][zeroJ-1] = 0;
            neighbors.add(new Board(left));
        }


        // move the tile on the right to the left
        if (zeroJ != N-1) {
            right[zeroI][zeroJ] = right[zeroI][zeroJ+1];
            right[zeroI][zeroJ+1] = 0;
            neighbors.add(new Board(right));
        }


        // move the tile on the top to the bottom
        if (zeroI != 0) {
            top[zeroI][zeroJ] = top[zeroI-1][zeroJ];
            top[zeroI-1][zeroJ] = 0;
            neighbors.add(new Board(top));
        }


        // move the tile on the bottom to the top
        if (zeroI != N-1) {
            bottom[zeroI][zeroJ] = bottom[zeroI+1][zeroJ];
            bottom[zeroI+1][zeroJ] = 0;
            neighbors.add(new Board(bottom));
        }


        return neighbors;
    }


    public String toString()               // string representation of this board (in the output format specified below)
    {
        StringBuilder s = new StringBuilder();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", blocks[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    public static void main(String[] args) // unit tests (not graded)
    {
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        System.out.println(initial.toString());

//        System.out.println(initial.toString());
//        System.out.println(initial.dimension());
        System.out.println(initial.hamming());
//        System.out.println(initial.manhattan());
//        System.out.println(initial.isGoal());
//
//        System.out.println(initial.twin().toString());
//
//        System.out.println(initial.twin().isGoal());
//
//        System.out.println(initial.equals(initial));


//        for (Board neighbor : initial.neighbors()) {
//            System.out.println(neighbor.toString());
//            System.out.println(neighbor.hamming());
//            System.out.println(neighbor.manhattan());
//        }



    }
}