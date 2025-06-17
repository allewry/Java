import java.util.*;

public class MaxPathFinder {

    private static final int SIZE = 3;

    private static final int[] dx = {-1, 1, 0, 0};
    private static final int[] dy = {0, 0, -1, 1};

    private static String maxNumber = "";

    public static String findMaxNumber(int[][] matrix) {
        boolean[][] visited = new boolean[SIZE][SIZE];

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                dfs(matrix, visited, i, j, "");
            }
        }
        return maxNumber;
    }

    private static void dfs(int[][] matrix, boolean[][] visited, int x, int y, String path) {
        if (x < 0  y < 0  x >= SIZE  y >= SIZE  visited[x][y]) return;

        visited[x][y] = true;

        path += matrix[x][y];

        if (path.compareTo(maxNumber) > 0) {
            maxNumber = path;
        }

        for (int dir = 0; dir < 4; dir++) {
            int newX = x + dx[dir];
            int newY = y + dy[dir];
            dfs(matrix, visited, newX, newY, path);
        }

        visited[x][y] = false;
    }
}
