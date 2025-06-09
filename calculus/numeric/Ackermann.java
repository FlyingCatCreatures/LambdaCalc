package calculus.numeric;

import static calculus.ExpressionBuilder.*;
import static calculus.numeric.Integer.*;
import static calculus.bool.BooleanLogic.*;

import calculus.Expression;

// This implementation works insofar as I can see, for inputs (1,n)
// For m>2 it either just takes a long time or I did something wrong and it never finishes
// Either is very possible
public class Ackermann {
    public final static Expression ACKERMANNexpr = Y(lam(lit('A'),
        lam(lit('m'), lam(lit('n'),
            ITE(app(ISZEROexpr, lit('m')),
                // if m == 0, return n + 1
                SUCC(lit('n')),
                ITE(app(ISZEROexpr, lit('n')),
                    // if m > 0 and n == 0, return A(m-1, 1)
                    app(app(lit('A'), PRED(lit('m'))), INT(1)),
                    // else return A(m-1, A(m, n-1))
                    app(app(lit('A'), PRED(lit('m'))), 
                        app(app(lit('A'), lit('m')), PRED(lit('n')))
                    )
                )
            )
        ))
    ));

    public static Expression ACKERMANN(Expression m, Expression n) {
        return app(app(ACKERMANNexpr, m), n);
    }
}
