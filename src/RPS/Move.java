package RockPaperScissors.src.RPS;

public enum Move {
    ROCK,
    PAPER,
    SCISSORS;

    public static Move fromInt(int choice) {
        switch (choice) {
            case 1:
                return ROCK;
            case 2:
                return PAPER;
            case 3:
                return SCISSORS;
            default:
                return null;
        }
    }

    @Override
    public String toString() {
        switch (this) {
            case ROCK:
                return "Rock";
            case PAPER:
                return "Paper";
            case SCISSORS:
                return "Scissors";
            default:
                return super.toString();
        }
    }
    
}
