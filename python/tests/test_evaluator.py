"""
Comprehensive tests for the RPN evaluator.
"""

import pytest
from reverse_polish.evaluator import (
    evaluate_rpn,
    evaluate_rpn_string,
    tokenize,
    RPNError,
    InvalidTokenError,
    InsufficientOperandsError,
    InvalidExpressionError,
)


class TestBasicOperations:
    """Test basic arithmetic operations."""

    def test_addition(self):
        assert evaluate_rpn(["2", "3", "+"]) == 5

    def test_subtraction(self):
        assert evaluate_rpn(["5", "3", "-"]) == 2

    def test_multiplication(self):
        assert evaluate_rpn(["4", "5", "*"]) == 20

    def test_division(self):
        assert evaluate_rpn(["15", "3", "/"]) == 5

    def test_integer_division_truncates_toward_zero(self):
        # Positive result
        assert evaluate_rpn(["7", "2", "/"]) == 3
        # Negative result - should truncate toward zero
        assert evaluate_rpn(["7", "-2", "/"]) == -3


class TestComplexExpressions:
    """Test more complex RPN expressions."""

    def test_expression_1(self):
        # (2 + 1) * 3 = 9
        assert evaluate_rpn(["2", "1", "+", "3", "*"]) == 9

    def test_expression_2(self):
        # 4 + (13 / 5) = 4 + 2 = 6
        assert evaluate_rpn(["4", "13", "5", "/", "+"]) == 6

    def test_expression_3(self):
        # Complex: 10 * (6 - (9 + 3) * -11) / 1 + 17 + 5
        # Following RPN: ["10", "6", "9", "3", "+", "-11", "*", "/", "*", "17", "+", "5", "+"]
        # Step by step:
        # 9 + 3 = 12
        # 12 * -11 = -132
        # 6 - (-132) = 138  # Note: this should be 6 / -132 based on the RPN
        # Wait, let me recalculate based on actual RPN order
        result = evaluate_rpn(["10", "6", "9", "3", "+", "-11", "*", "/", "*", "17", "+", "5", "+"])
        assert result == 22

    def test_single_number(self):
        assert evaluate_rpn(["42"]) == 42

    def test_negative_numbers(self):
        assert evaluate_rpn(["-5", "3", "+"]) == -2
        assert evaluate_rpn(["5", "-3", "+"]) == 2

    def test_floating_point(self):
        assert evaluate_rpn(["3.5", "2.5", "+"]) == 6.0
        assert evaluate_rpn(["10.5", "2", "/"]) == 5.25


class TestEdgeCases:
    """Test edge cases and boundary conditions."""

    def test_zero_operations(self):
        assert evaluate_rpn(["0", "5", "+"]) == 5
        assert evaluate_rpn(["5", "0", "-"]) == 5
        assert evaluate_rpn(["0", "5", "*"]) == 0
        assert evaluate_rpn(["5", "0", "*"]) == 0

    def test_large_numbers(self):
        assert evaluate_rpn(["1000000", "1000000", "+"]) == 2000000

    def test_many_operations(self):
        # (((1 + 2) + 3) + 4) + 5 = 15
        assert evaluate_rpn(["1", "2", "+", "3", "+", "4", "+", "5", "+"]) == 15


class TestErrors:
    """Test error handling."""

    def test_empty_expression(self):
        with pytest.raises(InvalidExpressionError, match="Empty expression"):
            evaluate_rpn([])

    def test_invalid_token(self):
        with pytest.raises(InvalidTokenError, match="Invalid token"):
            evaluate_rpn(["2", "abc", "+"])

    def test_insufficient_operands(self):
        with pytest.raises(InsufficientOperandsError, match="Insufficient operands"):
            evaluate_rpn(["2", "+"])

    def test_too_many_operands(self):
        with pytest.raises(InvalidExpressionError, match="Invalid expression"):
            evaluate_rpn(["2", "3", "4", "+"])

    def test_division_by_zero(self):
        with pytest.raises(RPNError, match="Division by zero"):
            evaluate_rpn(["5", "0", "/"])

    def test_only_operators(self):
        with pytest.raises(InsufficientOperandsError):
            evaluate_rpn(["+", "-"])

    def test_operator_without_enough_operands(self):
        with pytest.raises(InsufficientOperandsError):
            evaluate_rpn(["1", "+", "+"])


class TestTokenize:
    """Test the tokenize helper function."""

    def test_simple_tokenization(self):
        assert tokenize("2 1 + 3 *") == ["2", "1", "+", "3", "*"]

    def test_extra_spaces(self):
        assert tokenize("2  1   +") == ["2", "1", "+"]

    def test_negative_numbers(self):
        assert tokenize("-5 3 +") == ["-5", "3", "+"]


class TestEvaluateRPNString:
    """Test the string evaluation convenience function."""

    def test_evaluate_string(self):
        assert evaluate_rpn_string("2 1 + 3 *") == 9

    def test_evaluate_complex_string(self):
        assert evaluate_rpn_string("4 13 5 / +") == 6


class TestRealWorldExamples:
    """Test examples from common problem statements."""

    def test_leetcode_example_1(self):
        # ["2","1","+","3","*"] = ((2 + 1) * 3) = 9
        assert evaluate_rpn(["2", "1", "+", "3", "*"]) == 9

    def test_leetcode_example_2(self):
        # ["4","13","5","/","+"] = (4 + (13 / 5)) = 6
        assert evaluate_rpn(["4", "13", "5", "/", "+"]) == 6

    def test_postfix_calculator_example(self):
        # 3 4 + 2 * 7 / = ((3 + 4) * 2) / 7 = 14 / 7 = 2
        assert evaluate_rpn(["3", "4", "+", "2", "*", "7", "/"]) == 2

    def test_wikipedia_example(self):
        # 15 7 1 1 + − ÷ 3 × 2 1 1 + + − = 5
        # In our notation: 15 7 1 1 + - / 3 * 2 1 1 + + -
        # Step by step:
        # 1 + 1 = 2
        # 7 - 2 = 5
        # 15 / 5 = 3
        # 3 * 3 = 9
        # 1 + 1 = 2
        # 2 + 2 = 4
        # 9 - 4 = 5
        assert evaluate_rpn(["15", "7", "1", "1", "+", "-", "/", "3", "*", "2", "1", "1", "+", "+", "-"]) == 5


# Parametrized tests for comprehensive coverage
@pytest.mark.parametrize("tokens,expected", [
    (["1"], 1),
    (["0"], 0),
    (["-1"], -1),
    (["1", "1", "+"], 2),
    (["2", "2", "-"], 0),
    (["3", "3", "*"], 9),
    (["8", "4", "/"], 2),
    (["5", "3", "+", "2", "-"], 6),
    (["10", "2", "/", "3", "+"], 8),
])
def test_parametrized_expressions(tokens, expected):
    """Parametrized tests for various expressions."""
    assert evaluate_rpn(tokens) == expected
