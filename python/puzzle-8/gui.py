"""GUI visualizer for Puzzle-8 using pygame."""

import pygame
from typing import Optional, List
from models import PuzzleState
from operations import GOAL_STATE, move_tile, is_solvable, is_goal
from solver import solve


class Colors:
    """Color constants."""
    WHITE = (255, 255, 255)
    BLACK = (0, 0, 0)
    GRAY = (200, 200, 200)
    DARK_GRAY = (100, 100, 100)
    BLUE = (70, 130, 180)
    GREEN = (50, 200, 50)
    RED = (200, 50, 50)


class PuzzleGUI:
    """Interactive Puzzle-8 GUI."""

    TILE_SIZE = 100
    MARGIN = 10
    BOARD_SIZE = 3 * TILE_SIZE + 4 * MARGIN
    BUTTON_HEIGHT = 50
    BUTTON_MARGIN = 20
    WINDOW_HEIGHT = BOARD_SIZE + BUTTON_HEIGHT + BUTTON_MARGIN + MARGIN

    def __init__(self):
        pygame.init()
        self.screen = pygame.display.set_mode(
            (self.BOARD_SIZE, self.WINDOW_HEIGHT)
        )
        pygame.display.set_caption("Puzzle-8")
        self.font = pygame.font.Font(None, 48)
        self.button_font = pygame.font.Font(None, 32)

        self.current_state = GOAL_STATE
        self.is_animating = False
        self.animation_queue: List[PuzzleState] = []
        self.animation_delay = 500  # ms
        self.last_move_time = 0

    def get_tile_rect(self, index: int) -> pygame.Rect:
        """Get rectangle for tile at index."""
        row, col = index // 3, index % 3
        x = self.MARGIN + col * (self.TILE_SIZE + self.MARGIN)
        y = self.MARGIN + row * (self.TILE_SIZE + self.MARGIN)
        return pygame.Rect(x, y, self.TILE_SIZE, self.TILE_SIZE)

    def get_reset_button_rect(self) -> pygame.Rect:
        """Get rectangle for Reset button."""
        y = self.BOARD_SIZE + self.BUTTON_MARGIN
        return pygame.Rect(self.MARGIN, y,
                          self.BOARD_SIZE // 2 - self.MARGIN - 5,
                          self.BUTTON_HEIGHT)

    def get_solve_button_rect(self) -> pygame.Rect:
        """Get rectangle for Solve button."""
        y = self.BOARD_SIZE + self.BUTTON_MARGIN
        x = self.BOARD_SIZE // 2 + 5
        return pygame.Rect(x, y,
                          self.BOARD_SIZE // 2 - self.MARGIN - 5,
                          self.BUTTON_HEIGHT)

    def draw_tile(self, value: int, rect: pygame.Rect) -> None:
        """Draw a single tile."""
        if value == 8:  # Empty space
            pygame.draw.rect(self.screen, Colors.DARK_GRAY, rect)
        else:
            pygame.draw.rect(self.screen, Colors.BLUE, rect)
            pygame.draw.rect(self.screen, Colors.BLACK, rect, 2)
            text = self.font.render(str(value), True, Colors.WHITE)
            text_rect = text.get_rect(center=rect.center)
            self.screen.blit(text, text_rect)

    def draw_button(self, text: str, rect: pygame.Rect, color: tuple) -> None:
        """Draw a button."""
        pygame.draw.rect(self.screen, color, rect)
        pygame.draw.rect(self.screen, Colors.BLACK, rect, 2)
        text_surface = self.button_font.render(text, True, Colors.WHITE)
        text_rect = text_surface.get_rect(center=rect.center)
        self.screen.blit(text_surface, text_rect)

    def draw(self) -> None:
        """Draw the entire GUI."""
        self.screen.fill(Colors.WHITE)

        # Draw tiles
        for i, value in enumerate(self.current_state.state):
            rect = self.get_tile_rect(i)
            self.draw_tile(value, rect)

        # Draw buttons
        reset_rect = self.get_reset_button_rect()
        solve_rect = self.get_solve_button_rect()

        reset_color = Colors.GRAY if self.is_animating else Colors.GREEN
        solve_color = Colors.GRAY if self.is_animating else Colors.RED

        self.draw_button("Reset", reset_rect, reset_color)
        self.draw_button("Solve", solve_rect, solve_color)

        pygame.display.flip()

    def handle_tile_click(self, pos: tuple) -> None:
        """Handle click on a tile."""
        if self.is_animating:
            return

        for i in range(9):
            rect = self.get_tile_rect(i)
            if rect.collidepoint(pos):
                new_state = move_tile(self.current_state, i)
                if new_state:
                    self.current_state = new_state
                break

    def handle_reset_click(self) -> None:
        """Reset puzzle to solved state."""
        if not self.is_animating:
            self.current_state = GOAL_STATE

    def handle_solve_click(self) -> None:
        """Solve the current puzzle."""
        if self.is_animating or is_goal(self.current_state):
            return

        if not is_solvable(self.current_state):
            print("Puzzle is not solvable!")
            return

        solution = solve(self.current_state)
        if solution and len(solution) > 1:
            self.animation_queue = solution[1:]  # Skip current state
            self.is_animating = True
            self.last_move_time = pygame.time.get_ticks()

    def update_animation(self) -> None:
        """Update animation state."""
        if not self.is_animating or not self.animation_queue:
            self.is_animating = False
            return

        current_time = pygame.time.get_ticks()
        if current_time - self.last_move_time >= self.animation_delay:
            self.current_state = self.animation_queue.pop(0)
            self.last_move_time = current_time

    def handle_click(self, pos: tuple) -> None:
        """Handle mouse click."""
        reset_rect = self.get_reset_button_rect()
        solve_rect = self.get_solve_button_rect()

        if reset_rect.collidepoint(pos):
            self.handle_reset_click()
        elif solve_rect.collidepoint(pos):
            self.handle_solve_click()
        else:
            self.handle_tile_click(pos)

    def run(self) -> None:
        """Main GUI loop."""
        clock = pygame.time.Clock()
        running = True

        while running:
            for event in pygame.event.get():
                if event.type == pygame.QUIT:
                    running = False
                elif event.type == pygame.MOUSEBUTTONDOWN:
                    self.handle_click(event.pos)

            self.update_animation()
            self.draw()
            clock.tick(60)

        pygame.quit()


def run_gui() -> None:
    """Start the GUI."""
    gui = PuzzleGUI()
    gui.run()
