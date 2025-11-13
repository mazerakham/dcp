"""
Tests for the Reverse Polish Notation evaluator.
"""

import pytest
from reverse_polish import eval_reverse_polish, is_operator, is_number, operate


class TestHelperFunctions:
    """Test the helper functions."""

    def test_is_operator_valid_operators(self):
        """Test that valid operators are recognized."""
        assert is_operator('+')
        assert is_operator('-')
        assert is_operator('*')
        assert is_operator('/')

    def test_is_operator_invalid(self):
        """Test that non-operators are not recognized."""
        assert not is_operator('a')
        assert not is_operator('5')
        assert not is_operator('')
        assert not is_operator(5)

    def test_is_number_integers(self):
        """Test that integers are recognized as numbers."""
        assert is_number(0)
        assert is_number(5)
        assert is_number(-10)
        assert is_number(1000)

    def test_is_number_floats(self):
        """Test that floats are recognized as numbers."""
        assert is_number(3.14)
        assert is_number(-2.5)
        assert is_number(0.0)

    def test_is_number_invalid(self):
        """Test that non-numbers are not recognized."""
        assert not is_number('+')
        assert not is_number('5')
        assert not is_number(None)

    def test_operate_addition(self):
        """Test addition operation."""
        assert operate('+', 2, 3) == 5
        assert operate('+', -1, 1) == 0
        assert operate('+', 0, 0) == 0

    def test_operate_subtraction(self):
        """Test subtraction operation."""
        assert operate('-', 5, 3) == 2
        assert operate('-', 3, 5) == -2
        assert operate('-', 10, 10) == 0

    def test_operate_multiplication(self):
        """Test multiplication operation."""
        assert operate('*', 2, 3) == 6
        assert operate('*', -2, 3) == -6
        assert operate('*', 0, 100) == 0

    def test_operate_division(self):
        """Test division operation."""
        assert operate('/', 6, 3) == 2
        assert operate('/', 7, 2) == 3.5
        assert operate('/', -10, 2) == -5

    def test_operate_unknown_operator(self):
        """Test that unknown operators raise ValueError."""
        with pytest.raises(ValueError, match="unknown operator"):
            operate('%', 5, 2)


class TestReversePolish:
    """Test the main reverse polish evaluator."""

    def test_simple_addition(self):
        """Test simple addition: 5 + 3 = 8."""
        assert eval_reverse_polish([5, 3, '+']) == 8

    def test_simple_subtraction(self):
        """Test simple subtraction: 2 - 6 = -4."""
        assert eval_reverse_polish([2, 6, '-']) == -4

    def test_simple_multiplication(self):
        """Test simple multiplication: 4 * 3 = 12."""
        assert eval_reverse_polish([4, 3, '*']) == 12

    def test_simple_division(self):
        """Test simple division: 10 / 2 = 5."""
        assert eval_reverse_polish([10, 2, '/']) == 5

    def test_multiple_operations(self):
        """Test expression with multiple operations: 5 + 1 + 2 * 4 - 3."""
        # 5 1 2 + 4 * + 3 -
        # = 5 + ((1 + 2) * 4) - 3
        # = 5 + (3 * 4) - 3
        # = 5 + 12 - 3
        # = 14
        result = eval_reverse_polish([5, 1, 2, '+', 4, '*', '+', 3, '-'])
        assert result == 14

    def test_jane_street_example(self):
        """Test the example from the Jane Street problem."""
        # [15, 7, 1, 1, '+', '-', '/', 3, '*', 2, 1, 1, '+', '+', '-']
        # = ((15 / (7 - (1 + 1))) * 3) - (2 + (1 + 1))
        # = ((15 / (7 - 2)) * 3) - (2 + 2)
        # = ((15 / 5) * 3) - 4
        # = (3 * 3) - 4
        # = 9 - 4
        # = 5
        expr = [15, 7, 1, 1, '+', '-', '/', 3, '*', 2, 1, 1, '+', '+', '-']
        result = eval_reverse_polish(expr)
        assert result == 5

    def test_single_number(self):
        """Test expression with a single number."""
        assert eval_reverse_polish([42]) == 42
        assert eval_reverse_polish([0]) == 0
        assert eval_reverse_polish([-5]) == -5

    def test_floating_point(self):
        """Test with floating point numbers."""
        result = eval_reverse_polish([3.5, 2.5, '+'])
        assert result == 6.0

        result = eval_reverse_polish([10, 4, '/'])
        assert result == 2.5

    def test_negative_numbers(self):
        """Test with negative numbers."""
        assert eval_reverse_polish([-5, 3, '+']) == -2
        assert eval_reverse_polish([5, -3, '+']) == 2
        assert eval_reverse_polish([-5, -3, '*']) == 15

    def test_zero_in_operations(self):
        """Test operations involving zero."""
        assert eval_reverse_polish([0, 5, '+']) == 5
        assert eval_reverse_polish([5, 0, '-']) == 5
        assert eval_reverse_polish([5, 0, '*']) == 0

    def test_complex_nested_expression(self):
        """Test a complex nested expression."""
        # ((2 + 3) * (4 - 1)) / 5
        # = (5 * 3) / 5
        # = 15 / 5
        # = 3
        result = eval_reverse_polish([2, 3, '+', 4, 1, '-', '*', 5, '/'])
        assert result == 3

    def test_left_associative_subtraction(self):
        """Test that subtraction is evaluated correctly (left associative)."""
        # 10 - 5 - 2 should be (10 - 5) - 2 = 3, not 10 - (5 - 2) = 7
        # In RPN: 10 5 - 2 -
        assert eval_reverse_polish([10, 5, '-', 2, '-']) == 3

    def test_left_associative_division(self):
        """Test that division is evaluated correctly (left associative)."""
        # 20 / 4 / 2 should be (20 / 4) / 2 = 2.5, not 20 / (4 / 2) = 10
        # In RPN: 20 4 / 2 /
        assert eval_reverse_polish([20, 4, '/', 2, '/']) == 2.5

    def test_invalid_expression_not_enough_operands(self):
        """Test that invalid expressions raise ValueError."""
        with pytest.raises(ValueError, match="not enough operands"):
            eval_reverse_polish([5, '+'])

    def test_invalid_expression_too_many_operands(self):
        """Test that expressions with too many operands raise ValueError."""
        with pytest.raises(ValueError, match="does not contain exactly one value"):
            eval_reverse_polish([5, 3, 2, '+'])

    def test_invalid_expression_unknown_symbol(self):
        """Test that unknown symbols raise ValueError."""
        with pytest.raises(ValueError, match="Invalid symbol"):
            eval_reverse_polish([5, 3, 'x'])

    def test_empty_expression(self):
        """Test that an empty expression raises ValueError."""
        with pytest.raises(ValueError, match="does not contain exactly one value"):
            eval_reverse_polish([])
