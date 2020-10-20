package dailycodingproblem.puzzle8;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import com.google.common.base.Objects;

import ox.Log;
import ox.XList;

public class Puzzle8GraphSearch {

  private final Puzzle8Graph graph;
  private final Puzzle8 start;
  private final Puzzle8 finish;
  private final Map<Puzzle8, Integer> distances = new HashMap<>();
  private final Map<Puzzle8, Edge> incomingEdges = new HashMap<>();
  private final Queue<Edge> queue = new ArrayDeque<>();

  private Puzzle8 current;

  public Puzzle8GraphSearch(Puzzle8Graph graph, Puzzle8 start, Puzzle8 finish) {
    this.graph = graph;
    this.start = start;
    this.finish = finish;
  }

  public List<Edge> getShortestPath() {
    current = start;
    distances.put(current, 0);
    int i = 0;

    // As we enter a loop, current will be set to the node we are considering. We have already noted its distance from
    // the start, and its optimal incoming move.
    while (!Objects.equal(current, finish)) {
      i++;
      // Get edges emanating from current.
      List<Edge> edges = graph.getEdges(current);

      // Insert the edges into the priority queue by their value. The value of an edge (small is 'good') is defined to
      // be its weight (1) plus the heuristic of the destination.
      int currentValue = distances.get(current);
      for (Edge edge : edges) {
        Puzzle8 nextPuzzle = current.makeMove(edge.move);
        if (distances.containsKey(nextPuzzle) && distances.get(nextPuzzle) <= currentValue + 1) {
          continue;
        } else {
          distances.put(nextPuzzle, currentValue + 1);
          incomingEdges.put(nextPuzzle, edge);
          queue.add(edge);
        }
      }

      // Select the best edge.
      Edge nextEdge = getBestEdge();
      current = nextEdge.b;
    }

    Log.debug("Took %d steps to complete algorithm.", i);
    return backtrack();
  }

  // Loop through moves and return the first one which is an optimal move to the target node.
  private Edge getBestEdge() {
    int i = 0;
    do {
      Edge candidate = queue.remove();
      Edge best = incomingEdges.get(candidate.b);
      if (candidate.equals(best)) {
        return candidate;
      }
      if (i++ > 1000) {
        throw new RuntimeException("Seems to be broken");
      }
    } while (true);
  }

  private List<Edge> backtrack() {
    Puzzle8 current = finish;
    XList<Edge> ret = new XList<>();
    while (current != start) {
      Edge edge = incomingEdges.get(current);
      ret.add(edge);
      current = edge.a;
    }
    return ret.reverse();
  }

}
