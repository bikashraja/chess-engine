package com.bikashraja.chess.ui;

import com.bikashraja.chess.model.Move;
import com.bikashraja.chess.model.Position;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InputParserTest {

    @Test
    void parseMove_parsesCoordinateMove() {
        InputParser parser = new InputParser();

        Move move = parser.parseMove("e2e4");

        assertEquals(new Position(6, 4), move.getFrom());
        assertEquals(new Position(4, 4), move.getTo());
    }

}