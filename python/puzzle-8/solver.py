"""A* algorithm solver for Puzzle-8."""

import heapq
from typing import List, Optional, Set
from models import PuzzleState
from operations import is_goal, get_neighbors, manhattan_distance


class SearchNode:
    """Node for A* search with priority queue ordering."""

    def __init__(self, state: PuzzleState, path: List[PuzzleState],
                 g_score: int, counter: int):
        self.state = state
        self.path = path
        self.g_score = g_score
        self.h_score = manhattan_distance(state)
        self.f_score = g_score + self.h_score
        self.counter = counter

    def __lt__(self, other: 'SearchNode') -> bool:
        if self.f_score != other.f_score:
            return self.f_score < other.f_score
        return self.counter < other.counter


def solve(initial_state: PuzzleState) -> Optional[List[PuzzleState]]:
    """
    Solve puzzle using A* algorithm.

    Returns:
        List of states from initial to goal, or None if unsolvable.
    """
    if is_goal(initial_state):
        return [initial_state]

    pq = [SearchNode(initial_state, [initial_state], 0, 0)]
    visited: Set[PuzzleState] = {initial_state}
    counter = 1

    while pq:
        node = heapq.heappop(pq)

        if is_goal(node.state):
            return node.path

        for neighbor in get_neighbors(node.state):
            if neighbor not in visited:
                visited.add(neighbor)
                new_node = SearchNode(
                    neighbor,
                    node.path + [neighbor],
                    node.g_score + 1,
                    counter
                )
                heapq.heappush(pq, new_node)
                counter += 1

    return None
