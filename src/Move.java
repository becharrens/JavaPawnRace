import javax.sound.midi.Soundbank;

public class Move {
  private final Square from, to;
  private final boolean isCapture, isEnPassantCapture;

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
      sb.append(columnLetter(from.getX()) + 'x');
    }
    sb.append(columnLetter(to.getX()) + to.getY());
    return sb.toString();
  }

  private static char columnLetter(int y){
    return (char) (y + 'a');
  }
}
