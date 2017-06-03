import java.util.ArrayList;
import java.util.List;

public class Tree {
  private final Node tree;

  public Tree(Board board, int maxDepth) {
    tree = createTree(board, maxDepth);
  }

  private static Node createTree(Board board, int maxDepth) {

    return null;
  }

  private class Node {
    private final Move move;
    private final List<Node> children;
    private int eval;

    private Node(Move move) {
      this.move = move;
      this.children = new ArrayList<>();
    }

    public void addChild(Node child) {
      children.add(child);
    }

    public int numChildren() {
      return children.size();
    }

    public Node getChild(int childIdx) {
      //Pre: childIdx in range
      return children.get(childIdx);
    }

    public int getEval() {
      return eval;
    }

    public void setEval(int eval) {
      this.eval = eval;
    }
  }
}
