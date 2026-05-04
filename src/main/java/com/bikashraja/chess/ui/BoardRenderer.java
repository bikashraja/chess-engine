package com.bikashraja.chess.ui;

import com.bikashraja.chess.model.Board;
import com.bikashraja.chess.model.Piece;
import com.bikashraja.chess.model.Position;

public class BoardRenderer {

    public void render(Board board) {
        for (int row = 0; row < 8; row++) {
            int rank = 8 - row;
            System.out.print(rank + " ");

            for (int col = 0; col < 8; col++) {
                Position pos = new Position(row, col);
                Piece piece = board.getPiece(pos);

                if (piece == null) {
                    System.out.print(". ");
                } else {
                    System.out.print(piece.getSymbol() + " ");
                }
            }

            System.out.println();
        }

        System.out.println("  a b c d e f g h");
    }
}
