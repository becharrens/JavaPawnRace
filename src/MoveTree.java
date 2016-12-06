//Pre: Generating moves requires that the root's game current player is computer and the last move
//you will generate will be the opponent's
import java.util.ArrayList;

public class MoveTree {
  boolean isLeaf;
  private ArrayList<MoveTree> moveTree;
  private int boardEval;
  private Move move;
  private boolean isFinal;
  private static Colour cpu, opp;
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

  public MoveTree(ArrayList<MoveTree> moveTree, Move move, boolean searchMin) {
    this.move = move;
    this.isLeaf = false;
    this.moveTree = moveTree;
    this.boardEval = minMax(moveTree, searchMin);
  }

  private static MoveTree buildTree(Colour player, Colour opponent, int depth, int maxDepth) {
    boolean searchMin = opp == player;
    ArrayList<Move> moves = game.getValidMoves(player);
    ArrayList<MoveTree> subTrees = new ArrayList<MoveTree>();
    for (Move move : moves) {
      game.applyMove(move);
      if (!game.isFinished()) {
        if(depth < maxDepth) {
          subTrees.add(buildTree(opponent, player, depth + 1, maxDepth));
        } else {
          subTrees.add(new MoveTree(getLeafs(opponent), move, searchMin));
        }
      } else {
        subTrees.add(new MoveTree(move, true, game.evaluateBoard(cpu, opp))); //TODO
      }
      game.unApplyMove();
    }
    return new MoveTree(subTrees, game.getLastMove(), searchMin);
  }

  //Need to update heuristic values as I exit recursion;
  //Returns an int, be careful with function call;
  private static void extendTree(MoveTree tree){
    Colour player = Colour.NONE;
    for (int i = 0; i < tree.moveTree.size(); i++){
      if (!tree.moveTree.get(i).isFinal){
        game.applyMove(tree.moveTree.get(i).move);
        player = game.getCurrentPlayer();
        if (tree.moveTree.get(i).isLeaf){
          //extend by 2 moves, 1 from each player.
          tree.moveTree.set(i, buildTree(player, Colour.opposite(player), 1, 1));
        } else {
          extendTree(tree.moveTree.get(i));
        }
        game.unApplyMove();
      }
    }
//    Inefficient. Try to implement minmax algorithm as you go in the recursion. Useful for alpha beta prawning
    tree.boardEval = minMax(tree.moveTree, player == opp);
  }

  public static void updateTree(MoveTree tree){
    for (MoveTree subTree : tree.moveTree) {
      if (subTree.move == tree.move) {
        game.applyMove(subTree.move);
      }
    }
  }

  public static void updateTree(Move move, MoveTree tree){
    for (MoveTree subTree : tree.moveTree) {
      if (subTree.move == move) {
        tree = subTree;
        break;
      }
    }
  }

  public static MoveTree buildMoveTree(Game game, int maxDepth){
    setGame(game);
    cpu = game.getCurrentPlayer();
    opp = Colour.opposite(cpu);
    return buildTree(cpu, opp, 1, maxDepth);
  }

  private static ArrayList<MoveTree> getLeafs(Colour player) {
    ArrayList<Move> moves = game.getValidMoves(player);
    ArrayList<MoveTree> leaves = new ArrayList<MoveTree>();
    boolean isFinal = false;
    for (Move move : moves) {
      game.applyMove(move);
      if (game.isFinished()) isFinal = true;
      leaves.add(new MoveTree(move, isFinal, game.evaluateBoard(cpu, opp)));
      game.unApplyMove();
    }
    return leaves;
  }

  private static int minMax(ArrayList<MoveTree> moveTree, boolean searchMin){
    int boardEval;
    if (searchMin){
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
