import java.util.HashSet;
import java.util.Set;

public class Solver {

    private int M = 0;

    private MinPQ<SearchNode> boards = new MinPQ<>();
    private Set<Board> minima = new HashSet<>();
    private Stack<SearchNode> solution = new Stack<>();
    private int minMoves;


    private class SearchNode implements Comparable<SearchNode>{
        Board board;
        SearchNode previous;
        int priority;
        int moves;

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
        SearchNode dummy = new SearchNode(initial, 0, null);

        boards.insert(new SearchNode(initial, 0, dummy));

        SearchNode minimum = new SearchNode(initial, 0, dummy);

        while (!minimum.board.isGoal()) {
            minimum = boards.delMin();

            M++;

            for (Board neighbor : minimum.board.neighbors()) {
                if (!neighbor.equals(minimum.previous.board)) {
                    boards.insert(new SearchNode(neighbor, minimum.moves+1, minimum));
                }
            }
        }

        minMoves = minimum.moves;

        while(minimum != dummy) {
            solution.push(minimum);
            minimum = minimum.previous;
        }


    }

//    public boolean isSolvable()            // is the initial board solvable?
    public int moves()                     // min number of moves to solve initial board; -1 if unsolvable
    {
        return minMoves;
    }
//    public Iterable<Board> solution()      // sequence of boards in a shortest solution; null if unsolvable
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
        System.out.println(solver.moves());

        for (SearchNode searchNode : solver.solution) {
            System.out.println(searchNode.board.toString());
        }
    }
}