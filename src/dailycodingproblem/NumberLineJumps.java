package dailycodingproblem;

import ox.x.XList;

/**
 * Starting from 0 on a number line, you would like to make a series of jumps that lead to the integer N.
 * 
 * On the ith jump, you may move exactly i places to the left or right.
 * 
 * Find a path with the fewest number of jumps required to get from 0 to N.
 */
public class NumberLineJumps {

  /**
   * return the smallest m for which m(m+1)/2 >= n.
   */
  public static int f(int n) {
    double a = 1/2.0;
    double b = 1/2.0;
    double c = -n;
    return (int) Math.ceil((-b + Math.sqrt(b * b - 4 * a * c)) / (2 * a));
  }
  
  public static XList<Integer> getShortestPath(int n) {
    XList<Integer> ret = XList.create();
    int stepSize = f(n);
    int position = n;
    while (stepSize > 0) {
      ret.add(0, position);
      if (position > 0) {
        position -= stepSize;
      } else {
        position += stepSize;
      }
      stepSize--;
    }
    return ret;
  }
  
  public static void main(String... args) {
    DCPUtils.printList(getShortestPath(100));
  }
}
