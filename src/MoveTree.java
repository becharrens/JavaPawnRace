//Pre: Generating moves requires that the root's game current player is computer and the last move
//you will generate will be the opponent's
import java.util.ArrayList;

public class MoveTree {
  boolean isLeaf;
  private ArrayList<MoveTree> moveTree;
  private int boardEval;
  private Move move;
  private boolean isFinal;
  //private static Colour cpu, opp;
  //private static int BUILD_SEARCH_DEPTH = 6; // 3 moves ahead, 3 for each player
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

  public MoveTree(ArrayList<MoveTree> moveTree, Move move) {
    this.move = move;
    this.isLeaf = false;
    this.moveTree = moveTree;
    this.boardEval = minMax(moveTree);
  }

  private static MoveTree buildTree(int depth, int maxDepth) {
    //boolean searchMin = opp == player;
    Colour player = game.getCurrentPlayer();
    ArrayList<Move> moves = game.getValidMoves(player);
    ArrayList<MoveTree> subTrees = new ArrayList<MoveTree>();
    int boardEval;
    if (player == Colour.BLACK){
      boardEval = Integer.MIN_VALUE;
    } else {
      boardEval = Integer.MAX_VALUE;
    }
    for (Move move : moves) {
      game.applyMove(move);
      if (!game.isFinished()) {
        if(depth < maxDepth) {
          subTrees.add(buildTree(depth + 1, maxDepth));
        } else {
          subTrees.add(new MoveTree(getLeafs(Colour.opposite(player)), move));
        }
      } else {
        subTrees.add(new MoveTree(move, true, game.evaluateBoard())); //TODO
      }

      game.unApplyMove();
      if (player == Colour.BLACK){
        boardEval = Math.max(boardEval, subTrees.get(subTrees.size() - 1).boardEval);
      } else {
        boardEval = Math.min(boardEval, subTrees.get(subTrees.size() - 1).boardEval);
      }
    }
    return new MoveTree(subTrees, game.getLastMove());
  }

  //Need to update heuristic values as I exit recursion;
  //Returns an int, be careful with function call;
  public static void extendTree(MoveTree tree){
    Colour player = game.getCurrentPlayer();
    int boardEval;
    if (player == Colour.BLACK){
      boardEval = Integer.MIN_VALUE;
    } else {
      boardEval = Integer.MAX_VALUE;
    }
    for (int i = 0; i < tree.moveTree.size(); i++){
      if (!tree.moveTree.get(i).isFinal){
        game.applyMove(tree.moveTree.get(i).move);
        player = game.getCurrentPlayer();
        if (tree.moveTree.get(i).isLeaf){
          //extend by 2 moves, 1 from each player.
          tree.moveTree.set(i, buildTree(1, 1));
        } else {
          extendTree(tree.moveTree.get(i));
        }
        game.unApplyMove();
        if (player == Colour.BLACK){
          boardEval = Math.max(boardEval, tree.moveTree.get(i).boardEval);
        } else {
          boardEval = Math.min(boardEval, tree.moveTree.get(i).boardEval);
        }
      }
    }
//    Inefficient. Try to implement minmax algorithm as you go in the recursion. Useful for alpha beta prawning
    tree.boardEval = boardEval;
  }

  public static MoveTree applyMove(MoveTree tree){
    for (MoveTree subTree : tree.moveTree) {
      if (subTree.boardEval == tree.boardEval) {
        game.applyMove(subTree.move);
        return subTree;
      }
    }
    return null;
  }

  public static MoveTree updateTree(Move move, MoveTree tree){
    for (MoveTree subTree : tree.moveTree) {
      if (subTree.move == move) {
        return subTree;
      }
    }
    return null;
  }

  public static MoveTree buildMoveTree(Game game, int maxDepth){
    setGame(game);
    return buildTree(1, maxDepth);
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

  private static int minMax(ArrayList<MoveTree> moveTree){
    int boardEval;
    if (game.getCurrentPlayer() == Colour.BLACK){
      boardEval = Integer.MAX_VALUE;
      for (int i = 0; i < moveTree.size(); i++){
        int eval = moveTree.get(i).boardEval;
        if (eval < boardEval) boardEval = eval;
      }
    } else {
      boardEval = Integer.MIN_VALUE;
      for (int i = 0; i < moveTree.size(); i++){
        int eval = moveTree.get(i).boardEval;
        if (eval > boardEval) boardEval = eval;
      }
    }
    return boardEval;
  }

//  public static void switchPlayers(){
//    Colour tmp = cpu;
//    cpu = opp;
//    opp = tmp;
//  }
}
