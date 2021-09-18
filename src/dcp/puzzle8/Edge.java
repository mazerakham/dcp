package dcp.puzzle8;

import ox.Log;

public class Edge {

  public final Puzzle8 a;
  public final Puzzle8 b;
  public final Puzzle8Move move;

  public Edge(Puzzle8 a, Puzzle8 b, Puzzle8Move move) {
    this.a = a;
    this.b = b;
    this.move = move;
  }

  @Override
  public String toString() {
    return new Integer(a.get(move.a)).toString();
  }

  public void prettyPrint() {
    Log.debug("[" + a + ", " + b + ", " + move + "]");
  }
}
