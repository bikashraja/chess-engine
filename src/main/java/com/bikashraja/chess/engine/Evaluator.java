package com.bikashraja.chess.engine;

import com.bikashraja.chess.model.Board;
import com.bikashraja.chess.model.Color;
import com.bikashraja.chess.model.Piece;
import com.bikashraja.chess.model.Position;

public class Evaluator {

    /**
     * Evaluates the board from White's perspective.
     *
     * Positive score = White is better.
     * Negative score = Black is better.
     */
    public int evaluate(Board board) {
        int score = 0;

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = board.getPiece(new Position(row, col));

                if (piece == null) {
                    continue;
                }

                int value = piece.getValue();

                score += piece.getColor() == Color.WHITE ? value : -value;
            }
        }

        return score;
    }
}