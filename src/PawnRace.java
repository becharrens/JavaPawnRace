import java.net.CookiePolicy;

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
    Game game = new Game();
    System.out.println("Welcome to the Pawn Race program");
    String answer = yesNoInput("Will the Player 1 be human? (y/n)");
    Colour colour = getColour();
    if (answer == "y") {
      HumanPlayer player1 = new HumanPlayer(game, colour);
      answer = yesNoInput("Will the Player 2 be human? (y/n)");
      if (answer == "y"){
        HumanPlayer player2 = new HumanPlayer(game, Colour.opposite(colour));
      } else {
        ComputerPlayer player2 = new ComputerPlayer(game, Colour.opposite(colour));
      }
    } else {
      ComputerPlayer player1 = new ComputerPlayer(game, colour);
      answer = yesNoInput("Will the Player 2 be human? (y/n)");
      if (answer == "y"){
        HumanPlayer player2 = new HumanPlayer(game, Colour.opposite(colour));
      } else {
        ComputerPlayer player2 = new ComputerPlayer(game, Colour.opposite(colour));
      }
    }
  }

  private Colour getColour() {
    String answer = yesNoInput("Will it be the White colour? (Whites go first)");
    if (answer == "y"){
      return Colour.WHITE;
    } else {
      return Colour.BLACK;
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
