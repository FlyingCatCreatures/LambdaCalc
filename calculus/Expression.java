package calculus;

public interface Expression {
    public Expression betaReduce();
    public Expression replLiteral(Literal toReplace, Expression toReplaceWith);
    public boolean containsFree(Literal lit);
    public String toString();
    public boolean equalsAlpha(Expression other);
    public String prettyPrint(String prefix, boolean isTail);
    public default String prettyPrint() {
        return prettyPrint("", true);
    }

    default NormalizationResult normalize(int maxSteps) {
        Expression current = this;
        Expression reduced;

        StringBuilder debugSteps = new StringBuilder();
        int steps = 0;

    do {
        reduced = current;
        current = current.betaReduce();
        debugSteps.append("step ").append(steps).append(": ").append(reduced).append('\n');
        steps++;
    } while (!current.equalsAlpha(reduced) && (maxSteps < 0 || steps < maxSteps));


        boolean didExceed = (maxSteps >= 0 && !current.equals(reduced));
        return new NormalizationResult(current, debugSteps.toString(), didExceed);
    }

    default NormalizationResult normalize(){
        return normalize(100);
    }
}

