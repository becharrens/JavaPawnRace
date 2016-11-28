public class Square{
  private final int x, y;
  private Colour occupier = Colour.NONE;

  public Square(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  public Colour occupiedBy(){
    return occupier;
  }

  public void setOccupier(Colour colour){
    occupier = colour;
  }
}
