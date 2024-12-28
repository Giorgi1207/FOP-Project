import java.util.*;

class GoInterpreter {
    private Map<String, Integer> variables = new HashMap<>();
    private List<String> code = new ArrayList<>();
    private int currentLine = 0;

    public void interpret(String input) {
        code = Arrays.asList(input.split("\n"));
        while (currentLine < code.size()) {
            String line = code.get(currentLine).trim();
            System.out.println("Processing line: " + line); // Debug
            if (!line.isEmpty()) {
                interpretLine(line);
            }
            currentLine++;
        }
    }

    private void interpretLine(String line) {
        if (line.startsWith("var ")) {
            System.out.println("Variable declaration: " + line);
        }
        handleVariableDeclaration(line);
    } else if (line.startsWith("if ")) {
        System.out.println("If statement: " + line); // Debug
        handleIfStatement();
    } else if (line.startsWith("for ")) {
        System.out.println("For loop: " + line); // Debug
        handleForLoop();
    } else if (line.contains("=")) {
        System.out.println("Assignment: " + line); // Debug
        handleAssignment(line);
    }

private void handleVariableDeclaration(String line) {
    String[] parts = line.substring(4).split("=");
    if (parts.length == 2) {
        String varName = parts[0].trim();
        int value = evaluateExpression(parts[1].trim());
        variables.put(varName, value);
        System.out.println("Variable " + varName + " = " + value); // Debug
    }
}

private void handleAssignment(String line) {
    String[] parts = line.split("=");
    if (parts.length == 2) {
        String varName = parts[0].trim();
        int value = evaluateExpression(parts[1].trim());
        variables.put(varName, value);
        System.out.println("Assignment " + varName + " = " + value); // Debug for
    }
   }
  }
