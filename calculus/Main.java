package calculus;

import static calculus.bool.BooleanLogic.*;
import static calculus.numeric.Integer.*;

import static calculus.numeric.Ackermann.*;
import calculus.numeric.IntegerUtil;

public class Main {
	static void mainbool(){
        System.out.println("For reference, these are the values of TRUE and FALSE in lambda calculus:");
        System.out.println("TRUE  = " + TRUE);
        System.out.println("FALSE = " + FALSE);
        System.out.println();


		Expression expr = NOT(
			AND(
				OR(
					FALSE,
					NOT(
						AND(
							TRUE,
							OR(FALSE, TRUE)
						)
					)
				),
				NOT(
					ITE(TRUE, FALSE, TRUE)
				)
			)
		);

        System.out.println("Evaluating: NOT (AND (OR FALSE (NOT (AND TRUE (OR FALSE TRUE)))) (ITE TRUE FALSE TRUE))");
        var result = expr.normalize();
		System.out.println("Evaluation path: ");
		System.out.println(result.debugSteps);
		Expression resexpr = result.expression;
        System.out.println("Result: " + result.expression);
		if(resexpr.equals(TRUE)){
			System.out.println("So the result is true");
		}else if(resexpr.equals(FALSE)){
			System.out.println("So the result is false");
		}
	}

	static void mainInt(){
		Expression expr = MUL(INT(10),INT(10));
        System.out.println("Evaluating A(2,1)");
		var result = expr.normalize(-1);

		System.out.println("Evaluation path: ");
		System.out.println(result.debugSteps);
		if(result.depthLimitExceeded){
			System.out.println("This calculation took longer than the maximum.");
		}
		Expression resexpr = result.expression;
        System.out.println("Result: " + result.expression);
		int number = IntegerUtil.decodeChurchNumeral(resexpr);
		if (number >= 0) {
			System.out.println("So the result is the number " + number);
		} else {
			System.out.println("Could not decode result as a number");
		}
	}

	static void mainGeneral(){
		Expression expr = ADD(INT(5), INT(2));
        System.out.println("Evaluating +*+");
		var result = expr.normalize(-1);

		System.out.println("Evaluation path: ");
		System.out.println(result.debugSteps);
		if(result.depthLimitExceeded){
			System.out.println("This calculation took longer than the maximum.");
		}
        System.out.println("Result: " + result.expression);
	}
    
	public static void main(String[] args) {
		//mainbool();
		mainInt();
		//mainGeneral();
    }
}
