"""
Reverse Polish Notation (RPN) Evaluator

This module provides functionality to evaluate mathematical expressions
written in Reverse Polish Notation (postfix notation).

Example:
    >>> evaluate_rpn(["2", "1", "+", "3", "*"])
    9
    >>> evaluate_rpn(["4", "13", "5", "/", "+"])
    6
"""

from typing import List, Union


class RPNError(Exception):
    """Base exception for RPN evaluation errors."""
    pass


class InvalidTokenError(RPNError):
    """Raised when an invalid token is encountered."""
    pass


class InsufficientOperandsError(RPNError):
    """Raised when there are not enough operands for an operation."""
    pass


class InvalidExpressionError(RPNError):
    """Raised when the RPN expression is invalid."""
    pass


def evaluate_rpn(tokens: List[str]) -> Union[int, float]:
    """
    Evaluate a Reverse Polish Notation expression.

    In RPN, operators come after their operands. For example:
    - "2 3 +" means 2 + 3 = 5
    - "2 3 + 4 *" means (2 + 3) * 4 = 20

    Args:
        tokens: List of tokens representing the RPN expression.
                Each token is either a number (as string) or an operator (+, -, *, /).

    Returns:
        The result of evaluating the expression.

    Raises:
        InvalidTokenError: If a token is not a valid number or operator.
        InsufficientOperandsError: If there are not enough operands for an operation.
        InvalidExpressionError: If the expression is invalid (e.g., empty or multiple values remain).

    Examples:
        >>> evaluate_rpn(["2", "1", "+", "3", "*"])
        9
        >>> evaluate_rpn(["4", "13", "5", "/", "+"])
        6
        >>> evaluate_rpn(["10", "6", "9", "3", "+", "-11", "*", "/", "*", "17", "+", "5", "+"])
        22
    """
    if not tokens:
        raise InvalidExpressionError("Empty expression")

    stack = []
    operators = {'+', '-', '*', '/'}

    for token in tokens:
        if token in operators:
            # Need at least 2 operands for binary operators
            if len(stack) < 2:
                raise InsufficientOperandsError(
                    f"Insufficient operands for operator '{token}'. "
                    f"Stack has {len(stack)} element(s), need 2."
                )

            # Pop operands (note: right operand is popped first)
            right = stack.pop()
            left = stack.pop()

            # Perform operation
            if token == '+':
                result = left + right
            elif token == '-':
                result = left - right
            elif token == '*':
                result = left * right
            elif token == '/':
                if right == 0:
                    raise RPNError("Division by zero")
                # Use integer division if both operands are integers
                if isinstance(left, int) and isinstance(right, int):
                    result = int(left / right)  # Truncate toward zero
                else:
                    result = left / right

            stack.append(result)
        else:
            # Try to parse as a number
            try:
                # Try integer first
                if '.' not in token:
                    num = int(token)
                else:
                    num = float(token)
                stack.append(num)
            except ValueError:
                raise InvalidTokenError(f"Invalid token: '{token}'")

    # At the end, there should be exactly one value on the stack
    if len(stack) != 1:
        raise InvalidExpressionError(
            f"Invalid expression: {len(stack)} values remain on stack. "
            f"Expected 1 value."
        )

    return stack[0]


def tokenize(expression: str) -> List[str]:
    """
    Tokenize a space-separated RPN expression string.

    Args:
        expression: A string containing space-separated tokens.

    Returns:
        List of tokens.

    Examples:
        >>> tokenize("2 1 + 3 *")
        ['2', '1', '+', '3', '*']
    """
    return expression.split()


def evaluate_rpn_string(expression: str) -> Union[int, float]:
    """
    Evaluate an RPN expression given as a space-separated string.

    Args:
        expression: Space-separated RPN expression.

    Returns:
        The result of evaluating the expression.

    Examples:
        >>> evaluate_rpn_string("2 1 + 3 *")
        9
    """
    tokens = tokenize(expression)
    return evaluate_rpn(tokens)
