package dcp;

import java.util.Collection;
import java.util.function.BiFunction;

import ox.x.XList;

public class DCPUtils {

  public static <T> String toString(T[] items) {
    return toString(XList.of(items));
  }

  public static <T> String toString(Collection<T> items) {
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
    return sb.toString();
  }

  public static Integer sum(Integer[] numbers) {
    return DCPUtils.<Integer, Integer>reduce(numbers, 0, (a, b) -> a + b);
  }

  public static Long min(Long[] numbers) {
    return reduce(numbers, Long.MAX_VALUE, (a, b) -> Math.min(a, b));
  }

  public static long max(Long[] numbers) {
    return reduce(numbers, Long.MIN_VALUE, (a,b) -> Math.max(a, b));
  }

  private static <S, T> T reduce(S[] items, T initialVal, BiFunction<T, S, T> reducer) {
    T current = initialVal;
    for (S item : items) {
      current = reducer.apply(current, item);
    }
    return current;
  }
}
