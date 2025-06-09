package calculus.numeric;

import static calculus.ExpressionBuilder.*;
import calculus.Expression;
import calculus.Literal;

public class Integer {
    // Numbers are stored as repeated function application. So zero is a lambda that takes in a function and a value, and returns the value
    // 0 is (f,x)-> x
    // In the same spirit, 1 is a lambda that takes in a function and a value and applies it
    // 1 is (f,x) -> f(x)
    // Then 2 is (f,x) -> f(f(x))

    // Takes a number n and spits out n+1. Applies f to (nfx). n repeats f n times, so in total we return (n+1)fx
    // I.E. SUCC = λn.λf.λx. f(nfx)
    public final static Expression SUCCexpr = lam(lit('n'),lam(lit('f'),lam(lit('x'),app(lit('f'),app(app(lit('n'), lit('f')), lit('x'))))));
    public static Expression SUCC(Expression num){
        return app(SUCCexpr, num);
    }

    // PRED is the opposite of SUCC, it returns n-1 instead of n+1
    public final static Expression PREDexpr = lam(lit('n'),lam(lit('f'),lam(lit('x'),app(app(app(lit('n'),lam(lit('g'),lam(lit('h'),app(lit('h'), app(lit('g'), lit('f')))))), lam(lit('u'), lit('x'))),lam(lit('u'), lit('u'))))));
    public static Expression PRED(Expression num){
        return app(PREDexpr, num);
    }

    public static Expression INT(int num) {
        if (num == 0) {
            Literal f = lit('f');
            Literal x = lit('x');
            return lam(f, lam(x, x)); // λf.λx.x, which is 0 as a lambda
        } else {
            return SUCC(INT(num - 1));
        }
    }

    // From the definitions it follows that n+m is (m SUCC) n. We apply m to SUCC, and input n to the result. 
    // This follows from that a number applies a function some amount of times, so actually this means we are applying SUCC to n, m times over.
    public final static Expression ADDexpr = lam(lit('n'), lam(lit('m'), app(app(lit('m'), SUCCexpr), lit('n'))));
    public static Expression ADD(Expression n, Expression m){
        return app(app(ADDexpr, n), m);
    }

    // Multiplication
    public final static Expression MULexpr = lam(lit('n'), lam(lit('m'), lam(lit('f'), lam(lit('x'), app(app(lit('n'), app(lit('m'), lit('f'))),lit('x'))))));
    public static Expression MUL(Expression n, Expression m){
        return app(app(MULexpr, n), m);
    }

    // Exponentiation
    public final static Expression EXPexpr = lam(lit('n'), lam(lit('m'), app(lit('m'),lit('n'))));
    public static Expression EXP(Expression n, Expression m){
        return app(app(EXPexpr, n), m);
    }

    // The Y-combinator is a special expression that allows for recursion, even though lamba calculus doesn't support named functions
    public final static Expression Yexpr = lam(lit('f'),app(lam(lit('x'), app(lit('f'), app(lit('x'), lit('x')))),lam(lit('x'), app(lit('f'), app(lit('x'), lit('x'))))));
    public static Expression Y(Expression num){
        return app(Yexpr, num);
    }



}
