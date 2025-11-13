package dcp;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import ox.Log;

/**
 * You have n fair coins and you flip them all at the same time. Any that come up tails you set aside. The ones that
 * come up heads you flip again. How many rounds do you expect to play before only one coin remains? Write a function
 * that, given n, returns the number of rounds you'd expect to play until one coin remains.
 * 
 * Usage: new CoinFlips().computeExpectedValues(n) prints out all expected values for this game up to n coins.
 * 
 * Here is an explanation + derivation of the recursive formula.
 * 
 * https://www.notion.so/jakemirra/Coin-flips-9ff5686248b540b1b89ecd491984f925
 */
public class CoinFlips {

  private final Map<Integer, Double> expectedValues = new HashMap<>();

  private double computeExpectedValue(int n) {
    if (n == 0) {
      return 0;
    }

    if (expectedValues.containsKey(n)) {
      return expectedValues.get(n);
    }

    double nFact = factorial(n);
    double factor = 1.0 / (Math.pow(2.0, n) - 1);
    double ret = factor * 1.0;
    for (int m = 0; m < n; m++) {
      double summand = factor * nFact / (factorial(m) * factorial(n - m)) * (computeExpectedValue(m) + 1.0);
      ret += summand;
    }
    expectedValues.put(n, ret);
    return ret;
  }

  public void computeExpectedValues(int n) {
    Collection<Double> vals = new ArrayList<>();
    for (int m = 0; m <= n; m++) {
      vals.add(computeExpectedValue(m));
    }
    Log.debug(vals);
  }

  public static int factorial(int k) {
    if (k == 0) {
      return 1;
    } else {
      return k * factorial(k - 1);
    }
  }

  public static void main(String... args) {
    new CoinFlips().computeExpectedValues(20);
  }
}
