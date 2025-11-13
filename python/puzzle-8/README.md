# Puzzle-8 Solver

A modular implementation of the 8-puzzle game with A* solver, CLI, and GUI interfaces.

## Installation

```bash
pip install -r requirements.txt
```

## Usage

### GUI Mode (default)
```bash
python -m python.puzzle-8
```

**Features:**
- Click tiles adjacent to the empty space to move them
- **Reset** button: Return to solved state
- **Solve** button: Automatically solve using A* algorithm
- Scramble the puzzle manually, then watch it solve itself!

### CLI Mode
```bash
python -m python.puzzle-8 --cli
```

Enter puzzle as 9 space-separated values (0-7 for tiles, 8 for empty).

**Example:**
```
Enter puzzle: 1 2 3 4 5 6 7 8 0
```

## Architecture

- `models.py` - Pydantic models and types
- `operations.py` - Puzzle operations and utilities
- `solver.py` - A* algorithm implementation
- `cli.py` - Command-line interface
- `gui.py` - Pygame GUI
- `__main__.py` - Entry point

## Algorithm

Uses A* search with Manhattan distance heuristic for optimal solutions.
