import java.util.ArrayList;

public class Board {
//  private final char blackGap, whiteGap;
  private Square[][] chessBoard;

  public Board(int blackGap, int whiteGap) {
//    this.blackGap = blackGap;
//    this.whiteGap = whiteGap;
    chessBoard = new Square[8][8];
    for (int i = 0; i < 8; i++){
      for (int j = 0; j < 8; j++){
        chessBoard[i][j] = new Square(i, j);
        if (i == 1 && j != blackGap) {
          chessBoard[i][j].setOccupier(Colour.BLACK);
        } else if (i == 6 && j != whiteGap ) {
          chessBoard[i][j].setOccupier(Colour.WHITE);
        }
      }
    }
  }

  //Pre: r, c standard array indexing
  public Square getSquare(int r, int c){
    return chessBoard[r][c];
  }

  public void applyMove(Move move){
    int r1 = move.getFrom().getR();
    int c1 = move.getFrom().getC();
    int r2 = move.getTo().getR();
    int c2 = move.getTo().getC();
    Colour colour = move.getFrom().occupiedBy();
    chessBoard[r1][c1].setOccupier(Colour.NONE);
    chessBoard[r2][c2].setOccupier(colour);
    if (move.isEnPassantCapture()){
      chessBoard[r1][c2].setOccupier(Colour.NONE);
    }
  }

  public void unApplyMove(Move move){
    int r1 = move.getFrom().getR();
    int c1 = move.getFrom().getC();
    int r2 = move.getTo().getR();
    int c2 = move.getTo().getC();
    Colour colour1 = move.getFrom().occupiedBy();
    Colour colour2 = move.getTo().occupiedBy();
    chessBoard[r1][c1].setOccupier(colour1);
    if (move.isEnPassantCapture()){
      chessBoard[r1][c2].setOccupier(Colour.opposite(colour1));
    }
    chessBoard[r2][c2].setOccupier(colour2);
  }

  public void display(){
    String rowLabel = "    A B C D E F G H    ";
    int len = rowLabel.length();
    StringBuilder sb = new StringBuilder();
    sb.append(rowLabel + '\n');
    sb.append("\n");
    for (int i = 0; i < 8; i++){
      sb.append((8 - i) + "   ");
      for (int j = 0; j < 8; j++){
        sb.append(Colour.print(chessBoard[i][j].occupiedBy()) + " ");
      }
      sb.append("  " + (8 - i) + '\n');
    }
    sb.append("\n");
    sb.append(rowLabel + '\n');
    System.out.println(sb);
  }

  public ArrayList<Square> getAllPawns(Colour colour){
    ArrayList<Square> pawns = new ArrayList<Square>();
    Square square;
    for (int i = 0; i < 8; i++){
      for (int j = 0; j < 8; j++){
        square = chessBoard[i][j].clone();
        if (square.occupiedBy() == colour) pawns.add(square);
      }
    }
    return pawns;
  }
}
