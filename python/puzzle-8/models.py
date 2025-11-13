"""Pydantic models for Puzzle-8 types."""

from typing import Tuple, Literal
from pydantic import BaseModel, Field, field_validator


TileValue = Literal[0, 1, 2, 3, 4, 5, 6, 7, 8]
Position = Tuple[int, int]
BoardState = Tuple[TileValue, TileValue, TileValue, TileValue, TileValue,
                   TileValue, TileValue, TileValue, TileValue]


class PuzzleState(BaseModel):
    """Immutable puzzle state with validation."""

    state: BoardState

    @field_validator('state')
    @classmethod
    def validate_state(cls, v: Tuple[int, ...]) -> BoardState:
        if len(v) != 9:
            raise ValueError("State must have exactly 9 tiles")
        if set(v) != set(range(9)):
            raise ValueError("State must contain digits 0-8 exactly once")
        return v  # type: ignore

    @property
    def empty_pos(self) -> int:
        """Index of the empty tile (8)."""
        return self.state.index(8)

    def __hash__(self) -> int:
        return hash(self.state)

    def __str__(self) -> str:
        lines = []
        for i in range(0, 9, 3):
            row = [' ' if self.state[i + j] == 8 else str(self.state[i + j])
                   for j in range(3)]
            lines.append(' '.join(row))
        return '\n'.join(lines)

    class Config:
        frozen = True
