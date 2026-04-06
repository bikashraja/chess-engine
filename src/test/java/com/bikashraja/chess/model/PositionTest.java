package com.bikashraja.chess.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class PositionTest {

    @ParameterizedTest
    @CsvSource({
            "7, 0, a1",
            "4, 4, e4",
    })
    void testToAlgebraic(int row, int col, String expected) {
        assertEquals(expected, new Position(row, col).toAlgebraic());
    }

    @ParameterizedTest
    @CsvSource({
            "a1, 7, 0",
            "e4, 4, 4",
            "h8, 0, 7"
    })
    void testFromAlgebraic(String input, int expectedRow, int expectedCol) {
        Position result = Position.fromAlgebraic(input);
        assertNotNull(result);
        assertEquals(expectedRow, result.getRow());
        assertEquals(expectedCol, result.getCol());
    }

    @Test
    void testFromAlgebraic_invalidInput_returnsNull() {
        assertNull(Position.fromAlgebraic("z9"));
        assertNull(Position.fromAlgebraic(null));
        assertNull(Position.fromAlgebraic(""));
    }

    @ParameterizedTest
    @CsvSource({
            "0, 0, true",
            "7, 7, true",
            "3, 4, true",
            "-1, 0, false",
            "0, 8, false",
            "8, 0, false",
            "-1, -1, false"
    })
    void testIsValid(int row, int col, boolean expected) {
        assertEquals(expected, new Position(row, col).isValid());
    }

    @Test
    void testEquals_sameRowCol_returnsTrue() {
        Position a = new Position(3, 4);
        Position b = new Position(3, 4);
        assertEquals(a, b);
    }

    @Test
    void testEquals_differentRowCol_returnsFalse() {
        Position a = new Position(3, 4);
        Position b = new Position(4, 3);
        assertNotEquals(a, b);
    }

    @Test
    void testEquals_null_returnsFalse() {
        Position a = new Position(3, 4);
        assertNotEquals(null, a);
    }

    @Test
    void testHashCode_equalPositions_sameHashCode() {
        Position a = new Position(3, 4);
        Position b = new Position(3, 4);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    void testToString_returnsAlgebraic() {
        assertEquals("e4", new Position(4, 4).toString());
    }
}