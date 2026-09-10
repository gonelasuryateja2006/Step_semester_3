import java.util.*;
public class RockPaperScissors {
    static String playRound(String playerMove, String computerMove) {
        if (playerMove.equals(computerMove)) {
            return "Draw";
        }
        if ((playerMove.equals("Rock") && computerMove.equals("Scissors")) ||
                (playerMove.equals("Paper") && computerMove.equals("Rock")) ||
                (playerMove.equals("Scissors") && computerMove.equals("Paper"))) {
            return "Player Wins";
        }
        return "Computer Wins";
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Random random = new Random();
        String[] moves = {"Rock", "Paper", "Scissors"};
        String[][] table = new String[5][4];
        int wins = 0;
        int losses = 0;
        int draws = 0;
        for (int i = 0; i < 5; i++) {
            System.out.print("Enter your move (Rock/Paper/Scissors): ");
            String playerMove = sc.next();
            playerMove = playerMove.substring(0, 1).toUpperCase()
                    + playerMove.substring(1).toLowerCase();
            String computerMove = moves[random.nextInt(3)];
            String result = playRound(playerMove, computerMove);
            if (result.equals("Player Wins")) {
                wins++;
            } else if (result.equals("Computer Wins")) {
                losses++;
            } else {
                draws++;
            }
            table[i][0] = String.valueOf(i + 1);
            table[i][1] = playerMove;
            table[i][2] = computerMove;
            table[i][3] = result;
            System.out.println("Result: " + result);
            System.out.println();
        }
        System.out.println("========== FINAL SUMMARY ==========");
        System.out.printf("%-8s %-15s %-15s %-18s%n",
                "Round", "Player Move", "Computer Move", "Result");
        for (String[] row : table) {
            System.out.printf("%-8s %-15s %-15s %-18s%n",
                    row[0], row[1], row[2], row[3]);
        }
        double winPercentage = (wins / 5.0) * 100;
        System.out.println("\nWins   : " + wins);
        System.out.println("Losses : " + losses);
        System.out.println("Draws  : " + draws);
        System.out.printf("Win %%  : %.1f%%%n", winPercentage);
        sc.close();
    }
}