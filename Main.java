public class Main {
    public static void main(String[] args) {
        int[][] matrix = {
            {9, 1, 3},
            {6, 5, 2},
            {7, 4, 8}
        };

        String result = MaxPathFinder.findMaxNumber(matrix);

        System.out.println("Максимальное число: " + result);
    }
}
