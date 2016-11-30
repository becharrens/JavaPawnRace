public class Square implements Cloneable {
  private final int r, c;
  private Colour occupier;

  public Square(int r, int c) {
    this(r, c, Colour.NONE);
  }

  public Square(int r, int c, Colour colour) {
    this.r = r;
    this.c = c;
    this.occupier = colour;
  }

  public int getR() {
    return r;
  }

  public int getC() {
    return c;
  }

  public Colour occupiedBy(){
    return occupier;
  }

  @Override
  public Square clone() {
    return new Square(r, c, occupier);
  }

  public void setOccupier(Colour colour){
    occupier = colour;
  }
}
