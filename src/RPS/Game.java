package RockPaperScissors.src.RPS;
import java.util.Scanner;

public class Game {
    private Scanner scanner;
    private AIPlayer ai;
    private int playerWins;
    private int computerWins;
    private int ties;
    private int longestStreak;
    private int currentStreak; //positive = player, negative = computer

    public Game() {
        scanner = new Scanner(System.in);
    }

    public void start() {
        printWelcome();
        AIPlayer.Difficulty difficulty = chooseDifficulty();
        ai = new AIPlayer(difficulty);

        boolean running = true;
        while (running) {
            printMenu();
            int choice = getIntInput("Choose an option: ");

            switch (choice) {
                case 1:
                    playRound();
                    break;
                case 2:
                    showStats();
                    break;
                case 3:
                    System.out.println("Goodbye. Thanks for playing!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private void printWelcome() {
        System.out.println("\n=== Play Rock-Paper-Scissors ===\n");
    }

    private AIPlayer.Difficulty chooseDifficulty() {
        System.out.println("Select AI Difficulty:");
        System.out.println("1. Easy");
        System.out.println("2. Medium");
        System.out.println("3. Hard\n");

        while (true) {
            int choice = getIntInput("Choose difficulty: ");
            switch (choice) {
                case 1:
                    return AIPlayer.Difficulty.EASY;
                case 2:
                    return AIPlayer.Difficulty.MEDIUM;
                case 3:
                    return AIPlayer.Difficulty.HARD;
                default:
                    System.out.println("Invalid choice. Please select 1, 2, or 3.");
            }
        }
    }

    private void printMenu() {
        System.out.println("\n=== Main Menu ===");
        System.out.println("1. Play Round");
        System.out.println("2. View Stats");
        System.out.println("3. Exit\n");
    }

    private void playRound() {
        System.out.println("\n1. Rock");
        System.out.println("2. Paper");
        System.out.println("3. Scissors\n");

        int choice = getIntInput("Your move: ");
        Move playerMove = Move.fromInt(choice);

        if (playerMove == null) {
            System.out.println("Invalid move.");
            return;
        }

        dramaticCountdown();

        Move computerMove = ai.getComputerMove();
        ai.recordPlayerMove(playerMove);

        System.out.println("\nYou played: " + playerMove);
        System.out.println("Computer played: \n" + computerMove);

        int result = compareMoves(playerMove, computerMove);
        updateStats(result);
        printRoundResult(result);
    }

    private void dramaticCountdown() {
        try {
            System.out.print("\nRock");
            Thread.sleep(800);
            System.out.print("... Paper");
            Thread.sleep(800);
            System.out.println("... Scissors");
            Thread.sleep(800);
            System.out.println("... Shoot!\n");
            Thread.sleep(400);
        } catch (InterruptedException e) {
            //ignore
        }
    }

    private int compareMoves(Move player, Move computer) {
        if (player == computer) {
            return 0; //tie
        }
        
        switch(player) {
            case ROCK:
                return (computer == Move.SCISSORS) ? 1 : -1;
            case PAPER:
                return (computer == Move.ROCK) ? 1 : -1;
            case SCISSORS:
                return (computer == Move.PAPER) ? 1 : -1;
            default:
                return 0;
        }
    }

    private void updateStats(int result) {
        if (result == 1) {
            playerWins++;
            if (currentStreak >= 0) {
                currentStreak++;
            } else {
                currentStreak = 1;
            }
        } else if (result == -1) {
            computerWins++;
            if (currentStreak <= 0) {
                currentStreak--;
            } else {
                currentStreak = -1;
            }
        } else {
            ties++;
            //streak remains unchanged
        }

        int absStreak = Math.abs(currentStreak);
        if (absStreak > longestStreak) {
            longestStreak = absStreak;
        }
    }

    private void printRoundResult(int result) {
        if (result == 1) {
            System.out.println("\nYou win this round!");
        } else if (result == -1) {
            System.out.println("\nComputer wins this round!");
        } else {
            System.out.println("\nThis round is a tie!");
        }
    }

    private void showStats() {
        int totalGames = playerWins + computerWins + ties;

        System.out.println("\n=== Game Stats ===\n");
        System.out.println("Total Games: " + totalGames);
        System.out.println("You: " + playerWins);
        System.out.println("Computer: " + computerWins);
        System.out.println("Ties: " + ties);

        if (totalGames > 0) {
            double winRate = (playerWins * 100) / totalGames;
            System.out.printf("Your Win Rate: %.1f%%\n", winRate);
        }

        System.out.println("Longest Streak (either side): " + longestStreak + "\n");
    }

    private int getIntInput(String message) {
        System.out.print(message);
        while (!scanner.hasNextInt()) {
            System.out.print("Enter a valid number: ");
            scanner.next();
        }
        int value = scanner.nextInt();
        scanner.nextLine(); //consume newline
        return value;
    }
}