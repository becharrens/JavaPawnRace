import java.util.ArrayList;
import java.util.Random;

public class ComputerPlayer {
  private Game game;
  private Colour colour;
  private Colour opponent;

  public ComputerPlayer(Game game, Colour colour) {
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
    ArrayList<Move> moves = getAllValidMoves();
    game.applyMove(moves.get(new Random().nextInt(moves.size())));
  }
}
