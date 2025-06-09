package calculus;

public class Literal implements Expression {
    private final char c;
    public Literal(char c){
        this.c = c;
    }

    private static char nextFresh = 'α';

    public static Literal freshLiteral() {
        return new Literal(nextFresh++);
    }

    @Override
    public boolean containsFree(Literal lit) {
        return this.equals(lit);
    }

    @Override
    public Expression betaReduce(){
        return new Literal(this.c); // Reducing a literal doesn't change it
    }

    @Override
    public Expression replLiteral(Literal toReplace, Expression toReplaceWith) {
        if(toReplace.equals(this))
            return toReplaceWith;
        else
            return new Literal(this.c);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Literal other)) return false;
        return this.c == other.c;
    }

    @Override
    public int hashCode() {
        return Character.hashCode(c);
    }

    @Override
    public String toString(){
        return String.valueOf(c);
    }
}
