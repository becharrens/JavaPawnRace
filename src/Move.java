public class Move {
  private final Square from, to;
  private final boolean isCapture, isEnPassantCapture;

  public Move(){
    this.from =  new Square(0, 0);
    this.to = new Square(0, 0);
    this.isCapture = false;
    this.isEnPassantCapture = false;
  }

  public Move(Square from, Square to, boolean isCapture, boolean isEnPassantCapture){
    this.from =  from;
    this.to = to;
    this.isCapture = isCapture;
    this.isEnPassantCapture = isEnPassantCapture;
  }

  public Square getFrom() {
    return from;
  }

  public Square getTo() {
    return to;
  }

  public boolean isCapture() {
    return isCapture;
  }

  public boolean isEnPassantCapture() {
    return isEnPassantCapture;
  }

  public String getSAN(){
    //Short Algebraic Notation
    StringBuilder sb = new StringBuilder();
    if (isCapture || isEnPassantCapture()) {
      sb.append(columnLetter(from.getC()) + "x");
    }
    sb.append(columnLetter(to.getC()) + "" + (8 - to.getR()));
    return sb.toString();
  }

  private static char columnLetter(int y){ return (char) (y + 'A'); }
}
