import java.util.ArrayList;

public class MoveTree {
  boolean isNode, isLeaf;
  private MoveTree tree1;
  private MoveTree tree2;
  private int boardEval;
  private Move move;

  public MoveTree(Move move) {
    this.move = move;
    this.isLeaf = true;
    this.isNode = false;
    //TODO evalBoard;
  }

  public MoveTree(MoveTree t1, Move move) {
    this.move = move;
    this.isNode = true;
    this.isLeaf = false;
    this.tree1 = t1;
    //TODO evalBoard;
  }

  public MoveTree(MoveTree t1, MoveTree t2, Move move) {
    this.move = move;
    this.isNode = true;
    this.isLeaf = false;
    this.tree1 = t1;
    this.tree2 = t2;
    //TODO evalBoard;
  }
}
