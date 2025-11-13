package dcp.puzzle8.minheap;

public class Node<T> {

  public T val;

  public Node<T> left = null;

  public Node<T> right = null;

  public Node(T t) {
    this.val = t;
  }
}
