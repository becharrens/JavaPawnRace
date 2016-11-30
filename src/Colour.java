public enum Colour{
  BLACK, WHITE, NONE;

  public static Colour opposite(Colour colour){
    switch (colour){
      case WHITE: return BLACK;
      case BLACK: return WHITE;
      default: return NONE;
    }
  }

  public static char print(Colour colour){
    switch (colour){
      case WHITE: return 'W';
      case BLACK: return 'B';
      default: return '.';
    }
  }

  public static int getAdvanceR(Colour colour){
    switch (colour){
      case WHITE: return -1;
      case BLACK: return 1;
      default: return 0;
    }
  }

  public static int getTargetR(Colour colour){
    switch (colour){
      case WHITE: return 0;
      case BLACK: return 7;
      default: return -1;
    }
  }

  public static int getStartR(Colour colour){
    switch (colour){
      case WHITE: return 6;
      case BLACK: return 1;
      default: return -1;
    }
  }
}
