"""Puzzle-8 entry point."""

import sys
from cli import run_cli
from gui import run_gui


def main() -> None:
    """Main entry point."""
    if len(sys.argv) > 1 and sys.argv[1] == "--cli":
        run_cli()
    else:
        run_gui()


if __name__ == "__main__":
    main()
