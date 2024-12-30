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
    } // Giorgi

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
}private void handleAssignment(String line) {
        String[] parts = line.split("=");
        if (parts.length == 2) {
            String varName = parts[0].trim();
            int value = evaluateExpression(parts[1].trim());
            variables.put(varName, value);
            System.out.println("Assignment " + varName + " = " + value); // Debug
        }
    } //Elene

    private void handleIfStatement() {
        if (currentLine >= code.size() - 1) return;

        String condition = code.get(currentLine).substring(2).trim();
        System.out.println("If condition: " + condition); // Debug
        currentLine++;

        List<String> bodyLines = new ArrayList<>();
        while (currentLine < code.size() && !code.get(currentLine).trim().equals("}")) {
            String line = code.get(currentLine).trim();
            if (!line.isEmpty()) {
                bodyLines.add(line);
            }
            currentLine++;
        }

        boolean conditionResult = evaluateCondition(condition);
        System.out.println("Condition result: " + conditionResult); // Debug

        if (conditionResult) {
            for (String line : bodyLines) {
                interpretLine(line);
            }
        }
    }
   }// Sandro

private void handleForLoop() {
    if (currentLine >= code.size() - 1) return;

    String forLine = code.get(currentLine);
    String[] forParts = forLine.substring(4, forLine.indexOf("{")).split(";");
    if (forParts.length != 3) return;

    String init = forParts[0].trim();
    String condition = forParts[1].trim();
    String increment = forParts[2].trim();

    System.out.println("For loop init: " + init); // Debug
    System.out.println("For loop condition: " + condition); // Debug
    System.out.println("For loop increment: " + increment); // Debug

    interpretLine(init);
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

    while (evaluateCondition(condition)) {
        System.out.println("Loop iteration with condition: " + condition); // Debug
        for (String line : bodyLines) {
            interpretLine(line);
        }
        interpretLine(increment);
    }
}
     //Elene
