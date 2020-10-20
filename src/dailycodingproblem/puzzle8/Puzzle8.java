package dailycodingproblem.puzzle8;

import static ox.util.Functions.map;

import java.util.List;

import com.google.common.base.Objects;

import ox.Log;
import ox.XList;

public class Puzzle8 {

  public final int[][] grid;

  public Puzzle8(Puzzle8 that) {
    int[][] copyGrid = new int[3][3];
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        copyGrid[i][j] = that.grid[i][j];
      }
    }
    this.grid = copyGrid;
  }

  public Puzzle8(int[][] grid) {
    this.grid = grid;
  }

  public Tile getZero() {
    for (Tile tile : Tile.all()) {
      if (Objects.equal(0, this.get(tile))) {
        return tile;
      }
    }
    throw new IllegalStateException("Not zero tile.");
  }

  public Puzzle8 makeMove(Puzzle8Move move) {
    Puzzle8 ret = new Puzzle8(this);
    ret.set(move.b, get(move.a));
    ret.set(move.a, 0);
    return ret;
  }

  public int get(Tile tile) {
    return grid[tile.x][tile.y];
  }

  public void set(Tile tile, int val) {
    grid[tile.x][tile.y] = val;
  }

  public XList<Tile> getNeighbors(Tile tile) {
    return Tile.deltas().map(delta -> tile.add(delta)).filter(Tile::isInBounds);
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Puzzle8)) {
      return false;
    } else {
      return this.toString().equals(o.toString());
    }
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(this.toString());
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append('{');
    for (int i = 0; i < 3; i++) {
      int[] row = grid[i];
      sb.append("{" + row[0] + ", " + row[1] + ", " + row[2] + "}");
    }
    sb.append('}');
    return sb.toString();
  }

  public static List<Puzzle8Move> solve(Puzzle8 puzzle) {
    Puzzle8Graph graph = new Puzzle8Graph();
    List<Edge> path = new Puzzle8GraphSearch(graph, puzzle, Puzzle8.solved()).getShortestPath();
    Log.debug(path);
    return map(path, e -> e.move);
  }

  public static Puzzle8 solved() {
    return new Puzzle8(new int[][] { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 0 } });
  }

  public static void main(String... args) {
    Puzzle8 puzzle = new Puzzle8(new int[][] { { 5, 7, 1 }, { 6, 4, 8 }, { 2, 3, 0 } });
    List<Puzzle8Move> solution = solve(puzzle);

  }
}
