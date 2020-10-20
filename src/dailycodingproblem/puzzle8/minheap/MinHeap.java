package dailycodingproblem.puzzle8.minheap;

import static com.google.common.base.Preconditions.checkState;

import java.util.Comparator;

import ox.Log;
import ox.Pair;

public class MinHeap<T> {

  private final Comparator<T> comparator;
  
  private Node<T> root;

  public MinHeap(Comparator<T> comparator) {
    this.comparator = comparator;
  }
  
  public void insert(T val) {
    if (root == null) {
      root = new Node<>(val);
    } else {
      insert(root, val);
    }
  }

  private void insert(Node<T> node, T val) {
    T passdown;
    if (comparator.compare(val, node.val) < 0) {
      passdown = node.val;
      node.val = val;
    } else {
      passdown = val;
    }

    if (node.left == null) {
      node.left = new Node<>(passdown);
      return;
    } else if (node.right == null) {
      node.right = new Node<>(passdown);
      return;
    }

    int leftSize = size(node.left), rightSize = size(node.right);
    if (leftSize <= rightSize) {
      insert(node.left, passdown);
    } else {
      insert(node.right, passdown);
    }
  }

  public T remove() {
    checkState(!isEmpty());
    Pair<T, Node<T>> result = remove(root);
    root = result.b;
    balance();
    return result.a;
  }

  public T peek() {
    return peek(root);
  }

  private T peek(Node<T> node) {
    return node == null ? null : node.val;
  }

  /**
   * Gets the min node in the subtree, along with the subtree that results from removing that node from the subtree.
   */
  private Pair<T, Node<T>> remove(Node<T> node) {
    T ret = node.val;
    Node<T> left = node.left;
    Node<T> right = node.right;

    if (left == null && right == null) {
      return new Pair<>(ret, null);
    } else if (left != null && right == null) {
      Pair<T, Node<T>> result = remove(left);
      Node<T> newSubtree = new Node<>(result.a);
      newSubtree.left = result.b;
      return new Pair<>(ret, newSubtree);
    } else if (left == null && right != null) {
      Pair<T, Node<T>> result = remove(right);
      Node<T> newSubtree = new Node<>(result.a);
      newSubtree.right = result.b;
      return new Pair<>(ret, newSubtree);
    } else {
      T leftMin = peek(node.left);
      T rightMin = peek(node.right);
      if (comparator.compare(leftMin, rightMin) <= 0) {
        Pair<T, Node<T>> result = remove(left);
        Node<T> newSubtree = new Node<>(result.a);
        newSubtree.left = result.b;
        newSubtree.right = node.right;
        return new Pair<>(ret, newSubtree);
      } else {
        Pair<T, Node<T>> result = remove(right);
        Node<T> newSubtree = new Node<>(result.a);
        newSubtree.right = result.b;
        newSubtree.left = node.left;
        return new Pair<>(ret, newSubtree);
      }
    }
  }

  public void balance() {
    balance(root);
  }

  private void balance(Node<T> node) {
    if (node == null) {
      return;
    }

    int leftSize = size(node.left), rightSize = size(node.right);

    if (leftSize >= rightSize + 2) {
      Pair<T, Node<T>> result = remove(node.left);
      node.left = result.b;
      insert(node.right, result.a);
    } else if (rightSize >= leftSize + 2) {
      Pair<T, Node<T>> result = remove(node.right);
      node.right = result.b;
      insert(node.left, result.a);
    } else {
      return;
    }
  }

  private int size(Node<T> node) {
    if (node == null) {
      return 0;
    } else {
      return 1 + size(node.left) + size(node.right);
    }
  }

  public boolean isEmpty() {
    return root == null;
  }

  public void prettyPrint() {
    prettyPrint(root, 0);
  }

  private void prettyPrint(Node<T> node, int lvl) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < lvl; i++) {
      sb.append(' ');
    }

    if (node == null) {
      sb.append("[empty]");
      Log.debug(sb.toString());
      return;
    }

    sb.append(node.val.toString());
    Log.debug(sb.toString());
    prettyPrint(node.left, lvl + 1);
    prettyPrint(node.right, lvl + 1);
  }

  public static void main(String... args) {
    MinHeap<Integer> heap = new MinHeap<>((a, b) -> a - b);

    heap.insert(5);
    heap.insert(1);
    heap.insert(3);
    heap.insert(8);
    heap.insert(9);
    heap.insert(10);
    heap.insert(15);
    heap.insert(12);

    heap.prettyPrint();
    Log.debug("");
    Log.debug(heap.remove());
    Log.debug("");
    heap.prettyPrint();
    Log.debug("");
    Log.debug(heap.remove());
    Log.debug("");
    heap.prettyPrint();
    Log.debug("");
  }

}
