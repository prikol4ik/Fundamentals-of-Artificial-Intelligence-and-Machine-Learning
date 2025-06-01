import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;

import main.Board;
import main.Cell;
import org.junit.Before;
import org.junit.Test;

public class BoardTest
{
	private Board board;

	@Before
	public void setup()
	{
		this.board = new Board(6, 7);
	}

	private void doTurn(Cell player, int c)
	{
		this.board.doTurn(player, c);
	}

	private void doTurnNoWinner(Cell player, int c)
	{
		this.doTurn(player, c);
		assertTrue(this.board.getWinner().isEmpty());
	}

	private void doTurnWithWinner(Cell player, int c)
	{
		this.doTurnWithWinner(player, c, player);
	}

	private void doTurnWithWinner(Cell player, int c, Cell winner)
	{
		this.doTurn(player, c);
		assertTrue(this.board.getWinner().isPresent());
		assertEquals(winner, this.board.getWinner().get());
	}

	private void doTurnNonFilling(Cell player, int c)
	{
		this.doTurn(player, c);
		assertTrue(this.board.getAvailableColumns().contains(c));
	}

	private void doTurnFilling(Cell player, int c)
	{
		this.doTurn(player, c);
		assertFalse(this.board.getAvailableColumns().contains(c));
	}

	private void fillColumn(Cell player, int c)
	{
		for (int i = 0; i < 5; ++i)
		{
			doTurnNonFilling(player, c);
		}
		doTurnFilling(player, c);
	}

	@Test
	public void testWinnerHorizontal()
	{
		/*
		.......
		.......
		.......
		.......
		.......
		1111...
		*/
		doTurnNoWinner(Cell.P1, 0);
		doTurnNoWinner(Cell.P1, 1);
		doTurnNoWinner(Cell.P1, 2);
		doTurnWithWinner(Cell.P1, 3);
	}

	@Test
	public void testWinnerHorizontalMid()
	{
		/*
		.......
		.......
		.......
		.......
		.......
		..1111.
		*/
		doTurnNoWinner(Cell.P1, 2);
		doTurnNoWinner(Cell.P1, 4);
		doTurnNoWinner(Cell.P1, 5);
		doTurnWithWinner(Cell.P1, 3);
	}

	@Test
	public void testWinnerVertical()
	{
		/*
		.......
		.......
		1......
		1......
		1......
		1......
		*/
		doTurnNoWinner(Cell.P1, 0);
		doTurnNoWinner(Cell.P1, 0);
		doTurnNoWinner(Cell.P1, 0);
		doTurnWithWinner(Cell.P1, 0);
	}

	@Test
	public void testWinnerDiagonal()
	{
		/*
		.......
		.......
		...1...
		..12...
		.122...
		1222...
		*/
		doTurnNoWinner(Cell.P1, 0);

		doTurnNoWinner(Cell.P2, 1);
		doTurnNoWinner(Cell.P1, 1);

		doTurnNoWinner(Cell.P2, 2);
		doTurnNoWinner(Cell.P2, 2);
		doTurnNoWinner(Cell.P1, 2);

		doTurnNoWinner(Cell.P2, 3);
		doTurnNoWinner(Cell.P2, 3);
		doTurnNoWinner(Cell.P2, 3);
		doTurnWithWinner(Cell.P1, 3);
	}

	@Test
	public void testWinnerAntiDiagonal()
	{
		/*
		.......
		.......
		1......
		21.....
		221....
		2221...
		*/
		doTurnNoWinner(Cell.P2, 0);
		doTurnNoWinner(Cell.P2, 0);
		doTurnNoWinner(Cell.P2, 0);
		doTurnNoWinner(Cell.P1, 0);

		doTurnNoWinner(Cell.P2, 1);
		doTurnNoWinner(Cell.P2, 1);
		doTurnNoWinner(Cell.P1, 1);

		doTurnNoWinner(Cell.P2, 2);
		doTurnNoWinner(Cell.P1, 2);

		doTurnWithWinner(Cell.P1, 3);
	}

	@Test
	public void testAvail()
	{
		for (int i = 0; i <= 6; ++i)
		{
			fillColumn(Cell.P1, i);
		}
		assertTrue(this.board.getAvailableColumns().isEmpty());
	}

	@Test
	public void copyTest()
	{
		doTurnNoWinner(Cell.P1, 0);
		doTurnNoWinner(Cell.P1, 0);
		doTurnNoWinner(Cell.P1, 0);

		Board b = new Board(this.board);
		b.doTurn(Cell.P1, 0);
		assertTrue(b.getWinner().isPresent());
		assertEquals(Cell.P1, b.getWinner().get());
	}

	@Test
	public void winnerDoesntChange()
	{
		doTurnNoWinner(Cell.P1, 0);
		doTurnNoWinner(Cell.P1, 0);
		doTurnNoWinner(Cell.P1, 0);
		doTurnWithWinner(Cell.P1, 0);

		doTurnWithWinner(Cell.P2, 1, Cell.P1);
		doTurnWithWinner(Cell.P2, 1, Cell.P1);
		doTurnWithWinner(Cell.P2, 1, Cell.P1);
		doTurnWithWinner(Cell.P2, 1, Cell.P1);
	}
}
