package dcp;

import static dcp.DCPUtils.printList;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;

import ox.Log;
import ox.util.Utils;

public class ProductArray {

  public static List<Integer> computeProductArray(List<Integer> ints) {
    // base cases.
    if (ints.size() == 0) {
      return new ArrayList<>();
    } else if (ints.size() == 1) {
      return Lists.newArrayList(1);
    }

    List<Integer> partial = computeProductArray(ints.subList(0, ints.size() - 1));
    List<Integer> ret = Lists.newArrayList(partial);

    // Deal with all except the last index.
    int last = Utils.last(ints);
    for (int i = 0; i < ret.size(); i++) {
      ret.set(i, ret.get(i) * last);
    }
    
    // Deal with the last index.
    ret.add(partial.get(0) * ints.get(0));

    return ret;
  }

  public static void testProductArray(Integer... ints) {
    List<Integer> intsList = Lists.<Integer>newArrayList(ints);
    List<Integer> productArray = ProductArray.computeProductArray(Lists.<Integer>newArrayList(ints));
    Log.debug("Given %s", printList(intsList));
    Log.debug("Result: %s", printList(productArray));
  }

  public static void main(String... args) {
    Log.debug(printList(Lists.newArrayList(1, 2, 3)));
    Log.debug(printList(Lists.newArrayList()));
    testProductArray(1, 2, 3, 4, 5);
  }
}
