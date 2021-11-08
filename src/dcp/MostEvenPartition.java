package dcp;

import static com.google.common.base.Preconditions.checkState;

import ox.Log;
import ox.Pair;
import ox.x.XList;

/**
 * Given an array of positive integers, divide the array into two subsets such that the difference between the sum of
 * the subsets is as small as possible.
 * 
 * For example, given [5, 10, 15, 20, 25], return the sets {10, 25} and {5, 15, 20}, which has a difference of 5, which
 * is the smallest possible difference.
 */
public class MostEvenPartition {

  public Pair<Integer[], Integer[]> solve(Integer[] nums) {
    XList<Integer> numsList = XList.of(nums);
    Integer sum = DCPUtils.sum(nums);
    // return solve(nums, sum/2);
    throw new UnsupportedOperationException("WIP");
  }

  // /**
  // * Memoized method returning
  // */
  // private Pair<Pair<Integer[], Integer[]>, Integer> solve(Integer[] nums, Integer desired) {
  //
  // }

  public static void main(String... args) {
    Integer[] problem = new Integer[] { 5, 10, 15, 20, 25 };
    Pair<Integer[], Integer[]> actual = new MostEvenPartition().solve(problem);
    Log.debug("Problem: " + DCPUtils.toString(problem));
    Log.debug("Expected: [10, 25], [5, 15, 20]");
    checkState(actual != null && actual.a != null && actual.b != null, "Null was returned!");
    Log.debug(String.format("Actual: %s, %s", DCPUtils.toString(actual.a), DCPUtils.toString(actual.b)));
  }

}
