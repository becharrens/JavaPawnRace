/*import java.util.ArrayList;

public class Tree {
  private Node root;
  private static final int INIT_DEPTH = 6;

  public Tree(Game game){
    root = buildTree(game, game.getCurrentPlayer(), Colour.opposite(game.getCurrentPlayer()), 1);
  }

  public Tree(Integer moveEval, Move move) {
    root = new Node();
    root.moveEval = moveEval;
    root.move = move;
  }

  public static class Node {
    private Integer moveEval;
    private Move move;
    private ArrayList<Node> children;

    public void setMoveEval(Integer moveEval) {
      this.moveEval = moveEval;
    }

    private Node buildTree(Game game, Colour player, Colour opponent, int depth) {
      Integer evaluation;
      Tree child;
      ArrayList<Move> moves = game.getValidMoves(player);
      ArrayList<Tree> subTrees = new ArrayList<Tree>();
      ArrayList<Integer> evals = new ArrayList<Integer>();
      for (Move move : moves) {
        game.applyMove(move);
        if (!game.isFinished()) {
          if (depth < BUILD_SEARCH_DEPTH) {
            //Go deeper
          } else {
            //
          }
        } else {
          //TODO evaluation = game.evaluateBoard();
          subTrees.add(new MoveTree(move, true, evaluation));
        }
      }
      return new Tree();//TODO
    }
  }

  private static ArrayList<Tree> getLeafs(Game game, Colour player){
    ArrayList<Move> moves = game.getValidMoves(player);
    ArrayList<Tree> leaves = new ArrayList<Tree>();
    for (Move move : moves){
      game.applyMove(move);
      leaves.add(new Tree(game.evaluateBoard(), move));
      game.unApplyMove();
    }
    return leaves;
  }
}
*/