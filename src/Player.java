import java.util.ArrayList;
import java.util.Random;

public class Player {
  private Game game;
  private Colour colour;
  private Colour opponent;
  private boolean computerPlayer;

  public Player(Game game, Colour colour, boolean computerPlayer) {
    //colour must be Black or White;
    this.game = game;
    this.colour = colour;
    this.opponent = Colour.opposite(colour);
    this.computerPlayer = computerPlayer;
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
      ArrayList<Move> moves = getAllValidMoves();
      game.applyMove(moves.get(new Random().nextInt(moves.size())));
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
    }
  }
}