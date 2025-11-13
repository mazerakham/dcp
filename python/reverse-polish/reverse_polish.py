"""
This problem was asked by Jane Street.

Given an arithmetic expression in Reverse Polish Notation, write a program to evaluate it.

The expression is given as a list of numbers and operands. For example: [5, 3, '+']
should return 5 + 3 = 8.

For example, [15, 7, 1, 1, '+', '-', '/', 3, '*', 2, 1, 1, '+', '+', '-'] should return 5,
since it is equivalent to ((15 / (7 - (1 + 1))) * 3) - (2 + (1 + 1)) = 5.

You can assume the given expression is always valid.
"""


def is_operator(symbol):
    """Check if the symbol is a valid operator."""
    return symbol in ['+', '-', '*', '/']


def is_number(symbol):
    """Check if the symbol is a number."""
    return isinstance(symbol, (int, float))


def operate(operator, left, right):
    """
    Perform the operation on two operands.

    Args:
        operator: The operator symbol ('+', '-', '*', '/')
        left: The left operand
        right: The right operand

    Returns:
        The result of the operation

    Raises:
        ValueError: If an unknown operator is encountered
    """
    if operator == '+':
        return left + right
    elif operator == '-':
        return left - right
    elif operator == '*':
        return left * right
    elif operator == '/':
        return left / right
    else:
        raise ValueError(f"Encountered unknown operator: {operator}")


def eval_reverse_polish(expr):
    """
    Evaluate an arithmetic expression in Reverse Polish Notation.

    In RPN, operators follow their operands. For example:
    - [2, 3, '+'] means 2 + 3 = 5
    - [5, 1, 2, '+', 4, '*', '+', 3, '-'] means 5 + ((1 + 2) * 4) - 3 = 14

    Args:
        expr: A list containing numbers and operator symbols

    Returns:
        The result of evaluating the expression

    Raises:
        ValueError: If the expression is invalid
    """
    # Maintain a stack of numbers
    stack = []

    # Process each symbol in the expression
    for symbol in expr:
        if is_operator(symbol):
            # Pop two operands (note: order matters for - and /)
            if len(stack) < 2:
                raise ValueError("Invalid expression: not enough operands for operator")

            right = stack.pop()
            left = stack.pop()
            result = operate(symbol, left, right)
            stack.append(result)
        elif is_number(symbol):
            # Push numbers onto the stack
            stack.append(symbol)
        else:
            raise ValueError(f"Invalid symbol in expression: {symbol}")

    # At the end, there should be exactly one value on the stack
    if len(stack) != 1:
        raise ValueError("Invalid expression: stack does not contain exactly one value")

    return stack[0]
