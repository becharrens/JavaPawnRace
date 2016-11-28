public class Board {
//  private final char blackGap, whiteGap;
  private Square[][] chessBoard;

  public Board(char blackGap, char whiteGap) {
//    this.blackGap = blackGap;
//    this.whiteGap = whiteGap;
    int bGap = (int) blackGap - 'a';
    int wGap = (int) whiteGap - 'a';

    chessBoard = new Square[8][8];
    for (int i = 7; i >= 0; i--){
      for (int j = 0; j < 8; j++){
        chessBoard[7 - i][j] = new Square(j, i);
        if (i == 6 && j != bGap) {
          chessBoard[7 - i][j].setOccupier(Colour.BLACK);
        } else if (i == 1 && j != wGap ) {
          chessBoard[7 - i][j].setOccupier(Colour.WHITE);
        }
      }
    }
  }

  public Square getSquare(int x, int y){
  //Pre: x, y "Cartesian coordinates", not standard array indexing
    return chessBoard[7 - y][x];
  }

  public void applyMove(Move move){
    int x1 = move.getFrom().getX();
    int y1 = move.getFrom().getY();
    int x2 = move.getTo().getX();
    int y2 = move.getTo().getY();
    Colour c = move.getFrom().occupiedBy();
    chessBoard[7 - y1][x1].setOccupier(Colour.NONE);
    chessBoard[7 - y2][x2].setOccupier(c);
    if (move.isEnPassantCapture()) chessBoard[7 - y1][x2].setOccupier(Colour.NONE);
  }
//To Finish:
  public void unApplyMove(Move move){
    int x1 = move.getFrom().getX();
    int y1 = move.getFrom().getY();
    int x2 = move.getTo().getX();
    int y2 = move.getTo().getY();
    Colour c1 = move.getFrom().occupiedBy();
    chessBoard[7 - y1][x1].setOccupier(Colour.c1);
    if (move.isCapture() || move.isEnPassantCapture())
      chessBoard[7 - y2][x2].setOccupier(c);
  }
}
