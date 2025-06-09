package calculus.bool;

import static calculus.ExpressionBuilder.*;

import calculus.Expression;


public class BooleanLogic {
    public final static Expression TRUE = lam(lit('a'), lam(lit('b'), lit('a'))); // Essentially a selector that returns the first argument out of 2
    public final static Expression FALSE = lam(lit('a'), lam(lit('b'), lit('b'))); // Also essentially a selector, but returns the second argument out of 2


    public final static Expression NOTexpr = lam(lit('x'), ITE(lit('x'), FALSE, TRUE)); // if x is true then false, otherwise true
    public static Expression NOT(Expression arg){
        return app(NOTexpr, arg);
    }
    
    public final static Expression ANDexpr = lam(lit('x'), lam(lit('y'), ITE(lit('x'), lit('y'), FALSE))); // if x is true then y, otherwise false
    public static Expression AND(Expression arg1, Expression arg2){
        return app(app(ANDexpr, arg1), arg2);
    }

    public final static Expression ORexpr = lam(lit('x'), lam(lit('y'), ITE(lit('x'), TRUE, lit('y')))); // if x is true then true, otherwise y
    public static Expression OR(Expression arg1, Expression arg2){
        return app(app(ORexpr, arg1), arg2);
    }

    // Using true and false like this we can do things like
    // (B x y), where B is true or false. If B is true, x is selected. Otherwise y.
    // So this translates to if B then x else y
    public static Expression ITE (Expression bool, Expression then, Expression otherwise){
        return app(app(bool, then), otherwise);
    }

    public final static Expression ISZEROexpr = lam(lit('n'), app(app(lit('n'), lam(lit('x'), FALSE)), TRUE));
    public static Expression ISZERO(Expression num){
        return app(ISZEROexpr, num);
    }
}
