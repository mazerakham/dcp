"""Puzzle operations and transformations."""

from typing import List, Optional
from models import PuzzleState, Position


GOAL_STATE = PuzzleState(state=(0, 1, 2, 3, 4, 5, 6, 7, 8))


def get_position(index: int) -> Position:
    """Convert linear index to (row, col) position."""
    return (index // 3, index % 3)


def get_index(row: int, col: int) -> int:
    """Convert (row, col) position to linear index."""
    return row * 3 + col


def is_goal(state: PuzzleState) -> bool:
    """Check if state is the goal state."""
    return state.state == GOAL_STATE.state


def get_neighbors(state: PuzzleState) -> List[PuzzleState]:
    """Get all valid neighboring states from current state."""
    neighbors = []
    row, col = get_position(state.empty_pos)

    for dr, dc in [(-1, 0), (1, 0), (0, -1), (0, 1)]:
        new_row, new_col = row + dr, col + dc
        if 0 <= new_row < 3 and 0 <= new_col < 3:
            new_index = get_index(new_row, new_col)
            new_state = list(state.state)
            new_state[state.empty_pos], new_state[new_index] = \
                new_state[new_index], new_state[state.empty_pos]
            neighbors.append(PuzzleState(state=tuple(new_state)))

    return neighbors


def manhattan_distance(state: PuzzleState) -> int:
    """Calculate Manhattan distance heuristic to goal."""
    distance = 0
    for i, val in enumerate(state.state):
        if val != 8:
            curr_row, curr_col = get_position(i)
            goal_row, goal_col = get_position(val)
            distance += abs(curr_row - goal_row) + abs(curr_col - goal_col)
    return distance


def can_move_tile(state: PuzzleState, tile_pos: int) -> bool:
    """Check if tile at position can move to empty space."""
    tile_row, tile_col = get_position(tile_pos)
    empty_row, empty_col = get_position(state.empty_pos)
    return abs(tile_row - empty_row) + abs(tile_col - empty_col) == 1


def move_tile(state: PuzzleState, tile_pos: int) -> Optional[PuzzleState]:
    """Move tile at position to empty space if valid."""
    if not can_move_tile(state, tile_pos):
        return None

    new_state = list(state.state)
    new_state[state.empty_pos], new_state[tile_pos] = \
        new_state[tile_pos], new_state[state.empty_pos]
    return PuzzleState(state=tuple(new_state))


def is_solvable(state: PuzzleState) -> bool:
    """Check if puzzle state is solvable (even number of inversions)."""
    state_list = [x for x in state.state if x != 8]
    inversions = sum(1 for i in range(len(state_list))
                     for j in range(i + 1, len(state_list))
                     if state_list[i] > state_list[j])
    return inversions % 2 == 0
