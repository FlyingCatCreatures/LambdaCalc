package calculus;

// Wraps an expression and a string
public class NormalizationResult {
    public final Expression expression;
    public final String debugSteps;
    public final boolean depthLimitExceeded;
    
    public NormalizationResult(Expression result, String debugSteps, boolean depthLimitExceeded) {
        this.expression = result;
        this.debugSteps = debugSteps;
        this.depthLimitExceeded = depthLimitExceeded;
    }
}
