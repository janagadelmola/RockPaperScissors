package RockPaperScissors.src.RPS;
import java.util.Random;

public class AIPlayer {
    private int rockCount;
    private int paperCount;
    private int scissorsCount;
    private Random random;
    private Difficulty difficulty;

    public enum Difficulty {
        EASY,
        MEDIUM,
        HARD
    }

    public AIPlayer(Difficulty difficulty) {
        this.difficulty = difficulty;
        this.random = new Random();
    }

    public void recordPlayerMove(Move move) {
        if (move == null) return;

        switch (move) {
            case ROCK:
                rockCount++;
                break;
            case PAPER:
                paperCount++;
                break;
            case SCISSORS:
                scissorsCount++;
                break;
        }
    }

    public Move getComputerMove() {
        switch (difficulty) {
            case EASY:
                return randomMove();
            case MEDIUM:
                // 50% smart, 50% random
                if (random.nextBoolean()) {
                    return smartMove();
                } else {
                    return randomMove();
                }
            case HARD:
                return smartMove();
            default:
                return randomMove();
        }
    }

    private Move randomMove() {
        int choice = random.nextInt(3);
        switch (choice) {
            case 0:
                return Move.ROCK;
            case 1:
                return Move.PAPER;
            default:
                return Move.SCISSORS;
        }
    }

    private Move smartMove() {
        //predict player's most common move and counter it
        if (rockCount >= paperCount && rockCount >= scissorsCount) {
            return Move.PAPER; // Counter Rock
        } else if (paperCount >= rockCount && paperCount >= scissorsCount) {
            return Move.SCISSORS; // Counter Paper
        } else {
            return Move.ROCK; // Counter Scissors
        }
    }
    
}
