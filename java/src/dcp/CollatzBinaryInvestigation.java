package dcp;

import ox.Log;

public class CollatzBinaryInvestigation {

  public static void main(String... args) {
    int curr = 97;
    Log.debug("Doing collatz in binary starting at: " + curr);

    Log.debug(Integer.toBinaryString(curr));
    while (curr != 1) {
      curr = curr * 3 + 1;

      while (curr % 2 == 0) {
        curr = curr / 2;
      }

      Log.debug(Integer.toBinaryString(curr));
    }
  }
}
