package dcp;

import static com.google.common.base.Preconditions.checkState;
import static dcp.DCPUtils.printList;

import java.util.ArrayList;
import java.util.List;

import ox.Log;

public class ReverseCollatz {

  public static List<Long> run(long start) {
    long curr = start;
    long currMod6 = curr % 6;
    checkState(currMod6 != 3 && currMod6 != 0);

    List<Long> ret = new ArrayList<>();
    while (currMod6 != 3) {
      ret.add(curr);

      if (currMod6 == 4) {
        curr = (curr - 1) / 3;
      } else {
        curr = curr * 2;
      }
      currMod6 = curr % 6;
    }

    ret.add(curr);
    return ret;
  }

  public static void main(String... args) {
    List<Long> result = ReverseCollatz.run(18518L * 2);
    Log.debug(printList(result));
  }
}
