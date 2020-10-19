package dailycodingproblem.puzzle8;

import java.util.List;

public class Puzzle8Graph {

  public List<Puzzle8Move> shortestPath(Puzzle8 start, Puzzle8 finish) {
    return new Puzzle8GraphSearch(this, start, finish).getShortestPath();
  }

  public List<Puzzle8Move> getEdges(Puzzle8 puzzle) {
    Tile zero = puzzle.getZero();
    return puzzle.getNeighbors(zero).map(n -> new Puzzle8Move(n, zero));
  }
}
