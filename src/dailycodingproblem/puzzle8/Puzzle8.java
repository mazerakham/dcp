package dailycodingproblem.puzzle8;

import java.util.List;

public class Puzzle8 {

  public final int[][] grid;

  public Puzzle8(int[][] grid) {
    this.grid = grid;
  }

  public static List<Move> solve(Puzzle8 puzzle) {
    Puzzle8Graph graph = new Puzzle8Graph();
    return graph.shortestPath(puzzle, Puzzle8.solved());
  }

  public static Puzzle8 solved() {
    return new Puzzle8(new int[][] { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 0 } });
  }

  public static void main(String... args) {
    Puzzle8 puzzle = new Puzzle8(new int[][] { { 2, 5, 6 }, { 4, 1, 7 }, { 3, 8, 0 } });

    List<Move> solution = solve(puzzle);
  }
}
