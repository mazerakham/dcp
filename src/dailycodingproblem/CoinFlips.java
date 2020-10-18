package dailycodingproblem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import ox.Log;

public class CoinFlips {

  private final Map<Integer, Double> expectedValues = new HashMap<>();

  private double computeExpectedValue(int n) {
    if (n == 13) {
      Log.debug("We are here.");
    }
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
      return factorial(k - 1, k);
    }
  }

  private static int factorial(int k, int coll) {
    if (k == 0) {
      return coll;
    } else {
      return factorial(k - 1, coll * k);
    }
  }

  public static void main(String... args) {
    Log.debug(factorial(5));
    new CoinFlips().computeExpectedValues(20);
  }
}
