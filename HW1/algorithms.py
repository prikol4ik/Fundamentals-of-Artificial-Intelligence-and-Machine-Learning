from queue import Queue, PriorityQueue
import time

def my_search_bfs(map_data, start):
    # start can be a tuple (x, y)
    frontier = Queue()
    frontier.put(start)
    came_from = {}
    came_from[start] = None

    iterations = 0

    start = time.time()

    while not frontier.empty():
        iterations += 1
        current = frontier.get()
        x, y = current

        if map_data[x][y] == 'D':
            break

        # up
        if x > 0:
            next_x, next_y = x - 1, y
            if map_data[next_x][next_y] != '*' and (next_x, next_y) not in came_from:
                frontier.put((next_x, next_y))
                came_from[(next_x, next_y)] = current

        # down
        if x < len(map_data) - 1:
            next_x, next_y = x + 1, y
            if map_data[next_x][next_y] != '*' and (next_x, next_y) not in came_from:
                frontier.put((next_x, next_y))
                came_from[(next_x, next_y)] = current

        # left
        if y > 0:
            next_x, next_y = x, y - 1
            if map_data[next_x][next_y] != '*' and (next_x, next_y) not in came_from:
                frontier.put((next_x, next_y))
                came_from[(next_x, next_y)] = current

        # right
        if y < len(map_data[0]) - 1:
            next_x, next_y = x, y + 1
            if map_data[next_x][next_y] != '*' and (next_x, next_y) not in came_from:
                frontier.put((next_x, next_y))
                came_from[(next_x, next_y)] = current

    end = time.time()
    result_time = end - start

    path = []
    while current is not None:
        path.append(current)
        current = came_from[current]
    path.reverse()

    return path, result_time, iterations


def heuristic(a, b):
    # Manhattan distance on a square grid
    return abs(a[0] - b[0]) + abs(a[1] - b[1])


def my_search_greedy(map_data, start, goal):

    frontier = PriorityQueue()
    frontier.put((0, start))
    came_from = {}
    came_from[start] = None

    iterations = 0
    start = time.time()

    while not frontier.empty():
        iterations += 1
        _, current = frontier.get()
        x, y = current


        if current == goal:
            break

        # up
        if x > 0:
            next_x, next_y = x - 1, y
            if map_data[next_x][next_y] != '*' and (next_x, next_y) not in came_from:
                priority = heuristic((next_x, next_y), goal)
                frontier.put((priority, (next_x, next_y)))
                came_from[(next_x, next_y)] = current

        # down
        if x < len(map_data) - 1:
            next_x, next_y = x + 1, y
            if map_data[next_x][next_y] != '*' and (next_x, next_y) not in came_from:
                priority = heuristic((next_x, next_y), goal)
                frontier.put((priority, (next_x, next_y)))
                came_from[(next_x, next_y)] = current

        # left
        if y > 0:
            next_x, next_y = x, y - 1
            if map_data[next_x][next_y] != '*' and (next_x, next_y) not in came_from:
                priority = heuristic((next_x, next_y), goal)
                frontier.put((priority, (next_x, next_y)))
                came_from[(next_x, next_y)] = current

        # right
        if y < len(map_data[0]) - 1:
            next_x, next_y = x, y + 1
            if map_data[next_x][next_y] != '*' and (next_x, next_y) not in came_from:
                priority = heuristic((next_x, next_y), goal)
                frontier.put((priority, (next_x, next_y)))
                came_from[(next_x, next_y)] = current

    end = time.time()
    result_time = end - start

    path = []
    current = goal
    while current is not None:
        path.append(current)
        current = came_from.get(current)

    path.reverse()

    return path, result_time, iterations

