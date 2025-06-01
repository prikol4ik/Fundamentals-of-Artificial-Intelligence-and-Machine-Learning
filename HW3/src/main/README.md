# ITI0210 Homework 3: Monte Carlo
---

## `Results`
## position

5 .......  
4 .......  
3 .......  
2 .......  
1 ......2  
0 2.111.2  

X 0123456


## winning percentage for current position:  
column 0: 92.0  
column 1: 100.0  
column 2: 93.0  
column 3: 97.0  
column 4: 93.0  
column 5: 100.0  
column 6: 95.0

---
This repository contains a template to aid you in solving this homework.

Please read through this whole file before you start solving as there are quite many hints on how you can solve this that may change how you would approach this problem.
You don't have to implement everything described in here, as long as you create a monte carlo-based AI player and add a report you will have solved the homework.

## `Board` and `Cell`

`Cell` is an enum that represents cells in a board, it's also used as a way to represent a player.

`Board` is a representation of the playing board.
You will probably not have to change it at all.

You can print it out (`System.out.println(board)`) to get a visual representation.
Here is a listing of public methods that are of most usefulness:
* `Board(Board)`: copy constructor, you can use it to make copies for your rollouts
* `void doTurn(Cell ct, int col)`: does turn for player `ct` (expected `Cell.P1` or `Cell.P2`) at column `col`
* `Set<Integer> getAvailableColumns()`: returns a set of columns that can be used for this turn
* `Optional<Cell> getWinner()`: returns a winner if one already exists.

## `Game` and `GameMain`

`GameMain` is a main class that drives the game, `Game` is a class that manages a single game.
You can modify `GameMain` to setup players that should play against each other.
`Game` class itself you probably don't have to change.

## `Player` and `HumanPlayer`

`Player` is a base class for all players, your solution should extend it.
`HumanPlayer` is a sample implementation for a human player (reads input from standard input).

Here is a rough outline of what you have to do in order to solve the homework:
```
1. Extend Player class with your own
2. Decide somehow on what amount of games it should play (constructor parameter, constant, etc.)
3. Create implementation for decideTurn:
	1. For each game you should play:
		1. Create a copy of given board
		2. Select a turn to start with, remember it for later
		3. Randomly take turns until either the board is filled or you get a winner
		4. If you win, give a positive grade to the starting turn
		5. If you get a tie, give a neutral grade to the starting turn
		6. If you lose, give a negative grade to the starting turn
	2. Return which starting turn scored the most (break ties somehow if multiple scored top points)
```

The outline uses terms `positive, negative and neutral grade` in somewhat loose terms, it's up to you how to interpret this.
One possibility is to use numbers `2, 0, 1`.
You can experiment to see which numbers give you best results and summarize in your report.
