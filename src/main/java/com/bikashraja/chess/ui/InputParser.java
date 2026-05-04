package com.bikashraja.chess.ui;

import com.bikashraja.chess.model.Move;
import com.bikashraja.chess.model.Position;

public class InputParser {

    public Move parseMove(String input) {
        if (input == null) {
            throw new IllegalArgumentException("Input must not be null");
        }

        String trimmed = input.trim().toLowerCase();

        if (!trimmed.matches("[a-h][1-8][a-h][1-8]")) {
            throw new IllegalArgumentException("Invalid move format. Use format like e2e4.");
        }

        Position from = parsePosition(trimmed.substring(0, 2));
        Position to = parsePosition(trimmed.substring(2, 4));

        return Move.normal(from, to);
    }

    private Position parsePosition(String text) {
        char file = text.charAt(0);
        char rank = text.charAt(1);

        int col = file - 'a';
        int row = 8 - Character.getNumericValue(rank);

        return new Position(row, col);
    }
}
