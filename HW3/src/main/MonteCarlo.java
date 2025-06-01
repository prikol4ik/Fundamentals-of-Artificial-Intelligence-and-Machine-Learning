package main;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class MonteCarlo extends Player {

    private Random random = new Random();

    @Override
    public int decideTurn(Board b) {
        Set<Integer> availableColumns = b.getAvailableColumns();
        Map<Integer, Double> winningPercentages = new HashMap<>();

        for (int col : availableColumns) {
            int wins = 0;
            int draws = 0;

            for (int i = 0; i < 100; i++) {
                Board simulationBoard = new Board(b);
                simulationBoard.doTurn(this.getCellType(), col);

                Cell result = simulateGame(simulationBoard, this.getCellType());

                if (result == this.getCellType()) {
                    wins++;
                } else if (result == Cell.EMPTY) {
                    draws++;
                }
            }
            winningPercentages.put(col, wins + draws * 0.5);
        }

        saveWinningPercentage(winningPercentages);

        return Collections.max(winningPercentages.entrySet(), Map.Entry.comparingByValue()).getKey();
    }

    private void saveWinningPercentage(Map<Integer, Double> winRates) {
        try (FileWriter writer = new FileWriter("winning_percentages.txt", true)) {
            writer.write("winning percentage for current position:\n");
            for (Map.Entry<Integer, Double> entry : winRates.entrySet()) {
                writer.write("column " + entry.getKey() + ": " + entry.getValue() + "\n");
            }
            writer.write("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Cell simulateGame(Board board, Cell startingPlayer) {
        Cell currentPlayer = startingPlayer;
        Set<Integer> availableColumns = board.getAvailableColumns();

        while (!board.getWinner().isPresent() && !availableColumns.isEmpty()) {
            List<Integer> moves = new ArrayList<>(availableColumns);
            int randomMove = moves.get(random.nextInt(moves.size()));

            board.doTurn(currentPlayer, randomMove);
            currentPlayer = (currentPlayer == Cell.P1) ? Cell.P2 : Cell.P1;
            availableColumns = board.getAvailableColumns();
        }

        return board.getWinner().orElse(Cell.EMPTY);
    }
}
