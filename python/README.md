# Reverse Polish Notation (RPN) Calculator

A complete, well-tested Python implementation of a Reverse Polish Notation evaluator.

## What is Reverse Polish Notation?

Reverse Polish Notation (RPN), also known as postfix notation, is a mathematical notation where operators follow their operands. This eliminates the need for parentheses to indicate order of operations.

### Examples

| Infix Notation | RPN Notation | Result |
|----------------|--------------|--------|
| `2 + 3` | `2 3 +` | `5` |
| `(2 + 3) * 4` | `2 3 + 4 *` | `20` |
| `(4 + 13 / 5)` | `4 13 5 / +` | `6` |

## Installation

1. **Create a virtual environment** (recommended):
   ```bash
   cd python
   python -m venv venv
   source venv/bin/activate  # On Windows: venv\Scripts\activate
   ```

2. **Install dependencies**:
   ```bash
   pip install -r requirements.txt
   ```

## Usage

### Basic Usage

```python
from reverse_polish import evaluate_rpn

# Evaluate an RPN expression
result = evaluate_rpn(["2", "1", "+", "3", "*"])
print(result)  # Output: 9

# Using the string evaluator
from reverse_polish.evaluator import evaluate_rpn_string

result = evaluate_rpn_string("4 13 5 / +")
print(result)  # Output: 6
```

### Supported Operations

- Addition: `+`
- Subtraction: `-`
- Multiplication: `*`
- Division: `/` (truncates toward zero for integers)

### Error Handling

The evaluator provides detailed error messages:

```python
from reverse_polish import evaluate_rpn
from reverse_polish.evaluator import InvalidTokenError, InsufficientOperandsError

try:
    evaluate_rpn(["2", "+"])  # Not enough operands
except InsufficientOperandsError as e:
    print(f"Error: {e}")

try:
    evaluate_rpn(["2", "abc", "+"])  # Invalid token
except InvalidTokenError as e:
    print(f"Error: {e}")
```

## Running Tests

This project uses pytest with comprehensive test coverage.

### Quick Test Run

```bash
cd python
pytest
```

### Run Tests with Coverage

```bash
pytest --cov=reverse_polish --cov-report=term-missing --cov-report=html
```

View the HTML coverage report:
```bash
open htmlcov/index.html  # On macOS
# or
xdg-open htmlcov/index.html  # On Linux
```

### Run Specific Tests

```bash
# Run a specific test file
pytest tests/test_evaluator.py

# Run a specific test class
pytest tests/test_evaluator.py::TestBasicOperations

# Run a specific test
pytest tests/test_evaluator.py::TestBasicOperations::test_addition

# Run tests matching a pattern
pytest -k "addition"
```

### Run Tests in Parallel

```bash
pytest -n auto  # Uses all available CPU cores
```

## VS Code Integration

This project is configured for seamless testing in VS Code:

### Test Explorer

1. Install the **Python extension** for VS Code
2. Open the project in VS Code
3. The Test Explorer will automatically discover all tests
4. Click the flask icon in the sidebar to see all tests
5. Run individual tests or entire suites with one click

### Keyboard Shortcuts

- **Run All Tests**: `Ctrl+Shift+P` → "Python: Run All Tests"
- **Debug Tests**: Set breakpoints and click "Debug Test" in the Test Explorer

### Tasks

Run predefined tasks via `Ctrl+Shift+P` → "Tasks: Run Task":

- **Run All Python Tests** (Default test task)
- **Run Python Tests with Coverage**
- **Format Python Code (Black)**
- **Lint Python Code (Flake8)**
- **Type Check (mypy)**

## Project Structure

```
python/
├── reverse_polish/          # Main package
│   ├── __init__.py         # Package initialization
│   └── evaluator.py        # RPN evaluator implementation
├── tests/                   # Test suite
│   ├── __init__.py
│   └── test_evaluator.py   # Comprehensive tests
├── pytest.ini              # Pytest configuration
├── requirements.txt        # Project dependencies
├── requirements-dev.txt    # Development dependencies
└── README.md              # This file
```

## Development

### Code Quality Tools

This project uses several tools to maintain code quality:

- **Black**: Code formatting
- **Flake8**: Linting
- **mypy**: Static type checking
- **pytest**: Testing framework
- **pytest-cov**: Coverage reporting

### Running Quality Checks

```bash
# Format code
black .

# Lint code
flake8 reverse_polish tests

# Type check
mypy reverse_polish

# Run all tests
pytest -v
```

### Pre-commit Checklist

Before committing code:
1. ✅ Format with Black: `black .`
2. ✅ Run linter: `flake8 reverse_polish tests`
3. ✅ Run tests: `pytest -v`
4. ✅ Check coverage: `pytest --cov=reverse_polish`

## Algorithm Explanation

The RPN evaluator uses a stack-based algorithm:

1. **Initialize** an empty stack
2. **For each token** in the expression:
   - If it's a **number**: push it onto the stack
   - If it's an **operator**:
     - Pop the top two values from the stack
     - Apply the operation
     - Push the result back onto the stack
3. **Return** the final value on the stack

### Example Walkthrough

Evaluate `["2", "1", "+", "3", "*"]`:

```
Token | Stack      | Action
------|------------|------------------
2     | [2]        | Push 2
1     | [2, 1]     | Push 1
+     | [3]        | Pop 1, pop 2, push 2+1=3
3     | [3, 3]     | Push 3
*     | [9]        | Pop 3, pop 3, push 3*3=9
Result: 9
```

## License

This is a practice project for educational purposes.

## Contributing

This is a personal practice project, but suggestions are welcome!
