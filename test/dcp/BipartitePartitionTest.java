package dcp;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.google.common.base.Objects;

import ox.Pair;

public class BipartitePartitionTest {

  @Test
  public void test1() {
    Integer[][] input = new Integer[][] {
        { 1, 2 }, { 0, 3 }, { 0 }, { 1, 4, 5 }, { 3, 6 }, { 3 }, { 4 }
    };
    Pair<Integer[], Integer[]> actual = BipartitePartition.solve(input);
    assertTrue(equals(actual, new Pair<>(new Integer[] { 0, 3, 6 }, new Integer[] { 1, 2, 4, 5 })));
  }

  private boolean equals(Pair<Integer[], Integer[]> a, Pair<Integer[], Integer[]> b) {
    if (a == null) {
      return b == null;
    } else if (b == null) {
      return a == null;
    } else {
      return (Objects.equal(a.a, b.a) && Objects.equal(a.b, b.b))
          || (Objects.equal(a.a, b.b) && Objects.equal(a.b, b.a));
    }
  }
}
