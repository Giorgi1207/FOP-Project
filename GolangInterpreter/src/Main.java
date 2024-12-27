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
    }
}