package calculus;

public class Lambda implements Expression{
    private final Literal param;
    private final Expression func;
    
    public Lambda(Literal arg, Expression func){
        this.param=arg;
        this.func=func;
    }

    @Override
    public Expression betaReduce() {
        return new Lambda(param, func.betaReduce());
    }


    public Literal getLiteral(){
        return param;
    }

    public Expression getBody() {
        return func;
    }

    @Override
    public Expression replLiteral(Literal toReplace, Expression toReplaceWith) {
        if (param.equals(toReplace)) {
            return new Lambda(param, func); // prevent shadowing
        } 
        
        // If param appears free in 'toReplaceWith', alpha-convert first
        if (toReplaceWith.containsFree(param)) {
            Literal fresh = Literal.freshLiteral();
            Expression renamed = func.replLiteral(param, fresh);
            return new Lambda(fresh, renamed.replLiteral(toReplace, toReplaceWith));
        }

        return new Lambda(param, func.replLiteral(toReplace, toReplaceWith));
    }

    @Override
    public boolean containsFree(Literal lit) {
        if (param.equals(lit)) return false;
        return func.containsFree(lit);
    }

    @Override
    public String toString(){
        return "(λ" + param.toString() + '.' +  func.toString() + ")" ;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Lambda other)) return false;
        return param.equals(other.param) && func.equals(other.func);
    }

    @Override
    public int hashCode() {
        return 31 * param.hashCode() + func.hashCode();
    }

}
