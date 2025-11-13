# Daily Coding Problem - Python Solutions

This repository contains Python solutions to Daily Coding Problems.

## Project Structure

- `reverse-polish/` - Reverse Polish Notation evaluator
- `puzzle-8/` - 8-puzzle solver

## Setup

### Create Virtual Environment (Recommended)

```bash
python3 -m venv .venv
source .venv/bin/activate  # On Windows: .venv\Scripts\activate
```

### Install Dependencies

```bash
pip install -r requirements-dev.txt
```

**Note:** If using VS Code, the virtual environment will be automatically detected and used for testing.

## Running Tests

### Command Line

Run all tests:
```bash
pytest
```

Run tests with coverage:
```bash
pytest --cov
```

Run tests for a specific module:
```bash
pytest reverse-polish/
```

Run a specific test file:
```bash
pytest reverse-polish/test_reverse_polish.py
```

Run a specific test:
```bash
pytest reverse-polish/test_reverse_polish.py::TestReversePolish::test_simple_addition
```

### VS Code

The project is configured for easy testing in VS Code:

1. **Install the Python extension** (if not already installed)
2. **Open the Testing sidebar** (flask icon in the left sidebar)
3. **Tests will be automatically discovered** and displayed in a tree view
4. **Run tests** by clicking the play button next to any test, test class, or test file
5. **Debug tests** by right-clicking and selecting "Debug Test"
6. **View test results** inline in the editor

### Test Configuration

Tests are configured via:
- `pyproject.toml` - pytest configuration and project metadata
- `.vscode/settings.json` - VS Code test discovery and execution settings
- `.vscode/launch.json` - Debug configurations

## Project Layout

```
python/
├── pyproject.toml          # Project configuration and pytest settings
├── requirements-dev.txt    # Development dependencies
├── .vscode/
│   ├── settings.json       # VS Code Python and test settings
│   └── launch.json         # Debug configurations
├── reverse-polish/
│   ├── reverse_polish.py
│   └── test_reverse_polish.py
└── puzzle-8/
    ├── models.py
    ├── operations.py
    ├── solver.py
    ├── cli.py
    ├── gui.py
    └── __main__.py
```

## Best Practices

- **Test file naming**: Prefix test files with `test_` (e.g., `test_reverse_polish.py`)
- **Test class naming**: Prefix test classes with `Test` (e.g., `TestReversePolish`)
- **Test function naming**: Prefix test functions with `test_` (e.g., `test_simple_addition`)
- **Docstrings**: Add descriptive docstrings to all tests explaining what they test
- **Organize tests**: Group related tests into classes
- **Edge cases**: Always test edge cases, invalid inputs, and error conditions
