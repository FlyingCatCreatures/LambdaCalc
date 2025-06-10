package calculus.calculator;

import java.util.Scanner;
import java.util.Stack;

import static calculus.numeric.Integer.*;

import calculus.Expression;
import calculus.numeric.IntegerUtil;

public class Calculator {

    public static void start() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Enter calculation (or 'exit' to quit): ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("exit")) {
                System.out.println("Goodbye!");
                break;
            }

            try {
                // Parse the input string into an Expression object
                Expression expr = parseExpression(input);

                var result = expr.normalize(-1);

                int number = IntegerUtil.decodeChurchNumeral(result.expression);
                if (result.depthLimitExceeded) {
                    System.out.println("This calculation took longer than the maximum.");
                }else if (number >= 0) {
                    System.out.println("So the result is the number " + number);
                } else {
                    System.out.println("Could not decode result as a number.");
                    //System.out.println(resexpr.prettyPrint());
                }
            } catch (Exception e) {
                System.out.println("Invalid expression. Please try again.");
            }
        }

        scanner.close();
    }

    private static Expression parseExpression(String input) throws Exception {
        input = input.replaceAll("\\s+", ""); // Remove all whitespace
        
        // Convert to postfix notation (Reverse Polish Notation)
        String[] postfix = infixToPostfix(input);
        
        // Evaluate postfix expression
        return evaluatePostfix(postfix);
    }

    private static String[] infixToPostfix(String infix) throws Exception {
        Stack<Character> stack = new Stack<>();
        StringBuilder output = new StringBuilder();
        
        for (int i = 0; i < infix.length(); i++) {
            char c = infix.charAt(i);
            
            if (Character.isDigit(c)) {
                // Read the whole number (multi-digit)
                while (i < infix.length() && Character.isDigit(infix.charAt(i))) {
                    output.append(infix.charAt(i++));
                }
                output.append(" ");
                i--; // Adjust for the extra increment
            } else if (c == '(') {
                stack.push(c);
            } else if (c == ')') {
                while (!stack.isEmpty() && stack.peek() != '(') {
                    output.append(stack.pop()).append(" ");
                }
                if (stack.isEmpty()) {
                    throw new Exception("Mismatched parentheses");
                }
                stack.pop(); // Remove '(' from stack
            } else if (isOperator(c)) {
                while (!stack.isEmpty() && precedence(c) <= precedence(stack.peek())) {
                    output.append(stack.pop()).append(" ");
                }
                stack.push(c);
            } else {
                throw new Exception("Invalid character: " + c);
            }
        }
        
        // Pop all remaining operators
        while (!stack.isEmpty()) {
            if (stack.peek() == '(') {
                throw new Exception("Mismatched parentheses");
            }
            output.append(stack.pop()).append(" ");
        }
        
        return output.toString().trim().split("\\s+");
    }

    private static Expression evaluatePostfix(String[] postfix) throws Exception {
        Stack<Expression> stack = new Stack<>();
        
        for (String token : postfix) {
            if (token.length() == 1 && isOperator(token.charAt(0))) {
                // It's an operator
                if (stack.size() < 2) {
                    throw new Exception("Invalid expression - not enough operands");
                }
                Expression right = stack.pop();
                Expression left = stack.pop();
                stack.push(createExpression(left, right, token.charAt(0)));
            } else {
                // It's a number
                try {
                    int num = Integer.parseInt(token);
                    stack.push(INT(num));
                } catch (NumberFormatException e) {
                    throw new Exception("Invalid number: " + token);
                }
            }
        }
        
        if (stack.size() != 1) {
            throw new Exception("Invalid expression");
        }
        
        return stack.pop();
    }

    private static Expression createExpression(Expression left, Expression right, char op) throws Exception {
        switch (op) {
            case '+': return ADD(left, right);
            case '-': return SUB(left, right);
            case '*': return MUL(left, right);
            case '^': return EXP(left, right);
            default: throw new Exception("Unsupported operator: " + op);
        }
    }

    private static boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '^';
    }

    private static int precedence(char op) {
        switch (op) {
            case '^': return 3;
            case '*': return 2;
            case '+', '-': return 1;
            default: return 0;
        }
    }
}