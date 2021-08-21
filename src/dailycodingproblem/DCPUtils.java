package dailycodingproblem;

import static com.google.common.base.Preconditions.checkState;

import java.util.Collection;
import java.util.function.BiFunction;

import ox.Log;

public class DCPUtils {

  public static <T> String printList(Collection<T> items) {
    if (items.size() == 0) {
      return "[]";
    }

    StringBuilder sb = new StringBuilder();
    sb.append('[');
    items.forEach(item -> {
      sb.append(item + ", ");
    });
    sb.delete(sb.length() - 2, sb.length());
    sb.append(']');
    String ret = sb.toString();
    Log.debug(ret);
    return sb.toString();
  }

  public static long min(Long[] numbers) {
    return min((a,b) -> a - b, numbers);
  }
  
  public static long max(Long[] numbers) {
    return min((a,b) -> b - a, numbers);
  }
  
  private static long min(BiFunction<Long,Long,Long> comparator, Long[] numbers) {
    checkState(numbers.length > 0);
    Long currMin = numbers[0];
    for (int i = 1; i < numbers.length; i++) {
      if (comparator.apply(currMin, numbers[i]) < 0) {
        currMin = numbers[i];
      }
    }
    return currMin;
  }
}
