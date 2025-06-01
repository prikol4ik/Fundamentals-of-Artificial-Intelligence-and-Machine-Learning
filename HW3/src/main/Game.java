package main;

import java.util.*;

/**
 * Class that drives a game.
 *
 * You don't have to modify this, it's enough to just set your player in GameMain
 */
public class Game
{
	private Board b;
	private List<Player> players;

	public Game(Player p1, Player p2)
	{
		p1.setCellType(Cell.P1);
		p2.setCellType(Cell.P2);

		this.players = new ArrayList<>();
		this.players.add(p1);
		this.players.add(p2);
		this.b = new Board(6, 7);
	};

	public Cell play()
	{
		Optional<Cell> winner;
		int currentPlayerIndex = 0;
		while ((winner = b.getWinner()).isEmpty())
		{
			Player currentPlayer = this.players.get(currentPlayerIndex);

			System.out.println(b);
			System.out.println("Player " + (currentPlayerIndex+1) + "'s turn...\n");
			int c = currentPlayer.decideTurn(new Board(b));
			b.doTurn(currentPlayer.getCellType(), c);

			currentPlayerIndex += 1;
			currentPlayerIndex %= 2;
		}
		return winner.get();
	}

	public Board getBoard()
	{
		return new Board(b);
	}
};
