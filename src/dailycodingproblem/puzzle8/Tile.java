package dailycodingproblem.puzzle8;

import java.util.List;

import com.google.common.collect.ImmutableList;

import ox.XList;

public class Tile {

  private static final List<Tile> all;

  private static final XList<TileDelta> deltas;

  static {
    ImmutableList.Builder<Tile> builder = ImmutableList.builder();
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        builder.add(new Tile(i, j));
      }
    }
    all = builder.build();
    deltas = XList.of(new TileDelta(1, 0), new TileDelta(0, 1), new TileDelta(-1, 0), new TileDelta(0, -1));
  }

  public final int x, y;

  public Tile(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public boolean isInBounds() {
    return 0 <= x && x < 3 && 0 <= y && y < 3;
  }

  public Tile add(TileDelta delta) {
    return new Tile(this.x + delta.dx, this.y + delta.dy);
  }

  public static List<Tile> all() {
    return all;
  }

  public static XList<TileDelta> deltas() {
    return deltas;
  }
}
