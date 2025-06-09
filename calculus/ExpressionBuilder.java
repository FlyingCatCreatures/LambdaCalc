package calculus;

public class ExpressionBuilder {
    public static Lambda lam(Literal arg, Expression func){
        return new Lambda(arg, func);
    }

    public static Literal lit(char c){
        return new Literal(c);
    }

    public static Application app(Expression func, Expression arg){
        return new Application(func, arg);
    }
}
