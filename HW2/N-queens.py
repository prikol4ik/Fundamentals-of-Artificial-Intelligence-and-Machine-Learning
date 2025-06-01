import random
import time


class NQPosition:
    def __init__(self, N):
        self.N = N
        self.board = []
        for _ in range(N):
            self.board.append(random.randint(0, N - 1))

    def value(self):
        # calculate number of conflicts (queens that can capture each other)
        conflicts = 0
        for col in range(self.N):
            for row in range(col + 1, self.N):
                if self.board[col] == self.board[row]:
                    conflicts += 1
                elif abs(self.board[col] - self.board[row]) == abs(col - row):
                    conflicts += 1
        return conflicts

    def make_move(self, move):
        # actually execute a move (change the board)
        col, row = move
        self.board[col] = row

    def best_move(self):
        # find the best move and the value function after making that move
        value = self.value()
        move = None
        for col in range(self.N):
            row_at_start = self.board[col]
            for row in range(self.N):
                if row == row_at_start:
                    continue
                self.board[col] = row
                current_value = self.value()
                if current_value < value:
                    move = (col, row)
                    value = current_value
            self.board[col] = row_at_start
        return move, value


def hill_climbing(pos):
    curr_value = pos.value()
    while True:
        move, new_value = pos.best_move()
        if new_value >= curr_value:
            # no improvement, give up
            return pos, curr_value
        else:
            # position improves, keep searching
            curr_value = new_value
            pos.make_move(move)


def hill_climbing_with_restarts(pos):
    curr_value = pos.value()
    while True:
        move, new_value = pos.best_move()
        if new_value >= curr_value:
            pos = NQPosition(pos.N)
            curr_value = pos.value()
        else:
            curr_value = new_value
            pos.make_move(move)

        if curr_value == 0:
            # no conflict
            return pos, curr_value


def calculate_percentage(N):
    successful_launches = 0

    for trial in range(1000): #1000 launches
        pos = NQPosition(N)
        best_pos, best_value = hill_climbing(pos)

        if best_value == 0:
            successful_launches += 1

    res = (successful_launches / 1000) * 100
    return round(res, 1)

def calculate_percentage_with_restarts(N):

    successful_launches = 0

    for trial in range(1000): #1000 launches
        pos = NQPosition(N)
        best_pos, best_value = hill_climbing_with_restarts(pos)

        if best_value == 0:
            successful_launches += 1

    res = (successful_launches / 1000) * 100
    return res


start_time = time.time()
for N in range(4, 9):
    print(f"Success rate for {N}x{N} board: {calculate_percentage(N)}%")
end_time = time.time()
print(f"Time taken for hill_climbing: {end_time - start_time:.2f} seconds")

print(f"")

print(f"")

start_time = time.time()
for N in range(4, 9):
    print(f"Success rate for {N}x{N} board: {calculate_percentage_with_restarts(N)}%")
end_time = time.time()
print(f"Time taken for hill_climbing_with_restarts: {end_time - start_time:.2f} seconds")
