package dcp.puzzle8;

public class Puzzle8Move {

  // The piece.
  public final Tile a;

  // The empty slot next to the piece.
  public final Tile b;

  public Puzzle8Move(Tile a, Tile b) {
    this.a = a;
    this.b = b;
  }

  @Override
  public String toString() {
    return "[" + a.x + "," + a.y + "]";
  }
}