def my_search_astar(map_data, start, goal):

    frontier = PriorityQueue()
    frontier.put((0, start))
    came_from = {}
    cost_so_far = {}
    came_from[start] = None
    cost_so_far[start] = 0

    iterations = 0
    start = time.time()

    while not frontier.empty():
        iterations += 1
        _, current = frontier.get()
        x, y = current


        if current == goal:
            break

        # up
        if x > 0:
            next_x, next_y = x - 1, y
            if map_data[next_x][next_y] != '*':
                new_cost = cost_so_far[current] + 1
                if (next_x, next_y) not in cost_so_far or new_cost < cost_so_far[(next_x, next_y)]:
                    cost_so_far[(next_x, next_y)] = new_cost
                    priority = new_cost + heuristic((next_x, next_y), goal)
                    frontier.put((priority, (next_x, next_y)))
                    came_from[(next_x, next_y)] = current

        # down
        if x < len(map_data) - 1:
            next_x, next_y = x + 1, y
            if map_data[next_x][next_y] != '*':
                new_cost = cost_so_far[current] + 1
                if (next_x, next_y) not in cost_so_far or new_cost < cost_so_far[(next_x, next_y)]:
                    cost_so_far[(next_x, next_y)] = new_cost
                    priority = new_cost + heuristic((next_x, next_y), goal)
                    frontier.put((priority, (next_x, next_y)))
                    came_from[(next_x, next_y)] = current

        # left
        if y > 0:
            next_x, next_y = x, y - 1
            if map_data[next_x][next_y] != '*':
                new_cost = cost_so_far[current] + 1
                if (next_x, next_y) not in cost_so_far or new_cost < cost_so_far[(next_x, next_y)]:
                    cost_so_far[(next_x, next_y)] = new_cost
                    priority = new_cost + heuristic((next_x, next_y), goal)
                    frontier.put((priority, (next_x, next_y)))
                    came_from[(next_x, next_y)] = current

        # right
        if y < len(map_data[0]) - 1:
            next_x, next_y = x, y + 1
            if map_data[next_x][next_y] != '*':
                new_cost = cost_so_far[current] + 1
                if (next_x, next_y) not in cost_so_far or new_cost < cost_so_far[(next_x, next_y)]:
                    cost_so_far[(next_x, next_y)] = new_cost
                    priority = new_cost + heuristic((next_x, next_y), goal)
                    frontier.put((priority, (next_x, next_y)))
                    came_from[(next_x, next_y)] = current


    end = time.time()
    result_time = end - start

    path = []
    current = goal
    while current is not None:
        path.append(current)
        current = came_from.get(current)
    path.reverse()

    return path, result_time, iterations


def load_map(filename):
    with open(filename, 'r') as f:
        return [l.strip() for l in f.readlines() if len(l)>1]


map_300x300 = load_map("cave300x300")
map_600x600 = load_map("cave600x600")
map_900x900 = load_map("cave900x900")

start_300 = (2, 2)
goal_300 = (295, 257)

start_600 = (2, 2)
goal_600 = (598, 595)

start_900 = (2, 2)
goal_900 = (898, 895)

path_300_bfs, time_300_bfs, iterations_300_bfs = my_search_bfs(map_300x300, start_300)
path_600_bfs, time_600_bfs, iterations_600_bfs = my_search_bfs(map_600x600, start_600)
path_900_bfs, time_900_bfs, iterations_900_bfs = my_search_bfs(map_900x900, start_900)

print(f"BFS 300x300: path length: {len(path_300_bfs)}, time: {time_300_bfs}, iterations: {iterations_300_bfs}")
print(f"BFS 600x600: path length: {len(path_600_bfs)}, time: {time_600_bfs}, iterations: {iterations_600_bfs}")
print(f"BFS 900x900: path length: {len(path_900_bfs)}, time: {time_900_bfs}, iterations: {iterations_900_bfs}")

path_300_greedy, time_300_greedy, iterations_300_greedy = my_search_greedy(map_300x300, start_300, goal_300)
path_600_greedy, time_600_greedy, iterations_600_greedy = my_search_greedy(map_600x600, start_600, goal_600)
path_900_greedy, time_900_greedy, iterations_900_greedy = my_search_greedy(map_900x900, start_900, goal_900)

print(f"Greedy 300x300: path length: {len(path_300_greedy)}, time: {time_300_greedy}, iterations: {iterations_300_greedy}")
print(f"Greedy 600x600: path length: {len(path_600_greedy)}, time: {time_600_greedy}, iterations: {iterations_600_greedy}")
print(f"Greedy 900x900: path length: {len(path_900_greedy)}, time: {time_900_greedy}, iterations: {iterations_900_greedy}")

path_300_astar, time_300_astar, iterations_300_astar = my_search_astar(map_300x300, start_300, goal_300)
path_600_astar, time_600_astar, iterations_600_astar = my_search_astar(map_600x600, start_600, goal_600)
path_900_astar, time_900_astar, iterations_900_astar = my_search_astar(map_900x900, start_900, goal_900)

print(f"A* 300x300: path length: {len(path_300_astar)}, time: {time_300_astar}, iterations: {iterations_300_astar}")
print(f"A* 600x600: path length: {len(path_600_astar)}, time: {time_600_astar}, iterations: {iterations_600_astar}")
print(f"A* 900x900: path length: {len(path_900_astar)}, time: {time_900_astar}, iterations: {iterations_900_astar}")


def visualize(map_data, path, start, goal, filename):
    visual_map = [list(row) for row in map_data]

    for (x, y) in path:
        if (x, y) == start:
            visual_map[x][y] = 's'
        elif (x, y) == goal:
            visual_map[x][y] = 'D'
        else:
            visual_map[x][y] = '.'

    with open(filename, 'w') as f:
        for row in visual_map:
            f.write(''.join(row) + '\n')

visualize(map_300x300, path_300_bfs, start_300, goal_300, 'bfs_300x300_result.txt')
visualize(map_300x300, path_300_greedy, start_300, goal_300, 'greedy_300x300_result.txt')
visualize(map_300x300, path_300_astar, start_300, goal_300, 'astar_300x300_result.txt')


