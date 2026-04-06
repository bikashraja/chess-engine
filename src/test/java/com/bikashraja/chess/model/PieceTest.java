package com.bikashraja.chess.model;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class PieceTest {

    @ParameterizedTest
    @CsvSource({
            "PAWN, WHITE, ♙",
            "KING, BLACK, ♚"
    })
    void testGetSymbol(PieceType type, Color color, String expected) {
        assertEquals(expected, new Piece(type, color).getSymbol());
    }

    @ParameterizedTest
    @CsvSource({
            "PAWN, WHITE, P",
            "KING, BLACK, k"
    })
    void testGetAsciiSymbol(PieceType type, Color color, String expected) {
        assertEquals(expected, new Piece(type, color).getAsciiSymbol());
    }
}