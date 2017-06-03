public class MoveEvalPair {
  private final Move move;
  private final int eval;

  public MoveEvalPair(Move move, int eval) {
    this.move = move;
    this.eval = eval;
  }

  public Move getMove() {
    return move;
  }

  public int getEval() {
    return eval;
  }
}
