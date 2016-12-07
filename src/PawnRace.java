import java.util.HashMap;
import java.util.Random;

public class PawnRace {
  public static void main(String[] args) {
//    Board board = new Board('B', 'B');
//    board.display();
//
//    Move move = new Move(board.getSquare(6,5).clone(), board.getSquare(7,3).clone(), false, false);
//    board.applyMove(move);
//    board.display();
//    board.unApplyMove(move);
//    board.display();


    //Complete
    System.out.println("Welcome to the Pawn Race program");
    String answer = yesNoInput("Will the Player 1 be human? (y/n)");
    Colour colour1 = getColour(), colour2 = Colour.opposite(colour1);
    boolean isComp1, isComp2;
    Board board;
    isComp1 = answer.equals("n");
    answer = yesNoInput("Will the Player 2 be human? (y/n)");
    isComp2 = answer.equals("n");
    if (colour1 == Colour.BLACK) {
      board = getBoard(isComp1);
    } else {
      board = getBoard(isComp2);
    }
    Game game = new Game(board);
    Player player1 = new Player(game, colour1, isComp1, board);
    Player player2 = new Player(game, colour2, isComp2, board);
    HashMap<Colour, Player> players = new HashMap<Colour, Player>();
    players.put(colour1, player1);
    players.put(colour2, player2);
    Colour key = Colour.WHITE;
    board.display();
    MoveTree t = MoveTree.buildMoveTree(game, 1);
    while (!game.isFinished()){
      players.get(key).applyMove();
      System.out.println(Colour.print(players.get(key).getColour()) +
              players.get(key).getColour().toString().substring(1).toLowerCase() + "s " +
              "move " + game.getLastMove().getSAN() + ':');
      game.printBoard();
      key = Colour.opposite(key);
    }
    Colour winner = game.getGameResult();
    if (winner != Colour.NONE){
      System.out.println(Colour.print(winner) + winner.toString().substring(1).toLowerCase() + "s WON!!!");
    } else {
      System.out.println("The game has ended in STALEMATE");
    }
  }

  private static Colour getColour() {
    String answer = yesNoInput("Will it be the White colour? (Whites go first, blacks decide gaps)");
    if (answer.equals("y")){
      return Colour.WHITE;
    } else {
      return Colour.BLACK;
    }
  }

  public static int getGap(String colour) {
    System.out.println("Where should the gap for the " + colour + " pawns be?");
    String gapInput = IOUtil.readString().trim();
    int gap;
    while (true){
      if (gapInput.length() > 0) {
        gap = (int) gapInput.charAt(0) - 'A';
        if (gap < 8 && gap >= 0) return gap;
      }
      System.out.println("Invalid input!!\nWhere should the gap for the " + colour + " pawns be?");
      gapInput = IOUtil.readString().trim();
    }
  }

  private static Board getBoard(boolean isComputer) {
    if (isComputer) {
      return new Board(new Random().nextInt(8), new Random().nextInt(8));
    } else {
      return new Board(getGap("Black"), getGap("White"));
    }
  }

  public static String yesNoInput(String question){
    System.out.println(question);
    String answer = IOUtil.readString().trim();
    while (!answer.equals("n") && !answer.equals("y")){
      System.out.println("Error: Answer must be yes(y) or no(n)");
      System.out.println(question);
      answer = IOUtil.readString();
    }
    return answer;
  }
}
