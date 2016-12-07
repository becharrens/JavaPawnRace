import java.util.ArrayList;

public class Game {
  private Board chessBoard;
  private Move[] movesArr;
  private int moveIdx;
  private Colour currentPlayer;

  public Game(Board board){
    this.chessBoard = board;
    this.movesArr = new Move[84];
    this.moveIdx = 0;
    this.currentPlayer = Colour.WHITE;
  }

  public Colour getCurrentPlayer() {
    return currentPlayer;
  }

  public int getMoveIdx(){
    return moveIdx;
  }

  public Move getLastMove(){
    if (moveIdx == 0) return null;
    return movesArr[moveIdx - 1];
  }

  public void applyMove(Move move){
    movesArr[moveIdx] = move;
    moveIdx++;
    chessBoard.applyMove(move);
    //chessBoard.display();
    currentPlayer = Colour.opposite(currentPlayer);
  }

  public void unApplyMove(){
    if (moveIdx > 0) {
      moveIdx--;
      Move move = movesArr[moveIdx];
      chessBoard.unApplyMove(move);
      movesArr[moveIdx] = new Move();
      currentPlayer = Colour.opposite(currentPlayer);
    }
  }

  public boolean isFinished(){
    //Check if all pawns are gone
    if (moveIdx == 0) return false;
    if (chessBoard.getAllPawns(currentPlayer).size() == 0) return true;
    if (getLastMove().getTo().getR() == 0 ||
              getLastMove().getTo().getR() == 7) return true;
    for (int i = 0; i < 8; i++){
      for (int j = 0; j < 8; j++){
        if (chessBoard.getSquare(i, j).occupiedBy() == currentPlayer &&
                pawnMoves(currentPlayer, i, j).size() != 0){
          return false;
        }
      }
    }
    return true;
  }

  public ArrayList<Move> pawnMoves(Colour colour, int r, int c){
    //Maybe clone square for move
    ArrayList<Move> pawnMoves = new ArrayList<Move>();
    if (colour == Colour.NONE) return pawnMoves;
    Square square, originSq = chessBoard.getSquare(r, c).clone();
    int targetR = Colour.getTargetR(colour);
    int advanceR = Colour.getAdvanceR(colour);
    int startR = Colour.getStartR(colour);
    if (r == targetR) return pawnMoves;

    square = chessBoard.getSquare(r + advanceR, c).clone();
    if (square.occupiedBy() == Colour.NONE){
      pawnMoves.add(new Move(originSq, square, false, false));
      if (r == startR){
        square = chessBoard.getSquare(r + 2 * advanceR, c).clone();
        if (square.occupiedBy() == Colour.NONE) {
          pawnMoves.add(new Move(originSq, square, false, false));
        }
      }
    }
    boolean isEPC = false;
    if (getLastMove() != null) {
      if (c > 0) {
        square = chessBoard.getSquare(r + advanceR, c - 1).clone();
        if (square.occupiedBy() == Colour.opposite(colour)) {
          pawnMoves.add(new Move(originSq, square, true, false));
        } else {
          int toR = getLastMove().getTo().getR();
          int fromR = getLastMove().getFrom().getR();
          int toC = getLastMove().getTo().getC();
          isEPC = Math.abs(toR - fromR) == 2 && (toR == r && toC == c - 1);
          if (isEPC) pawnMoves.add(new Move(originSq, square, true, true));
        }
      }
      if (c < 7) {
        square = chessBoard.getSquare(r + advanceR, c + 1).clone();
        if (square.occupiedBy() == Colour.opposite(colour)) {
          pawnMoves.add(new Move(originSq, square, true, false));
        } else {
          int toR = getLastMove().getTo().getR();
          int fromR = getLastMove().getFrom().getR();
          int toC = getLastMove().getTo().getC();
          isEPC = Math.abs(toR - fromR) == 2 && (toR == r && toC == c + 1);
          if (isEPC) pawnMoves.add(new Move(originSq, square, true, true));
        }
      }
    }
    return pawnMoves;
  }

  public Colour getGameResult(){
    //Check for capture (all pawns gone)
    if (isFinished()) {
      Square square;
      Colour winner = Colour.opposite(currentPlayer);
      if (chessBoard.getAllPawns(currentPlayer).size() == 0) return winner;
      int r = getLastMove().getTo().getR();
      if (r == 0 || r == 7){
        return getLastMove().getFrom().occupiedBy();
      }
    }
    return Colour.NONE;
  }

