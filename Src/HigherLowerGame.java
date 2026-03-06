import java.util.Scanner;
import java.util.Random;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class HigherLowerGame {
    private static final String SCORE_FILE = "highscore.txt";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        int[] scores = loadScores();
        System.out.println("Welcome back! High score: " + scores[0] + " | Total score: " + scores[1]);

        int targetNumber = random.nextInt(100) + 1;
        int currentGuess = 0;
        int currentRoundScore = 100;

        System.out.println("I've picked a number between 1 and 100. Start guessing!");

        while (currentGuess != targetNumber) {
            System.out.print("Enter your guess: ");

            try {
                currentGuess = scanner.nextInt();
            } catch (Exception e) {
                System.out.println("Please enter a number!");
                scanner.next();
                continue;
            }

            if (currentGuess < targetNumber) {
                System.out.println("HIGHER!");
                currentRoundScore -= 5;
            } else if (currentGuess > targetNumber) {
                System.out.println("LOWER!");
                currentRoundScore -= 5;
            } else {
                System.out.println("Correct! You win this round.");
            }

            if (currentRoundScore < 0) currentRoundScore = 0;
        }

        System.out.println("Your score for this round: " + currentRoundScore);
        saveScores(currentRoundScore, scores[1] + currentRoundScore);

        scanner.close();
    }

    public static void saveScores(int roundScore, int totalScore) {
        int highScore = loadScores()[0];
        if (roundScore > highScore) highScore = roundScore;

        try (FileWriter writer = new FileWriter(SCORE_FILE)) {
            writer.write(highScore + "" + totalScore);
            System.out.println("High score: " + highScore + " | Total score: " + totalScore);
        } catch (IOException e) {
            System.out.println("Error saving score: " + e.getMessage());
        }
    }

    public static int[] loadScores() {
        File file = new File(SCORE_FILE);
        if (!file.exists()) return new int[]{0, 0};

        try (Scanner fileScanner = new Scanner(file)) {
            if (fileScanner.hasNextInt()) {
                int high = fileScanner.nextInt();
                int total = fileScanner.hasNextInt() ? fileScanner.nextInt() : 0;
                return new int[]{high, total};
            }
        } catch (IOException e) {
            System.out.println("Error loading score.");
        }
        return new int[]{0, 0};
    }
}