package calculus;

public class Test {
    public static void main(String[] args) {
        var tests = TestCases.getAll();
        int total = tests.length;
        int failures = 0;

        for (int i = 0; i < total; i++) {
            var test = tests[i];
            Expression expr = test.input();
			NormalizationResult res = expr.normalize();			
			Expression normalized = res.expression;
            if (!normalized.equals(test.expected()) || res.depthLimitExceeded) {
                failures++;
                System.out.println();
                System.out.println("❌ Test " + (i + 1) + ": " + test.description());
                System.out.println("Expected:   " + test.expected());
				System.out.println(res.debugSteps);
			}
        }

        System.out.println(failures>0? "\n\n": "" + "Done. " + (total - failures) + "/" + total + " tests passed.");
    }
}