  public ArrayList<Move> getValidMoves(Colour colour){
    ArrayList<Square> pawns = chessBoard.getAllPawns(currentPlayer);
    ArrayList<Move> moves = new ArrayList<Move>();
    for (Square pawn : pawns) {
      moves.addAll(pawnMoves(currentPlayer, pawn.getR(), pawn.getC()));
    }
    return moves;
  }

  public boolean isPassedPawn(Colour player, int r, int c){
    int advanceR = Colour.getAdvanceR(player);
    int targetR = Colour.getTargetR(player);
    Colour opponent = Colour.opposite(player);
    for (int i = r + advanceR; i != targetR; i += advanceR){
      for (int j = Math.max(0, c - 1); j < Math.min(7, c + 1); j++){
        if (j != c && i != r + advanceR && chessBoard.getSquare(i, j).occupiedBy() == opponent ||
            j == c && chessBoard.getSquare(i,j).occupiedBy() == Colour.NONE) return false;
      }
    }
    return true;
  }

  public boolean isIsolatedPawn(int r, int c) {
    Colour colour = chessBoard.getSquare(r, c).occupiedBy();
    for (int i = Math.max(0, r - 1); i < Math.min(7, r + 1); i++){
      for (int j = Math.max(0, c - 1); i < Math.min(7, c + 1); j++){
        if (chessBoard.getSquare(i,j).occupiedBy() == colour && (r != i || c != j)) return false;
      }
    }
    return true;
  }

  public Move parseMove(String san) {
    ArrayList<Move> moves = getValidMoves(currentPlayer);
    for (Move move : moves) {
      if (san.equals(move.getSAN())) {
        return move;
      }
    }
    return null;
  }
//
//  public int evaluateBoard(){
//    int eval;
//    if (isFinished()){
//      if (getGameResult() == ) {
//        eval = Integer.MAX_VALUE;
//        if ()
//      }
//    }
  public int evaluateBoard(){
    if (isFinished()){
      Colour result = getGameResult();
      if (result == Colour.BLACK){
        return Integer.MAX_VALUE;
      } else if (result == Colour.WHITE){
        return Integer.MIN_VALUE;
      } else {
        return 0;
      }
    }

    Colour colour;
    int bTargetR = Colour.getTargetR(Colour.BLACK), wTargetR = Colour.getTargetR(Colour.WHITE);
    int eval = 0;
    for (int i = 0; i < 8; i++){
      for (int j = 0; j < 8; j++){
        colour = chessBoard.getSquare(i, j).occupiedBy();
        if (colour == Colour.WHITE) {
          if (isPassedPawn(colour, i, j)) eval += (wTargetR - j) * 10;
        } else if (colour == Colour.BLACK) {
          if (isPassedPawn(colour, i, j)) eval += (bTargetR - j) * 10;
        }
      }
    }
    return eval;
  }

  public void printBoard(){
    chessBoard.display();
  }

//  parseMove:
//    //Validate move
//    Square to, from;
//    int advanceR = Colour.getAdvanceR(currentPlayer);
//    int startR = Colour.getAdvanceR(currentPlayer);
//    String[] fromTo = san.split("x");
//    if (fromTo.length > 2) return null;
//    if (fromTo[fromTo.length - 1].length() != 2) return null;
//    int c = (int) fromTo[fromTo.length - 1].charAt(0) - 'A';
//    int r = (int) fromTo[fromTo.length - 1].charAt(1);
//    if (c < 0 || c > 7 || r < 0 || r > 7) return null;
//    if ((startR == 1 && r <= startR) || (startR == 6 && r <= startR)) return false;
//    to = chessBoard.getSquare(r, c).clone();
//    if (fromTo.length == 1){
//      //Validate;
//      if (to.occupiedBy() == Colour.NONE){
//        from = chessBoard.getSquare(r - advanceR, c).clone();
//        if (from.occupiedBy() == currentPlayer){
//          return new Move(from, to, false, false);
//        } else {
//          if (from.occupiedBy() == Colour.opposite(currentPlayer)){
//            return null;
//          } else if(from.occupiedBy() == Colour.NONE && (r - 2 * advanceR == startR)){
//            from = chessBoard.getSquare(r - 2 * advanceR, c).clone();
//            if (from.occupiedBy() == currentPlayer){
//              return new Move(from, to, false, false);
//            }
//          }
//        }
//      }
//    } else {
//      if (fromTo[0].length() != 1) return null;
//      int c1 = (int) fromTo[0] - 'A';
//      if (c1 > 7 || c1 < 0) return null;
//      to = chessBoard.getSquare(r, c).clone();
//      if ()
//      for (int i = startR; i != r - advanceR; i += advanceR){
//
//      }
//    }
//  }
}
