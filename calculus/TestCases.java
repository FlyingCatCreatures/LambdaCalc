package calculus;

import static calculus.ExpressionBuilder.*;

public class TestCases {

    public record TestCase(String description, Expression input, Expression expected) {}

    public static TestCase[] getAll() {
        return new TestCase[] {
            new TestCase(
                "Identity function",
                app(lam(lit('x'), lit('x')), lit('y')),
                lit('y')
            ),
            new TestCase(
                "Constant function",
                app(app(lam(lit('x'), lam(lit('y'), lit('x'))), lit('a')), lit('b')),
                lit('a')
            ),
            new TestCase(
                "Boolean TRUE in IF",
                app(
                    app(
                        app(
                            lam(lit('b'), lam(lit('x'), lam(lit('y'), app(app(lit('b'), lit('x')), lit('y'))))), // IF
                            lam(lit('x'), lam(lit('y'), lit('x'))) // TRUE
                        ),
                        lit('a')
                    ),
                    lit('b')
                ),
                lit('a')
            ),
            new TestCase(
                "Double identity application",
                app(
                    app(
                        lam(lit('x'), app(lit('x'), lit('x'))),
                        lam(lit('y'), lit('y'))
                    ),
                    lit('z')
                ),
                lit('z')
            ),
            new TestCase(
                "Nested lambdas resolve correctly",
                app(
                    lam(lit('x'), lam(lit('y'), app(lit('x'), lit('y')))),
                    lam(lit('z'), lit('z'))
                ),
                lam(lit('y'), lit('y'))
            ),
            new TestCase(
                "Shadowing resolves to inner binding",
                app(
                    lam(lit('x'), lam(lit('x'), lit('x'))),
                    lit('a')
                ),
                lam(lit('x'), lit('x'))
            ),
            new TestCase(
                "Eta reduction structure: λx.(f x) ⇒ f",
                lam(lit('x'), app(lit('f'), lit('x'))),
                lam(lit('x'), app(lit('f'), lit('x'))) // keeping eta form here (eta reduction is optional)
            ),
            new TestCase(
                "Church-encoded TRUE applied to two values",
                app(app(lam(lit('x'), lam(lit('y'), lit('x'))), lit('1')), lit('0')),
                lit('1')
            ),
            new TestCase(
                "Church-encoded FALSE applied to two values",
                app(app(lam(lit('x'), lam(lit('y'), lit('y'))), lit('1')), lit('0')),
                lit('0')
            ),
            new TestCase(
                "IF FALSE yields second branch",
                app(
                    app(
                        app(
                            lam(lit('b'), lam(lit('x'), lam(lit('y'), app(app(lit('b'), lit('x')), lit('y'))))), // IF
                            lam(lit('x'), lam(lit('y'), lit('y'))) // FALSE
                        ),
                        lit('a')
                    ),
                    lit('b')
                ),
                lit('b')
            ),
            new TestCase(
                "Apply curried function to both arguments",
                app(
                    app(lam(lit('x'), lam(lit('y'), app(app(lit('f'), lit('x')), lit('y')))), lit('a')),
                    lit('b')
                ),
                app(app(lit('f'), lit('a')), lit('b'))
            ),
            new TestCase(
                "Long reduction chain normalizes eventually",
                lam(lit('x'),
                    app(
                        lam(lit('a'),
                            app(
                                lam(lit('b'),
                                    app(
                                        lam(lit('c'), lit('c')),
                                        lit('x')
                                    )
                                ),
                                lit('x')
                            )
                        ),
                        lit('x')
                    )
                ),
                lam(lit('x'), lit('x'))
            )
        };
    }
}
