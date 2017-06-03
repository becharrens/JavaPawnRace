import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Player {
  private static Game game;
  private Colour colour;
  private Colour opponent;
  private boolean computerPlayer;
//  private static MoveTree moveTree;
  private static int maxDepth = 6;
  private static int idx = 0;


  public Player(Game game, Colour colour, boolean computerPlayer, Board board) {
    //colour must be Black or White;
    this.game = game;
    this.colour = colour;
    this.opponent = Colour.opposite(colour);
    this.computerPlayer = computerPlayer;
//    if (computerPlayer && moveTree == null) moveTree = MoveTree.buildMoveTree(game, 5);
  }

  public Colour getColour() {
    return colour;
  }

  public Colour getOpponent() {
    return opponent;
  }

  public boolean isComputerPlayer() {
    return computerPlayer;
  }

  private ArrayList<Move> getAllValidMoves(){
    return game.getValidMoves(colour);
  }

  public void applyMove(){
    if (computerPlayer){
//      ArrayList<Move> moves = getAllValidMoves();
//      game.applyMove(moves.get(new Random().nextInt(moves.size())));
//      moveTree = MoveTree.applyMove(moveTree);
      game.applyMove(getComputerMove(maxDepth,
          Integer.MIN_VALUE, Integer.MAX_VALUE, colour).getMove());
//      maxDepth++;
    } else {
      System.out.println("Enter your move: ");
      String san = IOUtil.readString().trim();
      Move move = game.parseMove(san);
      while (move == null) {
        System.out.println("Error: Invalid move. Please enter a valid move: ");
        san = IOUtil.readString();
        move = game.parseMove(san);
      }
      game.applyMove(move);
//      if (moveTree != null) moveTree = MoveTree.updateTree(move, moveTree);
    }
    if (game.getCurrentPlayer() == Colour.BLACK && idx % 3 == 2) {
      maxDepth++;
    }
    idx++;
  }

  public MoveEvalPair getComputerMove(int depth, int alpha, int beta, Colour colour) {
    //Pre: depth >= 0 && colour != NONE
    if (depth == 0 || game.isFinished()) {
      return new MoveEvalPair(game.getLastMove(), game.evaluateBoard());
    }
    MoveEvalPair moveEvalPair;
    Move candidateMove = null;
    List<Move> moves = game.getValidMoves(colour);
    if (colour == Colour.WHITE) {
      for (Move move : moves) {
        game.applyMove(move);
        moveEvalPair = getComputerMove(depth - 1,
            alpha, beta, Colour.opposite(colour));
//        alpha = Math.max(alpha, moveEvalPair.getEval());
        if (alpha < moveEvalPair.getEval()) {
          candidateMove = move;
          alpha = moveEvalPair.getEval();
        }
        game.unApplyMove();
        if (beta <= alpha) {
          break;
        }
      }
      return new MoveEvalPair(candidateMove, alpha);
    } else {
      for (Move move : moves) {
        game.applyMove(move);
        moveEvalPair = getComputerMove(depth - 1,
            alpha, beta, Colour.opposite(colour));
//        beta = Math.min(beta, moveEvalPair.getEval());
        if (beta > moveEvalPair.getEval()) {
          candidateMove = move;
          beta = moveEvalPair.getEval();
        }
        game.unApplyMove();
        if (beta <= alpha) {
          break;
        }
      }
      return new MoveEvalPair(candidateMove, beta);
    }
  }

//  public static void extendMoveTree(){
//    MoveTree.extendTree(moveTree);
//  }
}
