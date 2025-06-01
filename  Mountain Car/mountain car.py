import numpy as np
import gymnasium as gym
import matplotlib.pyplot as plt


def discretize_state(state, bins):
    return tuple(np.digitize(s, bins[b]) for b, s in enumerate(state))


state_bins = [np.linspace(-1.2, 0.6, 20), np.linspace(-0.07, 0.07, 20)]
n_actions = 3

episodes = 3000
max_steps = 200
alpha = 0.2
gamma = 0.95
epsilon = 1.0
epsilon_min = 0.01
epsilon_decay = 0.99


Q_table = np.zeros(tuple(len(bins) + 1 for bins in state_bins) + (n_actions,))

episode_lengths = []
max_positions = []

env = gym.make("MountainCar-v0")

for episode in range(episodes):
    state, _ = env.reset()
    state_discrete = discretize_state(state, state_bins)
    total_reward = 0
    max_position = -1.2

    for step in range(max_steps):
        if np.random.rand() < epsilon:
            action = np.random.choice(n_actions)
        else:
            action = np.argmax(Q_table[state_discrete])

        next_state, reward, terminated, truncated, _ = env.step(action)
        next_state_discrete = discretize_state(next_state, state_bins)

        best_next_action = np.argmax(Q_table[next_state_discrete])
        Q_table[state_discrete + (action,)] += alpha * (
                reward + gamma * Q_table[next_state_discrete + (best_next_action,)] - Q_table[
            state_discrete + (action,)]
        )

        state_discrete = next_state_discrete
        max_position = max(max_position, next_state[0])
        total_reward += reward

        if terminated or truncated:
            break

    epsilon = max(epsilon_min, epsilon * epsilon_decay)

    episode_lengths.append(step + 1)
    max_positions.append(max_position)

    if (episode + 1) % 100 == 0:
        print(f"Episode {episode + 1}/{episodes}, Epsilon: {epsilon:.3f}")

env.close()

plt.figure(figsize=(12, 5))

plt.subplot(1, 2, 1)
plt.plot(max_positions)
plt.title("Max position")
plt.xlabel("Episode")
plt.ylabel("Max Position")
plt.grid()

plt.subplot(1, 2, 2)
plt.plot(episode_lengths)
plt.title("Episode length")
plt.xlabel("Episode")
plt.ylabel("Steps")
plt.grid()

plt.tight_layout()
plt.show()

env = gym.make("MountainCar-v0", render_mode="human")
for _ in range(5):
    state, _ = env.reset()
    state_discrete = discretize_state(state, state_bins)

    for _ in range(max_steps):
        action = np.argmax(Q_table[state_discrete])
        next_state, _, terminated, truncated, _ = env.step(action)
        state_discrete = discretize_state(next_state, state_bins)
        if terminated or truncated:
            break

env.close()
