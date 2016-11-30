import java.util.ArrayList;
import java.util.Random;

public class HumanPlayer {
  private Game game;
  private Colour colour;
  private Colour opponent;

  public HumanPlayer(Game game, Colour colour) {
    //colour must be Black or White;
    this.game = game;
    this.colour = colour;
    this.opponent = Colour.opposite(colour);
  }

  public Colour getColour() {
    return colour;
  }

  public Colour getOpponent() {
    return opponent;
  }

  private ArrayList<Move> getAllValidMoves(){
    return game.getValidMoves(colour);
  }

  public void applyMove(){
    System.out.println("Enter your move: ");
    String san = IOUtil.readString().trim();
    Move move = game.parseMove(san);
    while (move == null){
      System.out.println("Error: Invalid move. Please enter a valid move: ");
      san = IOUtil.readString();
      move = game.parseMove(san);
    }
    game.applyMove(move);
  }
}
