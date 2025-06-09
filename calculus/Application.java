package calculus;

public class Application implements Expression {
    public final Expression function; // The function getting applied
    public Expression argument; // What is getting passed as an argument

    public Application(Expression function, Expression argument){
        this.function = function;
        this.argument = argument;
    }

    @Override
    public boolean containsFree(Literal lit) {
        return function.containsFree(lit) || argument.containsFree(lit);
    }

    @Override
    public Expression betaReduce() {
        if (function instanceof Lambda lambda) {
            return lambda.getBody().replLiteral(lambda.getLiteral(), argument);
        } else {
            return new Application(function.betaReduce(), argument.betaReduce());
        }
    }

    @Override
    public Expression replLiteral(Literal toReplace, Expression toReplaceWith) {
        Expression safeFunction = function;
        Expression safeArgument = argument;

        // If 'toReplaceWith' contains a variable that's bound in the function,
        // alpha-convert those to fresh names to avoid capture.
        if (function instanceof Lambda lambdaFunc && toReplaceWith.containsFree(lambdaFunc.getLiteral())) {
            Literal fresh = Literal.freshLiteral();
            safeFunction = new Lambda(fresh, lambdaFunc.getBody().replLiteral(lambdaFunc.getLiteral(), fresh));
        }

        // Same for argument
        if (argument instanceof Lambda lambdaArg && toReplaceWith.containsFree(lambdaArg.getLiteral())) {
            Literal fresh = Literal.freshLiteral();
            safeArgument = new Lambda(fresh, lambdaArg.getBody().replLiteral(lambdaArg.getLiteral(), fresh));
        }

        return new Application(
            safeFunction.replLiteral(toReplace, toReplaceWith),
            safeArgument.replLiteral(toReplace, toReplaceWith)
        );
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Application other)) return false;
        return function.equals(other.function) && argument.equals(other.argument);
    }

    @Override
    public int hashCode() {
        return 31 * function.hashCode() + argument.hashCode();
    }


    @Override
    public String toString(){
        return "(" + function.toString() + argument.toString() + ")";
    }
    
}