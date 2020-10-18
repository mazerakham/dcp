package dailycodingproblem;

import static dailycodingproblem.DCPUtils.max;
import static dailycodingproblem.DCPUtils.printList;

import java.util.List;

import com.google.common.collect.Lists;

import ox.Log;

public class LargestNonAdjacentSum {

  public static long run(List<Long> nums) {
    // The following invariants will be maintained at the END of each iteration.

    // The index of the number we're about to consider, immediately after the last number we considered.
    int currentIndex = 0;

    // max using all numbers to the left of the current number but NOT the previous number.
    long freeMax = 0;

    // max using all numbers to the left, possibly including the previous number.
    long encumberedMax = 0;

    while (currentIndex < nums.size()) {
      long lastFreeMax = freeMax;
      freeMax = encumberedMax;
      encumberedMax = max(lastFreeMax, lastFreeMax + nums.get(currentIndex), encumberedMax);
      currentIndex++;
    }

    return encumberedMax;
  }

  public static void main(String... args) {
    List<Long> example = Lists.newArrayList(-1L, 2L, 16L, 4L, 6L, 2L, 5L, 10L);
    long result = run(example);

    Log.debug(printList(example));
    Log.debug("result: %d", result);
  }
}
