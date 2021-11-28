package dcp;

import static com.google.common.base.Preconditions.checkState;

import ox.Pair;
import ox.x.XMap;

public class BipartitePartition {

  
  public static Pair<Integer[], Integer[]> solve(Integer[][] adjMatrix) {
    XMap<Integer, Integer> assignments = XMap.create();
    int n = adjMatrix.length;
    for (Integer i = 0; i < n; i++) {
      if (assignments.containsKey(i)) {
        continue;
      } else {
        assignments.put(i, 0);
        if (visit(i, assignments, adjMatrix)) {
          continue;
        } else {
          return null;
        }
      }
    }
    
    XList<Integer.>
  }
  
  private static boolean visit(Integer i, XMap<Integer, Integer> assignments, Integer[][] adjMatrix) { 
    checkState(assignments.containsKey(i));
    Integer assignment = assignments.get(i);
    Integer other = assignment == 0 ? 1 : 0;
    for (Integer j : adjMatrix[i]) {
      if (!assignments.containsKey(j)) {
        assignments.put(j, other);
        return visit(j, assignments, adjMatrix);
      } else if (assignments.get(j) == other) {
        continue;
      } else {
        return false;
      }
    }
    return true;
  }
}
