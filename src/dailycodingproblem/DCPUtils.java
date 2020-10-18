package dailycodingproblem;

import static com.google.common.base.Preconditions.checkState;

import java.util.Collection;

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
    return sb.toString();
  }

  public static long max(Long... numbers) {
    checkState(numbers.length > 0);
    long ret = Long.MIN_VALUE;
    for (long num : numbers) {
      ret = Math.max(ret, num);
    }
    return ret;
  }

  public static long min(Iterable<Long> numbers) {
    checkState(numbers.iterator().hasNext());
    long ret = Long.MAX_VALUE;
    for (long num : numbers) {
      ret = Math.min(ret, num);
    }
    return ret;
  }
}
