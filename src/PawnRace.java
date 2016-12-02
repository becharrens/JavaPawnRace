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
    Game game;
    System.out.println("Welcome to the Pawn Race program");
    String answer = yesNoInput("Will the Player 1 be human? (y/n)");
    Colour colour1 = getColour(), colour2 = Colour.opposite(colour1);
    boolean isComp1, isComp2;
    Board board;
    isComp1 = answer == "y";
    String answer = yesNoInput("Will the Player 2 be human? (y/n)");
    isComp2 = answer == "y";
    if (colour1 == Colour.BLACK) {
      board = getBoard(isComp1);
    } else {
      board = getBoard(isComp2);
    }
  }

  private static Colour getColour() {
    String answer = yesNoInput("Will it be the White colour? (Whites go first, blacks decide gaps)");
    if (answer == "y"){
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
    while (answer != "n" && answer != "y"){
      System.out.println("Error: Answer must be yes(y) or no(n)");
      System.out.println(question);
      answer = IOUtil.readString();
    }
    return answer;
  }
}
