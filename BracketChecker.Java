import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.*;
import java.util.*;

class BracketPair {
    public String left;
    public String right;
}

class BracketConfig {
    public List<BracketPair> bracket;
}

public class BracketChecker {

    public static void main(String[] args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        BracketConfig config = mapper.readValue(new File("config.json"), BracketConfig.class);

        String content = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get("input.txt")));

        checkBrackets(config, content);
    }

    public static void checkBrackets(BracketConfig config, String content) {
        Map<String, String> bracketMap = new HashMap<>();
        Set<String> opening = new HashSet<>();
        Set<String> closing = new HashSet<>();

        for (BracketPair pair : config.bracket) {
            bracketMap.put(pair.right, pair.left);
            opening.add(pair.left);
            closing.add(pair.right);
        }

        Stack<String> stack = new Stack<>();
        for (int i = 0; i < content.length(); i++) {
            String ch = String.valueOf(content.charAt(i));

            if (opening.contains(ch)) {
                stack.push(ch);
            }

            else if (closing.contains(ch)) {
                if (stack.isEmpty() || !stack.peek().equals(bracketMap.get(ch))) {
                    System.out.println("Ошибка в позиции " + i + ": ожидается " + bracketMap.get(ch));
                    return;
                } else {
                    stack.pop();
                }
            }
        }

        if (stack.isEmpty()) {
            System.out.println("Скобки расставлены правильно.");
        } else {
            System.out.println("Ошибка: остались незакрытые скобки.");
        }
    }
}
