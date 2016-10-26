public class Solver {

    private int M = 0;

    private MinPQ<SearchNode> boards = new MinPQ<>();
    private MinPQ<SearchNode> twinBoards = new MinPQ<>();
    private SearchNode minimum = new SearchNode();
    private SearchNode dummy = new SearchNode();

    private int minMoves;
    private boolean isSolved = false;


    private class SearchNode implements Comparable<SearchNode>{
        Board board;
        SearchNode previous;
        int priority;
        int moves;

        public SearchNode(){}

        public SearchNode(Board board, int moves, SearchNode previous) {
            this.board = board;
            this.previous = previous;
            this.priority = board.manhattan() + moves;
            this.moves = moves;
        }

        public int compareTo(SearchNode that) {
            if (this.priority < that.priority) return -1;
            if (this.priority > that.priority) return 1;
            if (this.moves < that.moves) return -1;
            if (this.moves > that.moves) return 1;
            return 0;
        }
    }

    public Solver(Board initial)           // find a solution to the initial board (using the A* algorithm)
    {
        dummy = new SearchNode(initial, 0, null);

        boards.insert(new SearchNode(initial, 0, dummy));
        twinBoards.insert(new SearchNode(initial.twin(), 0, dummy));

        minimum = new SearchNode(initial, 0, dummy);
        SearchNode twinMinimum = new SearchNode(initial.twin(), 0, dummy);

        while (!minimum.board.isGoal() && !twinMinimum.board.isGoal()) {
            minimum = boards.delMin();
            twinMinimum = twinBoards.delMin();

            M++;

            for (Board neighbor : minimum.board.neighbors()) {
                if (!neighbor.equals(minimum.previous.board)) {
                    boards.insert(new SearchNode(neighbor, minimum.moves+1, minimum));
                }
            }

            for (Board neighbor : twinMinimum.board.neighbors()) {
                if (!neighbor.equals(twinMinimum.previous.board)) {
                    twinBoards.insert(new SearchNode(neighbor, twinMinimum.moves+1, twinMinimum));
                }
            }
        }

        if (minimum.board.isGoal()) isSolved = true;

        minMoves = minimum.moves;

    }

    public boolean isSolvable()            // is the initial board solvable?
    {
        return isSolved;
    }
    public int moves()                     // min number of moves to solve initial board; -1 if unsolvable
    {
        if (!isSolvable()) return -1;
        return minMoves;
    }
    public Iterable<Board> solution()      // sequence of boards in a shortest solution; null if unsolvable
    {
        Stack<Board> solution = new Stack<>();

        while(minimum != dummy) {
            solution.push(minimum.board);
            minimum = minimum.previous;
        }

        if(!isSolvable()) return null;

        return solution;
    }
    public static void main(String[] args) // solve a slider puzzle (given below)
    {

        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }

    }
}