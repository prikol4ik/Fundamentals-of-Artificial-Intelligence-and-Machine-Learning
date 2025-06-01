package main;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Board representation.
 *
 * It's enough to understand what the public methods do.
 */
public class Board
{
	private List<Cell> board = new ArrayList<Cell>();
	private Set<Integer> avail = new HashSet<Integer>();
	private List<Integer> topEmptyRow = new ArrayList<Integer>();
	private Optional<Cell> winner = Optional.empty();
	private int rows;
	private int cols;
	private static final List<List<Integer>> DIFFS = Arrays.asList
	(
		Arrays.asList(0, 1),
		Arrays.asList(1, 0),
		Arrays.asList(1, 1),
		Arrays.asList(1, -1)
	);

	public Board(int rows, int columns)
	{
		this.rows = rows;
		this.cols = columns;

		for (int c = 0; c < cols; ++c)
		{
			this.topEmptyRow.add(0);
			this.avail.add(c);
		}

		for (int idx = 0; idx < rows * columns; ++idx)
		{
			this.board.add(Cell.EMPTY);
		}
	}

	public Board(Board b)
	{
		this.board = new ArrayList<Cell>(b.board);
		this.avail = new HashSet<Integer>(b.avail);
		this.topEmptyRow = new ArrayList<Integer>(b.topEmptyRow);
		this.winner = Optional.empty();
		if (b.winner.isPresent())
		{
			this.winner = Optional.of(b.winner.get());
		}
		this.rows = b.rows;
		this.cols = b.cols;
	}

	public Cell get(int r, int c)
	{
		return this.board.get(r * this.cols + c);
	}

	private Cell set(int r, int c, Cell ct)
	{
		return this.board.set(r * this.cols + c, ct);
	}

	public void doTurn(Cell ct, int col)
	{
		if (!this.avail.contains(col))
		{
			throw new RuntimeException("Row exceeded");
		}

		int top = this.topEmptyRow.get(col);
		this.set(top, col, ct);
		if (this.winner.isEmpty() && this.checkWinner(top, col))
		{
			this.winner = Optional.of(ct);
		}

		top += 1;
		this.topEmptyRow.set(col, top);

		if (top == this.rows)
		{
			this.avail.remove(col);
		}
	};

	public Set<Integer> getAvailableColumns()
	{
		return Collections.unmodifiableSet(this.avail);
	}

	public Optional<Cell> getWinner()
	{
		return this.winner;
	}

	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();

		for (int r = this.rows-1; r >= 0; --r)
		{
			sb.append(r);
			sb.append(" ");
			for (int c = 0; c < this.cols; ++c)
			{
				sb.append(Cell.cellString(this.get(r, c)));
			}
			sb.append("\n");
		}
		sb.append("\nX ");
		for (int c = 0; c < this.cols; ++c)
		{
			sb.append(c);
		}
		return sb.toString();
	}

	/**
	 * Check if there is a winning streak that includes (r, c)
	 */
	private boolean checkWinner(int r, int c)
	{
		for (List<Integer> direction: DIFFS)
		{
			List<Integer> oppositeDirection = direction
				.stream()
				.mapToInt(x -> -x)
				.boxed()
				.collect(Collectors.toList());

			int cnt =
				this.countLine(r, c, direction)
				+ this.countLine(r, c, oppositeDirection)
				- 1; // (r, c) is counted twice

			if (cnt >= 4) return true;
		}

		return false;
	}


	/**
	 * Count how many elements are the same starting from (r, c) going in direction d.
	 */
	private int countLine(int r, int c, List<Integer> d)
	{
		int cnt = 0;
		Cell p = this.get(r, c);
		while (r >= 0 && c >= 0 && r < this.rows && c < this.cols)
		{
			if (this.get(r, c) != p) break;
			++cnt;
			r += d.get(0);
			c += d.get(1);
		}
		return cnt;
	}
};
