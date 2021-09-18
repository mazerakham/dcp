package dcp;

import java.util.Map;

import ox.Log;
import ox.x.XMap;

/**
 * Solve this problem: There exists a staircase with N steps, and you can climb up either 1 or 2 steps at a time. Given
 * N, write a function that returns the number of unique ways you can climb the staircase. The order of the steps
 * matters.
 * 
 * For example, if N is 4, then there are 5 unique ways:
 * 
 * 1, 1, 1, 1 <br/>
 * 2, 1, 1 <br/>
 * 1, 2, 1 <br/> 
 * 1, 1, 2 <br/>
 * 2, 2 <br/>
 * 
 * What if, instead of being able to climb 1 or 2 steps at a time, you could climb any number from a set of positive
 * integers X? For example, if X = {1, 3, 5}, you could climb 1, 3, or 5 steps at a time.
 *
 */
public class StaircaseStepsProblem {

  private int solve(int n, Map<Integer, Integer> memo) {
    if (n == 0 || n == 1) {
      return 1;
    } else if (memo.containsKey(n)) {
      return memo.get(n);
    } else {
      int ret = solve(n - 1, memo) + solve(n - 2, memo);
      memo.put(n, ret);
      return ret;
    }
  }
  
  public int solve(int n) {
    XMap<Integer, Integer> memo = XMap.create();
    return solve(n, memo);
  }
  
  public static void main(String... args) {
    int actual = new StaircaseStepsProblem().solve(4);
    Log.debug("Expected: " + 5 + ", Actual: " + actual);
  }
}
