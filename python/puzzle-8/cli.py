"""Command-line interface for Puzzle-8."""

from typing import List
from models import PuzzleState
from operations import is_solvable
from solver import solve


def parse_input(input_str: str) -> PuzzleState:
    """Parse space-separated input string to PuzzleState."""
    values = input_str.strip().split()
    if len(values) != 9:
        raise ValueError("Must provide exactly 9 values")

    # Convert to integers, replacing space/empty with 8
    tiles = []
    for v in values:
        if v in [' ', '_', 'x', 'X']:
            tiles.append(8)
        else:
            tiles.append(int(v))

    return PuzzleState(state=tuple(tiles))


def print_solution(solution: List[PuzzleState]) -> None:
    """Print solution path with step numbers."""
    print(f"\nSolution found in {len(solution) - 1} moves:\n")
    for i, state in enumerate(solution):
        print(f"Step {i}:")
        print(state)
        print()


def run_cli() -> None:
    """Run the command-line interface."""
    print("Puzzle-8 Solver")
    print("=" * 40)
    print("Enter puzzle state as 9 space-separated values.")
    print("Use 0-7 for tiles, 8 (or _, x) for empty space.")
    print("Example: 1 2 3 4 5 6 7 8 0")
    print()

    try:
        user_input = input("Enter puzzle: ")
        state = parse_input(user_input)

        print("\nInitial state:")
        print(state)

        if not is_solvable(state):
            print("\nThis puzzle configuration is not solvable!")
            return

        print("\nSolving...")
        solution = solve(state)

        if solution:
            print_solution(solution)
        else:
            print("\nNo solution found!")

    except ValueError as e:
        print(f"\nError: {e}")
    except KeyboardInterrupt:
        print("\n\nExiting...")
