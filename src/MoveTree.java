//Pre: Generating moves requires that the root's game current player is computer and the last move
//you will generate will be the opponent's
import java.util.ArrayList;

public class MoveTree {
  boolean isNode, isLeaf;
  private ArrayList<MoveTree> moveTree;
  private int boardEval;
  private Move move;
  private boolean isFinal;
  private static final int BUILD_SEARCH_DEPTH = 6; // 3 moves ahead, 3 for each player
  private static Game game;

  public static void setGame(Game game) {
    MoveTree.game = game;
  }

  public MoveTree(Move move, boolean isFinal, int boardEval) {
    this.move = move;
    this.isLeaf = true;
    this.isFinal = isFinal;
    this.boardEval = boardEval;
  }

  public MoveTree(ArrayList<MoveTree> moveTree, Move move, boolean searchMin) {
    this.move = move;
    this.isNode = true;
    this.moveTree = moveTree;
    int boardEval = Integer.MIN_VALUE;
    for (int i = 0; i < moveTree.size(); i++){
      int eval = moveTree.get(i).boardEval;
      if (searchMin) eval = -eval;
      boardEval = Math.max(boardEval, eval);
    }
    if (searchMin) boardEval = -boardEval;
    this.boardEval = boardEval;
  }

  private static MoveTree buildTree(Colour player, Colour opponent, int depth) {
    boolean searchMin = depth % 2 != 0;
    ArrayList<Move> moves = game.getValidMoves(player);
    ArrayList<MoveTree> subTrees = new ArrayList<MoveTree>();
    for (Move move : moves) {
      game.applyMove(move);
      if (!game.isFinished()) {
        if(depth < BUILD_SEARCH_DEPTH) {
          subTrees.add(buildTree(opponent, player, depth + 1));
        } else {
          subTrees.add(new MoveTree(getLeafs(opponent), move, searchMin));
        }
      } else {
        subTrees.add(new MoveTree(move, true, game.evaluateBoard())); //TODO
      }
      game.unApplyMove();
    }
    return new MoveTree(subTrees, game.getLastMove(), searchMin);
  }

  private static ArrayList<MoveTree> getLeafs(Colour player) {
    ArrayList<Move> moves = game.getValidMoves(player);
    ArrayList<MoveTree> leaves = new ArrayList<MoveTree>();
    boolean isFinal = false;
    for (Move move : moves) {
      game.applyMove(move);
      if (game.isFinished()) isFinal = true;
      leaves.add(new MoveTree(move, isFinal, game.evaluateBoard()));
      game.unApplyMove();
    }
    return leaves;
  }

  //public static minMax(idx)
}
