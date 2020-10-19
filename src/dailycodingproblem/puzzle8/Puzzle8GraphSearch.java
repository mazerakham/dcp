package dailycodingproblem.puzzle8;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import com.google.common.base.Objects;

public class Puzzle8GraphSearch {

  private final Puzzle8Graph graph;
  private final Puzzle8 start;
  private final Puzzle8 finish;
  private final Map<Puzzle8, Integer> distances = new HashMap<>();
  private final Map<Puzzle8, Puzzle8Move> incomingMoves = new HashMap<>();
  private final Queue<Puzzle8Move> queue = new ArrayDeque<>();

  private Puzzle8 current;

  public Puzzle8GraphSearch(Puzzle8Graph graph, Puzzle8 start, Puzzle8 finish) {
    this.graph = graph;
    this.start = start;
    this.finish = finish;
  }

  public List<Puzzle8Move> getShortestPath() {
    current = start;
    distances.put(current, 0);

    // As we enter a loop, current will be set to the node we are considering. We have already noted its distance from
    // the start, and its optimal incoming move.
    while (!Objects.equal(current, finish)) {
      // Get edges emanating from current. Discard any which are *strictly worse*.
      List<Puzzle8Move> legalMoves = graph.getEdges(current);

      // Insert the edges into the priority queue by their value. The value of an edge (small is 'good') is defined to
      // be its weight (1) plus the heuristic of the destination.
      int currentValue = distances.get(current);
      for (Puzzle8Move move : legalMoves) {
        Puzzle8 nextPuzzle = current.makeMove(move);
        if (distances.containsKey(nextPuzzle) && distances.get(nextPuzzle) <= currentValue + 1) {
          continue;
        } else {
          distances.put(nextPuzzle, currentValue + 1);
          queue.add(move);
        }
      }

      // Select the best edge. Traverse it, noting the distance from the terminus to the start, as well as its incoming
      // move.
      Puzzle8Move nextMove = queue.remove();

    }
  }

}
