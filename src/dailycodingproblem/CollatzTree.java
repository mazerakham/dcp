package dailycodingproblem;

import static com.google.common.base.Preconditions.checkState;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;

import ox.Log;

public class CollatzTree {

  public static void printTree(int numRows, boolean printLists, long maxValueCutoff) {
    checkState(numRows >= 1, "numRows must be a positive integer.");

    List<Integer> sizes = new ArrayList<>();
    List<Long> minima = new ArrayList<>();
    List<Long> currentList = Lists.newArrayList(8L);
    List<Long> nextList;
    int step = 1;
    while (step <= numRows) {
      if (printLists) {
        Log.debug("%s", currentList);
      }
      sizes.add(currentList.size());
      minima.add(DCPUtils.min(currentList));
      nextList = new ArrayList<>();
      for (Long num : currentList) {
        if (num % 3 == 0 || num > maxValueCutoff) {
          continue;
        }
        nextList.add(num * 2);
        if (num % 6 == 4) {
          nextList.add((num - 1) / 3);
        }
        currentList = nextList;
      }
      step++;
    }

    Log.debug("Sizes: %s", sizes);
    Log.debug("Minima: %s", minima);
  }

  public static void main(String... args) {
    printTree(150, false, 10_000_000_000L);
  }
}
