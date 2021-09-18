package dcp.puzzle8;

import java.util.List;

public class Puzzle8Graph {

  public List<Edge> shortestPath(Puzzle8 start, Puzzle8 finish) {
    return new Puzzle8GraphSearch(this, start, finish).getShortestPath();
  }

  public List<Edge> getEdges(Puzzle8 puzzle) {
    Tile zero = puzzle.getZero();

    return puzzle.getNeighbors(zero).map(n -> new Puzzle8Move(n, zero))
        .map(move -> new Edge(puzzle, puzzle.makeMove(move), move));
  }
}
