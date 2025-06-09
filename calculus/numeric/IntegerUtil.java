package calculus.numeric;

import calculus.Expression;
import calculus.Application;
import calculus.Lambda;
import calculus.Literal;

public class IntegerUtil {

    // Returns -1 if the expression is not a Church numeral
    public static int decodeChurchNumeral(Expression expr) {
        // Church numeral must be of form: λf.λx. BODY
        if (!(expr instanceof Lambda outer)) return -1;
        Expression innerExpr = outer.getBody();
        if (!(innerExpr instanceof Lambda inner)) return -1;

        Literal f = outer.getLiteral();
        Literal x = inner.getLiteral();

        Expression body = inner.getBody();

        // Count how many times 'f' is applied in succession to 'x'
        return countApplications(body, f, x);
    }

    private static int countApplications(Expression expr, Literal f, Literal x) {
        int count = 0;

        while (expr instanceof Application app) {
            if (!(app.function instanceof Literal appliedF) || !appliedF.equals(f)) {
                return -1; // Not a repeated application of f
            }
            expr = app.argument;
            count++;
        }

        // Final expression must be x
        if (expr instanceof Literal finalLit && finalLit.equals(x)) {
            return count;
        }

        return -1;
    }
}
