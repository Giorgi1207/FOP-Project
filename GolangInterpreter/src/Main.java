import java.util.*;

public class Main{


}

class GoInterpreter {
    private Map<String, Integer> variables = new HashMap<>();
    private List<String> code = new ArrayList<>();
    private int currentLine = 0;

    public void interpret(String input) {
        code = Arrays.asList(input.split("\n"));
        while (currentLine < code.size()) {
            String line = code.get(currentLine).trim();
            System.out.println("Processing line: " + line);
            if (!line.isEmpty()) {
                lineInterpretation(line);
            }
            currentLine++;
        }
    }

    private void lineInterpretation(String line) {
        if (line.startsWith("var ")) {
            System.out.println("Variable declaration: " + line);
            VarDeclarationHandling(line);
        } else if (line.startsWith("if ")) {
            System.out.println("If statement: " + line);
            ifStatementHandling();
        } else if (line.startsWith("for ")) {
            System.out.println("For loop: " + line);
            ForLoop();
        } else if (line.contains("=")) {
            System.out.println("Assignment: " + line);
            AssignmentHandling(line);
        }
    }



    private void AssignmentHandling(String line) {
        String[] split = line.split("=");
        if (split.length == 2) {
            String variableName = split[0].trim();
            int val = evaluateExpression(split[1].trim());
            variables.put(variableName, val);
            System.out.println("Assignment " + variableName + " = " + val);
        }
    }

    private void VarDeclarationHandling(String line) {
        String[] parts = line.substring(4).split("=");
        if (parts.length == 2) {
            String varName = parts[0].trim();
            int value = evaluateExpression(parts[1].trim());
            variables.put(varName, value);
            System.out.println("Variable " + varName + " = " + value);
        }
    }


    private void ifStatementHandling() {
        if (currentLine >= code.size()) return;

        // Gets the current line
        String line = code.get(currentLine).trim();

        // returns "Invalid if statement: " + line if the line doesn't start with "if
        if (!line.startsWith("if ")) {
            System.out.println("Invalid if statement: " + line);
            return;
        }

        // Takes the condition
        int conditionTop = line.indexOf("if ") + 3; // Search after "if" keyword
        int conditionStart = line.indexOf("{");          // Block start
        String condition;

        if (conditionStart != -1) {
            // Extract condition
            condition = line.substring(conditionTop, conditionStart).trim();
        } else {
            // if there's no '{', return invalid
            System.out.println("Invalid if statement: Missing '{' in line: " + line);
            return;
        }

        System.out.println("If condition: " + condition);

        // Evaluate condition
        boolean conditionResult = Condition(condition);
        System.out.println("Condition result: " + conditionResult);

        currentLine++; // Move to the next line (body starts here)

        // Parse the body of the if statement
        List<String> body = new ArrayList<>();
        while (currentLine < code.size() && !code.get(currentLine).trim().equals("}")) {
            String bodyLine = code.get(currentLine).trim();
            if (!bodyLine.isEmpty()) {
                body.add(bodyLine);
            }
            currentLine++;
        }

        // Find '}'
        if (currentLine >= code.size() || !code.get(currentLine).trim().equals("}")) {
            System.out.println("Invalid if statement: Missing '}' for condition: " + condition);
            return;
        }

        currentLine++; // Move over

        // Do if condition is true
        if (conditionResult) {
            for (String bodyLine : body) {
                lineInterpretation(bodyLine);
            }
        }
    }




    private boolean Condition(String condition) {
        String[] parts = condition.split(" ");
        if (parts.length < 3) return false;

        int left = evaluateExpression(parts[0]);
        String operator = parts[1];
        int right = evaluateExpression(parts[2]);

        System.out.println("Evaluating condition: " + left + " " + operator + " " + right);

        switch (operator) {
            case "<":
                return left < right;
            case ">":
                return left > right;
            case "<=":
                return left <= right;
            case ">=":
                return left >= right;
            case "==":
                return left == right;
            case "!=":
                return left != right;
            default:
                return false;
        }
    }

    private void ForLoop() {
        if (currentLine >= code.size() - 1) return;

        String forLine = code.get(currentLine);
        String[] forParts = forLine.substring(4, forLine.indexOf("{")).split(";");
        if (forParts.length != 3) return;

        String init = forParts[0].trim();
        String condition = forParts[1].trim();
        String increment = forParts[2].trim();

        System.out.println("For loop init: " + init);
        System.out.println("For loop condition: " + condition);
        System.out.println("For loop increment: " + increment);

        lineInterpretation(init);
        currentLine++;

        int loopStartLine = currentLine;
        List<String> bodyLines = new ArrayList<>();
        while (currentLine < code.size() && !code.get(currentLine).trim().equals("}")) {
            String line = code.get(currentLine).trim();
            if (!line.isEmpty()) {
                bodyLines.add(line);
            }
            currentLine++;
        }

        while (Condition(condition)) {
            System.out.println("Loop iteration with condition: " + condition);
            for (String line : bodyLines) {
                lineInterpretation(line);
            }
            lineInterpretation(increment);
        }
    }

    private int evaluateExpression(String expr) {
        expr = expr.trim();
        if (expr.contains("+")) {
            String[] parts = expr.split("\\+");
            return evaluateExpression(parts[0].trim()) + evaluateExpression(parts[1].trim());
        }
        try {
            return Integer.parseInt(expr);
        } catch (NumberFormatException e) {
            return variables.getOrDefault(expr, 0);
        }
    }

    public int getVariable(String name) {
        return variables.getOrDefault(name, 0);
    }
    public static void main(String[] args) {
        GoInterpreter interpreter = new GoInterpreter();
        String program = """
                var x = 0
                for i = 0; i < 5; i = i + 1 {
                    if i > 2 {
                        x = x + 2
                    }
                }
                """;
        interpreter.interpret(program);
        System.out.println("Final value of x = " + interpreter.getVariable("x"));
        System.out.println("Variables: " + interpreter.variables);
    }
}